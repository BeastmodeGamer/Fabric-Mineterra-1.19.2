package net.andychen.mineterra.entity.client.model;

import net.andychen.mineterra.entity.custom.SpikyBallEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;

@Environment(EnvType.CLIENT)
public class SpikyBallEntityModel<T extends SpikyBallEntity> extends SinglePartEntityModel<T> {
    private final ModelPart root;
    private final ModelPart main;

    public SpikyBallEntityModel(ModelPart root) {
        this.root = root;
        this.main = root.getChild("main");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create()
                .uv(14, 0).cuboid(-0.5F, -5.0F, -0.5F, 1.0F, 5.0F, 1.0F, new Dilation(0.0F))
                .uv(7, 13).cuboid(-2.5F, -3.0F, -0.5F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-0.5F, -3.0F, -2.5F, 1.0F, 1.0F, 5.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 2.5F, 0.0F));

        main.addChild("spike_1", ModelPartBuilder.create()
                .uv(0, 0).cuboid(-0.5F, -0.5F, -2.5F, 1.0F, 1.0F, 5.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, -2.5F, 0.0F, 0.7854F, 0.7854F, 0.0F));

        main.addChild("spike_2", ModelPartBuilder.create()
                .uv(0, 0).cuboid(-0.5F, -0.5F, -2.5F, 1.0F, 1.0F, 5.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, -2.5F, 0.0F, -0.7854F, 0.7854F, 0.0F));

        main.addChild("spike_3", ModelPartBuilder.create()
                .uv(0, 0).cuboid(-0.5F, -0.5F, -2.5F, 1.0F, 1.0F, 5.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, -2.5F, 0.0F, -0.7854F, -0.7854F, 0.0F));

        main.addChild("spike_4", ModelPartBuilder.create()
                .uv(0, 0).cuboid(-0.5F, -0.5F, -2.5F, 1.0F, 1.0F, 5.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, -2.5F, 0.0F, 0.7854F, -0.7854F, 0.0F));

        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}
