package com.nora.rpgsp.item;

import com.nora.rpgsp.Rpgsp;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.UUID;

public class ItemInit {
    public static final HashMap<String, Item> entryMap = new HashMap<>();
    public static ItemGroup RPGSP;
    public static Item EMERALDRING;
    public static Item AMETHYSTRING;
    public static Item DIAMONDRING;
    public static Item UNSETRING;

    public static Item OPALRING;
    public static Item EMERALDAMULET;
    public static Item AMETHYSTAMULET;
    public static Item OPALAMULET;

    public static Item DIAMONDAMULET;
    public static Item SKILLPOWER;

    public static RegistryKey<ItemGroup> KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(),new Identifier(Rpgsp.MOD_ID,"generic"));
    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, new Identifier(Rpgsp.MOD_ID,name),item);
    }
    private static void addItemToGroup(Item item){
        ItemGroupEvents.modifyEntriesEvent(KEY).register((content) -> {
            content.add(item);
        });
    }
    public static void register() {
        EMERALDRING = registerItem("emeraldring", new EmeraldRing(new FabricItemSettings().maxCount(1), 40,new EntityAttributeModifier(UUID.randomUUID(), "rpgsp:skillpoint", 50, EntityAttributeModifier.Operation.ADDITION)));
        entryMap.put("rpgsp:emeraldring",EMERALDRING);
        OPALRING = registerItem("opalring", new opalRing(new FabricItemSettings().maxCount(1), 0.4F,new EntityAttributeModifier(UUID.randomUUID(), "rpgsp:skillpointregen", 0.4F, EntityAttributeModifier.Operation.MULTIPLY_BASE)));
        entryMap.put("rpgsp:opalring",OPALRING);

        AMETHYSTRING = registerItem("amethystring", new AmethystRing(new FabricItemSettings().maxCount(1), -0.1F,new EntityAttributeModifier(UUID.randomUUID(), "rpgsp:skillpointcost", -0.1F, EntityAttributeModifier.Operation.MULTIPLY_BASE)));
        entryMap.put("rpgsp:amethystring",AMETHYSTRING);

        DIAMONDRING = registerItem("diamondring", new AmethystRing(new FabricItemSettings().maxCount(1), 0.2F,new EntityAttributeModifier(UUID.randomUUID(), "rpgsp:skillpointcost", 0.2F, EntityAttributeModifier.Operation.MULTIPLY_BASE)));
        entryMap.put("rpgsp:diamondring",DIAMONDRING);

        EMERALDAMULET = registerItem("emerald_awakened", new EmeraldRing(new FabricItemSettings().maxCount(1), 80, new EntityAttributeModifier(UUID.randomUUID(), "rpgsp:skillpoint", 100, EntityAttributeModifier.Operation.ADDITION)));
        entryMap.put("rpgsp:emerald_awakened",EMERALDAMULET);
        OPALAMULET = registerItem("opal_awakened", new opalRing(new FabricItemSettings().maxCount(1), 0.8F,new EntityAttributeModifier(UUID.randomUUID(), "rpgsp:skillpointregen", 0.8F, EntityAttributeModifier.Operation.MULTIPLY_BASE)));
        entryMap.put("rpgsp:opal_awakened",OPALAMULET);

        AMETHYSTAMULET = registerItem("amethyst_awakened", new AmethystRing(new FabricItemSettings().maxCount(1), -0.2F,new EntityAttributeModifier(UUID.randomUUID(), "rpgsp:skillpointcost", -0.2F, EntityAttributeModifier.Operation.MULTIPLY_BASE)));
        entryMap.put("rpgsp:amethyst_awakened",AMETHYSTAMULET);
        DIAMONDAMULET = registerItem("diamond_awakened", new AmethystRing(new FabricItemSettings().maxCount(1), 0.4F,new EntityAttributeModifier(UUID.randomUUID(), "rpgsp:skillpointcost", 0.4F, EntityAttributeModifier.Operation.MULTIPLY_BASE)));
        entryMap.put("rpgsp:diamond_awakened",DIAMONDAMULET);
        UNSETRING = registerItem("unsetring", new EmeraldRing(new FabricItemSettings().maxCount(1),20F,new EntityAttributeModifier(UUID.randomUUID(), "rpgsp:skillpoint", 20, EntityAttributeModifier.Operation.ADDITION)));
        entryMap.put("rpgsp:unsetring",UNSETRING);
        SKILLPOWER = registerItem("skillpower", new Item(new FabricItemSettings()));

        RPGSP = FabricItemGroup.builder()
                .icon(() -> new ItemStack(SKILLPOWER))
                .displayName(Text.translatable("itemGroup.rpgsp.general"))
                .build();
        Registry.register(Registries.ITEM_GROUP, KEY, RPGSP);
        addItemToGroup(UNSETRING);
        addItemToGroup(EMERALDRING);
        addItemToGroup(AMETHYSTRING);
        addItemToGroup(OPALRING);
        addItemToGroup(DIAMONDRING);
        addItemToGroup(EMERALDAMULET);
        addItemToGroup(AMETHYSTAMULET);
        addItemToGroup(OPALAMULET);
        addItemToGroup(DIAMONDAMULET);




    }
}
