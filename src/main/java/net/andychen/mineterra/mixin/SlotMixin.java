package net.andychen.mineterra.mixin;

import net.andychen.mineterra.item.custom.TestItem;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slot.class)
public abstract class SlotMixin {

    @Inject(method = "getMaxItemCount(Lnet/minecraft/item/ItemStack;)I", at = @At(value = "HEAD"), cancellable = true)
    private void getMaxItemCountInjector(ItemStack stack, CallbackInfoReturnable cir) {
        if (stack.getItem() instanceof TestItem) {
            cir.setReturnValue(Math.min(100, stack.getMaxCount()));
        }
    }
}
