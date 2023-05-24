package net.andychen.mineterra.entity.custom;

import net.andychen.mineterra.entity.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class LanternEntity extends ProjectileEntity {

    public LanternEntity(EntityType<? extends LanternEntity> type, World world) {
        super(type, world);
        this.setNoGravity(true);
    }

    public LanternEntity(World world, LivingEntity owner) {
        this(ModEntities.LANTERN, world);
        this.setOwner(owner);
    }

    public void tick() {
        super.tick();
        this.setNoGravity(true);
        this.age();
        HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
        this.onCollision(hitResult);
        this.updateRotation();

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

        if (!this.hasNoGravity()) {
            Vec3d vec3d4 = this.getVelocity();
            this.setVelocity(vec3d4.x, vec3d4.y - 0.05000000074505806D, vec3d4.z);
        }

        this.setPosition(h, j, k);
        this.checkBlockCollision();


        float i = this.age > 100 ? 0.05F : 0.1F;
        this.setVelocity(this.getVelocity().getX(), 0.5D, this.getVelocity().getZ(), i, 1.0f);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
    }

    protected void age() {
        ++this.age;
        if (this.age >= 1500) {
            this.discard();
        }
    }

    public boolean shouldRender(double distance) {
        return distance < 16384.0D;
    }

    @Override
    protected void initDataTracker() {
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putShort("age", (short) this.age);
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.age = nbt.getShort("age");
    }

    public boolean isAttackable() {
        return false;
    }

    public int getAge() {
        return this.age;
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
