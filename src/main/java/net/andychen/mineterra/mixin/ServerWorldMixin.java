package net.andychen.mineterra.mixin;

import net.andychen.mineterra.event.FallingStarEvent;
import net.andychen.mineterra.event.LanternNightEvent;
import net.andychen.mineterra.invasion.Invasion;
import net.andychen.mineterra.invasion.InvasionManager;
import net.andychen.mineterra.networking.ModNetworking;
import net.andychen.mineterra.networking.packet.AddParticleS2CPacket;
import net.andychen.mineterra.util.access.ServerWorldMixinAccess;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.level.storage.LevelStorage;
import net.minecraft.world.spawner.Spawner;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin implements ServerWorldMixinAccess {
    @Final
    protected InvasionManager invasionManager;

    @Inject(method = "<init>", at = @At("TAIL"))
    protected void ServerWorldConstructor(MinecraftServer server, Executor workerExecutor, LevelStorage.Session session, ServerWorldProperties properties, RegistryKey<World> worldKey, DimensionOptions dimensionOptions, WorldGenerationProgressListener worldGenerationProgressListener, boolean debugWorld, long seed, List<Spawner> spawners, boolean shouldTickTime, CallbackInfo info) {
        this.invasionManager = ((ServerWorld)(Object)this).getPersistentStateManager().getOrCreate((nbt) -> {
            return InvasionManager.fromNbt(((ServerWorld)(Object)this), nbt);
        }, () -> {
            return new InvasionManager(((ServerWorld)(Object)this));
        }, InvasionManager.nameFor(((ServerWorld)(Object)this).getDimensionEntry()));
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/village/raid/RaidManager;tick()V", shift = At.Shift.AFTER))
    private void tickInject(BooleanSupplier shouldKeepTicking, CallbackInfo info) {
        Profiler profilerMixin = ((ServerWorld)(Object)this).getProfiler();
        profilerMixin.swap("invasion");
        this.invasionManager.tick();
    }

    @Inject(method = "tick", at = @At(value = "TAIL"))
    private void tickInjectTail(BooleanSupplier shouldKeepTicking, CallbackInfo info) {
        FallingStarEvent.spawnFallingStar(((ServerWorld)(Object)this), ((ServerWorld)(Object)this).getServer());
        LanternNightEvent.startLanternNight(((ServerWorld)(Object)this), ((ServerWorld)(Object)this).getServer());
    }

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

    public InvasionManager getInvasionManager() {
        return this.invasionManager;
    }

    @Nullable
    public Invasion getInvasionAt(BlockPos pos) {
        return this.invasionManager.getInvasionAt(pos, 9216);
    }

    public boolean hasInvasionAt(BlockPos pos) {
        return this.getInvasionAt(pos) != null;
    }

}
