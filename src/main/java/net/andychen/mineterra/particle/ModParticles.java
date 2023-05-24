package net.andychen.mineterra.particle;

import net.andychen.mineterra.MineTerra;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModParticles {
    public static final DefaultParticleType BLOOD = registerParticle("blood", FabricParticleTypes.simple());
    public static final DefaultParticleType FALLEN_STAR_PURPLE = registerParticle("fallen_star_purple", FabricParticleTypes.simple());
    public static final DefaultParticleType FALLEN_STAR_YELLOW = registerParticle("fallen_star_yellow", FabricParticleTypes.simple());
    public static final DefaultParticleType FIRE = registerParticle("fire", FabricParticleTypes.simple());
    public static final DefaultParticleType MAGIC_DUST = registerParticle("magic_dust", FabricParticleTypes.simple());
    public static final DefaultParticleType SHADOW_FLAME = registerParticle("shadow_flame", FabricParticleTypes.simple());

    private static DefaultParticleType registerParticle(String name, DefaultParticleType particle) {
        return Registry.register(Registry.PARTICLE_TYPE, new Identifier(MineTerra.MOD_ID, name), particle);
    }
}
