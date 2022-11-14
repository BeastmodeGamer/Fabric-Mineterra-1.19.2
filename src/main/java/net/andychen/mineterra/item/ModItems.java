package net.andychen.mineterra.item;

import net.andychen.mineterra.MineTerra;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    // COPPER
    public static final Item COPPER_ORE = registerItem("copper_ore",
            new Item(new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item COPPER_INGOT = registerItem("copper_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.MINETERRA)));

    public static final Item COPPER_SWORD = registerItem("copper_sword",
            new SwordItem(ModToolMaterial.COPPER, 3, -2.4f, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item COPPER_PICKAXE = registerItem("copper_pickaxe",
            new PickaxeItem(ModToolMaterial.COPPER, 1, -2.8f, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item COPPER_AXE = registerItem("copper_axe",
            new AxeItem(ModToolMaterial.COPPER, 5, -3.0f, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item COPPER_SHOVEL = registerItem("copper_shovel",
            new ShovelItem(ModToolMaterial.COPPER, 1.5F, -3.0F, new FabricItemSettings().group(ModItemGroup.MINETERRA)));

    public static final Item COPPER_HELMET = registerItem("copper_helmet",
            new ArmorItem(ModArmorMaterial.COPPER, EquipmentSlot.HEAD, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item COPPER_CHESTPLATE = registerItem("copper_chestplate",
            new ArmorItem(ModArmorMaterial.COPPER, EquipmentSlot.CHEST, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item COPPER_LEGGINGS = registerItem("copper_leggings",
            new ArmorItem(ModArmorMaterial.COPPER, EquipmentSlot.LEGS, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item COPPER_BOOTS = registerItem("copper_boots",
            new ArmorItem(ModArmorMaterial.COPPER, EquipmentSlot.FEET, new FabricItemSettings().group(ModItemGroup.MINETERRA)));

    // TIN
    public static final Item TIN_ORE = registerItem("tin_ore",
            new Item(new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item TIN_INGOT = registerItem("tin_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.MINETERRA)));

    public static final Item TIN_SWORD = registerItem("tin_sword",
            new SwordItem(ModToolMaterial.TIN, 3, -2.4f, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item TIN_PICKAXE = registerItem("tin_pickaxe",
            new PickaxeItem(ModToolMaterial.TIN, 1, -2.8f, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item TIN_AXE = registerItem("tin_axe",
            new AxeItem(ModToolMaterial.TIN, 5, -3.0f, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item TIN_SHOVEL = registerItem("tin_shovel",
            new ShovelItem(ModToolMaterial.TIN, 1.5F, -3.0F, new FabricItemSettings().group(ModItemGroup.MINETERRA)));

    public static final Item TIN_HELMET = registerItem("tin_helmet",
            new ArmorItem(ModArmorMaterial.TIN, EquipmentSlot.HEAD, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item TIN_CHESTPLATE = registerItem("tin_chestplate",
            new ArmorItem(ModArmorMaterial.TIN, EquipmentSlot.CHEST, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item TIN_LEGGINGS = registerItem("tin_leggings",
            new ArmorItem(ModArmorMaterial.TIN, EquipmentSlot.LEGS, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item TIN_BOOTS = registerItem("tin_boots",
            new ArmorItem(ModArmorMaterial.TIN, EquipmentSlot.FEET, new FabricItemSettings().group(ModItemGroup.MINETERRA)));

    // LEAD
    public static final Item LEAD_ORE = registerItem("lead_ore",
            new Item(new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item LEAD_INGOT = registerItem("lead_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.MINETERRA)));

    public static final Item LEAD_SWORD = registerItem("lead_sword",
            new SwordItem(ModToolMaterial.LEAD, 3, -2.4f, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item LEAD_PICKAXE = registerItem("lead_pickaxe",
            new PickaxeItem(ModToolMaterial.LEAD, 1, -2.8f, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item LEAD_AXE = registerItem("lead_axe",
            new AxeItem(ModToolMaterial.LEAD, 5, -3.0f, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item LEAD_SHOVEL = registerItem("lead_shovel",
            new ShovelItem(ModToolMaterial.LEAD, 1.5F, -3.0F, new FabricItemSettings().group(ModItemGroup.MINETERRA)));

    public static final Item LEAD_HELMET = registerItem("lead_helmet",
            new ArmorItem(ModArmorMaterial.LEAD, EquipmentSlot.HEAD, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item LEAD_CHESTPLATE = registerItem("lead_chestplate",
            new ArmorItem(ModArmorMaterial.LEAD, EquipmentSlot.CHEST, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item LEAD_LEGGINGS = registerItem("lead_leggings",
            new ArmorItem(ModArmorMaterial.LEAD, EquipmentSlot.LEGS, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item LEAD_BOOTS = registerItem("lead_boots",
            new ArmorItem(ModArmorMaterial.LEAD, EquipmentSlot.FEET, new FabricItemSettings().group(ModItemGroup.MINETERRA)));

    // SILVER
    public static final Item SILVER_ORE = registerItem("silver_ore",
            new Item(new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item SILVER_INGOT = registerItem("silver_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.MINETERRA)));

    public static final Item SILVER_SWORD = registerItem("silver_sword",
            new SwordItem(ModToolMaterial.SILVER, 3, -2.4f, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item SILVER_PICKAXE = registerItem("silver_pickaxe",
            new PickaxeItem(ModToolMaterial.SILVER, 1, -2.8f, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item SILVER_AXE = registerItem("silver_axe",
            new AxeItem(ModToolMaterial.SILVER, 5, -3.0f, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item SILVER_SHOVEL = registerItem("silver_shovel",
            new ShovelItem(ModToolMaterial.SILVER, 1.5F, -3.0F, new FabricItemSettings().group(ModItemGroup.MINETERRA)));

    public static final Item SILVER_HELMET = registerItem("silver_helmet",
            new ArmorItem(ModArmorMaterial.SILVER, EquipmentSlot.HEAD, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item SILVER_CHESTPLATE = registerItem("silver_chestplate",
            new ArmorItem(ModArmorMaterial.SILVER, EquipmentSlot.CHEST, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item SILVER_LEGGINGS = registerItem("silver_leggings",
            new ArmorItem(ModArmorMaterial.SILVER, EquipmentSlot.LEGS, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item SILVER_BOOTS = registerItem("silver_boots",
            new ArmorItem(ModArmorMaterial.SILVER, EquipmentSlot.FEET, new FabricItemSettings().group(ModItemGroup.MINETERRA)));

    // TUNGSTEN
    public static final Item TUNGSTEN_ORE = registerItem("tungsten_ore",
            new Item(new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item TUNGSTEN_INGOT = registerItem("tungsten_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.MINETERRA)));

    public static final Item TUNGSTEN_SWORD = registerItem("tungsten_sword",
            new SwordItem(ModToolMaterial.TUNGSTEN, 3, -2.4f, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item TUNGSTEN_PICKAXE = registerItem("tungsten_pickaxe",
            new PickaxeItem(ModToolMaterial.TUNGSTEN, 1, -2.8f, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item TUNGSTEN_AXE = registerItem("tungsten_axe",
            new AxeItem(ModToolMaterial.TUNGSTEN, 5, -3.0f, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item TUNGSTEN_SHOVEL = registerItem("tungsten_shovel",
            new ShovelItem(ModToolMaterial.TUNGSTEN, 1.5F, -3.0F, new FabricItemSettings().group(ModItemGroup.MINETERRA)));

    public static final Item TUNGSTEN_HELMET = registerItem("tungsten_helmet",
            new ArmorItem(ModArmorMaterial.TUNGSTEN, EquipmentSlot.HEAD, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item TUNGSTEN_CHESTPLATE = registerItem("tungsten_chestplate",
            new ArmorItem(ModArmorMaterial.TUNGSTEN, EquipmentSlot.CHEST, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item TUNGSTEN_LEGGINGS = registerItem("tungsten_leggings",
            new ArmorItem(ModArmorMaterial.TUNGSTEN, EquipmentSlot.LEGS, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item TUNGSTEN_BOOTS = registerItem("tungsten_boots",
            new ArmorItem(ModArmorMaterial.TUNGSTEN, EquipmentSlot.FEET, new FabricItemSettings().group(ModItemGroup.MINETERRA)));

    // PLATINUM
    public static final Item PLATINUM_ORE = registerItem("platinum_ore",
            new Item(new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item PLATINUM_INGOT = registerItem("platinum_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.MINETERRA)));

    public static final Item PLATINUM_SWORD = registerItem("platinum_sword",
            new SwordItem(ModToolMaterial.PLATINUM, 3, -2.4f, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item PLATINUM_PICKAXE = registerItem("platinum_pickaxe",
            new PickaxeItem(ModToolMaterial.PLATINUM, 1, -2.8f, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item PLATINUM_AXE = registerItem("platinum_axe",
            new AxeItem(ModToolMaterial.PLATINUM, 5, -3.0f, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item PLATINUM_SHOVEL = registerItem("platinum_shovel",
            new ShovelItem(ModToolMaterial.PLATINUM, 1.5F, -3.0F, new FabricItemSettings().group(ModItemGroup.MINETERRA)));

    public static final Item PLATINUM_HELMET = registerItem("platinum_helmet",
            new ArmorItem(ModArmorMaterial.PLATINUM, EquipmentSlot.HEAD, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item PLATINUM_CHESTPLATE = registerItem("platinum_chestplate",
            new ArmorItem(ModArmorMaterial.PLATINUM, EquipmentSlot.CHEST, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item PLATINUM_LEGGINGS = registerItem("platinum_leggings",
            new ArmorItem(ModArmorMaterial.PLATINUM, EquipmentSlot.LEGS, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item PLATINUM_BOOTS = registerItem("platinum_boots",
            new ArmorItem(ModArmorMaterial.PLATINUM, EquipmentSlot.FEET, new FabricItemSettings().group(ModItemGroup.MINETERRA)));

    // DEMONITE
    public static final Item DEMONITE_ORE = registerItem("demonite_ore",
            new Item(new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item DEMONITE_INGOT = registerItem("demonite_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.MINETERRA)));

    public static final Item DEMONITE_SWORD = registerItem("demonite_sword",
            new SwordItem(ModToolMaterial.DEMONITE, 3, -2.4f, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item DEMONITE_PICKAXE = registerItem("demonite_pickaxe",
            new PickaxeItem(ModToolMaterial.DEMONITE, 1, -2.8f, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item DEMONITE_AXE = registerItem("demonite_axe",
            new AxeItem(ModToolMaterial.DEMONITE, 5, -3.0f, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item DEMONITE_SHOVEL = registerItem("demonite_shovel",
            new ShovelItem(ModToolMaterial.DEMONITE, 1.5F, -3.0F, new FabricItemSettings().group(ModItemGroup.MINETERRA)));

    public static final Item DEMONITE_HELMET = registerItem("demonite_helmet",
            new ArmorItem(ModArmorMaterial.DEMONITE, EquipmentSlot.HEAD, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item DEMONITE_CHESTPLATE = registerItem("demonite_chestplate",
            new ArmorItem(ModArmorMaterial.DEMONITE, EquipmentSlot.CHEST, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item DEMONITE_LEGGINGS = registerItem("demonite_leggings",
            new ArmorItem(ModArmorMaterial.DEMONITE, EquipmentSlot.LEGS, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item DEMONITE_BOOTS = registerItem("demonite_boots",
            new ArmorItem(ModArmorMaterial.DEMONITE, EquipmentSlot.FEET, new FabricItemSettings().group(ModItemGroup.MINETERRA)));

    // CRIMSON
    public static final Item CRIMTANE_ORE = registerItem("crimtane_ore",
            new Item(new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item CRIMTANE_INGOT = registerItem("crimtane_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.MINETERRA)));

    public static final Item CRIMTANE_SWORD = registerItem("crimtane_sword",
            new SwordItem(ModToolMaterial.CRIMTANE, 3, -2.4f, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item CRIMTANE_PICKAXE = registerItem("crimtane_pickaxe",
            new PickaxeItem(ModToolMaterial.CRIMTANE, 1, -2.8f, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item CRIMTANE_AXE = registerItem("crimtane_axe",
            new AxeItem(ModToolMaterial.CRIMTANE, 5, -3.0f, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item CRIMTANE_SHOVEL = registerItem("crimtane_shovel",
            new ShovelItem(ModToolMaterial.CRIMTANE, 1.5F, -3.0F, new FabricItemSettings().group(ModItemGroup.MINETERRA)));

    public static final Item CRIMTANE_HELMET = registerItem("crimtane_helmet",
            new ArmorItem(ModArmorMaterial.CRIMTANE, EquipmentSlot.HEAD, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item CRIMTANE_CHESTPLATE = registerItem("crimtane_chestplate",
            new ArmorItem(ModArmorMaterial.CRIMTANE, EquipmentSlot.CHEST, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item CRIMTANE_LEGGINGS = registerItem("crimtane_leggings",
            new ArmorItem(ModArmorMaterial.CRIMTANE, EquipmentSlot.LEGS, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item CRIMTANE_BOOTS = registerItem("crimtane_boots",
            new ArmorItem(ModArmorMaterial.CRIMTANE, EquipmentSlot.FEET, new FabricItemSettings().group(ModItemGroup.MINETERRA)));

    // METEORITE
    public static final Item METEORITE_ORE = registerItem("meteorite_ore",
            new Item(new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item METEORITE_INGOT = registerItem("meteorite_ingot",
            new Item(new FabricItemSettings().group(ModItemGroup.MINETERRA)));

    public static final Item METEORITE_AXE = registerItem("meteorite_axe",
            new AxeItem(ModToolMaterial.METEORITE, 5.5F, -3.0f, new FabricItemSettings().group(ModItemGroup.MINETERRA)));

    public static final Item METEORITE_HELMET = registerItem("meteorite_helmet",
            new ArmorItem(ModArmorMaterial.METEORITE, EquipmentSlot.HEAD, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item METEORITE_CHESTPLATE = registerItem("meteorite_chestplate",
            new ArmorItem(ModArmorMaterial.METEORITE, EquipmentSlot.CHEST, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item METEORITE_LEGGINGS = registerItem("meteorite_leggings",
            new ArmorItem(ModArmorMaterial.METEORITE, EquipmentSlot.LEGS, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item METEORITE_BOOTS = registerItem("meteorite_boots",
            new ArmorItem(ModArmorMaterial.METEORITE, EquipmentSlot.FEET, new FabricItemSettings().group(ModItemGroup.MINETERRA)));

    // HELLSTONE
    public static final Item HELLSTONE_ORE = registerItem("hellstone_ore",
            new Item(new FabricItemSettings().fireproof().group(ModItemGroup.MINETERRA)));
    public static final Item HELLSTONE_INGOT = registerItem("hellstone_ingot",
            new Item(new FabricItemSettings().fireproof().group(ModItemGroup.MINETERRA)));

    public static final Item HELLSTONE_SWORD = registerItem("hellstone_sword",
            new SwordItem(ModToolMaterial.HELLSTONE, 4, -2.4f, new FabricItemSettings().fireproof().group(ModItemGroup.MINETERRA)));
    public static final Item HELLSTONE_PICKAXE = registerItem("hellstone_pickaxe",
            new PickaxeItem(ModToolMaterial.HELLSTONE, 2, -2.8f, new FabricItemSettings().fireproof().group(ModItemGroup.MINETERRA)));
    public static final Item HELLSTONE_AXE = registerItem("hellstone_axe",
            new AxeItem(ModToolMaterial.HELLSTONE, 6, -3.0f, new FabricItemSettings().fireproof().group(ModItemGroup.MINETERRA)));
    public static final Item HELLSTONE_SHOVEL = registerItem("hellstone_shovel",
            new ShovelItem(ModToolMaterial.HELLSTONE, 1.5F, -3.0F, new FabricItemSettings().group(ModItemGroup.MINETERRA)));

    public static final Item HELLSTONE_HELMET = registerItem("hellstone_helmet",
            new ArmorItem(ModArmorMaterial.HELLSTONE, EquipmentSlot.HEAD, new FabricItemSettings().fireproof().group(ModItemGroup.MINETERRA)));
    public static final Item HELLSTONE_CHESTPLATE = registerItem("hellstone_chestplate",
            new ArmorItem(ModArmorMaterial.HELLSTONE, EquipmentSlot.CHEST, new FabricItemSettings().fireproof().group(ModItemGroup.MINETERRA)));
    public static final Item HELLSTONE_LEGGINGS = registerItem("hellstone_leggings",
            new ArmorItem(ModArmorMaterial.HELLSTONE, EquipmentSlot.LEGS, new FabricItemSettings().fireproof().group(ModItemGroup.MINETERRA)));
    public static final Item HELLSTONE_BOOTS = registerItem("hellstone_boots",
            new ArmorItem(ModArmorMaterial.HELLSTONE, EquipmentSlot.FEET, new FabricItemSettings().fireproof().group(ModItemGroup.MINETERRA)));

    // GEMS
    public static final Item AMETHYST = registerItem("amethyst",
            new Item(new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item TOPAZ = registerItem("topaz",
            new Item(new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item SAPPHIRE = registerItem("sapphire",
            new Item(new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item EMERALD = registerItem("emerald",
            new Item(new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item RUBY = registerItem("ruby",
            new Item(new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    public static final Item DIAMOND = registerItem("diamond",
            new Item(new FabricItemSettings().group(ModItemGroup.MINETERRA)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(MineTerra.MOD_ID, name), item);
    }

    public static void registerModItems() {
        System.out.println("Registering ModItems for " + MineTerra.MOD_ID);
    }
}
