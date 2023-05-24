package net.andychen.mineterra.entity.attribute;

import net.andychen.mineterra.entity.ModEntities;
import net.andychen.mineterra.entity.custom.goblin.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public class ModEntityAttributeRegistry {

    public static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(ModEntities.GOBLIN_ARCHER, GoblinArcherEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.GOBLIN_PEON, GoblinPeonEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.GOBLIN_SCOUT, GoblinScoutEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.GOBLIN_SORCERER, GoblinSorcererEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.GOBLIN_THIEF, GoblinThiefEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.GOBLIN_WARRIOR, GoblinWarriorEntity.createAttributes());
    }
}
