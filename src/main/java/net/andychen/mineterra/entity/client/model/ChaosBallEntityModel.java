package net.andychen.mineterra.entity.client.model;

import net.andychen.mineterra.entity.custom.ChaosBallEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;

@Environment(EnvType.CLIENT)
public class ChaosBallEntityModel<T extends ChaosBallEntity> extends SinglePartEntityModel<T> {
    private final ModelPart root;
    private final ModelPart ball;

    public ChaosBallEntityModel(ModelPart root) {
        this.root = root;
        this.ball = root.getChild("ball");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild("ball", ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3.0F, -11.0F, -3.0F, 6.0F, 6.0F, 6.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 8.0F, 0.0F));

        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void setAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }
}