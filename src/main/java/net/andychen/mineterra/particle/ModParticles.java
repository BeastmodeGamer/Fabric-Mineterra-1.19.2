package net.andychen.mineterra.particle;

import net.andychen.mineterra.MineTerra;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModParticles {
    public static final DefaultParticleType FIRE = registerParticle("fire", FabricParticleTypes.simple());

    private static DefaultParticleType registerParticle(String name, DefaultParticleType particle) {
        return Registry.register(Registry.PARTICLE_TYPE, new Identifier(MineTerra.MOD_ID, name), particle);
    }
}
