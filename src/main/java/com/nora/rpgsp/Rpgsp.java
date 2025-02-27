package com.nora.rpgsp;

import com.nora.rpgsp.api.SkillpowerInstance;
import com.nora.rpgsp.api.SkillpowerInterface;
import com.nora.rpgsp.api.SpellcostMixinInterface;
import com.nora.rpgsp.config.ClientConfig;
import com.nora.rpgsp.config.ClientConfigWrapper;
import com.nora.rpgsp.config.ServerConfig;
import com.nora.rpgsp.config.ServerConfigWrapper;

import com.nora.rpgsp.effect.SpRegeneraionIndicator;
import com.nora.rpgsp.item.ItemInit;
import com.nora.rpgsp.loot.Default;
import com.nora.rpgsp.loot.LootConfig;
import com.nora.rpgsp.loot.LootHelper;
import com.nora.rpgsp.network.ConfigSync;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.internals.SpellHelper;
import net.spell_engine.internals.casting.SpellCast;
import net.spell_engine.internals.casting.SpellCasterEntity;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchool;
import net.spell_power.api.SpellSchools;
import net.spell_power.api.enchantment.SpellPowerEnchanting;
import net.tinyconfig.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static net.spell_engine.internals.SpellHelper.*;

public class Rpgsp implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("rpgsp");
	public static final String MOD_ID = "rpgsp";

	public static ConfigManager<LootConfig> lootConfig = new ConfigManager<>
			("loot_v2", Default.loot)
			.builder()
			.setDirectory(MOD_ID)
			.sanitize(true)
			.build();
	public static StatusEffect SPREGENERATIONINDICATOR;

	public static ServerConfig config;
	private static PacketByteBuf configSerialized = PacketByteBufs.create();
	public static ClientConfig clientConfig	;

	public static final ClampedEntityAttribute SKILLPOWER = new ClampedEntityAttribute("attribute.name.rpgsp.skillpower", 0,0,999999);
	public static final ClampedEntityAttribute SKILLPOWERREGEN = new ClampedEntityAttribute("attribute.name.rpgsp.skillpowerregen", 4,-999999,999999);
	public static final ClampedEntityAttribute SKILLPOWERCOST = new ClampedEntityAttribute("attribute.name.rpgsp.skillpowercost", 100,0,999999);
	public static final ClampedEntityAttribute SPREGENPERATTACK = new ClampedEntityAttribute("attribute.name.rpgsp.spregenperattack", 0,-999999,999999);

	private static float getSkillpowerCost(PlayerEntity player, Spell spell, SpellCast.Action action, float progress){
		float channelMultiplier = 1.0F;

		SpellHelper.ImpactContext context = new SpellHelper.ImpactContext(channelMultiplier, 1.0F, (Vec3d)null, new SpellPower.Result(SpellSchools.ARCANE,0,0,0), impactTargetingMode(spell));
		float coeff = 0;
		int proj = 1;
		for(Spell.Impact impact : spell.impact){
			if(impact.action != null && impact.action.damage != null) {
				coeff += impact.action.damage.spell_power_coefficient;
			}
		}
		if(spell.impact.length > 0){
			coeff /= spell.impact.length;
		}
		if(spell.release.target.projectile != null) {
			proj += spell.release.target.projectile.launch_properties.extra_launch_count;
		}
		return ((SpellcostMixinInterface)spell.cost).calculateSkillpowerCost() ? (float) Math.max( 20 * context.total(), (1 + 0.05 *40 * context.total() * coeff * proj * ((float) player.getAttributeValue(Rpgsp.SKILLPOWERCOST) * 0.01F))) :
				((SpellcostMixinInterface)spell.cost).getSkillpowerCost();
	}

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		SPREGENERATIONINDICATOR = new SpRegeneraionIndicator(StatusEffectCategory.BENEFICIAL, 9154528)
				.addAttributeModifier(Rpgsp.SKILLPOWERREGEN,"3a33dd71-5941-5bb8-79ab-3deb43a17927",20,EntityAttributeModifier.Operation.ADDITION);
		Registry.register(Registries.STATUS_EFFECT,new Identifier(MOD_ID,"sp_regeneraion_indicator"),SPREGENERATIONINDICATOR);

		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			if (!player.getWorld().isClient) {
			player.addStatusEffect(new StatusEffectInstance(SPREGENERATIONINDICATOR, 1, (int) player.getAttributeValue(Rpgsp.SPREGENPERATTACK), false, false), player);
			}
			return ActionResult.PASS;
		});

		lootConfig.refresh();
		AutoConfig.register(ServerConfigWrapper.class, PartitioningSerializer.wrap(JanksonConfigSerializer::new));
		AutoConfig.register(ClientConfigWrapper.class, PartitioningSerializer.wrap(JanksonConfigSerializer::new));
		config = AutoConfig.getConfigHolder(ServerConfigWrapper.class).getConfig().server;
		clientConfig = AutoConfig.getConfigHolder(ClientConfigWrapper.class).getConfig().client;
		configSerialized = ConfigSync.write(config);
		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			sender.sendPacket(ConfigSync.ID, configSerialized);
		});

		Registry.register(Registries.ATTRIBUTE,new Identifier(MOD_ID,"skillpower"),SKILLPOWER);
		Registry.register(Registries.ATTRIBUTE,new Identifier(MOD_ID,"skillpowerregen"),SKILLPOWERREGEN);
		Registry.register(Registries.ATTRIBUTE,new Identifier(MOD_ID,"skillpowercost"),SKILLPOWERCOST);
		Registry.register(Registries.ATTRIBUTE,new Identifier(MOD_ID,"spregenperattack"),SPREGENPERATTACK);

		for(SpellSchool school : SpellSchools.all()) {
			school.addSource(SpellSchool.Trait.POWER, SpellSchool.Apply.MULTIPLY, queryArgs -> {
				if (queryArgs.entity() instanceof PlayerEntity player && player instanceof SpellCasterEntity casterEntity && player instanceof SkillpowerInterface skillpowerInterface && skillpowerInterface.getSkillpower() > 0.1) {
					List<SkillpowerInstance> list = skillpowerInterface.getSkillpowerInstances();
					double amount = 0;
					for(SkillpowerInstance instance : list){
						amount += instance.value;
					}
					return amount*0.01/4;
				} else {
					return 0D;
				}
			});
		}

		SKILLPOWER.setTracked(true);
		SKILLPOWERREGEN.setTracked(true);
		SKILLPOWERCOST.setTracked(true);
		SPREGENPERATTACK.setTracked(true);

		ItemInit.register();
		LOGGER.info("Loading RPSP");
		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			LootHelper.configure(id, tableBuilder, lootConfig.value, ItemInit.entryMap);
		});

	}

}