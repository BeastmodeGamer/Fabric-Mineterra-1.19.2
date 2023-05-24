package net.andychen.mineterra.entity.custom;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.andychen.mineterra.invasion.Invasion;
import net.andychen.mineterra.invasion.InvasionManager;
import net.andychen.mineterra.util.access.ServerWorldMixinAccess;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PatrolEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestTypes;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;

public abstract class InvaderEntity extends PatrolEntity {
    static final Predicate<ItemEntity> OBTAINABLE_OMINOUS_BANNER_PREDICATE;
    @Nullable
    protected Invasion invasion;
    private boolean ableToJoinInvasion;
    private int outOfInvasionCounter;

    protected InvaderEntity(EntityType<? extends InvaderEntity> entityType, World world) {
        super(entityType, world);
    }

    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(1, new InvaderEntity.PickupBannerAsLeaderGoal(this));
        this.goalSelector.add(3, new MoveToInvasionCenterGoal(this));
        this.goalSelector.add(4, new InvaderEntity.AttackHomeGoal(this, 1.0499999523162842D, 1));
    }

    protected void initDataTracker() {
        super.initDataTracker();
    }

    public boolean canJoinInvasion() {
        return this.ableToJoinInvasion;
    }

    public void setAbleToJoinInvasion(boolean ableToJoinInvasion) {
        this.ableToJoinInvasion = ableToJoinInvasion;
    }

    public void tickMovement() {
        if (this.world instanceof ServerWorld && this.isAlive()) {
            Invasion invasion = this.getInvasion();
            if (this.canJoinInvasion()) {
                if (invasion == null) {
                    if (this.world.getTime() % 20L == 0L) {
                        Invasion invasion2 = ((ServerWorldMixinAccess)((ServerWorld)this.world)).getInvasionAt(this.getBlockPos());
                        if (invasion2 != null && InvasionManager.isValidInvaderFor(this, invasion2)) {
                            invasion2.addInvader(this, (BlockPos)null, true, true);
                        }
                    }
                } else {
                    LivingEntity livingEntity = this.getTarget();
                    if (livingEntity != null && (livingEntity.getType() == EntityType.PLAYER || livingEntity.getType() == EntityType.IRON_GOLEM)) {
                        this.despawnCounter = 0;
                    }
                }
            }
        }

        super.tickMovement();
    }

    protected void updateDespawnCounter() {
        this.despawnCounter += 2;
    }

    public void onDeath(DamageSource damageSource) {
        if (this.world instanceof ServerWorld) {
            Entity entity = damageSource.getAttacker();
            Invasion invasion = this.getInvasion();
            if (invasion != null) {
                if (this.isPatrolLeader()) {
                    invasion.removeLeader();
                }

                if (entity != null && entity.getType() == EntityType.PLAYER) {
                    invasion.addHero(entity);
                }

                invasion.removeFromInvasion(this, true);
            }
        }

        super.onDeath(damageSource);
    }

    public boolean hasNoInvasion() {
        return !this.hasActiveInvasion();
    }

    public void setInvasion(@Nullable Invasion invasion) {
        this.invasion = invasion;
    }

    @Nullable
    public Invasion getInvasion() {
        return this.invasion;
    }

    public boolean hasActiveInvasion() {
        return this.getInvasion() != null && this.getInvasion().isActive();
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("CanJoinInvasion", this.ableToJoinInvasion);
        if (this.invasion != null) {
            nbt.putInt("InvasionId", this.invasion.getInvasionId());
        }

    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.ableToJoinInvasion = nbt.getBoolean("CanJoinInvasion");
        if (nbt.contains("InvasionId", 3)) {
            if (this.world instanceof ServerWorld) {
                this.invasion = ((ServerWorldMixinAccess)((ServerWorld)this.world)).getInvasionManager().getInvasion(nbt.getInt("InvasionId"));
            }

            if (this.invasion != null) {
                this.invasion.addToInvasion(this, false);
                if (this.isPatrolLeader()) {
                    this.invasion.setCaptain(this);
                }
            }
        }
    }

    protected void loot(ItemEntity item) {
        ItemStack itemStack = item.getStack();
        boolean bl = this.hasActiveInvasion() && this.getInvasion().getCaptain() != null;
        if (this.hasActiveInvasion() && !bl && ItemStack.areEqual(itemStack, Invasion.getBanner())) {
            EquipmentSlot equipmentSlot = EquipmentSlot.HEAD;
            ItemStack itemStack2 = this.getEquippedStack(equipmentSlot);
            double d = (double)this.getDropChance(equipmentSlot);
            if (!itemStack2.isEmpty() && (double)Math.max(this.random.nextFloat() - 0.1F, 0.0F) < d) {
                this.dropStack(itemStack2);
            }

            this.triggerItemPickedUpByEntityCriteria(item);
            this.equipStack(equipmentSlot, itemStack);
            this.sendPickup(item, itemStack.getCount());
            item.discard();
            this.getInvasion().setCaptain(this);
            this.setPatrolLeader(true);
        } else {
            super.loot(item);
        }

    }

    public boolean canLead() {
        return true;
    }

    public boolean canImmediatelyDespawn(double distanceSquared) {
        return this.getInvasion() == null ? super.canImmediatelyDespawn(distanceSquared) : false;
    }

    public boolean cannotDespawn() {
        return super.cannotDespawn() || this.getInvasion() != null;
    }

    public int getOutOfInvasionCounter() {
        return this.outOfInvasionCounter;
    }

    public void setOutOfInvasionCounter(int outOfInvasionCounter) {
        this.outOfInvasionCounter = outOfInvasionCounter;
    }

    public boolean damage(DamageSource source, float amount) {
        if (this.hasActiveInvasion()) {
            this.getInvasion().updateBar();
        }

        return super.damage(source, amount);
    }

    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.setAbleToJoinInvasion(spawnReason != SpawnReason.NATURAL);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    static {
        OBTAINABLE_OMINOUS_BANNER_PREDICATE = (itemEntity) -> {
            return !itemEntity.cannotPickup() && itemEntity.isAlive() && ItemStack.areEqual(itemEntity.getStack(), Invasion.getBanner());
        };
    }

    public class PickupBannerAsLeaderGoal<T extends InvaderEntity> extends Goal {
        private final T actor;

        public PickupBannerAsLeaderGoal(T actor) {
            this.actor = actor;
            this.setControls(EnumSet.of(Control.MOVE));
        }

        public boolean canStart() {
            Invasion invasion = this.actor.getInvasion();
            if (this.actor.hasActiveInvasion() && !this.actor.getInvasion().isFinished() && this.actor.canLead() && !ItemStack.areEqual(this.actor.getEquippedStack(EquipmentSlot.HEAD), Invasion.getBanner())) {
                InvaderEntity invaderEntity = invasion.getCaptain();
                if (invaderEntity == null || !invaderEntity.isAlive()) {
                    List<ItemEntity> list = this.actor.world.getEntitiesByClass(ItemEntity.class, this.actor.getBoundingBox().expand(16.0D, 8.0D, 16.0D), InvaderEntity.OBTAINABLE_OMINOUS_BANNER_PREDICATE);
                    if (!list.isEmpty()) {
                        return this.actor.getNavigation().startMovingTo((Entity)list.get(0), 1.149999976158142D);
                    }
                }

                return false;
            } else {
                return false;
            }
        }

        public void tick() {
            if (this.actor.getNavigation().getTargetPos().isWithinDistance(this.actor.getPos(), 1.414D)) {
                List<ItemEntity> list = this.actor.world.getEntitiesByClass(ItemEntity.class, this.actor.getBoundingBox().expand(4.0D, 4.0D, 4.0D), InvaderEntity.OBTAINABLE_OMINOUS_BANNER_PREDICATE);
                if (!list.isEmpty()) {
                    this.actor.loot((ItemEntity)list.get(0));
                }
            }
        }
    }

    static class AttackHomeGoal extends Goal {
        private final InvaderEntity invader;
        private final double speed;
        private BlockPos home;
        private final List<BlockPos> lastHomes = Lists.newArrayList();
        private final int distance;
        private boolean finished;

        public AttackHomeGoal(InvaderEntity invader, double speed, int distance) {
            this.invader = invader;
            this.speed = speed;
            this.distance = distance;
            this.setControls(EnumSet.of(Control.MOVE));
        }

        public boolean canStart() {
            this.purgeMemory();
            return this.isInvading() && this.tryFindHome() && this.invader.getTarget() == null;
        }

        private boolean isInvading() {
            return this.invader.hasActiveInvasion() && !this.invader.getInvasion().isFinished();
        }

        private boolean tryFindHome() {
            ServerWorld serverWorld = (ServerWorld)this.invader.world;
            BlockPos blockPos = this.invader.getBlockPos();
            Optional<BlockPos> optional = serverWorld.getPointOfInterestStorage().getPosition((registryEntry) -> {
                return registryEntry.matchesKey(PointOfInterestTypes.HOME);
            }, this::canLootHome, PointOfInterestStorage.OccupationStatus.ANY, blockPos, 48, this.invader.random);
            if (!optional.isPresent()) {
                return false;
            } else {
                this.home = ((BlockPos)optional.get()).toImmutable();
                return true;
            }
        }

        public boolean shouldContinue() {
            if (this.invader.getNavigation().isIdle()) {
                return false;
            } else {
                return this.invader.getTarget() == null && !this.home.isWithinDistance(this.invader.getPos(), (double)(this.invader.getWidth() + (float)this.distance)) && !this.finished;
            }
        }

        public void stop() {
            if (this.home.isWithinDistance(this.invader.getPos(), (double)this.distance)) {
                this.lastHomes.add(this.home);
            }

        }

        public void start() {
            super.start();
            this.invader.setDespawnCounter(0);
            this.invader.getNavigation().startMovingTo((double)this.home.getX(), (double)this.home.getY(), (double)this.home.getZ(), this.speed);
            this.finished = false;
        }

        public void tick() {
            if (this.invader.getNavigation().isIdle()) {
                Vec3d vec3d = Vec3d.ofBottomCenter(this.home);
                Vec3d vec3d2 = NoPenaltyTargeting.findTo(this.invader, 16, 7, vec3d, 0.3141592741012573D);
                if (vec3d2 == null) {
                    vec3d2 = NoPenaltyTargeting.findTo(this.invader, 8, 7, vec3d, 1.5707963705062866D);
                }

                if (vec3d2 == null) {
                    this.finished = true;
                    return;
                }

                this.invader.getNavigation().startMovingTo(vec3d2.x, vec3d2.y, vec3d2.z, this.speed);
            }

        }

        private boolean canLootHome(BlockPos pos) {
            Iterator var2 = this.lastHomes.iterator();

            BlockPos blockPos;
            do {
                if (!var2.hasNext()) {
                    return true;
                }

                blockPos = (BlockPos)var2.next();
            } while(!Objects.equals(pos, blockPos));

            return false;
        }

        private void purgeMemory() {
            if (this.lastHomes.size() > 2) {
                this.lastHomes.remove(0);
            }

        }
    }

    protected class PatrolApproachGoal extends Goal {
        private final InvaderEntity invader;
        private final float squaredDistance;
        public final TargetPredicate closeRaiderPredicate = TargetPredicate.createNonAttackable().setBaseMaxDistance(8.0D).ignoreVisibility().ignoreDistanceScalingFactor();

        public PatrolApproachGoal(InvaderEntity invader, float distance) {
            this.invader = invader;
            this.squaredDistance = distance * distance;
            this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
        }

        public boolean canStart() {
            LivingEntity livingEntity = this.invader.getAttacker();
            return this.invader.getInvasion() == null && this.invader.isRaidCenterSet() && this.invader.getTarget() != null && !this.invader.isAttacking() && (livingEntity == null || livingEntity.getType() != EntityType.PLAYER);
        }

        public void start() {
            super.start();
            this.invader.getNavigation().stop();
            List<InvaderEntity> list = this.invader.world.getTargets(InvaderEntity.class, this.closeRaiderPredicate, this.invader, this.invader.getBoundingBox().expand(8.0D, 8.0D, 8.0D));
            Iterator var2 = list.iterator();

            while(var2.hasNext()) {
                InvaderEntity invaderEntity = (InvaderEntity)var2.next();
                invaderEntity.setTarget(this.invader.getTarget());
            }

        }

        public void stop() {
            super.stop();
            LivingEntity livingEntity = this.invader.getTarget();
            if (livingEntity != null) {
                List<InvaderEntity> list = this.invader.world.getTargets(InvaderEntity.class, this.closeRaiderPredicate, this.invader, this.invader.getBoundingBox().expand(8.0D, 8.0D, 8.0D));
                Iterator var3 = list.iterator();

                while(var3.hasNext()) {
                    InvaderEntity invaderEntity = (InvaderEntity)var3.next();
                    invaderEntity.setTarget(livingEntity);
                    invaderEntity.setAttacking(true);
                }

                this.invader.setAttacking(true);
            }

        }

        public boolean shouldRunEveryTick() {
            return true;
        }

        public void tick() {
            LivingEntity livingEntity = this.invader.getTarget();
            if (livingEntity != null) {
                if (this.invader.squaredDistanceTo(livingEntity) > (double)this.squaredDistance) {
                    this.invader.getLookControl().lookAt(livingEntity, 30.0F, 30.0F);
                    if (this.invader.random.nextInt(50) == 0) {
                        this.invader.playAmbientSound();
                    }
                } else {
                    this.invader.setAttacking(true);
                }

                super.tick();
            }
        }
    }

    protected class MoveToInvasionCenterGoal extends Goal {
        private static final int FREE_RAIDER_CHECK_INTERVAL = 20;
        private static final float WALK_SPEED = 1.0F;
        private final InvaderEntity actor;
        private int nextFreeInvaderCheckAge;

        public MoveToInvasionCenterGoal(InvaderEntity actor) {
            this.actor = actor;
            this.setControls(EnumSet.of(Control.MOVE));
        }

        public boolean canStart() {
            return this.actor.getTarget() == null && !this.actor.hasPassengers() && this.actor.hasActiveInvasion() && !this.actor.getInvasion().isFinished() && ((ServerWorld)this.actor.world).getChunk(this.actor.getBlockPos()) != ((ServerWorld)this.actor.world).getChunk(this.actor.getInvasion().getCenter());
        }

        public boolean shouldContinue() {
            return this.actor.hasActiveInvasion() && !this.actor.getInvasion().isFinished() && this.actor.world instanceof ServerWorld && ((ServerWorld)this.actor.world).getChunk(this.actor.getBlockPos()) != ((ServerWorld)this.actor.world).getChunk(this.actor.getInvasion().getCenter());
        }

        public void tick() {
            if (this.actor.hasActiveInvasion()) {
                Invasion invasion = this.actor.getInvasion();
                if (this.actor.age > this.nextFreeInvaderCheckAge) {
                    this.nextFreeInvaderCheckAge = this.actor.age + 20;
                    this.includeFreeRaiders(invasion);
                }

                if (!this.actor.isNavigating()) {
                    Vec3d vec3d = NoPenaltyTargeting.findTo(this.actor, 15, 4, Vec3d.ofBottomCenter(invasion.getCenter()), 1.5707963705062866D);
                    if (vec3d != null) {
                        this.actor.getNavigation().startMovingTo(vec3d.x, vec3d.y, vec3d.z, 1.0D);
                    }
                }
            }

        }

        private void includeFreeRaiders(Invasion invasion) {
            if (invasion.isActive()) {
                Set<InvaderEntity> set = Sets.newHashSet();
                List<InvaderEntity> list = this.actor.world.getEntitiesByClass(InvaderEntity.class, this.actor.getBoundingBox().expand(16.0D), (invader) -> {
                    return !invader.hasActiveInvasion() && InvasionManager.isValidInvaderFor(invader, invasion);
                });
                set.addAll(list);
                Iterator var4 = set.iterator();

                while(var4.hasNext()) {
                    InvaderEntity invaderEntity = (InvaderEntity)var4.next();
                    invasion.addInvader(invaderEntity, (BlockPos)null, true, true);
                }
            }

        }
    }

}

