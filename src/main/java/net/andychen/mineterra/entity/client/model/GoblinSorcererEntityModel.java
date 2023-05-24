package net.andychen.mineterra.entity.client.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import net.andychen.mineterra.entity.custom.goblin.GoblinSorcererEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class GoblinSorcererEntityModel <T extends GoblinSorcererEntity> extends BipedEntityModel<T> {
    public final ModelPart robe;

    public GoblinSorcererEntityModel(ModelPart root) {
        super(root);
        this.hat.visible = false;
        this.robe = root.getChild("robe");
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
                        .uv(0, 16).cuboid(-2.1F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, new Dilation(0.0F)),
                ModelTransform.pivot(-1.9F, 13.0F, 0.0F));

        modelPartData.addChild("left_leg", ModelPartBuilder.create()
                        .uv(0, 16).mirrored().cuboid(-1.9F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.pivot(1.9F, 13.0F, 0.0F));

        modelPartData.addChild("robe", ModelPartBuilder.create()
                .uv(0, 31).cuboid(-4.5F, -1.0F, -1.75F, 9.0F, 12.0F, 4.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, -11.0F, 0.0F, 0.2182F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    protected Iterable<ModelPart> getBodyParts() {
        return Iterables.concat(super.getBodyParts(), ImmutableList.of(this.robe));
    }

    public void setAngles(T goblinEntity, float f, float g, float h, float i, float j) {
        super.setAngles(goblinEntity, f, g, h, i, j);
        this.head.visible = true;
        this.robe.visible = true;
        this.head.setPivot(0.0F, 2.0F, 0.0F);
        this.body.setPivot(0.0F, 2.0F, 0.0F);
        this.rightArm.setPivot(-5.0F, 4.0F, 0.0F);
        this.leftArm.setPivot(5.0F, 4.0F, 0.0F);
        this.rightLeg.setPivot(-1.9F, 13.0F, 0.0F);
        this.leftLeg.setPivot(1.9F, 13.0F, 0.0F);
        this.robe.setPivot(0.0F, 13.0F, 0.0F);

        if (goblinEntity.isAttacking()) {
            this.rightArm.pitch = this.rightArm.pitch + MathHelper.cos(h * 0.6662F) * 0.25F;
            this.rightArm.roll = this.rightArm.roll + MathHelper.sin(h * 0.6662F) * 0.25F;
            this.rightArm.yaw = this.rightArm.yaw + MathHelper.sin(h * 0.6662F) * 0.25F;
            this.leftArm.yaw = this.leftArm.yaw + 0.4561945F;
        }

        if (this.rightLeg.pitch > 0.0F) {
            this.robe.pitch = this.rightLeg.pitch + 0.2182F;
        } else if (this.leftLeg.pitch > 0.0F) {
            this.robe.pitch = this.leftLeg.pitch + 0.2182F;
        } else {
            this.robe.pitch = 0.2182F;
        }
    }

    public void animateModel(T mobEntity, float f, float g, float h) {
        this.rightArmPose = ArmPose.EMPTY;
        this.leftArmPose = ArmPose.EMPTY;
        if (mobEntity.isAttacking()) {
            if (mobEntity.getMainArm() == Arm.RIGHT) {
                this.rightArmPose = ArmPose.BOW_AND_ARROW;
            } else {
                this.leftArmPose = ArmPose.BOW_AND_ARROW;
            }
        }

        super.animateModel(mobEntity, f, g, h);
    }
}
