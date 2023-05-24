package net.andychen.mineterra.entity.attribute;

import net.andychen.mineterra.MineTerra;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModAttributes {
    public static final EntityAttribute GENERIC_CRIT_CHANCE = registerAttribute("generic.crit_chance",
            new ClampedEntityAttribute("attribute.name.generic.crit_chance", 0.0, 0.0, 1.0D).setTracked(true));
    public static final EntityAttribute GENERIC_RANGED_DAMAGE = registerAttribute("generic.ranged_damage",
            new ClampedEntityAttribute("attribute.name.generic.ranged_damage", 0.0, 0.0, 2048.0D).setTracked(true));

    private static EntityAttribute registerAttribute(String name, EntityAttribute attribute) {
        return Registry.register(Registry.ATTRIBUTE, new Identifier(MineTerra.MOD_ID, name), attribute);
    }

    public static void registerModAttributes(){
        System.out.println("Registering ModAttributes for " + MineTerra.MOD_ID);
    }
}
