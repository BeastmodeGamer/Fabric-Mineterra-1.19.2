package net.andychen.mineterra.datagen;

import net.andychen.mineterra.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        //blockStateModelGenerator.registerCubeAllModelTexturePool()
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.COPPER_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.TIN_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.LEAD_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.SILVER_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.TUNGSTEN_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.PLATINUM_PICKAXE, Models.HANDHELD);

        itemModelGenerator.register(ModItems.COPPER_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.TIN_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.LEAD_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.SILVER_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.TUNGSTEN_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.PLATINUM_AXE, Models.HANDHELD);

        itemModelGenerator.register(ModItems.COPPER_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.TIN_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.LEAD_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.SILVER_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.TUNGSTEN_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.PLATINUM_SHOVEL, Models.HANDHELD);

        itemModelGenerator.register(ModItems.COPPER_HELMET, Models.GENERATED);
        itemModelGenerator.register(ModItems.TIN_HELMET, Models.GENERATED);
        itemModelGenerator.register(ModItems.LEAD_HELMET, Models.GENERATED);
        itemModelGenerator.register(ModItems.SILVER_HELMET, Models.GENERATED);
        itemModelGenerator.register(ModItems.TUNGSTEN_HELMET, Models.GENERATED);
        itemModelGenerator.register(ModItems.PLATINUM_HELMET, Models.GENERATED);
        itemModelGenerator.register(ModItems.DEMONITE_HELMET, Models.GENERATED);
        itemModelGenerator.register(ModItems.CRIMTANE_HELMET, Models.GENERATED);
        itemModelGenerator.register(ModItems.METEORITE_HELMET, Models.GENERATED);
        itemModelGenerator.register(ModItems.HELLSTONE_HELMET, Models.GENERATED);

        itemModelGenerator.register(ModItems.COPPER_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(ModItems.TIN_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(ModItems.LEAD_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(ModItems.SILVER_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(ModItems.TUNGSTEN_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(ModItems.PLATINUM_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(ModItems.DEMONITE_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(ModItems.CRIMTANE_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(ModItems.METEORITE_CHESTPLATE, Models.GENERATED);
        itemModelGenerator.register(ModItems.HELLSTONE_CHESTPLATE, Models.GENERATED);

        itemModelGenerator.register(ModItems.COPPER_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(ModItems.TIN_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(ModItems.LEAD_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(ModItems.SILVER_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(ModItems.TUNGSTEN_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(ModItems.PLATINUM_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(ModItems.DEMONITE_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(ModItems.CRIMTANE_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(ModItems.METEORITE_LEGGINGS, Models.GENERATED);
        itemModelGenerator.register(ModItems.HELLSTONE_LEGGINGS, Models.GENERATED);

        itemModelGenerator.register(ModItems.COPPER_BOOTS, Models.GENERATED);
        itemModelGenerator.register(ModItems.TIN_BOOTS, Models.GENERATED);
        itemModelGenerator.register(ModItems.LEAD_BOOTS, Models.GENERATED);
        itemModelGenerator.register(ModItems.SILVER_BOOTS, Models.GENERATED);
        itemModelGenerator.register(ModItems.TUNGSTEN_BOOTS, Models.GENERATED);
        itemModelGenerator.register(ModItems.PLATINUM_BOOTS, Models.GENERATED);
        itemModelGenerator.register(ModItems.DEMONITE_BOOTS, Models.GENERATED);
        itemModelGenerator.register(ModItems.CRIMTANE_BOOTS, Models.GENERATED);
        itemModelGenerator.register(ModItems.METEORITE_BOOTS, Models.GENERATED);
        itemModelGenerator.register(ModItems.HELLSTONE_BOOTS, Models.GENERATED);
        
        itemModelGenerator.register(ModItems.COPPER_ORE, Models.GENERATED);
        itemModelGenerator.register(ModItems.TIN_ORE, Models.GENERATED);
        itemModelGenerator.register(ModItems.LEAD_ORE, Models.GENERATED);
        itemModelGenerator.register(ModItems.SILVER_ORE, Models.GENERATED);
        itemModelGenerator.register(ModItems.TUNGSTEN_ORE, Models.GENERATED);
        itemModelGenerator.register(ModItems.PLATINUM_ORE, Models.GENERATED);
        itemModelGenerator.register(ModItems.DEMONITE_ORE, Models.GENERATED);
        itemModelGenerator.register(ModItems.CRIMTANE_ORE, Models.GENERATED);
        itemModelGenerator.register(ModItems.METEORITE_ORE, Models.GENERATED);
        itemModelGenerator.register(ModItems.HELLSTONE_ORE, Models.GENERATED);

        itemModelGenerator.register(ModItems.COPPER_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.TIN_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.LEAD_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.SILVER_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.TUNGSTEN_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.PLATINUM_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.DEMONITE_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.CRIMTANE_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.METEORITE_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.HELLSTONE_INGOT, Models.GENERATED);
        
        itemModelGenerator.register(ModItems.AMETHYST, Models.GENERATED);
        itemModelGenerator.register(ModItems.TOPAZ, Models.GENERATED);
        itemModelGenerator.register(ModItems.SAPPHIRE, Models.GENERATED);
        itemModelGenerator.register(ModItems.EMERALD, Models.GENERATED);
        itemModelGenerator.register(ModItems.RUBY, Models.GENERATED);
        itemModelGenerator.register(ModItems.DIAMOND, Models.GENERATED);
    }
}
