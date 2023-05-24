package net.andychen.mineterra.particle;

import net.andychen.mineterra.MineTerra;
import net.andychen.mineterra.particle.custom.GlowParticle;
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
            registry.register(new Identifier(MineTerra.MOD_ID, "particle/blood"));
        }));
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
            registry.register(new Identifier(MineTerra.MOD_ID, "particle/fallen_star_purple"));
        }));
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
            registry.register(new Identifier(MineTerra.MOD_ID, "particle/fallen_star_yellow"));
        }));
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
            registry.register(new Identifier(MineTerra.MOD_ID, "particle/fire"));
        }));
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
            registry.register(new Identifier(MineTerra.MOD_ID, "particle/magic_dust"));
        }));
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
            registry.register(new Identifier(MineTerra.MOD_ID, "particle/shadow_flame"));
        }));

        ParticleFactoryRegistry.getInstance().register(ModParticles.BLOOD, FlameParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.FALLEN_STAR_PURPLE, GlowParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.FALLEN_STAR_YELLOW, GlowParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.FIRE, FlameParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.MAGIC_DUST, GlowParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.SHADOW_FLAME, FlameParticle.Factory::new);
    }
}
