package net.andychen.mineterra.item.custom.swords;

import net.andychen.mineterra.effect.ModStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

public class BloodButchererSwordItem extends SwordItem {

    public BloodButchererSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.addStatusEffect(new StatusEffectInstance(ModStatusEffects.BLOOD_BUTCHERED, 180, 0), attacker);
        return super.postHit(stack, target, attacker);
    }
}
