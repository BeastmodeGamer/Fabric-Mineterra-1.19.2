package net.andychen.mineterra.effect.custom;

import net.andychen.mineterra.particle.ModParticles;
import net.andychen.mineterra.util.access.ServerWorldMixinAccess;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import java.util.Random;

public class BloodButcheredStatusEffect extends StatusEffect {

    public BloodButcheredStatusEffect() {
        super(StatusEffectCategory.HARMFUL, 0xB3FC00);
    }

    // This method is called every tick to check whether it should apply the status effect or not
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    // This method is called when it applies the status effect. We implement custom functionality here.
    @Override
    public void applyUpdateEffect(LivingEntity livingEntity, int amplifier) {
        ServerWorld world = (ServerWorld) livingEntity.getWorld();
        this.spawnBloodParticles(world, livingEntity);
    }

    public void spawnBloodParticles(World world, LivingEntity livingEntity) {
        ServerWorldMixinAccess serverWorld = (ServerWorldMixinAccess) ((ServerWorld) world);
        Random random = new Random();
        serverWorld.addParticle(ModParticles.BLOOD, true, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(),
                random.nextDouble(-1.0D, 1.0D), random.nextDouble(-1.0D, 1.0D), random.nextDouble(-1.0D, 1.0D), 80);

    }
}