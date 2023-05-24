package net.andychen.mineterra.event;

import net.andychen.mineterra.entity.ModEntities;
import net.andychen.mineterra.entity.custom.LanternEntity;
import net.andychen.mineterra.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class LanternNightEvent {

    public static void startLanternNight(ServerWorld world, MinecraftServer server) {
        if (world.isNight()) {
            Random random = new Random();
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                BlockPos pos = player.getBlockPos();
                BlockState blockState = world.getBlockState(pos);
                if (pos.getY() >= 63 && world.isSkyVisible(pos) && blockState.isAir() && !player.isSpectator()) {
                    if (random.nextInt(10) == 1 || player.isHolding(ModItems.RELEASE_LANTERN)) {
                        double x = pos.getX() + random.nextDouble(-48, 48);
                        double y = pos.getY() - 6;
                        double z = pos.getZ() + random.nextDouble(-48, 48);
                        LanternEntity lanternEntity = new LanternEntity(ModEntities.LANTERN, world);
                        lanternEntity.setPosition(x, y, z);
                        world.spawnEntity(lanternEntity);
                    }
                }
                if (!player.hasStatusEffect(StatusEffects.LUCK)) {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.LUCK, 24000, 0, false, false));
                }
            }
        }
    }
}
