package net.andychen.mineterra.effect;

import net.andychen.mineterra.MineTerra;
import net.andychen.mineterra.effect.custom.WrathStatusEffect;
import net.andychen.mineterra.entity.attribute.ModAttributes;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModStatusEffects {
    //public static final StatusEffect BLOOD_BUTCHERED = new BloodButcheredStatusEffect();
    public static final StatusEffect WRATH = new WrathStatusEffect(StatusEffectCategory.BENEFICIAL, 0xD8493F)
            .addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, "3159ca25-f3e5-476c-bdac-91b2e05a4428", 0.1D, EntityAttributeModifier.Operation.MULTIPLY_TOTAL)
            .addAttributeModifier(ModAttributes.GENERIC_RANGED_DAMAGE, "da221519-41dd-4acc-a7c6-d5dbb277f6a9", 0.1D, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);

    public static void registerEffects(){
        //Registry.register(Registry.STATUS_EFFECT, new Identifier(MineTerra.MOD_ID, "blood_butchered"), BLOOD_BUTCHERED);
        Registry.register(Registry.STATUS_EFFECT, new Identifier(MineTerra.MOD_ID, "wrath"), WRATH);
    }
}
