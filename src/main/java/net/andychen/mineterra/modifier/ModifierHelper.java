package net.andychen.mineterra.modifier;

import net.andychen.mineterra.MineTerra;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class ModifierHelper {

    public ModifierHelper() {
    }

    @Nullable
    public static Identifier getIdFromNbt(String modifier) {
        return Identifier.tryParse(modifier);
    }

    @Nullable
    public static Identifier getModifierId(Modifier modifier) {
        return FabricRegistryBuilder.createSimple(Modifier.class, new Identifier(MineTerra.MOD_ID, "modifier"))
                .attribute(RegistryAttribute.MODDED)
                .buildAndRegister().getId(modifier);
    }
}
