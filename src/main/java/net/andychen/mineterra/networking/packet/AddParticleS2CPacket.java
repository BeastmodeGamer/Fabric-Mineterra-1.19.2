package net.andychen.mineterra.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Random;

public class AddParticleS2CPacket {
    private final boolean alwaysSpawn;
    private final double x;
    private final double y;
    private final double z;
    private final double velocityX1;
    private final double velocityX2;
    private final double velocityY1;
    private final double velocityY2;
    private final double velocityZ1;
    private final double velocityZ2;
    private final int count;
    private final ParticleEffect parameters;

    public AddParticleS2CPacket(ParticleEffect parameters, boolean alwaysSpawn, double x, double y, double z, double velocityX1, double velocityX2, double velocityY1, double velocityY2, double velocityZ1, double velocityZ2, int count) {
        this.alwaysSpawn = alwaysSpawn;
        this.x = x;
        this.y = y;
        this.z = z;
        this.velocityX1 = velocityX1;
        this.velocityX2 = velocityX2;
        this.velocityY1 = velocityY1;
        this.velocityY2 = velocityY2;
        this.velocityZ1 = velocityZ1;
        this.velocityZ2 = velocityZ2;
        this.count = count;
        this.parameters = parameters;
    }

    public AddParticleS2CPacket(ParticleEffect parameters, boolean alwaysSpawn, double x, double y, double z, double velocityX, double velocityY, double velocityZ, int count) {
        this.alwaysSpawn = alwaysSpawn;
        this.x = x;
        this.y = y;
        this.z = z;
        this.velocityX1 = velocityX;
        this.velocityX2 = 0;
        this.velocityY1 = velocityY;
        this.velocityY2 = 0;
        this.velocityZ1 = velocityZ;
        this.velocityZ2 = 0;
        this.count = count;
        this.parameters = parameters;
    }

    public AddParticleS2CPacket(PacketByteBuf buf) {
        ParticleType<ParticleEffect> particleType = (ParticleType) buf.readRegistryValue(Registry.PARTICLE_TYPE);
        this.alwaysSpawn = buf.readBoolean();
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
        this.velocityX1 = buf.readDouble();
        this.velocityX2 = buf.readDouble();
        this.velocityY1 = buf.readDouble();
        this.velocityY2 = buf.readDouble();
        this.velocityZ1 = buf.readDouble();
        this.velocityZ2 = buf.readDouble();
        this.count = buf.readInt();
        this.parameters = this.readParticleParameters(buf, particleType);
    }

    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        Random random = new Random();
        ParticleType<ParticleEffect> particleType = (ParticleType) buf.readRegistryValue(Registry.PARTICLE_TYPE);
        boolean alwaysSpawn = buf.readBoolean();
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        double velocityX1 = buf.readDouble();
        double velocityX2 = buf.readDouble();
        double velocityY1 = buf.readDouble();
        double velocityY2 = buf.readDouble();
        double velocityZ1 = buf.readDouble();
        double velocityZ2 = buf.readDouble();
        int count = buf.readInt();
        ParticleEffect parameters = particleType.getParametersFactory().read(particleType, buf);
        client.execute(() -> {
            if (velocityX2 == 0 && velocityY2 == 0 && velocityZ2 == 0) {
                for (int i = 0; i < count; i++) {
                    ((World) client.world).addParticle(parameters, alwaysSpawn, x, y, z, velocityX1,
                            velocityY1, velocityZ1);
                }
            } else {
                for (int i = 0; i < count; i++) {
                    ((World) client.world).addParticle(parameters, alwaysSpawn, x, y, z, random.nextDouble(velocityX1, velocityX2),
                            random.nextDouble(velocityY1, velocityY2), random.nextDouble(velocityZ1, velocityZ2));
                }
            }
        });
    }

    private ParticleEffect readParticleParameters(PacketByteBuf buf, ParticleType<ParticleEffect> type) {
        return type.getParametersFactory().read(type, buf);
    }

    public void write(PacketByteBuf buf) {
        buf.writeRegistryValue(Registry.PARTICLE_TYPE, this.parameters.getType());
        buf.writeBoolean(this.alwaysSpawn);
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
        buf.writeDouble(this.velocityX1);
        buf.writeDouble(this.velocityX2);
        buf.writeDouble(this.velocityY1);
        buf.writeDouble(this.velocityY2);
        buf.writeDouble(this.velocityZ1);
        buf.writeDouble(this.velocityZ2);
        buf.writeInt(this.count);
        this.parameters.write(buf);
    }

    public boolean isAlwaysSpawn() {
        return this.alwaysSpawn;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public double getVelocityX1() {
        return this.velocityX1;
    }

    public double getVelocityX2() {
        return this.velocityX2;
    }

    public double getVelocityY1() {
        return this.velocityY1;
    }

    public double getVelocityY2() {
        return this.velocityY2;
    }

    public double getVelocityZ1() {
        return this.velocityZ1;
    }

    public double getVelocityZ2() {
        return this.velocityZ2;
    }

    public int getCount() {
        return this.count;
    }

    public ParticleEffect getParameters() {
        return this.parameters;
    }
}
