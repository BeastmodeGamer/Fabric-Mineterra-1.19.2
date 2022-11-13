package net.andychen.mineterra;

import net.andychen.mineterra.item.ModItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MineTerra implements ModInitializer {
	public static final String MOD_ID = "mineterra";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModItems.registerModItems();

		LOGGER.info("Hello Fabric world!");
	}
}
