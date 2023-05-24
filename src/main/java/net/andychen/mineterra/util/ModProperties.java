package net.andychen.mineterra.util;

import net.andychen.mineterra.block.enums.DoublePart;
import net.minecraft.state.property.EnumProperty;

public class ModProperties {
    public static final EnumProperty<DoublePart> DOUBLE_PART;
    public ModProperties() {
    }
    static {
        DOUBLE_PART = EnumProperty.of("part", DoublePart.class);
    }
}
