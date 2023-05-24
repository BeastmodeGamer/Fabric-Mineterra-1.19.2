package net.andychen.mineterra.entity.client.render;

import net.andychen.mineterra.MineTerra;
import net.andychen.mineterra.entity.client.ModEntityModelLayerRegistry;
import net.andychen.mineterra.entity.client.model.GoblinPeonEntityModel;
import net.andychen.mineterra.entity.custom.goblin.GoblinPeonEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class GoblinPeonEntityRenderer extends BipedEntityRenderer<GoblinPeonEntity, GoblinPeonEntityModel<GoblinPeonEntity>> {
    private static final Identifier TEXTURE = new Identifier(MineTerra.MOD_ID, "textures/entity/goblin_peon.png");

    public GoblinPeonEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new GoblinPeonEntityModel(context.getPart(ModEntityModelLayerRegistry.GOBLIN_PEON)), 0.5F);
    }

    public void render(GoblinPeonEntity goblinPeonEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(goblinPeonEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    public Identifier getTexture(GoblinPeonEntity goblinPeonEntity) {
        return TEXTURE;
    }
}
