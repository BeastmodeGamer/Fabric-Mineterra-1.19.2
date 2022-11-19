package net.andychen.mineterra.mixin;

import net.andychen.mineterra.mana.ManaManager;
import net.andychen.mineterra.mana.ManaManagerAccess;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityManaMixin extends LivingEntity implements ManaManagerAccess {
    private ManaManager manaManager = new ManaManager();

    @Override
    public ManaManager getManaManager() {
        return this.manaManager;
    }

    protected PlayerEntityManaMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "Lnet/minecraft/entity/player/PlayerEntity;tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/HungerManager;update(Lnet/minecraft/entity/player/PlayerEntity;)V", shift = At.Shift.AFTER))
    private void tickInjector(CallbackInfo info) {
        this.manaManager.updateMana((PlayerEntity)(Object) this);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At(value = "TAIL"))
    private void readManaNbtDataInjector(NbtCompound tag, CallbackInfo info) {
        this.manaManager.readNbt(tag);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At(value = "TAIL"))
    private void writeManaNbtDataInjector(NbtCompound tag, CallbackInfo info) {
        this.manaManager.writeNbt(tag);
    }
}
