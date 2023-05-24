package net.andychen.mineterra.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import org.jetbrains.annotations.Nullable;

public class ModDamageSource {
    public static DamageSource fallenStar(Entity projectile, @Nullable LivingEntity attacker) {
        return new ProjectileDamageSource("fallenStar", projectile, attacker).setBypassesArmor();
    }
}
