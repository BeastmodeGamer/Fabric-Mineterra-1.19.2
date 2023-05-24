package net.andychen.mineterra.entity.client.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import net.andychen.mineterra.entity.custom.goblin.GoblinScoutEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;

@Environment(EnvType.CLIENT)
public class GoblinScoutEntityModel<T extends GoblinScoutEntity> extends BipedEntityModel<T> {
    public final ModelPart sleepingBag;

    public GoblinScoutEntityModel(ModelPart root) {
        super(root);
        this.sleepingBag = root.getChild("sleeping_bag");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = BipedEntityModel.getModelData(Dilation.NONE, 0.0F);
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create()
                .uv(0, 1).cuboid(-4.0F, -6.0F, -4.0F, 8.0F, 6.0F, 8.0F, new Dilation(0.0F)),
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

        ModelPartData hat = modelPartData.addChild("hat", ModelPartBuilder.create()
                .uv(36, 39).cuboid(-4.5F, -5.0F, -3.0F, 1.0F, 6.0F, 2.0F, new Dilation(0.0F))
                .uv(27, 31).cuboid(-4.5F, -5.0F, 0.5F, 9.0F, 4.0F, 4.0F, new Dilation(0.0F))
                .uv(0, 31).cuboid(-4.5F, -7.0F, -4.5F, 9.0F, 2.0F, 9.0F, new Dilation(0.0F))
                .uv(0, 42).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 1.0F, 8.0F, new Dilation(0.0F))
                .uv(36, 39).cuboid(3.5F, -5.0F, -3.0F, 1.0F, 6.0F, 2.0F, new Dilation(0.0F))
                .uv(30, 42).cuboid(-4.5F, 0.0F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(30, 42).cuboid(3.5F, 0.0F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(10, 51).cuboid(1.0F, -9.0F, -5.0F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(10, 51).cuboid(-3.0F, -9.0F, -5.0F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(16, 51).cuboid(1.0F, -6.01F, -5.0F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(16, 51).cuboid(-3.0F, -6.01F, -5.0F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 51).cuboid(0.0F, -8.0F, -5.0F, 4.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 51).cuboid(-4.0F, -8.0F, -5.0F, 4.0F, 2.0F, 1.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 2.0F, 0.0F));
        hat.addChild("flaps", ModelPartBuilder.create()
                .uv(42, 39).cuboid(7.49F, -4.0F, -2.0F, 1.0F, 7.0F, 4.0F, new Dilation(0.0F))
                .uv(42, 39).cuboid(-0.49F, -4.0F, -2.0F, 1.0F, 7.0F, 4.0F, new Dilation(0.0F)),
                ModelTransform.of(-4.0F, -2.5793F, 0.1152F, -0.3927F, 0.0F, 0.0F));

        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create()
                .uv(16, 16).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 11.0F, 4.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 2.0F, 0.0F));

        body.addChild("backpack", ModelPartBuilder.create()
                .uv(0, 66).cuboid(-4.5F, -5.0F, -2.75F, 9.0F, 5.0F, 4.0F, new Dilation(0.0F))
                .uv(0, 55).cuboid(-5.0F, 0.0F, -3.25F, 10.0F, 6.0F, 5.0F, new Dilation(0.0F))
                .uv(0, 75).cuboid(-4.0F, 1.0F, 1.75F, 8.0F, 5.0F, 1.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 5.0F, 4.75F));
        modelPartData.addChild("sleeping_bag", ModelPartBuilder.create()
                .uv(0, 81).cuboid(-5.5F, 0.0F, -2.0F, 11.0F, 4.0F, 4.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 13.0F, 4.0F));

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

        return TexturedModelData.of(modelData, 128, 128);
    }

    protected Iterable<ModelPart> getBodyParts() {
        return Iterables.concat(super.getBodyParts(), ImmutableList.of(this.sleepingBag));
    }

    public void setAngles(T goblinEntity, float f, float g, float h, float i, float j) {
        super.setAngles(goblinEntity, f, g, h, i, j);
        this.head.visible = true;
        this.sleepingBag.visible = true;
        this.head.setPivot(0.0F, 2.0F, 0.0F);
        this.body.setPivot(0.0F, 2.0F, 0.0F);
        this.sleepingBag.setPivot(0.0F, 13.0F, 4.0F);
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

        if (this.rightLeg.pitch > 0.0F) {
            this.sleepingBag.pitch = this.rightLeg.pitch;
        } else if (this.leftLeg.pitch > 0.0F) {
            this.sleepingBag.pitch = this.leftLeg.pitch;
        } else {
            this.sleepingBag.pitch = 0.0F;
        }
    }
}
