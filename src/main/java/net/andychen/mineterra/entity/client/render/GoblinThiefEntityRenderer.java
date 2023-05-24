package net.andychen.mineterra.entity.client.render;

import net.andychen.mineterra.MineTerra;
import net.andychen.mineterra.entity.client.ModEntityModelLayerRegistry;
import net.andychen.mineterra.entity.client.model.GoblinThiefEntityModel;
import net.andychen.mineterra.entity.custom.goblin.GoblinThiefEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class GoblinThiefEntityRenderer extends BipedEntityRenderer<GoblinThiefEntity, GoblinThiefEntityModel<GoblinThiefEntity>> {
    private static final Identifier TEXTURE = new Identifier(MineTerra.MOD_ID, "textures/entity/goblin_thief.png");

    public GoblinThiefEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new GoblinThiefEntityModel(context.getPart(ModEntityModelLayerRegistry.GOBLIN_THIEF)), 0.5F);
    }

    public void render(GoblinThiefEntity goblinThiefEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(goblinThiefEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    public Identifier getTexture(GoblinThiefEntity goblinThiefEntity) {
        return TEXTURE;
    }
}
