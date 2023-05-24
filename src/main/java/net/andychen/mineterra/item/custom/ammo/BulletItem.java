package net.andychen.mineterra.item.custom.ammo;

import net.andychen.mineterra.entity.custom.ranged.BulletEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BulletItem extends AmmoItem {

    public BulletItem(double ammoDamage, float velocity, int velocityMultiplier, Settings settings) {
        super(ammoDamage, velocity, velocityMultiplier, settings);
    }

    public BulletEntity createAmmo(World world, ItemStack stack, LivingEntity shooter) {
        return new BulletEntity(world, shooter);
    }
}
