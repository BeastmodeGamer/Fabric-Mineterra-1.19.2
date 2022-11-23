package net.andychen.mineterra.effect;

import net.andychen.mineterra.MineTerra;
import net.andychen.mineterra.effect.custom.BloodButcheredStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModStatusEffects {
    public static final StatusEffect BLOOD_BUTCHERED = new BloodButcheredStatusEffect();

    public static void registerEffects(){
        Registry.register(Registry.STATUS_EFFECT, new Identifier(MineTerra.MOD_ID, "blood_butchered"), BLOOD_BUTCHERED);
    }
}
