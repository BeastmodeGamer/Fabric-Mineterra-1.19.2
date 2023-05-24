package net.andychen.mineterra.block.entity;

import net.andychen.mineterra.MineTerra;
import net.andychen.mineterra.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlockEntities {
    public static BlockEntityType<HellforgeBlockEntity> HELLFORGE;

    public static void registerBlockEntities() {
        HELLFORGE = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MineTerra.MOD_ID, "hellforge"),
                FabricBlockEntityTypeBuilder.create(HellforgeBlockEntity::new, ModBlocks.HELLFORGE).build(null));
    }
}
