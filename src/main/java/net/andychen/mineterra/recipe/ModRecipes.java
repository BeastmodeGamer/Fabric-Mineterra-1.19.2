package net.andychen.mineterra.recipe;

import net.andychen.mineterra.MineTerra;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModRecipes {
    public static void registerRecipes() {
        Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(MineTerra.MOD_ID, HellforgeRecipe.Serializer.ID),
                HellforgeRecipe.Serializer.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, new Identifier(MineTerra.MOD_ID, HellforgeRecipe.Type.ID),
                HellforgeRecipe.Type.INSTANCE);
    }
}
