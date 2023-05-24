package net.andychen.mineterra.entity.client.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;

@Environment(EnvType.CLIENT)
public class ModArmorModel<T extends LivingEntity> extends BipedEntityModel<T> {
    public final ModelPart helmet;
    public final ModelPart hatLayer;
    public final ModelPart chestplate;
    public final ModelPart belt;
    public final ModelPart rightArmor;
    public final ModelPart leftArmor;
    public final ModelPart rightLegging;
    public final ModelPart leftLegging;
    public final ModelPart rightBoot;
    public final ModelPart leftBoot;

    public ModArmorModel(ModelPart root) {
        super(root);
        this.helmet = this.head.getChild("helmet");
        this.hatLayer = this.head.getChild("hat_layer");
        this.chestplate = this.body.getChild("chestplate");
        this.belt = this.body.getChild("belt");
        this.rightArmor = this.rightArm.getChild("right_armor");
        this.leftArmor = this.leftArm.getChild("left_armor");
        this.rightLegging = this.rightLeg.getChild("right_legging");
        this.leftLegging = this.leftLeg.getChild("left_legging");
        this.rightBoot = this.rightLeg.getChild("right_boot");
        this.leftBoot = this.leftLeg.getChild("left_boot");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = BipedEntityModel.getModelData(Dilation.NONE, 0.0F);
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create(),
                ModelTransform.NONE);
        head.addChild("helmet", ModelPartBuilder.create().uv(0, 0)
                        .cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(1.0f)),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        head.addChild("hat_layer", ModelPartBuilder.create()
                        .uv(32, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(1.5f))
                        .uv(0, 17).cuboid(0.0F, -17.0F, -7.0F, 0.0F, 14.0F, 15.0F)
                        .uv(31, 33).cuboid(-2.0F, -10.5F, -5.5F, 4.0F, 6.0F, 0.0F),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create(),
                ModelTransform.NONE);
        body.addChild("chestplate", ModelPartBuilder.create()
                        .uv(16, 16).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(1.01f)),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        body.addChild("belt", ModelPartBuilder.create()
                        .uv(16, 48).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.51f)),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData rightArm = modelPartData.addChild("right_arm", ModelPartBuilder.create(),
                ModelTransform.NONE);
        rightArm.addChild("right_armor", ModelPartBuilder.create()
                        .uv(40, 32).cuboid(2.0F, -4.25F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.3f))
                        .uv(40, 16).cuboid(2.0F, -4.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(1.0f))
                        .uv(40, 48).cuboid(2.0F, -4.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.7f)),
                ModelTransform.pivot(-5.0F, 2.0F, 0.0F));

        ModelPartData leftArm = modelPartData.addChild("left_arm", ModelPartBuilder.create(),
                ModelTransform.NONE);
        leftArm.addChild("left_armor", ModelPartBuilder.create()
                        .uv(40, 32).mirrored().cuboid(-6.0F, -4.25F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.3f))
                        .uv(40, 16).mirrored().cuboid(-6.0F, -4.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(1.0f))
                        .uv(40, 48).mirrored().cuboid(-6.0F, -4.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.7f)),
                ModelTransform.pivot(5.0F, 2.0F, 0.0F));

        ModelPartData rightLeg = modelPartData.addChild("right_leg", ModelPartBuilder.create(),
                ModelTransform.NONE);
        rightLeg.addChild("right_legging", ModelPartBuilder.create()
                        .uv(0, 48).cuboid(0.0F, -12.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.5f)),
                ModelTransform.pivot(-1.9F, 12.0F, 0.0F));
        rightLeg.addChild("right_boot", ModelPartBuilder.create()
                        .uv(0, 16).cuboid(0.0F, -12.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(1.0f)),
                ModelTransform.pivot(-1.9F, 12.0F, 0.0F));

        ModelPartData leftLeg = modelPartData.addChild("left_leg", ModelPartBuilder.create(),
                ModelTransform.NONE);
        leftLeg.addChild("left_legging", ModelPartBuilder.create()
                        .uv(0, 48).mirrored().cuboid(0.0F, -12.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.5f)),
                ModelTransform.pivot(-1.9F, 12.0F, 0.0F));
        leftLeg.addChild("left_boot", ModelPartBuilder.create()
                        .uv(0, 16).mirrored().cuboid(0.0F, -12.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(1.0f)),
                ModelTransform.pivot(-1.9F, 12.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    public void setVisible(boolean visible) {
        this.helmet.visible = visible;
        this.hatLayer.visible = visible;
        this.hat.visible = visible;
        this.chestplate.visible = visible;
        this.belt.visible = visible;
        this.rightArmor.visible = visible;
        this.leftArmor.visible = visible;
        this.rightLegging.visible = visible;
        this.leftLegging.visible = visible;
        this.rightBoot.visible = visible;
        this.leftBoot.visible = visible;
    }
}
