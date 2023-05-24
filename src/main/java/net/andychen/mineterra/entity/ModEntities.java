package net.andychen.mineterra.entity;

import net.andychen.mineterra.MineTerra;
import net.andychen.mineterra.entity.custom.ChaosBallEntity;
import net.andychen.mineterra.entity.custom.FallenStarEntity;
import net.andychen.mineterra.entity.custom.LanternEntity;
import net.andychen.mineterra.entity.custom.SpikyBallEntity;
import net.andychen.mineterra.entity.custom.goblin.*;
import net.andychen.mineterra.entity.custom.ranged.BulletEntity;
import net.andychen.mineterra.entity.custom.ranged.JesterArrowEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEntities {

    public static final EntityType<BulletEntity> BULLET = register("bullet",
            FabricEntityTypeBuilder.<BulletEntity>create(SpawnGroup.MISC, BulletEntity::new)
                    .dimensions(EntityDimensions.fixed(0.1F, 0.1F)).trackRangeBlocks(256).trackedUpdateRate(1).forceTrackedVelocityUpdates(true));
    public static final EntityType<ChaosBallEntity> CHAOS_BALL = register("chaos_ball",
            FabricEntityTypeBuilder.<ChaosBallEntity>create(SpawnGroup.MISC, ChaosBallEntity::new)
                    .dimensions(EntityDimensions.fixed(0.35F, 0.35F)).trackRangeBlocks(128).trackedUpdateRate(10));
    public static final EntityType<FallenStarEntity> FALLEN_STAR = register("fallen_star",
            FabricEntityTypeBuilder.<FallenStarEntity>create(SpawnGroup.MISC, FallenStarEntity::new)
                    .dimensions(EntityDimensions.fixed(0.35F, 0.35F)).trackRangeBlocks(256).trackedUpdateRate(1).forceTrackedVelocityUpdates(true));
    public static final EntityType<GoblinArcherEntity> GOBLIN_ARCHER = register("goblin_archer",
            FabricEntityTypeBuilder.<GoblinArcherEntity>create(SpawnGroup.MONSTER, GoblinArcherEntity::new)
                    .dimensions(EntityDimensions.fixed(0.6F, 1.85F)));
    public static final EntityType<GoblinPeonEntity> GOBLIN_PEON = register("goblin_peon",
            FabricEntityTypeBuilder.<GoblinPeonEntity>create(SpawnGroup.MONSTER, GoblinPeonEntity::new)
                    .dimensions(EntityDimensions.fixed(0.6F, 1.85F)));
    public static final EntityType<GoblinScoutEntity> GOBLIN_SCOUT = register("goblin_scout",
            FabricEntityTypeBuilder.<GoblinScoutEntity>create(SpawnGroup.MONSTER, GoblinScoutEntity::new)
                    .dimensions(EntityDimensions.fixed(0.6F, 1.85F)));
    public static final EntityType<GoblinSorcererEntity> GOBLIN_SORCERER = register("goblin_sorcerer",
            FabricEntityTypeBuilder.<GoblinSorcererEntity>create(SpawnGroup.MONSTER, GoblinSorcererEntity::new)
                    .dimensions(EntityDimensions.fixed(0.6F, 1.85F)));
    public static final EntityType<GoblinThiefEntity> GOBLIN_THIEF = register("goblin_thief",
            FabricEntityTypeBuilder.<GoblinThiefEntity>create(SpawnGroup.MONSTER, GoblinThiefEntity::new)
                    .dimensions(EntityDimensions.fixed(0.6F, 1.85F)));
    public static final EntityType<GoblinWarriorEntity> GOBLIN_WARRIOR = register("goblin_warrior",
            FabricEntityTypeBuilder.<GoblinWarriorEntity>create(SpawnGroup.MONSTER, GoblinWarriorEntity::new)
                    .dimensions(EntityDimensions.fixed(0.8F, 2.0F)));
    public static final EntityType<JesterArrowEntity> JESTER_ARROW = register("jester_arrow",
            FabricEntityTypeBuilder.<JesterArrowEntity>create(SpawnGroup.MISC, JesterArrowEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5F, 0.5F)).trackRangeBlocks(256).trackedUpdateRate(1).forceTrackedVelocityUpdates(true));
    public static final EntityType<LanternEntity> LANTERN = register("lantern",
            FabricEntityTypeBuilder.<LanternEntity>create(SpawnGroup.MISC, LanternEntity::new)
                    .dimensions(EntityDimensions.fixed(0.4F, 0.5F)).trackRangeBlocks(128).trackedUpdateRate(1).forceTrackedVelocityUpdates(true));
    public static final EntityType<SpikyBallEntity> SPIKY_BALL = register("spiky_ball",
            FabricEntityTypeBuilder.<SpikyBallEntity>create(SpawnGroup.MISC, SpikyBallEntity::new)
                    .dimensions(EntityDimensions.fixed(0.3F, 0.3F)).trackRangeBlocks(128).trackedUpdateRate(1));


    private static <T extends Entity> EntityType register(String name, FabricEntityTypeBuilder<T> type) {
        return Registry.register(Registry.ENTITY_TYPE, new Identifier(MineTerra.MOD_ID, name), type.build());
    }

    public static void registerModEntities() {
        System.out.println("Registering ModEntities for " + MineTerra.MOD_ID);
    }
}