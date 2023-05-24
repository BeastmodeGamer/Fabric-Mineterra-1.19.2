package net.andychen.mineterra;

import net.andychen.mineterra.entity.client.ModEntityModelLayerRegistry;
import net.andychen.mineterra.entity.client.ModEntityRenderRegistry;
import net.andychen.mineterra.networking.ModNetworking;
import net.andychen.mineterra.particle.ModParticleRegistry;
import net.andychen.mineterra.screen.HellforgeScreen;
import net.andychen.mineterra.screen.ModScreenHandlers;
import net.andychen.mineterra.util.ModModelPredicateProvider;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;

public class MineTerraClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        ModNetworking.registerS2CPackets();

        ModEntityModelLayerRegistry.registerEntityModels();
        ModEntityRenderRegistry.registerEntityRenderers();
        ModParticleRegistry.registerParticles();
        ModModelPredicateProvider.registerModModels();

        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
            registry.register(new Identifier(MineTerra.MOD_ID, "gui/empty_accessory_slot"));
        }));

        HandledScreens.register(ModScreenHandlers.HELLFORGE_SCREEN_HANDLER, HellforgeScreen::new);
    }
}
