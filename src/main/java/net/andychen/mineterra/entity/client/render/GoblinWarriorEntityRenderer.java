package net.andychen.mineterra.entity.client.render;

import net.andychen.mineterra.MineTerra;
import net.andychen.mineterra.entity.client.ModEntityModelLayerRegistry;
import net.andychen.mineterra.entity.client.model.GoblinWarriorEntityModel;
import net.andychen.mineterra.entity.custom.goblin.GoblinWarriorEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class GoblinWarriorEntityRenderer extends BipedEntityRenderer<GoblinWarriorEntity, GoblinWarriorEntityModel<GoblinWarriorEntity>> {
    private static final Identifier TEXTURE = new Identifier(MineTerra.MOD_ID, "textures/entity/goblin_warrior.png");

    public GoblinWarriorEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new GoblinWarriorEntityModel(context.getPart(ModEntityModelLayerRegistry.GOBLIN_WARRIOR)), 0.6F);
    }

    public void render(GoblinWarriorEntity goblinWarriorEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(goblinWarriorEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    protected void scale(GoblinWarriorEntity goblinWarriorEntity, MatrixStack matrixStack, float f) {
        matrixStack.scale(1.25f, 1.25f, 1.25f);
    }

    public Identifier getTexture(GoblinWarriorEntity goblinWarriorEntity) {
        return TEXTURE;
    }
}
