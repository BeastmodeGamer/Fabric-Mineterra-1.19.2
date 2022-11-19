package net.andychen.mineterra.block;

import net.andychen.mineterra.MineTerra;
import net.andychen.mineterra.block.custom.HellstoneOreBlock;
import net.andychen.mineterra.block.custom.MeteoriteOreBlock;
import net.andychen.mineterra.item.ModItemGroup;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;

public class ModBlocks {

    // GEMS
    public static final Block AMETHYST_ORE = registerBlock("amethyst_ore_block",
            new Block(FabricBlockSettings.of(Material.STONE).strength(3.0f).resistance(3.0f).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block TOPAZ_ORE = registerBlock("topaz_ore_block",
            new Block(FabricBlockSettings.of(Material.STONE).strength(3.0f).resistance(3.0f).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block SAPPHIRE_ORE = registerBlock("sapphire_ore_block",
            new Block(FabricBlockSettings.of(Material.STONE).strength(3.0f).resistance(3.0f).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block EMERALD_ORE = registerBlock("emerald_ore_block",
            new Block(FabricBlockSettings.of(Material.STONE).strength(3.0f).resistance(3.0f).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block RUBY_ORE = registerBlock("ruby_ore_block",
            new Block(FabricBlockSettings.of(Material.STONE).strength(3.0f).resistance(3.0f).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block DIAMOND_ORE = registerBlock("diamond_ore_block",
            new Block(FabricBlockSettings.of(Material.STONE).strength(3.0f).resistance(3.0f).requiresTool().sounds(BlockSoundGroup.STONE)));

    // ORE
    public static final Block COPPER_ORE = registerBlock("copper_ore_block",
            new Block(FabricBlockSettings.of(Material.STONE).strength(2.5f).resistance(3.0f).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block TIN_ORE = registerBlock("tin_ore_block",
            new Block(FabricBlockSettings.of(Material.STONE).strength(2.5f).resistance(3.0f).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block LEAD_ORE = registerBlock("lead_ore_block",
            new Block(FabricBlockSettings.of(Material.STONE).strength(3.0f).resistance(3.0f).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block SILVER_ORE = registerBlock("silver_ore_block",
            new Block(FabricBlockSettings.of(Material.STONE).strength(3.5f).resistance(4.0f).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block TUNGSTEN_ORE = registerBlock("tungsten_ore_block",
            new Block(FabricBlockSettings.of(Material.STONE).strength(3.5f).resistance(4.0f).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block PLATINUM_ORE = registerBlock("platinum_ore_block",
            new Block(FabricBlockSettings.of(Material.STONE).strength(4.0f).resistance(4.0f).requiresTool().sounds(BlockSoundGroup.STONE)));
    public static final Block DEMONITE_ORE_BLOCK = registerBlock("demonite_ore_block",
            new Block(FabricBlockSettings.of(Material.STONE).strength(5.0f).resistance(6000.0f).requiresTool().sounds(BlockSoundGroup.STONE).luminance(6)));
    public static final Block CRIMTANE_ORE_BLOCK = registerBlock("crimtane_ore_block",
            new Block(FabricBlockSettings.of(Material.STONE).strength(5.0f).resistance(6000.0f).requiresTool().sounds(BlockSoundGroup.STONE).luminance(6)));
    public static final Block METEORITE_ORE_BLOCK = registerBlock("meteorite_ore_block",
            new MeteoriteOreBlock(FabricBlockSettings.of(Material.STONE).strength(5.5f).resistance(6000.0f).requiresTool().sounds(BlockSoundGroup.STONE).luminance(6)));
    public static final Block HELLSTONE_ORE_BLOCK = registerBlock("hellstone_ore_block",
            new HellstoneOreBlock(FabricBlockSettings.of(Material.STONE).strength(6.0f).resistance(6000.0f).requiresTool().sounds(BlockSoundGroup.DEEPSLATE).luminance(10)));

    private static boolean never(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    private static Block registerBlock(String name, Block block){
        registerBlockItem(name, block);
        return Registry.register(Registry.BLOCK, new Identifier(MineTerra.MOD_ID, name), block);
    }

    private static Block registerBlockWithoutBlockItem(String name, Block block){
        return Registry.register(Registry.BLOCK, new Identifier(MineTerra.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block){
        return Registry.register(Registry.ITEM, new Identifier(MineTerra.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings().group(ModItemGroup.MINETERRA)));
    }

    public static void registerModBlocks(){
        System.out.println("Registering ModBlocks for " + MineTerra.MOD_ID);
    }
}
