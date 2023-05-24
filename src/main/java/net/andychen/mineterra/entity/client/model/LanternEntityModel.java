package net.andychen.mineterra.entity.client.model;

import net.andychen.mineterra.entity.custom.LanternEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class LanternEntityModel<T extends LanternEntity> extends SinglePartEntityModel<T> {
    private final ModelPart root;
    private final ModelPart model;

    public LanternEntityModel(ModelPart root) {
        this.root = root;
        this.model = root.getChild("model");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();
        root.addChild("model", ModelPartBuilder.create().uv(0, 0).cuboid(-3.5F, -8.0F, -3.5F, 7.0F, 9.0F, 7.0F, new Dilation(0.0F))
                .uv(0, 16).cuboid(-2.5F, -9.0F, -2.5F, 5.0F, 1.0F, 5.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-0.5F, -2.05F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(-1, 22).cuboid(-2.5F, 1.0F, -0.5F, 5.0F, 0.0F, 1.0F, new Dilation(0.0F))
                .uv(-5, 23).cuboid(-0.5F, 1.0F, -2.5F, 1.0F, 0.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.0F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        root.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
    }
}
