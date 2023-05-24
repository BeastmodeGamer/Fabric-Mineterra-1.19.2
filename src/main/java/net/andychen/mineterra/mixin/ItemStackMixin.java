package net.andychen.mineterra.mixin;

import net.andychen.mineterra.modifier.Modifier;
import net.andychen.mineterra.modifier.ModifierHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    private static final String MODIFIER_KEY = "Modifier";
    @Nullable
    @Shadow
    private NbtCompound nbt;

    /*
    public static void appendModifier(List<Text> tooltip, String modifier) {
        FabricRegistryBuilder.createSimple(Modifier.class, new Identifier(MineTerra.MOD_ID, "modifier"))
                .attribute(RegistryAttribute.MODDED)
                .buildAndRegister().getOrEmpty(ModifierHelper.getIdFromNbt(modifier)).ifPresent((e) -> {
            tooltip.add(e.getName());
        });
    }*/

    /*@Inject(method = "getTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/EntityAttributeModifier;getValue()D", shift = At.Shift.BY, by = 2))
    private void getTooltipInjector(@Nullable PlayerEntity player, TooltipContext context, CallbackInfoReturnable cir) {
    }*/

    public String getModifier() {
        return this.nbt != null ? this.nbt.getString("Modifier") : new String();
    }

    public boolean isModifiable() {
        if (!((ItemStack) (Object) this).getItem().isEnchantable(((ItemStack) (Object) this))) {
            return false;
        } else {
            return !this.hasEnchantments();
        }
    }

    public void addModifier(Modifier modifier) {
        ((ItemStack) (Object) this).getOrCreateNbt();
        if (!this.nbt.contains("Modifier", 9)) {
            this.nbt.putString("Modifier", String.valueOf(ModifierHelper.getModifierId(modifier)));
        }
    }

    public boolean hasEnchantments() {
        if (this.nbt != null && this.nbt.contains("Modifier", 9)) {
            return !this.nbt.getString("Modifier").isEmpty();
        } else {
            return false;
        }
    }
}
