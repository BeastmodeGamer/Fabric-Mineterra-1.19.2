package net.andychen.mineterra.entity.custom;

import net.andychen.mineterra.entity.ModEntities;
import net.andychen.mineterra.particle.ModParticles;
import net.andychen.mineterra.sounds.ModSounds;
import net.andychen.mineterra.util.access.WorldMixinAccess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ChaosBallEntity extends ProjectileEntity {

    public ChaosBallEntity(EntityType<? extends ChaosBallEntity> entityType, World world) {
        super(entityType, world);
    }

    public ChaosBallEntity(World world, LivingEntity owner) {
        this(ModEntities.CHAOS_BALL, world);
        this.setNoGravity(true);
        this.setOwner(owner);
        this.setPosition(owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ());
    }

    @Override
    public void tick() {
        super.tick();
        HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
        this.onCollision(hitResult);
        this.updateRotation();
        this.age();

        Vec3d vec3d = this.getVelocity();
        double e = vec3d.x;
        double f = vec3d.y;
        double g = vec3d.z;

        double h = this.getX() + e;
        double j = this.getY() + f;
        double k = this.getZ() + g;
        double l = vec3d.horizontalLength();

        this.setPitch((float) (MathHelper.atan2(f, l) * 57.2957763671875D));
        this.setPitch(updateRotation(this.prevPitch, this.getPitch()));
        this.setYaw(updateRotation(this.prevYaw, this.getYaw()));
        float m = 0.99F;

        this.setVelocity(vec3d.multiply((double) m));
        if (!this.hasNoGravity()) {
            Vec3d vec3d4 = this.getVelocity();
            this.setVelocity(vec3d4.x, vec3d4.y - 0.05000000074505806D, vec3d4.z);
        }

        this.setPosition(h, j, k);
        this.checkBlockCollision();

        ((WorldMixinAccess) this.world).addParticle(ModParticles.SHADOW_FLAME, false, this.getX(), this.getY() + 0.15D, this.getZ(),
                -0.05F, 0.05F, -0.05F, 0.05F, -0.05F, 0.05F, 5);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity owner = this.getOwner();
        if (owner instanceof LivingEntity) {
            if (!entityHitResult.getEntity().isTeammate(owner)) {
                entityHitResult.getEntity().damage(DamageSource.mobProjectile(this, (LivingEntity) owner).setProjectile(), 4.0F);
                if (!this.world.isClient) {
                    ((ServerWorld)this.world).spawnParticles(ModParticles.SHADOW_FLAME, this.getX(), this.getY(), this.getZ(), 15, 0.2D, 0.2D, 0.2D, 0.0D);
                    this.playSound(ModSounds.ENTITY_SPELL_DEATH, 1.0F, 1.0F);
                }
                this.discard();
            }
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!this.world.isClient) {
            ((ServerWorld)this.world).spawnParticles(ModParticles.SHADOW_FLAME, this.getX(), this.getY(), this.getZ(), 15, 0.2D, 0.2D, 0.2D, 0.0D);
            this.playSound(ModSounds.ENTITY_SPELL_DEATH, 1.0F, 1.0F);
            this.discard();
        }
    }

    public boolean damage(DamageSource source, float amount) {
        if (!this.world.isClient) {
            this.playSound(ModSounds.ENTITY_SPELL_DEATH, 1.0F, 1.0F);
            ((ServerWorld)this.world).spawnParticles(ModParticles.SHADOW_FLAME, this.getX(), this.getY(), this.getZ(), 15, 0.2D, 0.2D, 0.2D, 0.0D);
            this.discard();
        }
        return true;
    }

    protected void age() {
        ++this.age;
        if (this.age > 200) {
            this.discard();
        }
    }

    public boolean isAttackable() {
        return true;
    }

    public boolean canHit() {
        return true;
    }

    public boolean isOnFire() {
        return false;
    }

    @Override
    protected void initDataTracker() {
    }

    public boolean shouldRender(double distance) {
        return distance < 16384.0D;
    }

    public float getBrightnessAtEyes() {
        return 1.0F;
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putShort("age", (short) this.age);
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.age = nbt.getShort("age");
    }

    @Override
    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        int i = packet.getId();
        double d = packet.getX();
        double e = packet.getY();
        double f = packet.getZ();
        this.updateTrackedPosition(d, e, f);
        this.refreshPositionAfterTeleport(d, e, f);
        this.setPitch((float) (packet.getPitch() * 360) / 256.0f);
        this.setYaw((float) (packet.getYaw() * 360) / 256.0f);
        this.setId(i);
        this.setUuid(packet.getUuid());
        Entity entity = this.world.getEntityById(packet.getEntityData());
        if (entity != null) {
            this.setOwner(entity);
        }
        this.setVelocity(d, e, f);
    }
}
