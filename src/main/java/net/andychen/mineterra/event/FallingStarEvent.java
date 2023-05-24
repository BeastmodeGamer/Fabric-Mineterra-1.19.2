package net.andychen.mineterra.event;

import net.andychen.mineterra.entity.ModEntities;
import net.andychen.mineterra.entity.custom.FallenStarEntity;
import net.andychen.mineterra.item.ModItems;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class FallingStarEvent {

    public static void spawnFallingStar(ServerWorld world, MinecraftServer server) {
        if (world.isNight()) {
            Random random = new Random();
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                BlockPos pos = player.getBlockPos();
                if (pos.getY() >= 0 && world.isSkyVisible(pos)) {
                    if (random.nextInt(16800) == 42 || player.isHolding(ModItems.FALLEN_STAR)) {
                        double x = pos.getX() + random.nextDouble(-32, 32);
                        double y = pos.getY() + 256;
                        double z = pos.getZ() + random.nextDouble(-32, 32);
                        FallenStarEntity fallenStarEntity = new FallenStarEntity(ModEntities.FALLEN_STAR, world);
                        fallenStarEntity.setPosition(x, y, z);
                        fallenStarEntity.setVelocity(random.nextFloat(-1, 1), 0, random.nextFloat(-1, 1), 0.5f, 1.0f);
                        world.spawnEntity(fallenStarEntity);
                    }
                }
            }
        }
    }
}
