package net.andychen.mineterra.entity.custom.ranged;

import net.andychen.mineterra.entity.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

public class BulletEntity extends AmmoEntity {

    public BulletEntity(EntityType<? extends BulletEntity> entityType, World world) {
        super(entityType, world);
    }

    public BulletEntity(World world, LivingEntity owner) {
        super(ModEntities.BULLET, world, owner);
    }

    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        this.world.addParticle(ParticleTypes.SMOKE, false, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        if (!this.world.isClient) {
            this.discard();
        }
    }
}
