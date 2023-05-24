package net.andychen.mineterra.mixin;

import net.andychen.mineterra.util.access.WorldMixinAccess;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(World.class)
public abstract class WorldMixin implements WorldMixinAccess {

    public void addParticle(ParticleEffect parameters, boolean alwaysSpawn, double x, double y, double z, double velocityX1, double velocityX2,
                            double velocityY1, double velocityY2, double velocityZ1, double velocityZ2, int count) {
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            ((World) (Object) this).addParticle(parameters, alwaysSpawn, x, y, z, random.nextDouble(velocityX1, velocityX2),
                    random.nextDouble(velocityY1, velocityY2), random.nextDouble(velocityZ1, velocityZ2));
        }
    }
}
