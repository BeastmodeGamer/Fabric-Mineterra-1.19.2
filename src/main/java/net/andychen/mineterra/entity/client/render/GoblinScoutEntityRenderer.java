package net.andychen.mineterra.entity.client.render;

import net.andychen.mineterra.MineTerra;
import net.andychen.mineterra.entity.client.ModEntityModelLayerRegistry;
import net.andychen.mineterra.entity.client.model.GoblinScoutEntityModel;
import net.andychen.mineterra.entity.custom.goblin.GoblinScoutEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class GoblinScoutEntityRenderer extends BipedEntityRenderer<GoblinScoutEntity, GoblinScoutEntityModel<GoblinScoutEntity>> {
    private static final Identifier TEXTURE = new Identifier(MineTerra.MOD_ID, "textures/entity/goblin_scout.png");

    public GoblinScoutEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new GoblinScoutEntityModel(context.getPart(ModEntityModelLayerRegistry.GOBLIN_SCOUT)), 0.5F);
    }

    public void render(GoblinScoutEntity goblinScoutEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(goblinScoutEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    public Identifier getTexture(GoblinScoutEntity goblinScoutEntity) {
        return TEXTURE;
    }
}
