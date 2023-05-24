package net.andychen.mineterra.entity.custom.ranged;

import net.minecraft.block.AbstractBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class AmmoEntity extends ProjectileEntity {
    private static final TrackedData<Byte> PROJECTILE_FLAGS = DataTracker.registerData(AmmoEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static final TrackedData<Byte> PIERCE_LEVEL = DataTracker.registerData(AmmoEntity.class, TrackedDataHandlerRegistry.BYTE);
    private int life;
    private double damage;
    private int piercedEntities = 0;

    public AmmoEntity(EntityType<? extends AmmoEntity> entityType, World world) {
        super(entityType, world);
    }

    public AmmoEntity(EntityType<? extends AmmoEntity> entityType, World world, LivingEntity owner) {
        this(entityType, world);
        this.setNoGravity(true);
        this.setOwner(owner);
        this.setPosition(owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ());
    }

    public void tick() {
        super.tick();
        if (!this.world.isClient) {
            this.age();
        }

        Vec3d vec3d = this.getVelocity();
        HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
        this.onCollision(hitResult);
        double d = this.getX() + vec3d.x;
        double e = this.getY() + vec3d.y;
        double f = this.getZ() + vec3d.z;
        this.updateRotation();
        if (this.world.getStatesInBox(this.getBoundingBox()).noneMatch(AbstractBlock.AbstractBlockState::isAir) && !this.isInsideWaterOrBubbleColumn()) {
            this.discard();
        } else {
            this.setVelocity(vec3d.multiply(0.9900000095367432D));
            if (!this.hasNoGravity()) {
                this.setVelocity(this.getVelocity().add(0.0D, -0.05999999865889549D, 0.0D));
            }

            this.setPosition(d, e, f);
        }

        if (this.isCritical()) {
            this.world.addParticle(ParticleTypes.CRIT, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        }
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if (this.piercedEntities >= this.getPierceLevel()) {
            this.discard();
            return;
        }
        this.piercedEntities += 1;

        Entity owner = this.getOwner();
        if (owner instanceof LivingEntity) {
            entityHitResult.getEntity().damage(DamageSource.mobProjectile(this, (LivingEntity) owner).setProjectile(), (float) this.damage);
            /*if (entityHitResult.getEntity() instanceof EndCrystalEntity) {
                entityHitResult.getEntity().damage(DamageSource.GENERIC,  (float) this.rangedDamage);
            }*/
        }
    }

    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(PROJECTILE_FLAGS, (byte) 0);
        this.dataTracker.startTracking(PIERCE_LEVEL, (byte)1);
    }

    private void setProjectileFlag(int index, boolean flag) {
        byte b = (Byte) this.dataTracker.get(PROJECTILE_FLAGS);
        if (flag) {
            this.dataTracker.set(PROJECTILE_FLAGS, (byte) (b | index));
        } else {
            this.dataTracker.set(PROJECTILE_FLAGS, (byte) (b & ~index));
        }
    }

    public double getDamage() {
        return this.damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void calcCritical(double critChance) {
        if ((double) random.nextInt(100) / 100.0D <= critChance) {
            this.setDamage(this.damage * 2);
            this.setCritical(true);
        }
    }

    public boolean isCritical() {
        byte b = (Byte) this.dataTracker.get(PROJECTILE_FLAGS);
        return (b & 1) != 0;
    }

    public void setCritical(boolean critical) {
        this.setProjectileFlag(1, critical);
    }

    public byte getPierceLevel() {
        return (Byte)this.dataTracker.get(PIERCE_LEVEL);
    }

    public void setPierceLevel(byte level) {
        this.dataTracker.set(PIERCE_LEVEL, level);
    }

    public boolean isAttackable() {
        return false;
    }

    public boolean isFireImmune() {
        return true;
    }

    public boolean shouldRender(double distance) {
        return distance < 16384.0D;
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
            if (this.isCritical()) {
                this.playSound(SoundEvents.ENTITY_ARROW_HIT_PLAYER,1.0F, 1.5F);
            }
        }
        this.setVelocity(d, e, f);
    }

    protected void age() {
        ++this.life;
        if (this.life >= 100) {
            this.discard();
        }
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putShort("life", (short) this.life);
        nbt.putDouble("damage", this.damage);
        nbt.putBoolean("crit", this.isCritical());
        nbt.putByte("PierceLevel", this.getPierceLevel());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.life = nbt.getShort("life");
        if (nbt.contains("damage", 99)) {
            this.damage = nbt.getDouble("damage");
        }
        this.setCritical(nbt.getBoolean("crit"));
        this.setPierceLevel(nbt.getByte("PierceLevel"));
    }
}
