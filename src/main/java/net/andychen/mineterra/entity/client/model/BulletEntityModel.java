package net.andychen.mineterra.entity.client.model;

import net.andychen.mineterra.entity.custom.ranged.BulletEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class BulletEntityModel<T extends BulletEntity> extends SinglePartEntityModel<T> {
    private final ModelPart bullet;

    public BulletEntityModel(ModelPart root) {
        this.bullet = root.getChild("bullet");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();

        root.addChild("bullet", ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-0.5F, -1.0F, -5.0F, 1.0F, 1.0F, 10.0F)
                , ModelTransform.pivot(-3.75F, -1.0F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public ModelPart getPart() {
        return this.bullet;
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        float k = 1.0F;
        this.bullet.pitch = 0F;
        this.bullet.yaw = -1.575F + MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance / k;
    }
}
