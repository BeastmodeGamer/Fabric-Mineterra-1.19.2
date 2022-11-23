package net.andychen.mineterra.mixin;

import net.andychen.mineterra.networking.ModNetworking;
import net.andychen.mineterra.networking.packet.AddParticleS2CPacket;
import net.andychen.mineterra.util.access.ServerWorldMixinAccess;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin implements ServerWorldMixinAccess {

    public int addParticle(ParticleEffect particle, boolean alwaysSpawn, double x, double y, double z, double velocityX1, double velocityX2,
                            double velocityY1, double velocityY2, double velocityZ1, double velocityZ2, int count) {
        AddParticleS2CPacket addParticleS2CPacket = new AddParticleS2CPacket(particle, alwaysSpawn, x, y, z, velocityX1, velocityX2, velocityY1, velocityY2, velocityZ1, velocityZ2, count);
        int i = 0;

        for (int j = 0; j < ((ServerWorld) (Object) this).getPlayers().size(); ++j) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) ((ServerWorld) (Object) this).getPlayers().get(j);
            if (this.sendParticleToNearbyPlayers(serverPlayerEntity, false, x, y, z, addParticleS2CPacket)) {
                ++i;
            }
        }

        return i;
    }

    public int addParticle(ParticleEffect particle, boolean alwaysSpawn, double x, double y, double z, double velocityX, double velocityY, double velocityZ, int count) {
        AddParticleS2CPacket addParticleS2CPacket = new AddParticleS2CPacket(particle, alwaysSpawn, x, y, z, velocityX, velocityY, velocityZ, count);
        int i = 0;

        for (int j = 0; j < ((ServerWorld) (Object) this).getPlayers().size(); ++j) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) ((ServerWorld) (Object) this).getPlayers().get(j);
            if (this.sendParticleToNearbyPlayers(serverPlayerEntity, false, x, y, z, addParticleS2CPacket)) {
                ++i;
            }
        }

        return i;
    }

    public final boolean sendParticleToNearbyPlayers(ServerPlayerEntity player, boolean force, double x, double y, double z, AddParticleS2CPacket addParticleS2CPacket) {
        if (player.getWorld() != ((ServerWorld) (Object) this)) {
            return false;
        } else {
            BlockPos blockPos = player.getBlockPos();
            if (blockPos.isWithinDistance(new Vec3d(x, y, z), force ? 512.0D : 32.0D)) {
                PacketByteBuf buf = PacketByteBufs.create();
                addParticleS2CPacket.write(buf);
                player.networkHandler.sendPacket(new CustomPayloadS2CPacket(ModNetworking.ADD_PARTICLE_ID, buf));
                return true;
            } else {
                return false;
            }
        }
    }
}
