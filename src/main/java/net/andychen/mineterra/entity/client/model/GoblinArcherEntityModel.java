package net.andychen.mineterra.entity.client.model;

import net.andychen.mineterra.entity.custom.goblin.GoblinArcherEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.CrossbowPosing;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class GoblinArcherEntityModel<T extends GoblinArcherEntity> extends BipedEntityModel<T> {

    public GoblinArcherEntityModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = BipedEntityModel.getModelData(Dilation.NONE, 0.0F);
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create()
                .uv(0, 0).cuboid(-4.0F, -7.0F, -4.0F, 8.0F, 7.0F, 8.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 2.0F, 0.0F));
        head.addChild("nose", ModelPartBuilder.create()
                .uv(32, 10).cuboid(-1.0F, -3.2F, -4.25F, 2.0F, 1.0F, 4.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, -2.9061F, -0.6158F, 0.7854F, 0.0F, 0.0F));
        head.addChild("right_ear", ModelPartBuilder.create()
                .uv(32, 0).cuboid(-0.5F, -0.5F, -2.5F, 1.0F, 3.0F, 5.0F, new Dilation(0.0F)),
                ModelTransform.of(-4.5F, -4.5F, 1.0F, 0.3927F, -0.3927F, 0.0F));
        head.addChild("left_ear", ModelPartBuilder.create()
                .uv(32, 0).mirrored().cuboid(-0.5F, -1.5F, -2.5F, 1.0F, 3.0F, 5.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.of(4.5F, -3.5F, 1.0F, 0.3927F, 0.3927F, 0.0F));

        modelPartData.addChild("hat", ModelPartBuilder.create()
                .uv(0, 43).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 1.0F, 9.0F, new Dilation(0.0F))
                .uv(0, 31).cuboid(-4.5F, -7.0F, -4.5F, 9.0F, 2.0F, 10.0F, new Dilation(0.0F))
                .uv(0, 53).cuboid(-3.5F, -5.0F, 4.0F, 7.0F, 3.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 58).cuboid(-3.0F, -2.0F, 4.0F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 2.0F, 0.0F));

        ModelPartData quiver = modelPartData.addChild("quiver", ModelPartBuilder.create(),
                ModelTransform.pivot(2.0F, 7.5F, 3.0F));
        quiver.addChild("arrow_1", ModelPartBuilder.create()
                .uv(50, 33).cuboid(-1.0F, -8.5F, 0.0F, 3.0F, 3.0F, 0.0F, new Dilation(0.0F))
                .uv(38, 31).cuboid(-2.0F, -5.5F, -1.0F, 4.0F, 11.0F, 2.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3927F));
        quiver.addChild("arrow_2", ModelPartBuilder.create()
                .uv(50, 33).cuboid(-1.5F, -8.5F, -0.5F, 3.0F, 3.0F, 0.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.3927F));

        modelPartData.addChild("body", ModelPartBuilder.create()
                .uv(16, 16).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 11.0F, 4.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 2.0F, 0.0F));

        modelPartData.addChild("right_arm", ModelPartBuilder.create()
                .uv(40, 16).mirrored().cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 11.0F, 4.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.pivot(-5.0F, 4.0F, 0.0F));

        modelPartData.addChild("left_arm", ModelPartBuilder.create()
                .uv(40, 16).cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 11.0F, 4.0F, new Dilation(0.0F)),
                ModelTransform.pivot(5.0F, 4.0F, 0.0F));

        modelPartData.addChild("right_leg", ModelPartBuilder.create()
                .uv(0, 16).mirrored().cuboid(-2.1F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.pivot(-1.9F, 13.0F, 0.0F));

        modelPartData.addChild("left_leg", ModelPartBuilder.create()
                .uv(0, 16).cuboid(-1.9F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, new Dilation(0.0F)),
                ModelTransform.pivot(1.9F, 13.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    public void animateModel(T mobEntity, float f, float g, float h) {
        this.rightArmPose = ArmPose.EMPTY;
        this.leftArmPose = ArmPose.EMPTY;
        ItemStack itemStack = mobEntity.getStackInHand(Hand.MAIN_HAND);
        if (itemStack.isOf(Items.BOW) && mobEntity.isAttacking()) {
            if (mobEntity.getMainArm() == Arm.RIGHT) {
                this.rightArmPose = ArmPose.BOW_AND_ARROW;
            } else {
                this.leftArmPose = ArmPose.BOW_AND_ARROW;
            }
        }

        super.animateModel(mobEntity, f, g, h);
    }

    public void setAngles(T goblinEntity, float f, float g, float h, float i, float j) {
        super.setAngles(goblinEntity, f, g, h, i, j);
        this.head.visible = true;
        this.head.setPivot(0.0F, 2.0F, 0.0F);
        this.body.setPivot(0.0F, 2.0F, 0.0F);
        this.rightArm.setPivot(-5.0F, 4.0F, 0.0F);
        this.leftArm.setPivot(5.0F, 4.0F, 0.0F);
        this.rightLeg.setPivot(-1.9F, 13.0F, 0.0F);
        this.leftLeg.setPivot(1.9F, 13.0F, 0.0F);

        this.hat.pivotX = this.head.pivotX;
        this.hat.pivotY = this.head.pivotY;
        this.hat.pivotZ = this.head.pivotZ;
        this.hat.pitch = this.head.pitch;
        this.hat.yaw = this.head.yaw;
        this.hat.roll = this.head.roll;

        ItemStack itemStack = goblinEntity.getMainHandStack();
        if (goblinEntity.isAttacking() && (itemStack.isEmpty() || !itemStack.isOf(Items.BOW))) {
            float k = MathHelper.sin(this.handSwingProgress * 3.1415927F);
            float l = MathHelper.sin((1.0F - (1.0F - this.handSwingProgress) * (1.0F - this.handSwingProgress)) * 3.1415927F);
            this.rightArm.roll = 0.0F;
            this.leftArm.roll = 0.0F;
            this.rightArm.yaw = -(0.1F - k * 0.6F);
            this.leftArm.yaw = 0.1F - k * 0.6F;
            this.rightArm.pitch = -1.5707964F;
            this.leftArm.pitch = -1.5707964F;
            ModelPart var10000 = this.rightArm;
            var10000.pitch -= k * 1.2F - l * 0.4F;
            var10000 = this.leftArm;
            var10000.pitch -= k * 1.2F - l * 0.4F;
            CrossbowPosing.swingArms(this.rightArm, this.leftArm, h);
        }
    }

    public void setArmAngle(Arm arm, MatrixStack matrices) {
        float f = arm == Arm.RIGHT ? 1.0F : -1.0F;
        ModelPart modelPart = this.getArm(arm);
        modelPart.pivotX += f;
        modelPart.rotate(matrices);
        modelPart.pivotX -= f;
    }
}
