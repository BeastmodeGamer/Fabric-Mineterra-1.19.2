package net.andychen.mineterra.particle.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;

public class BloodParticle extends SpriteBillboardParticle {
    private final SpriteProvider spriteProvider;

    protected BloodParticle(ClientWorld level, double xCoord, double yCoord, double zCoord,
                              SpriteProvider spriteSet, double xd, double yd, double zd) {
        super(level, xCoord, yCoord, zCoord, xd, yd, zd);
        this.velocityMultiplier = 0.8F;
        this.velocityX = this.velocityX * 0.009999999776482582D;
        this.velocityY = this.velocityY * 0.009999999776482582D;
        this.velocityZ = this.velocityZ * 0.009999999776482582D;
        this.spriteProvider = spriteSet;
        this.x = xd;
        this.y = yd;
        this.z = zd;
        this.scale *= 1F;
        this.setSpriteForAge(spriteSet);
        this.maxAge = 100;
        this.red = 1f;
        this.green = 1f;
        this.blue = 1f;
    }

    public void tick() {
        super.tick();
        if (this.age >= this.maxAge) {
            this.fadeOut();
        }
    }

    private void fadeOut() {
        this.alpha = (-(1/(float)maxAge) * age + 1);
    }

    public void move(double dx, double dy, double dz) {
        this.setBoundingBox(this.getBoundingBox().offset(dx, dy, dz));
        this.repositionFromBoundingBox();
    }

    public float getSize(float tickDelta) {
        return this.scale * MathHelper.clamp(((float)this.age + tickDelta) / (float)this.maxAge * 32.0F, 0.0F, 1.0F);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider sprites;

        public Factory(SpriteProvider spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(DefaultParticleType particleType, ClientWorld level, double x, double y, double z,
                                       double dx, double dy, double dz) {
            return new BloodParticle(level, x, y, z, this.sprites, dx, dy, dz);
        }
    }
}

