package net.andychen.mineterra.entity.goal;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class RangedAttackGoal extends Goal {
    private final MobEntity mob;
    private final RangedAttackMob owner;
    @Nullable
    private LivingEntity target;
    private int updateCountdownTicks;
    private final double mobSpeed;
    private int seenTargetTicks;
    private final int minIntervalTicks;
    private final int maxIntervalTicks;
    private final float maxShootRange;
    private final float squaredMaxShootRange;

    public RangedAttackGoal(RangedAttackMob mob, double mobSpeed, int intervalTicks, float maxShootRange) {
        this(mob, mobSpeed, intervalTicks, intervalTicks, maxShootRange);
    }

    public RangedAttackGoal(RangedAttackMob mob, double mobSpeed, int minIntervalTicks, int maxIntervalTicks, float maxShootRange) {
        this.updateCountdownTicks = -1;
        if (!(mob instanceof LivingEntity)) {
            throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
        } else {
            this.owner = mob;
            this.mob = (MobEntity) mob;
            this.mobSpeed = mobSpeed;
            this.minIntervalTicks = minIntervalTicks;
            this.maxIntervalTicks = maxIntervalTicks;
            this.maxShootRange = maxShootRange;
            this.squaredMaxShootRange = maxShootRange * maxShootRange;
            this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
        }
    }

    public boolean canStart() {
        LivingEntity livingEntity = this.mob.getTarget();
        if (livingEntity != null && livingEntity.isAlive()) {
            this.target = livingEntity;
            this.mob.setAttacking(true);
            return true;
        } else {
            return false;
        }
    }

    public boolean shouldContinue() {
        return this.canStart() || this.target.isAlive() && !this.mob.getNavigation().isIdle();
    }

    public void stop() {
        this.target = null;
        this.mob.setAttacking(false);
        this.seenTargetTicks = 0;
        this.updateCountdownTicks = -1;
    }

    public boolean shouldRunEveryTick() {
        return true;
    }

    public void tick() {
        double d = this.mob.squaredDistanceTo(this.target.getX(), this.target.getY(), this.target.getZ());
        boolean bl = this.mob.getVisibilityCache().canSee(this.target);
        if (bl) {
            ++this.seenTargetTicks;
        } else {
            this.seenTargetTicks = 0;
        }

        if (!(d > (double) this.squaredMaxShootRange) && this.seenTargetTicks >= 5) {
            this.mob.getNavigation().stop();
        } else {
            this.mob.getNavigation().startMovingTo(this.target, this.mobSpeed);
        }

        this.mob.getLookControl().lookAt(this.target, 30.0F, 30.0F);
        if (--this.updateCountdownTicks == 0) {
            if (!bl) {
                return;
            }

            float f = (float) Math.sqrt(d) / this.maxShootRange;
            float g = MathHelper.clamp(f, 0.1F, 1.0F);
            this.owner.attack(this.target, g);
            this.updateCountdownTicks = MathHelper.floor(f * (float) (this.maxIntervalTicks - this.minIntervalTicks) + (float) this.minIntervalTicks);
        } else if (this.updateCountdownTicks < 0) {
            this.updateCountdownTicks = MathHelper.floor(MathHelper.lerp(Math.sqrt(d) / (double) this.maxShootRange, (double) this.minIntervalTicks, (double) this.maxIntervalTicks));
        }

    }
}
