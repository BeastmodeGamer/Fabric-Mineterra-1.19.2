package net.andychen.mineterra.util.access;

import net.andychen.mineterra.invasion.Invasion;
import net.andychen.mineterra.invasion.InvasionManager;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.BlockPos;

public interface ServerWorldMixinAccess {
    int addParticle(ParticleEffect particle, boolean alwaysSpawn, double x, double y, double z, double velocityX1, double velocityX2, double velocityY1, double velocityY2, double velocityZ1, double velocityZ2, int count);
    int addParticle(ParticleEffect particle, boolean alwaysSpawn, double x, double y, double z, double velocityX, double velocityY, double velocityZ, int count);
    InvasionManager getInvasionManager();
    Invasion getInvasionAt(BlockPos pos);
    boolean hasInvasionAt(BlockPos pos);
}
