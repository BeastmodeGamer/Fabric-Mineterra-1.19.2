package net.andychen.mineterra.block.enums;

import net.minecraft.util.StringIdentifiable;

public enum DoublePart implements StringIdentifiable {
    LEFT("left"),
    RIGHT("right");

    private final String name;

    private DoublePart(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String asString() {
        return this.name;
    }
}
