package net.andychen.mineterra.datagen;

import net.andychen.mineterra.MineTerra;
import net.andychen.mineterra.block.ModBlocks;
import net.andychen.mineterra.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.data.server.BlockLootTableGenerator;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;

public class ModLootTableGenerator extends SimpleFabricLootTableProvider {
    public ModLootTableGenerator(FabricDataGenerator dataGenerator) {
        super(dataGenerator, LootContextTypes.BLOCK);
    }

    @Override
    public void accept(BiConsumer<Identifier, LootTable.Builder> identifierBuilderBiConsumer) {
        identifierBuilderBiConsumer.accept(new Identifier(MineTerra.MOD_ID, "blocks/amethyst_ore_block"),
                BlockLootTableGenerator.oreDrops(ModBlocks.AMETHYST_ORE, ModItems.AMETHYST));
        identifierBuilderBiConsumer.accept(new Identifier(MineTerra.MOD_ID, "blocks/topaz_ore_block"),
                BlockLootTableGenerator.oreDrops(ModBlocks.TOPAZ_ORE, ModItems.TOPAZ));
        identifierBuilderBiConsumer.accept(new Identifier(MineTerra.MOD_ID, "blocks/sapphire_ore_block"),
                BlockLootTableGenerator.oreDrops(ModBlocks.SAPPHIRE_ORE, ModItems.SAPPHIRE));
        identifierBuilderBiConsumer.accept(new Identifier(MineTerra.MOD_ID, "blocks/emerald_ore_block"),
                BlockLootTableGenerator.oreDrops(ModBlocks.EMERALD_ORE, ModItems.EMERALD));
        identifierBuilderBiConsumer.accept(new Identifier(MineTerra.MOD_ID, "blocks/ruby_ore_block"),
                BlockLootTableGenerator.oreDrops(ModBlocks.RUBY_ORE, ModItems.RUBY));
        identifierBuilderBiConsumer.accept(new Identifier(MineTerra.MOD_ID, "blocks/diamond_ore_block"),
                BlockLootTableGenerator.oreDrops(ModBlocks.DIAMOND_ORE, ModItems.DIAMOND));
        identifierBuilderBiConsumer.accept(new Identifier(MineTerra.MOD_ID, "blocks/copper_ore_block"),
                BlockLootTableGenerator.oreDrops(ModBlocks.COPPER_ORE, ModItems.COPPER_ORE));
        identifierBuilderBiConsumer.accept(new Identifier(MineTerra.MOD_ID, "blocks/tin_ore_block"),
                BlockLootTableGenerator.oreDrops(ModBlocks.TIN_ORE, ModItems.TIN_ORE));
        identifierBuilderBiConsumer.accept(new Identifier(MineTerra.MOD_ID, "blocks/lead_ore_block"),
                BlockLootTableGenerator.oreDrops(ModBlocks.LEAD_ORE, ModItems.LEAD_ORE));
        identifierBuilderBiConsumer.accept(new Identifier(MineTerra.MOD_ID, "blocks/silver_ore_block"),
                BlockLootTableGenerator.oreDrops(ModBlocks.SILVER_ORE, ModItems.SILVER_ORE));
        identifierBuilderBiConsumer.accept(new Identifier(MineTerra.MOD_ID, "blocks/tungsten_ore_block"),
                BlockLootTableGenerator.oreDrops(ModBlocks.TUNGSTEN_ORE, ModItems.TUNGSTEN_ORE));
        identifierBuilderBiConsumer.accept(new Identifier(MineTerra.MOD_ID, "blocks/platinum_ore_block"),
                BlockLootTableGenerator.oreDrops(ModBlocks.PLATINUM_ORE, ModItems.PLATINUM_ORE));
        identifierBuilderBiConsumer.accept(new Identifier(MineTerra.MOD_ID, "blocks/demonite_ore_block"),
                BlockLootTableGenerator.oreDrops(ModBlocks.DEMONITE_ORE, ModItems.DEMONITE_ORE));
        identifierBuilderBiConsumer.accept(new Identifier(MineTerra.MOD_ID, "blocks/crimtane_ore_block"),
                BlockLootTableGenerator.oreDrops(ModBlocks.CRIMTANE_ORE, ModItems.CRIMTANE_ORE));
        identifierBuilderBiConsumer.accept(new Identifier(MineTerra.MOD_ID, "blocks/meteorite_ore_block"),
                BlockLootTableGenerator.oreDrops(ModBlocks.METEORITE_ORE, ModItems.METEORITE_ORE));
        identifierBuilderBiConsumer.accept(new Identifier(MineTerra.MOD_ID, "blocks/hellstone_ore_block"),
                BlockLootTableGenerator.oreDrops(ModBlocks.HELLSTONE_ORE, ModItems.HELLSTONE_ORE));

        identifierBuilderBiConsumer.accept(new Identifier(MineTerra.MOD_ID, "blocks/hellstone_bricks"),
                BlockLootTableGenerator.drops(ModBlocks.HELLSTONE_BRICKS));
    }
}
