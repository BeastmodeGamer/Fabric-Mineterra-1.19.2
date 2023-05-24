package net.andychen.mineterra.mixin.client;

import net.andychen.mineterra.item.custom.guns.GunItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(BipedEntityModel.class)
public abstract class BipedEntityModelMixin<T extends LivingEntity> extends AnimalModel<T> implements ModelWithArms, ModelWithHead {

    @Inject(method = "positionRightArm", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/CrossbowPosing;hold(Lnet/minecraft/client/model/ModelPart;Lnet/minecraft/client/model/ModelPart;Lnet/minecraft/client/model/ModelPart;Z)V", shift = At.Shift.BEFORE),cancellable = true)
    private void positionRightArmInject(T entity, CallbackInfo info) {
        if (entity.getStackInHand(Hand.MAIN_HAND).getItem() instanceof GunItem) {
            info.cancel();
        }
    }

    @Inject(method = "positionLeftArm", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/CrossbowPosing;hold(Lnet/minecraft/client/model/ModelPart;Lnet/minecraft/client/model/ModelPart;Lnet/minecraft/client/model/ModelPart;Z)V", shift = At.Shift.BEFORE),cancellable = true)
    private void positionLeftArmInject(T entity, CallbackInfo info) {
        if (entity.getStackInHand(Hand.MAIN_HAND).getItem() instanceof GunItem) {
            info.cancel();
        }
    }
}
