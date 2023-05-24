package net.andychen.mineterra.entity.custom;

import net.andychen.mineterra.entity.ModEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

public class SpikyBallEntity extends ProjectileEntity {
    public int age;
    protected boolean inGround;
    protected int inGroundTime;
    private BlockState inBlockState;

    public SpikyBallEntity(EntityType<? extends SpikyBallEntity> entityType, World world) {
        super(entityType, world);
    }

    public SpikyBallEntity(World world, LivingEntity owner) {
        this(ModEntities.SPIKY_BALL, world);
        this.setOwner(owner);
        this.setPosition(owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ());
    }

    public void updateTrackedPositionAndAngles(double x, double y, double z, float yaw, float pitch, int interpolationSteps, boolean interpolate) {
        this.setPosition(x, y, z);
        this.setRotation(yaw, pitch);
    }

    public void tick() {
        super.tick();
        Vec3d vec3d = this.getVelocity();
        if (this.prevPitch == 0.0F && this.prevYaw == 0.0F) {
            double d = vec3d.horizontalLength();
            this.setYaw((float) (MathHelper.atan2(vec3d.x, vec3d.z) * 57.2957763671875D));
            this.setPitch((float) (MathHelper.atan2(vec3d.y, d) * 57.2957763671875D));
            this.prevYaw = this.getYaw();
            this.prevPitch = this.getPitch();
        }

        if (!this.world.isClient) {
            this.age();
        }

        BlockPos blockPos = this.getBlockPos();
        BlockState blockState = this.world.getBlockState(blockPos);
        Vec3d vec3d2;
        if (!blockState.isAir()) {
            VoxelShape voxelShape = blockState.getCollisionShape(this.world, blockPos);
            if (!voxelShape.isEmpty()) {
                vec3d2 = this.getPos();
                Iterator var7 = voxelShape.getBoundingBoxes().iterator();

                while (var7.hasNext()) {
                    Box box = (Box) var7.next();
                    if (box.offset(blockPos).contains(vec3d2)) {
                        this.inGround = true;
                        break;
                    }
                }
            }
        }

        if (this.isTouchingWaterOrRain() || blockState.isOf(Blocks.POWDER_SNOW)) {
            this.extinguish();
        }

        HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
        this.onCollision(hitResult);

        if (this.inGround) {
            if (this.inBlockState != blockState && this.shouldFall()) {
                this.fall();
            }

            ++this.inGroundTime;
        } else {
            this.inGroundTime = 0;
            //this.getOwner().sendMessage(Text.translatable("GRAVITYING"));

            vec3d = this.getVelocity();
            double e = vec3d.x;
            double f = vec3d.y;
            double g = vec3d.z;

            double h = this.getX() + e;
            double j = this.getY() + f;
            double k = this.getZ() + g;
            double l = vec3d.horizontalLength();

            //this.getOwner().sendMessage(Text.translatable("X: " + e));
            //this.getOwner().sendMessage(Text.translatable("Z: " + g));
            // this.getOwner().sendMessage(Text.translatable("Length squared: " + this.getVelocity().lengthSquared()));
            // this.getVelocity().lengthSquared() > 1.0E-7D

            this.setYaw((float) (MathHelper.atan2(e, g) * 57.2957763671875D));
            this.setPitch((float) (MathHelper.atan2(f, l) * 57.2957763671875D));
            this.setPitch(updateRotation(this.prevPitch, this.getPitch()));
            this.setYaw(updateRotation(this.prevYaw, this.getYaw()));
            float m = 0.99F;
            if (this.isTouchingWater()) {
                for (int o = 0; o < 4; ++o) {
                    this.world.addParticle(ParticleTypes.BUBBLE, h - e * 0.25D, j - f * 0.25D, k - g * 0.25D, e, f, g);
                }

                m = this.getDragInWater();
            }

            this.setVelocity(vec3d.multiply((double) m));
            if (!this.hasNoGravity()) {
                Vec3d vec3d4 = this.getVelocity();
                this.setVelocity(vec3d4.x, vec3d4.y - 0.05000000074505806D, vec3d4.z);
            }

            this.setPosition(h, j, k);
            this.checkBlockCollision();
        }
    }

    private boolean shouldFall() {
        return this.inGround && this.world.isSpaceEmpty((new Box(this.getPos(), this.getPos())).expand(0.06D));
    }

    private void fall() {
        this.inGround = false;
        Vec3d vec3d = this.getVelocity();
        //this.getOwner().sendMessage(Text.translatable("FALLING"));
        this.setVelocity(vec3d.multiply((double) (this.random.nextFloat() * 0.2F), (double) (this.random.nextFloat() * 0.2F), (double) (this.random.nextFloat() * 0.2F)));
    }

