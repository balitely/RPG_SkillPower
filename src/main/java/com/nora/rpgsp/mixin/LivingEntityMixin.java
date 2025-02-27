package com.nora.rpgsp.mixin;

import com.nora.rpgsp.Rpgsp;
import com.nora.rpgsp.api.SkillpowerInstance;
import com.nora.rpgsp.api.SkillpowerInterface;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.spell_engine.api.spell.Spell;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(PlayerEntity.class)
public class LivingEntityMixin implements SkillpowerInterface {

	private static final TrackedData<Float> CURRENTSKILLPOWER;

	public List<SkillpowerInstance> skillpowerInstances = new ArrayList<SkillpowerInstance>(List.of());
public int timefull = 0;

	static{
		CURRENTSKILLPOWER = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.FLOAT);

	}

	@Override
	public double getMaxSkillpower() {
		PlayerEntity player = (PlayerEntity) (Object) this;
		return Rpgsp.config.baseskillpower+ Rpgsp.config.skillpower*(player.getAttributeValue(Rpgsp.SKILLPOWER));
	}
	public double getSkillpowerRegen(){
		PlayerEntity living = (PlayerEntity) (Object) this;
		return Rpgsp.config.skillpowerregen*((1+living.getAttributeValue(Rpgsp.SKILLPOWERREGEN)/20));
	}

	@Override
	public List<SkillpowerInstance> getSkillpowerInstances() {
		return this.skillpowerInstances;
	}
	@Inject(at = @At("HEAD"), method = "tick")
	public void tickSkillpower(CallbackInfo callbackInfo) {
		LivingEntity living = (LivingEntity) (Object) this;

		if(living.getDataTracker().get(CURRENTSKILLPOWER)< getMaxSkillpower()){
			living.getDataTracker().set(CURRENTSKILLPOWER,(float)Math.min(living.getDataTracker().get(CURRENTSKILLPOWER)+getSkillpowerRegen(),getMaxSkillpower()));
			timefull =0;
		}
		else{
			living.getDataTracker().set(CURRENTSKILLPOWER,(float)getMaxSkillpower());
			timefull++;
		}
		for(SkillpowerInstance instance : this.getSkillpowerInstances()){
			instance.tick();
		}
		if(!this.getSkillpowerInstances().isEmpty()) {
			this.getSkillpowerInstances().removeIf(skillpowerInstance -> skillpowerInstance.remainingduration <= 0);
		}
	}
	@Inject(at = @At("TAIL"), method = "initDataTracker")
	protected void initDataTrackerSkillpower(CallbackInfo callbackInfo) {
		LivingEntity living = (LivingEntity) (Object) this;
		living.getDataTracker().startTracking(CURRENTSKILLPOWER, 0F);
	}
		@Inject(method = "createPlayerAttributes", at = @At("RETURN"))
	private static void addAttributesextraspellattributes_RETURN(final CallbackInfoReturnable<DefaultAttributeContainer.Builder> info) {
		info.getReturnValue().add(Rpgsp.SKILLPOWER);
			info.getReturnValue().add(Rpgsp.SKILLPOWERREGEN);
			info.getReturnValue().add(Rpgsp.SKILLPOWERCOST);
			info.getReturnValue().add(Rpgsp.SPREGENPERATTACK);
	}
	public @Nullable Spell lastSpell;

	public Spell getLastSpell() {
		return lastSpell;
	}

	public void setLastSpell(Spell lastSpell) {
		this.lastSpell = lastSpell;
	}
	@Override
	public double getSkillpower() {
		LivingEntity living = (LivingEntity) (Object) this;
		return living.getDataTracker().get(CURRENTSKILLPOWER);
	}

	@Override
	public double spendSkillpower(double toadd) {
		LivingEntity living = (LivingEntity) (Object) this;
		if(living instanceof PlayerEntity player) {
			this.getSkillpowerInstances().add(new SkillpowerInstance(player, 80, -toadd));
		}
		living.getDataTracker().set(CURRENTSKILLPOWER,(float)(living.getDataTracker().get(CURRENTSKILLPOWER)+toadd));
		return living.getDataTracker().get(CURRENTSKILLPOWER);
	}

	@Override
	public int getTimeFull() {
		return timefull;
	}
}