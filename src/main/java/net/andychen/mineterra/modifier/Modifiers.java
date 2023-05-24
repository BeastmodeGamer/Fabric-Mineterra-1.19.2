package net.andychen.mineterra.modifier;

import net.andychen.mineterra.MineTerra;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Modifiers {
    public static final Modifier SHARP;

    public Modifiers() {
    }

    private static Modifier register(String name, Modifier modifier) {
        return (Modifier) Registry.register(FabricRegistryBuilder.createSimple(Modifier.class, new Identifier(MineTerra.MOD_ID, "modifier"))
                .attribute(RegistryAttribute.MODDED)
                .buildAndRegister(), name, modifier);
    }

    static {
        SHARP = register("sharp", new MeleeModifier(0.15F, Modifier.Rarity.COMMON, new EquipmentSlot[]{EquipmentSlot.MAINHAND}));
    }
}
