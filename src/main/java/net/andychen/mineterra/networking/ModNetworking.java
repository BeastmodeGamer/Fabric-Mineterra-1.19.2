package net.andychen.mineterra.networking;

import net.andychen.mineterra.MineTerra;
import net.andychen.mineterra.networking.packet.ManaRegenTimeSyncDataS2CPacket;
import net.andychen.mineterra.networking.packet.ManaSyncDataS2CPacket;
import net.andychen.mineterra.networking.packet.MaxManaSyncDataS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;

public class ModNetworking {
    public static final Identifier MANA_SYNC_ID = new Identifier(MineTerra.MOD_ID, "mana_sync");
    public static final Identifier MAX_MANA_SYNC_ID = new Identifier(MineTerra.MOD_ID, "max_mana_sync");
    public static final Identifier MANA_REGEN_TIME_SYNC_ID = new Identifier(MineTerra.MOD_ID, "mana_regen_time_sync");

    public static void registerC2SPackets() {
    }

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(MANA_SYNC_ID, ManaSyncDataS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(MAX_MANA_SYNC_ID, MaxManaSyncDataS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(MANA_REGEN_TIME_SYNC_ID, ManaRegenTimeSyncDataS2CPacket::receive);
    }
}
