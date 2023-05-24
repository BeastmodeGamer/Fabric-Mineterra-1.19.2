package net.andychen.mineterra.entity.custom;

import net.andychen.mineterra.entity.ModEntities;
import net.andychen.mineterra.item.ModItems;
import net.andychen.mineterra.particle.ModParticles;
import net.andychen.mineterra.sounds.ModSounds;
import net.andychen.mineterra.util.ModDamageSource;
import net.andychen.mineterra.util.access.WorldMixinAccess;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;

import java.util.Iterator;

public class FallenStarEntity extends ProjectileEntity {
    private BlockState inBlockState;
    protected boolean inGround;
    protected int inGroundTime;
    public final float uniqueOffset;
    private int bounces;
    private int life;

    public FallenStarEntity(EntityType<? extends FallenStarEntity> entityType, World world) {
        super(entityType, world);
        this.uniqueOffset = this.random.nextFloat() * 3.1415927F * 2.0F;
    }

    public FallenStarEntity(World world, LivingEntity owner) {
        this(ModEntities.FALLEN_STAR, world);
        this.setNoGravity(true);
        this.setOwner(owner);
        this.setPosition(owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ());
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.world.isClient && this.world.isDay() && this.isNatural()) {
            this.world.playSound(null, this.getX(), this.getY(), this.getZ(), ModSounds.ENTITY_FALLEN_STAR_DISAPPEAR, SoundCategory.PLAYERS, 1.0F, 1.0F);
            this.discard();
        }
        HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
        this.onCollision(hitResult);
        this.updateRotation();
        this.age();

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

        if (this.inGround && this.bounces > 1) {
            if (this.inBlockState != blockState && this.shouldFall() && this.inGroundTime > 15) {
                this.fall();
            }
            ++this.inGroundTime;
        } else {
            this.inGroundTime = 0;
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

            if (this.age % 5 == 0 && this.bounces < 1) {
                float volume = this.isNatural() ? 1.5F : 1.0F;
                this.playSound(ModSounds.ENTITY_FALLEN_STAR_FALLING, volume, 1.0F);
            }
        }

        int count = this.bounces > 0 ? 1 : this.isNatural() ? 25 : 5;
        float p = this.isNatural() && this.bounces < 1 ? 0.1F : 0.05F;
        ((WorldMixinAccess) this.world).addParticle(ModParticles.MAGIC_DUST, true, this.getX(), this.getY() + 0.2, this.getZ(),
                -p, p, -p, p, -p, p, count);
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        if (!this.world.isClient && this.inGround) {
            ItemStack itemStack = ModItems.FALLEN_STAR.getDefaultStack();
            Item item = itemStack.getItem();
            if (this.isNatural() && this.bounces > 1 && this.inGroundTime > 20 && player.getInventory().insertStack(itemStack)) {
                player.sendPickup(this, 1);
                if (itemStack.isEmpty()) {
                    world.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    this.discard();
                    itemStack.setCount(1);
                }
                player.increaseStat(Stats.PICKED_UP.getOrCreateStat(item), 1);
            }

        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity owner = this.getOwner();
        if (owner instanceof LivingEntity) {
            if (!entityHitResult.getEntity().isTeammate(owner)) {
                entityHitResult.getEntity().damage(ModDamageSource.fallenStar(this, (LivingEntity) owner).setProjectile(), 20.0F);
            }
        }
        if (owner == null) {
            this.addFallenStarParticles(this.world);
            entityHitResult.getEntity().damage(ModDamageSource.fallenStar(this, null).setProjectile(), 1000.0F);
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        this.inBlockState = this.world.getBlockState(blockHitResult.getBlockPos());
        super.onBlockHit(blockHitResult);
        Vec3d vec3d = blockHitResult.getPos().subtract(this.getX(), this.getY(), this.getZ());
        this.setVelocity(vec3d);
        Vec3d vec3d2 = vec3d.normalize().multiply(0.05000000074505806D);
        this.setPos(this.getX() - vec3d2.x, this.getY() - vec3d2.y, this.getZ() - vec3d2.z);
        if (this.bounces <= 1) {
            this.playSound(ModSounds.ENTITY_SPELL_DEATH, 1.0F, 1.0F);
            this.playSound(ModSounds.ENTITY_FALLEN_STAR_DISAPPEAR, 1.0F, 0.9F);
        }

        if (!this.isNatural()) {
            if (!this.world.isClient) {
                this.discard();
            }
        } else {
            if (this.bounces < 1) {
                this.addFallenStarParticles(this.world);
                this.setVelocity(0, 0.4D, 0);
                ++this.bounces;
            } else if (this.bounces <= 1) {
                this.addFallenStarParticles(this.world);
                ++this.bounces;
            }
            this.inGround = true;
        }
    }

    public void addFallenStarParticles(World world) {
        WorldMixinAccess worldMixinAccess = (WorldMixinAccess) world;
        worldMixinAccess.addParticle(ModParticles.FALLEN_STAR_PURPLE, true, this.getX(), this.getY(), this.getZ(),
                -0.3D, 0.3D, -0.3D, 0.3D, -0.3D, 0.3D, 30);
        worldMixinAccess.addParticle(ModParticles.FALLEN_STAR_YELLOW, true, this.getX(), this.getY(), this.getZ(),
                -0.3D, 0.3D, -0.3D, 0.3D, -0.3D, 0.3D, 30);
    }

    private boolean shouldFall() {
        return this.inGround && this.bounces <= 1 && this.world.isSpaceEmpty((new Box(this.getPos(), this.getPos())).expand(0.06D));
    }

    private void fall() {
        this.inGround = false;
        Vec3d vec3d = this.getVelocity();
        this.setVelocity(vec3d.multiply((double) (this.random.nextFloat() * 0.2F), (double) (this.random.nextFloat() * 0.2F), (double) (this.random.nextFloat() * 0.2F)));
        this.inGroundTime = 0;
    }

    @Override
    public void onRemoved() {
        this.addFallenStarParticles(this.world);
    }

    public boolean isNatural() {
        return this.getOwner() == null;
    }

    public float getRotation(float tickDelta) {
        return ((float) this.age + tickDelta) / 20.0F + this.uniqueOffset;
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
    protected void initDataTracker() {
    }

    protected void age() {
        ++this.life;
        if (!this.isNatural() && this.life > 200) {
            this.discard();
        }
    }

    public int getLife() {
        return this.life;
    }

    public boolean isInGround() {
        return this.inGround;
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putShort("life", (short) this.life);
        if (this.inBlockState != null) {
            nbt.put("inBlockState", NbtHelper.fromBlockState(this.inBlockState));
        }
        nbt.putBoolean("inGround", this.inGround);
        nbt.putInt("bounces", this.bounces);
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.life = nbt.getShort("life");
        if (nbt.contains("inBlockState", 10)) {
            this.inBlockState = NbtHelper.toBlockState(nbt.getCompound("inBlockState"));
        }
        this.inGround = nbt.getBoolean("inGround");
        this.bounces = nbt.getInt("bounces");
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
