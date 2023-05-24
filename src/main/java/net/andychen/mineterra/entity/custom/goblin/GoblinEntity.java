package net.andychen.mineterra.entity.custom.goblin;

import net.andychen.mineterra.entity.ModEntityGroup;
import net.andychen.mineterra.entity.custom.InvaderEntity;
import net.andychen.mineterra.sounds.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public abstract class GoblinEntity extends InvaderEntity {
    static final Predicate<Difficulty> DIFFICULTY_ALLOWS_DOOR_BREAKING_PREDICATE = (difficulty) -> {
        return difficulty == Difficulty.NORMAL || difficulty == Difficulty.HARD;
    };

    protected GoblinEntity(EntityType<? extends GoblinEntity> entityType, World world) {
        super(entityType, world);
    }

    public boolean isTeammate(Entity other) {
        if (super.isTeammate(other)) {
            return true;
        } else if (other instanceof LivingEntity && ((LivingEntity)other).getGroup() == ModEntityGroup.GOBLIN) {
            return this.getScoreboardTeam() == null && other.getScoreboardTeam() == null;
        } else {
            return false;
        }
    }

    @Override
    public boolean canTarget(LivingEntity target) {
        if (target instanceof GoblinEntity) {
            return false;
        }
        return super.canTarget(target);
    }

    public void playAmbientSound() {
        SoundEvent soundEvent = this.getAmbientSound();
        if (soundEvent != null) {
            this.playSound(soundEvent, 1.0f, 0.9F + random.nextFloat() * (1.1F - 0.9F));
        }
    }

    protected void playHurtSound(DamageSource source) {
        SoundEvent soundEvent = this.getHurtSound(source);
        if (soundEvent != null) {
            this.playSound(soundEvent, 1.0f, 0.9F + random.nextFloat() * (1.1F - 0.9F));
        }
    }

    protected SoundEvent getAmbientSound() {
        List<SoundEvent> sounds = List.of(ModSounds.ENTITY_GOBLIN_AMBIENT_1, ModSounds.ENTITY_GOBLIN_AMBIENT_2);
        SoundEvent soundEvent = sounds.get(random.nextInt(sounds.size()));
        return random.nextInt(9) == 1 ? soundEvent : null;
    }

    protected SoundEvent getDeathSound() {
        return ModSounds.ENTITY_GENERIC_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        List<SoundEvent> sounds = List.of(ModSounds.ENTITY_GENERIC_HURT, ModSounds.ENTITY_GOBLIN_HURT);
        SoundEvent soundEvent = sounds.get(random.nextInt(sounds.size()));
        return soundEvent;
    }

    public EntityGroup getGroup() {
        return ModEntityGroup.GOBLIN;
    }

    protected class LongDoorInteractGoal extends net.minecraft.entity.ai.goal.LongDoorInteractGoal {
        public LongDoorInteractGoal(InvaderEntity invader) {
            super(invader, false);
        }

        public boolean canStart() {
            return super.canStart() && GoblinEntity.this.hasActiveInvasion();
        }
    }

    static class BreakDoorGoal extends net.minecraft.entity.ai.goal.BreakDoorGoal {
        public BreakDoorGoal(MobEntity mobEntity) {
            super(mobEntity, 6, GoblinWarriorEntity.DIFFICULTY_ALLOWS_DOOR_BREAKING_PREDICATE);
            this.setControls(EnumSet.of(Control.MOVE));
        }

        public boolean shouldContinue() {
            GoblinEntity goblinEntity = (GoblinEntity)this.mob;
            return goblinEntity.hasActiveInvasion() && super.shouldContinue();
        }

        public boolean canStart() {
            GoblinEntity goblinEntity = (GoblinEntity)this.mob;
            return goblinEntity.hasActiveInvasion() && goblinEntity.random.nextInt(toGoalTicks(10)) == 0 && super.canStart();
        }

        public void start() {
            super.start();
            this.mob.setDespawnCounter(0);
        }
    }

    static class TargetGoal extends ActiveTargetGoal<LivingEntity> {
        public TargetGoal(GoblinEntity goblin) {
            super(goblin, LivingEntity.class, 0, true, true, LivingEntity::isMobOrPlayer);
        }

        public boolean canStart() {
            return super.canStart();
        }

        public void start() {
            super.start();
            this.mob.setDespawnCounter(0);
        }
    }
}
