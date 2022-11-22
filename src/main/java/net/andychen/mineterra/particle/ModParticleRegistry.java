package net.andychen.mineterra.particle;

import net.andychen.mineterra.MineTerra;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ModParticleRegistry {

    public static void registerParticles() {
        System.out.println("Registering ModParticles for " + MineTerra.MOD_ID);

        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
            registry.register(new Identifier(MineTerra.MOD_ID, "particle/fire"));
        }));

        ParticleFactoryRegistry.getInstance().register(ModParticles.FIRE, FlameParticle.Factory::new);
    }
}
