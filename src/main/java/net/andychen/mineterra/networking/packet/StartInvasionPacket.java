package net.andychen.mineterra.networking.packet;

import net.andychen.mineterra.invasion.InvasionManager;
import net.andychen.mineterra.sounds.ModSounds;
import net.andychen.mineterra.util.access.ServerWorldMixinAccess;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

public class StartInvasionPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        // Everything here happens ONLY on the Server!
        InvasionManager invasionManager = ((ServerWorldMixinAccess)((ServerWorld)player.world)).getInvasionManager();
        if (invasionManager.getInvasionsIsEmpty()) {
            ((ServerWorldMixinAccess) ((ServerWorld) player.world)).getInvasionManager().startInvasion((ServerPlayerEntity) player);
            player.world.playSound(null, player.getX(), player.getY(), player.getZ(),
                    ModSounds.ENTITY_BOSS_ROAR, SoundCategory.NEUTRAL, 1F, 1F);
            player.world.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ENTITY_WITHER_SPAWN, SoundCategory.NEUTRAL, 1F, 1F);
        }
    }
}
