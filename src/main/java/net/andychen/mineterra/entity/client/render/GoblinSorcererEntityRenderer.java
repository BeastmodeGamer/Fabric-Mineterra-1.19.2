package net.andychen.mineterra.entity.client.render;

import net.andychen.mineterra.MineTerra;
import net.andychen.mineterra.entity.client.ModEntityModelLayerRegistry;
import net.andychen.mineterra.entity.client.model.GoblinSorcererEntityModel;
import net.andychen.mineterra.entity.custom.goblin.GoblinSorcererEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class GoblinSorcererEntityRenderer extends BipedEntityRenderer<GoblinSorcererEntity, GoblinSorcererEntityModel<GoblinSorcererEntity>> {
    private static final Identifier TEXTURE = new Identifier(MineTerra.MOD_ID, "textures/entity/goblin_sorcerer.png");

    public GoblinSorcererEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new GoblinSorcererEntityModel(context.getPart(ModEntityModelLayerRegistry.GOBLIN_SORCERER)), 0.5F);
    }

    public void render(GoblinSorcererEntity goblinSorcererEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(goblinSorcererEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    public Identifier getTexture(GoblinSorcererEntity goblinSorcererEntity) {
        return TEXTURE;
    }
}
