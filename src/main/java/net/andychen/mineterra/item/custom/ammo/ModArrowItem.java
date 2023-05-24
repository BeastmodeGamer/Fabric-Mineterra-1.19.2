package net.andychen.mineterra.item.custom.ammo;

import net.andychen.mineterra.entity.custom.ranged.AmmoEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ModArrowItem extends AmmoItem {

    public ModArrowItem(double ammoDamage, float velocity, int velocityMultiplier, Settings settings) {
        super(ammoDamage, velocity, velocityMultiplier, settings);
    }

    public AmmoEntity createArrow(World world, ItemStack stack, LivingEntity shooter) {
        return null;
    }
}
