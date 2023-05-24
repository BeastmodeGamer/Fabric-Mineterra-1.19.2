package net.andychen.mineterra.mixin;

import net.andychen.mineterra.entity.attribute.ModAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Redirect(method = "createLivingAttributes", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/DefaultAttributeContainer;builder()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;"))
    private static DefaultAttributeContainer.Builder createLivingAttributesRedirector() {
        return DefaultAttributeContainer.builder().add(ModAttributes.GENERIC_CRIT_CHANCE).add(ModAttributes.GENERIC_RANGED_DAMAGE);
    }

    /*@Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;applyDamage(Lnet/minecraft/entity/damage/DamageSource;F)V", shift = At.Shift.BY, by = 3, ordinal = 1))
    protected void damageInject(DamageSource source, float amount, CallbackInfoReturnable cir) {
    }*/
}
