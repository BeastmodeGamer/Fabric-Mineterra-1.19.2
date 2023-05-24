package net.andychen.mineterra.entity.client.render;

import net.andychen.mineterra.MineTerra;
import net.andychen.mineterra.entity.client.ModEntityModelLayerRegistry;
import net.andychen.mineterra.entity.client.model.GoblinArcherEntityModel;
import net.andychen.mineterra.entity.custom.goblin.GoblinArcherEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class GoblinArcherEntityRenderer extends BipedEntityRenderer<GoblinArcherEntity, GoblinArcherEntityModel<GoblinArcherEntity>> {
    private static final Identifier TEXTURE = new Identifier(MineTerra.MOD_ID, "textures/entity/goblin_archer.png");

    public GoblinArcherEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new GoblinArcherEntityModel(context.getPart(ModEntityModelLayerRegistry.GOBLIN_ARCHER)), 0.5F);
    }

    public void render(GoblinArcherEntity goblinArcherEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(goblinArcherEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    public Identifier getTexture(GoblinArcherEntity goblinArcherEntity) {
        return TEXTURE;
    }
}
