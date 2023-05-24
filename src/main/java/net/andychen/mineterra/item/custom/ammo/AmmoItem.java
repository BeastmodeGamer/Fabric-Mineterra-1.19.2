package net.andychen.mineterra.item.custom.ammo;

import net.minecraft.item.Item;

public class AmmoItem extends Item {
    private final double ammoDamage;
    private final float velocity;
    private final int velocityMultiplier;

    public AmmoItem(double ammoDamage, float velocity, int velocityMultiplier, Settings settings) {
        super(settings);
        this.ammoDamage = ammoDamage;
        this.velocity = velocity;
        this.velocityMultiplier = velocityMultiplier;
    }

    public double getAmmoDamage() {
        return this.ammoDamage;
    }

    public float getVelocity() {
        return this.velocity;
    }

    public int getVelocityMultiplier() {
        return this.velocityMultiplier;
    }
}
