package net.andychen.mineterra.mixin;

import com.mojang.authlib.GameProfile;
import net.andychen.mineterra.mana.ManaManager;
import net.andychen.mineterra.mana.ManaManagerAccess;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.encryption.PlayerPublicKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityManaMixin extends PlayerEntity {
    private ManaManager manaManager = ((ManaManagerAccess) this).getManaManager();
    private int syncedMana = -99999999;
    private int syncedMaxMana = -99999999;
    private int syncedManaRegenTime = -99999999;

    public ServerPlayerEntityManaMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile, @Nullable PlayerPublicKey publicKey) {
        super(world, pos, yaw, gameProfile, publicKey);
    }
    
    @Inject(method = "playerTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;tick()V", shift = At.Shift.AFTER))
    public void playerTickMixin(CallbackInfo info) {
        if (this.syncedMana != this.manaManager.getMana()) {
            manaManager.syncMana(((ServerPlayerEntity)(Object) this));
            this.syncedMana = manaManager.getMana();
        }
        if (this.syncedMaxMana != this.manaManager.getMaxMana()) {
            manaManager.syncMaxMana(((ServerPlayerEntity)(Object) this));
            this.syncedMaxMana = manaManager.getMaxMana();
        }
        if (this.syncedManaRegenTime != this.manaManager.getManaRegenTime()) {
            manaManager.syncManaRegenTime(((ServerPlayerEntity)(Object) this));
            this.syncedManaRegenTime = manaManager.getManaRegenTime();
        }
    }

    @Inject(method = "Lnet/minecraft/server/network/ServerPlayerEntity;copyFrom(Lnet/minecraft/server/network/ServerPlayerEntity;Z)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;setHealth(F)V"))
    public void copyFromMixinOne(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo info) {
        this.manaManager.setMana(((ManaManagerAccess) oldPlayer).getManaManager().getMana());
        this.manaManager.setMaxMana(((ManaManagerAccess) oldPlayer).getManaManager().getMaxMana());
        this.manaManager.setManaRegenTime(((ManaManagerAccess) oldPlayer).getManaManager().getManaRegenTime());
    }

    @Inject(method = "Lnet/minecraft/server/network/ServerPlayerEntity;copyFrom(Lnet/minecraft/server/network/ServerPlayerEntity;Z)V", at = @At(value = "TAIL"))
    public void copyFromMixinTwo(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo info) {
        this.syncedMana = -1;
        this.syncedMaxMana = -1;
        this.syncedManaRegenTime = -1;
    }
}
