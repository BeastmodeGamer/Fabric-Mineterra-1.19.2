package net.andychen.mineterra.entity.client;

import com.google.common.collect.Sets;
import net.andychen.mineterra.MineTerra;
import net.andychen.mineterra.entity.client.model.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

import java.util.Set;

@Environment(EnvType.CLIENT)
public class ModEntityModelLayerRegistry {
    private static final String MAIN = "main";
    private static final Set<EntityModelLayer> LAYERS = Sets.newHashSet();

    public static final EntityModelLayer BULLET = registerMain("bullet");
    public static final EntityModelLayer CHAOS_BALL = registerMain("chaos_ball");
    public static final EntityModelLayer GOBLIN_ARCHER = registerMain("goblin_archer");
    public static final EntityModelLayer GOBLIN_PEON = registerMain("goblin_peon");
    public static final EntityModelLayer GOBLIN_SCOUT = registerMain("goblin_scout");
    public static final EntityModelLayer GOBLIN_SORCERER = registerMain("goblin_sorcerer");
    public static final EntityModelLayer GOBLIN_THIEF = registerMain("goblin_thief");
    public static final EntityModelLayer GOBLIN_WARRIOR = registerMain("goblin_warrior");
    public static final EntityModelLayer LANTERN = registerMain("lantern");
    public static final EntityModelLayer SPIKY_BALL = registerMain("spiky_ball");

    // ARMOR
    public static final EntityModelLayer MOD_ARMOR = register("mod_armor", "outer_armor");

    public static void registerEntityModels() {
        EntityModelLayerRegistry.registerModelLayer(BULLET, BulletEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(CHAOS_BALL, ChaosBallEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(GOBLIN_ARCHER, GoblinArcherEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(GOBLIN_PEON, GoblinPeonEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(GOBLIN_SCOUT, GoblinScoutEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(GOBLIN_SORCERER, GoblinSorcererEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(GOBLIN_THIEF, GoblinThiefEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(GOBLIN_WARRIOR, GoblinWarriorEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(LANTERN, LanternEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(SPIKY_BALL, SpikyBallEntityModel::getTexturedModelData);

        EntityModelLayerRegistry.registerModelLayer(MOD_ARMOR, ModArmorModel::getTexturedModelData);
    }

    private static EntityModelLayer registerMain(String id) {
        return register(id, "main");
    }

    private static EntityModelLayer register(String id, String layer) {
        EntityModelLayer entityModelLayer = create(id, layer);
        if (!LAYERS.add(entityModelLayer)) {
            throw new IllegalStateException("Duplicate registration for " + entityModelLayer);
        } else {
            return entityModelLayer;
        }
    }

    private static EntityModelLayer create(String id, String layer) {
        return new EntityModelLayer(new Identifier(MineTerra.MOD_ID, id), layer);
    }

}
