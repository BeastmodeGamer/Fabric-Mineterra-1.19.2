package net.andychen.mineterra.entity.client;

import net.andychen.mineterra.entity.ModEntities;
import net.andychen.mineterra.entity.client.render.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class ModEntityRenderRegistry {

    public static void registerEntityRenderers() {
        EntityRendererRegistry.register(ModEntities.BULLET, (context) -> new BulletEntityRenderer(context));
        EntityRendererRegistry.register(ModEntities.CHAOS_BALL, (context) -> new ChaosBallEntityRenderer(context));
        EntityRendererRegistry.register(ModEntities.FALLEN_STAR, (context) -> new FallenStarEntityRenderer(context));
        EntityRendererRegistry.register(ModEntities.GOBLIN_ARCHER, (context) -> new GoblinArcherEntityRenderer(context));
        EntityRendererRegistry.register(ModEntities.GOBLIN_PEON, (context) -> new GoblinPeonEntityRenderer(context));
        EntityRendererRegistry.register(ModEntities.GOBLIN_SCOUT, (context) -> new GoblinScoutEntityRenderer(context));
        EntityRendererRegistry.register(ModEntities.GOBLIN_SORCERER, (context) -> new GoblinSorcererEntityRenderer(context));
        EntityRendererRegistry.register(ModEntities.GOBLIN_THIEF, (context) -> new GoblinThiefEntityRenderer(context));
        EntityRendererRegistry.register(ModEntities.GOBLIN_WARRIOR, (context) -> new GoblinWarriorEntityRenderer(context));
        EntityRendererRegistry.register(ModEntities.JESTER_ARROW, (context) -> new JesterArrowEntityRenderer(context));
        EntityRendererRegistry.register(ModEntities.LANTERN, (context) -> new LanternEntityRenderer(context));
        EntityRendererRegistry.register(ModEntities.SPIKY_BALL, (context) -> new SpikyBallEntityRenderer(context));
    }
}
