package net.andychen.mineterra;

import net.andychen.mineterra.entity.client.armor.CopperArmorRenderer;
import net.andychen.mineterra.item.ModItems;
import net.andychen.mineterra.networking.ModNetworking;
import net.andychen.mineterra.particle.ModParticleRegistry;
import net.andychen.mineterra.util.ModModelPredicateProvider;
import net.fabricmc.api.ClientModInitializer;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class MineTerraClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        ModNetworking.registerS2CPackets();

        ModParticleRegistry.registerParticles();
        ModModelPredicateProvider.registerModModels();

        GeoArmorRenderer.registerArmorRenderer(new CopperArmorRenderer(), ModItems.COPPER_HELMET,
                ModItems.COPPER_CHESTPLATE, ModItems.COPPER_LEGGINGS, ModItems.COPPER_BOOTS);
    }
}
