package net.andychen.mineterra.modifier;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;

public class MeleeModifier extends Modifier {
    public final float damage;

    public MeleeModifier(float damage, Modifier.Rarity weight, EquipmentSlot... slots) {
        super(weight, ModifierTarget.MELEE, slots);
        this.damage = damage;
    }

    public float getAttackDamage() {
        return 1.0F;
    }

    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof AxeItem ? true : super.isAcceptableItem(stack);
    }
}
