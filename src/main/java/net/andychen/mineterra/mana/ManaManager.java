package net.andychen.mineterra.mana;

import io.netty.buffer.Unpooled;
import net.andychen.mineterra.networking.ModNetworking;
import net.andychen.mineterra.sounds.ModSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.MathHelper;

public class ManaManager {
    private int mana = 2;
    private int maxMana = 2;
    private int manaRegenTime;

    public void addMana(int mana) {
        this.mana = MathHelper.clamp(this.mana + mana, 0, this.maxMana);
    }

    public void removeMana(int mana) {
        this.mana = MathHelper.clamp(this.mana - mana, 0, this.maxMana);
        this.manaRegenTime = 100;
    }

    public void updateMana(PlayerEntity player) {
        if (this.manaRegenTime > 0) {
            --this.manaRegenTime;
        }
        if (this.manaRegenTime == 0) {
            if (this.mana == this.maxMana - 1) {
                this.addMana(1);
                player.world.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.ENTITY_PLAYER_FULL_MANA, SoundCategory.NEUTRAL, 1.0f, 1.0f);
            } else if (this.mana < this.maxMana) {
                this.addMana(1);
                this.manaRegenTime = 30;
            }
        }
    }

    public static void syncMana(ServerPlayerEntity player) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeIntArray(new int[] { player.getId(), ((ManaManagerAccess) player).getManaManager().getMana() });
        player.networkHandler.sendPacket(new CustomPayloadS2CPacket(ModNetworking.MANA_SYNC_ID, buf));
    }

    public static void syncMaxMana(ServerPlayerEntity player) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeIntArray(new int[] { player.getId(), ((ManaManagerAccess) player).getManaManager().getMaxMana() });
        player.networkHandler.sendPacket(new CustomPayloadS2CPacket(ModNetworking.MAX_MANA_SYNC_ID, buf));
    }

    public static void syncManaRegenTime(ServerPlayerEntity player) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeIntArray(new int[] { player.getId(), ((ManaManagerAccess) player).getManaManager().getManaRegenTime() });
        player.networkHandler.sendPacket(new CustomPayloadS2CPacket(ModNetworking.MANA_REGEN_TIME_SYNC_ID, buf));
    }

    public void readNbt(NbtCompound tag) {
        if (tag.contains("Mana", 99)) {
            this.mana = tag.getInt("Mana");
            this.maxMana = tag.getInt("MaxMana");
            this.manaRegenTime = tag.getInt("ManaRegenTime");
        }
    }

    public void writeNbt(NbtCompound tag) {
        tag.putInt("Mana", this.mana);
        tag.putInt("MaxMana", this.maxMana);
        tag.putInt("ManaRegenTime", this.manaRegenTime);
    }

    public void setManaRegenTime(int manaRegenTime) {
        this.manaRegenTime = manaRegenTime;
    }

    public int getManaRegenTime() {
        return this.manaRegenTime;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getMana() {
        return this.mana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public int getMaxMana() {
        return this.maxMana;
    }
}
