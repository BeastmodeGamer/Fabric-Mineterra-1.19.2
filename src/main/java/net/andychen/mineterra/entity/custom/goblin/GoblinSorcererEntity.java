package net.andychen.mineterra.entity.custom.goblin;

import net.andychen.mineterra.entity.custom.ChaosBallEntity;
import net.andychen.mineterra.entity.custom.InvaderEntity;
import net.andychen.mineterra.entity.goal.RangedAttackGoal;
import net.andychen.mineterra.sounds.ModSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class GoblinSorcererEntity extends GoblinEntity implements RangedAttackMob {
    public int lastHurtTimer;
    public int shots;
    private int teleportCooldownTicks;

    public GoblinSorcererEntity(EntityType<? extends GoblinEntity> entityType, World world) {
        super(entityType, world);
    }

    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(2, new PatrolApproachGoal(this, 10.0F));
        this.goalSelector.add(3, new RangedAttackGoal(this, 0.0, 40, 40.0f));
        this.targetSelector.add(1, (new RevengeGoal(this, new Class[]{InvaderEntity.class})).setGroupRevenge(new Class[0]));
        this.targetSelector.add(2, new ActiveTargetGoal(this, PlayerEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal(this, MerchantEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal(this, IronGolemEntity.class, true));
        //this.targetSelector.add(4, new TargetGoal(this));
        this.goalSelector.add(8, new WanderAroundGoal(this, 0.6D));
        this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 3.0F, 1.0F));
        this.goalSelector.add(10, new LookAtEntityGoal(this, MobEntity.class, 8.0F));
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D);
    }

    public void tick() {
        super.tick();
        if (this.teleportCooldownTicks > 0) {
            --this.teleportCooldownTicks;
        }
        if (this.lastHurtTimer > 0) {
            --this.lastHurtTimer;
        }
        if (this.getTarget() != null && this.canTeleport()) {
            this.getTarget().sendMessage(Text.translatable("TELEPORTING"));
            this.teleportAroundTarget(this.getTarget());
            this.shots = 0;
        }
    }

    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        EntityData entityData2 = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        ((MobNavigation) this.getNavigation()).setCanPathThroughDoors(true);
        Random random = world.getRandom();
        this.initEquipment(random, difficulty);
        this.updateEnchantments(random, difficulty);
        return entityData2;
    }

    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("shots", this.shots);
        nbt.putInt("lastHurtTimer", this.lastHurtTimer);
        nbt.putInt("teleportCooldownTicks", this.teleportCooldownTicks);
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.shots = nbt.getInt("shots");
        this.lastHurtTimer = nbt.getInt("lastHurtTimer");
        this.teleportCooldownTicks = nbt.getInt("teleportCooldownTicks");
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        if (target != null && this.canShoot()) {
            ChaosBallEntity chaosBallEntity = new ChaosBallEntity(this.world, this);
            double d = target.getX() - this.getX();
            double e = target.getBodyY(0.5D) - this.getBodyY(0.5D);
            double f = target.getZ() - this.getZ();
            chaosBallEntity.setVelocity(d, e, f, 0.9F, 0.0F);
            this.playSound(ModSounds.ENTITY_GENERIC_TELEPORT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
            this.world.spawnEntity(chaosBallEntity);
            this.teleportCooldownTicks = 90;
            ++this.shots;
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        this.lastHurtTimer = 30;
        return super.damage(source, amount);
    }

    public boolean canShoot() {
        return this.lastHurtTimer == 0 && this.shots < 3;
    }

    public boolean canTeleport() {
        return this.lastHurtTimer == 0 && !this.canShoot() && this.teleportCooldownTicks == 0;
    }

    protected boolean teleportAroundTarget(LivingEntity target) {
        if (!this.world.isClient() && this.isAlive()) {
            double d = target.getX() + (this.random.nextDouble() - 0.5D) * 40.0D;
            double e = target.getY() + (double) (this.random.nextInt(32) - 16);
            double f = target.getZ() + (this.random.nextDouble() - 0.5D) * 40.0D;
            return this.teleportTo(d, e, f);
        } else {
            return false;
        }
    }

    boolean teleportTo(Entity entity) {
        Vec3d vec3d = new Vec3d(this.getX() - entity.getX(), this.getBodyY(0.5D) - entity.getEyeY(), this.getZ() - entity.getZ());
        vec3d = vec3d.normalize();
        double d = 16.0D;
        double e = this.getX() + (this.random.nextDouble() - 0.5D) * 8.0D - vec3d.x * 16.0D;
        double f = this.getY() + (double) (this.random.nextInt(16) - 8) - vec3d.y * 16.0D;
        double g = this.getZ() + (this.random.nextDouble() - 0.5D) * 8.0D - vec3d.z * 16.0D;
        return this.teleportTo(e, f, g);
    }

    private boolean teleportTo(double x, double y, double z) {
        BlockPos.Mutable mutable = new BlockPos.Mutable(x, y, z);

        while (mutable.getY() > this.world.getBottomY() && !this.world.getBlockState(mutable).getMaterial().blocksMovement()) {
            mutable.move(Direction.DOWN);
        }

        BlockState blockState = this.world.getBlockState(mutable);
        boolean bl = blockState.getMaterial().blocksMovement();
        boolean bl2 = blockState.getFluidState().isIn(FluidTags.WATER);
        if (bl && !bl2) {
            Vec3d vec3d = this.getPos();
            boolean bl4 = this.teleport(x, y, z, true);
            if (bl4) {
                this.world.emitGameEvent(GameEvent.TELEPORT, vec3d, GameEvent.Emitter.of(this));
                if (!this.isSilent()) {
                    this.world.playSound((PlayerEntity) null, this.prevX, this.prevY, this.prevZ, SoundEvents.ENTITY_ENDERMAN_TELEPORT, this.getSoundCategory(), 1.0F, 1.0F);
                    this.world.playSound((PlayerEntity) null, this.prevX, this.prevY, this.prevZ, ModSounds.ENTITY_GENERIC_TELEPORT, this.getSoundCategory(), 1.0F, 1.0F);
                    this.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
                    this.playSound(ModSounds.ENTITY_GENERIC_TELEPORT, 1.0F, 1.0F);
                }
            }

            return bl4;
        } else {
            return false;
        }
    }
}
