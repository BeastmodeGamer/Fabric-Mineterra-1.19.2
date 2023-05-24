package net.andychen.mineterra.entity.custom.ranged;

import net.andychen.mineterra.entity.ModEntities;
import net.andychen.mineterra.particle.ModParticles;
import net.andychen.mineterra.sounds.ModSounds;
import net.andychen.mineterra.util.access.WorldMixinAccess;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class JesterArrowEntity extends AmmoEntity {

    public JesterArrowEntity(EntityType<? extends AmmoEntity> entityType, World world) {
        super(entityType, world);
    }

    public JesterArrowEntity(World world, LivingEntity owner) {
        super(ModEntities.JESTER_ARROW, world, owner);
    }

    public void tick() {
        super.tick();
        if (this.getPierceLevel() != (byte) 127) {
            this.setPierceLevel((byte) 127);
        }
        ((WorldMixinAccess) this.world).addParticle(ModParticles.MAGIC_DUST, true, this.getX(), this.getY(), this.getZ(),
                -0.1F, 0.1F, -0.1F, 0.1F, -0.1F, 0.1F, 1);
        ((WorldMixinAccess) this.world).addParticle(ModParticles.FALLEN_STAR_PURPLE, true, this.getX(), this.getY(), this.getZ(),
                -0.1F, 0.1F, -0.1F, 0.1F, -0.1F, 0.1F, 1);
        ((WorldMixinAccess) this.world).addParticle(ModParticles.FALLEN_STAR_YELLOW, true, this.getX(), this.getY(), this.getZ(),
                -0.1F, 0.1F, -0.1F, 0.1F, -0.1F, 0.1F, 1);
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
    }

    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        this.playSound(ModSounds.ENTITY_SPELL_DEATH, 1.0F, 1.0F);
        if (!this.world.isClient) {
            this.discard();
        }
    }

    @Override
    public void onRemoved() {
        this.addFallenStarParticles(this.world);
    }

    public void addFallenStarParticles(World world) {
        WorldMixinAccess worldMixinAccess = (WorldMixinAccess) world;
        worldMixinAccess.addParticle(ModParticles.MAGIC_DUST, true, this.getX(), this.getY(), this.getZ(),
                -0.3D, 0.3D, -0.3D, 0.3D, -0.3D, 0.3D, 10);
        worldMixinAccess.addParticle(ModParticles.FALLEN_STAR_PURPLE, true, this.getX(), this.getY(), this.getZ(),
                -0.3D, 0.3D, -0.3D, 0.3D, -0.3D, 0.3D, 10);
        worldMixinAccess.addParticle(ModParticles.FALLEN_STAR_YELLOW, true, this.getX(), this.getY(), this.getZ(),
                -0.3D, 0.3D, -0.3D, 0.3D, -0.3D, 0.3D, 10);

    }
}
