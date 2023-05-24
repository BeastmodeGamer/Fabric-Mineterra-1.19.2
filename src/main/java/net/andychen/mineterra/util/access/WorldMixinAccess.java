package net.andychen.mineterra.util.access;

import net.minecraft.particle.ParticleEffect;

public interface WorldMixinAccess {
    void addParticle(ParticleEffect parameters, boolean alwaysSpawn, double x, double y, double z, double velocityX1, double velocityX2,
                     double velocityY1, double velocityY2, double velocityZ1, double velocityZ2, int count);
}