    private boolean shouldRoll() {
        return Math.abs(this.getVelocity().getX()) > 0.02D && Math.abs(this.getVelocity().getZ()) > 0.02D;
    }

    private void roll() {
        Vec3d vec3d = this.getVelocity();
        //this.getOwner().sendMessage(Text.translatable("ROLLING"));
        this.setVelocity(vec3d.multiply(0.90D, 0, 0.90D));
    }

    public void move(MovementType movementType, Vec3d movement) {
        super.move(movementType, movement);
        if (movementType != MovementType.SELF && this.shouldFall()) {
            this.fall();
        }
        if (movementType != MovementType.SELF && this.shouldRoll()) {
            this.roll();
        }
    }

    protected void age() {
        ++this.age;
        if (this.age >= 1200) {
            this.discard();
        }

    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = this.getOwner();
        if (entity instanceof LivingEntity) {
            entityHitResult.getEntity().damage(DamageSource.mobProjectile(this, (LivingEntity) entity).setProjectile(), 1.0F);
        }
    }

    protected void onBlockHit(BlockHitResult blockHitResult) {
        this.inBlockState = this.world.getBlockState(blockHitResult.getBlockPos());
        super.onBlockHit(blockHitResult);
        Vec3d vec3d;
        Vec3d vel = this.getVelocity();
        if (blockHitResult.getSide() == Direction.NORTH || blockHitResult.getSide() == Direction.SOUTH) {
            this.setVelocity(vel.multiply(0.2D, 0.2D, -0.2D));
        } else if (blockHitResult.getSide() == Direction.EAST || blockHitResult.getSide() == Direction.WEST) {
            this.setVelocity(vel.multiply(-0.2D, 0.2D, 0.2D));
        } else if (blockHitResult.getSide() == Direction.DOWN) {
            this.setVelocity(vel.multiply(0.2D, -0.2D, 0.2D));
        } else if (blockHitResult.getSide() == Direction.UP) {
            //this.getOwner().sendMessage(Text.translatable("Y " + this.getVelocity().getY()));

            /*
              if (vel.y < 0.0D) {
                this.setVelocity(vel.x, -vel.y * 0.6600000262260437D, vel.z);
            }
            */

            if (vel.y < -0.3D) {
                //this.getOwner().sendMessage(Text.translatable("Bouncing Y " + this.getVelocity().getY()));
                this.setVelocity(vel.multiply(0.3D, -0.2D, 0.3D));
            }
            if (vel.y < 0.0D) {
                this.setVelocity(vel.x, -vel.y * 0.6600000262260437D, vel.z);
            } else if (this.shouldRoll()) {
                //this.getOwner().sendMessage(Text.translatable("ROLLING"));
                this.roll();
            } else {
                //this.getOwner().sendMessage(Text.translatable("DONE"));
                vec3d = blockHitResult.getPos().subtract(this.getX(), this.getY(), this.getZ());
                this.setVelocity(vec3d);
                Vec3d vec3d2 = vec3d.normalize().multiply(0.05000000074505806D);
                this.setPos(this.getX() - vec3d2.x, this.getY() - vec3d2.y, this.getZ() - vec3d2.z);
                this.inGround = true;
            }
        }
    }

    @Nullable
    protected EntityHitResult getEntityCollision(Vec3d currentPosition, Vec3d nextPosition) {
        return ProjectileUtil.getEntityCollision(this.world, this, currentPosition, nextPosition, this.getBoundingBox().stretch(this.getVelocity()).expand(1.0D), this::canHit);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putShort("age", (short) this.age);
        if (this.inBlockState != null) {
            nbt.put("inBlockState", NbtHelper.fromBlockState(this.inBlockState));
        }
        nbt.putBoolean("inGround", this.inGround);
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.age = nbt.getShort("age");
        if (nbt.contains("inBlockState", 10)) {
            this.inBlockState = NbtHelper.toBlockState(nbt.getCompound("inBlockState"));
        }
        this.inGround = nbt.getBoolean("inGround");
    }

    public boolean shouldRender(double distance) {
        double d = this.getBoundingBox().getAverageSideLength() * 10.0D;
        if (Double.isNaN(d)) {
            d = 1.0D;
        }

        d *= 64.0D * getRenderDistanceMultiplier();
        return distance < d * d;
    }

    @Override
    protected void initDataTracker() {
    }

    public boolean isAttackable() {
        return false;
    }

    protected float getDragInWater() {
        return 0.6F;
    }
}
