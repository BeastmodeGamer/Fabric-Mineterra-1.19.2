package net.andychen.mineterra.mixin.client;

import net.andychen.mineterra.entity.client.ModEntityModelLayerRegistry;
import net.andychen.mineterra.entity.client.armor.ModArmorFeatureRenderer;
import net.andychen.mineterra.entity.client.model.ModArmorModel;
import net.andychen.mineterra.item.custom.guns.GunItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

    public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    protected void PlayerEntityRendererMixinConstructor(EntityRendererFactory.Context ctx, boolean slim, CallbackInfo info) {
        this.addFeature(new ModArmorFeatureRenderer(this, new ModArmorModel(ctx.getPart(ModEntityModelLayerRegistry.MOD_ARMOR))));
    }

    @Inject(method = "getArmPose", at = @At("TAIL"), cancellable = true)
    private static void getArmPoseInjector(AbstractClientPlayerEntity player, Hand hand, CallbackInfoReturnable cir) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.getItem() instanceof GunItem) {
            cir.setReturnValue(BipedEntityModel.ArmPose.CROSSBOW_HOLD);
        }
        if (itemStack.getItem() instanceof GunItem && player.isUsingItem()) {
            cir.setReturnValue(BipedEntityModel.ArmPose.CROSSBOW_HOLD);
        }
    }
}
