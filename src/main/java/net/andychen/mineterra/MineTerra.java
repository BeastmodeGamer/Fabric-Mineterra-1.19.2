package net.andychen.mineterra;

import net.andychen.mineterra.block.ModBlocks;
import net.andychen.mineterra.effect.ModStatusEffects;
import net.andychen.mineterra.event.AttackEntityHandler;
import net.andychen.mineterra.event.PlayerEvents;
import net.andychen.mineterra.item.ModItems;
import net.andychen.mineterra.networking.ModNetworking;
import net.andychen.mineterra.sounds.ModSounds;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MineTerra implements ModInitializer {
	public static final String MOD_ID = "mineterra";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModSounds.registerSounds();
		ModStatusEffects.registerEffects();

		ModNetworking.registerC2SPackets();
		ServerPlayerEvents.COPY_FROM.register(new PlayerEvents());
		AttackEntityCallback.EVENT.register(new AttackEntityHandler());

		LOGGER.info("Hello Fabric world!");
	}
}
