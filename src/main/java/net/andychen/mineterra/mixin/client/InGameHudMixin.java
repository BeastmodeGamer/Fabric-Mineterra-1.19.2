package net.andychen.mineterra.mixin.client;

import net.andychen.mineterra.mana.ManaHudRender;
import net.andychen.mineterra.mana.ManaManager;
import net.andychen.mineterra.mana.ManaManagerAccess;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Shadow
    private int ticks;
    @Shadow
    private int scaledWidth;
    @Shadow
    private int scaledHeight;

    @Inject(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V", ordinal = 1))
    private void renderStatusBarsMixin(MatrixStack matrices, CallbackInfo info) {
        ManaHudRender.renderManaBar(matrices, this.getCameraPlayer(), scaledWidth, scaledHeight, ticks);
    }

    @Inject(method = "getHeartRows", at = @At(value = "HEAD"), cancellable = true)
    private void getHeartRowsMixin(int heartCount, CallbackInfoReturnable<Integer> info) {
        ManaManager manaManager = ((ManaManagerAccess) this.getCameraPlayer()).getManaManager();
        if (manaManager.getMaxMana() > 20) {
            info.setReturnValue((int) Math.ceil((double) heartCount / 10.0D) + 2);
        } else {
            info.setReturnValue((int) Math.ceil((double) heartCount / 10.0D) + 1);
        }
    }

    @Shadow
    private PlayerEntity getCameraPlayer() {
        return null;
    }

}
