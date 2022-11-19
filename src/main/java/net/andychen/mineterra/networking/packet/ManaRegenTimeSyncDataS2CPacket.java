package net.andychen.mineterra.networking.packet;

import net.andychen.mineterra.mana.ManaManager;
import net.andychen.mineterra.mana.ManaManagerAccess;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;

public class ManaRegenTimeSyncDataS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        int[] bufferArray = buf.readIntArray();
        int entityId = bufferArray[0];
        int manaRegenTime = bufferArray[1];
        client.execute(() -> {
            if (client.player.world.getEntityById(entityId) != null) {
                PlayerEntity player = (PlayerEntity) client.player.world.getEntityById(entityId);
                ManaManager manaManager = ((ManaManagerAccess) player).getManaManager();
                manaManager.setManaRegenTime(manaRegenTime);
            }
        });
    }
}
