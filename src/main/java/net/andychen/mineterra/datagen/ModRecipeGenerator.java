package net.andychen.mineterra.datagen;

import net.andychen.mineterra.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeGenerator extends FabricRecipeProvider {
    public ModRecipeGenerator(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
        offerSmelting(exporter, List.of(ModItems.COPPER_ORE), ModItems.COPPER_INGOT, 0.7f, 200, "copper");
        offerSmelting(exporter, List.of(ModItems.TIN_ORE), ModItems.TIN_INGOT, 0.7f, 200, "tin");
        offerSmelting(exporter, List.of(ModItems.LEAD_ORE), ModItems.LEAD_INGOT, 0.7f, 200, "lead");
        offerSmelting(exporter, List.of(ModItems.SILVER_ORE), ModItems.SILVER_INGOT, 0.8f, 200, "silver");
        offerSmelting(exporter, List.of(ModItems.TUNGSTEN_ORE), ModItems.TUNGSTEN_INGOT, 0.8f, 200, "tungsten");
        offerSmelting(exporter, List.of(ModItems.PLATINUM_ORE), ModItems.PLATINUM_INGOT, 1.0f, 200, "platinum");
        offerSmelting(exporter, List.of(ModItems.DEMONITE_ORE), ModItems.DEMONITE_INGOT, 1.5f, 400, "demonite");
        offerSmelting(exporter, List.of(ModItems.CRIMTANE_ORE), ModItems.CRIMTANE_INGOT, 1.5f, 400, "crimtane");

        offerBlasting(exporter, List.of(ModItems.COPPER_ORE), ModItems.COPPER_INGOT, 0.7f, 100, "copper");
        offerBlasting(exporter, List.of(ModItems.TIN_ORE), ModItems.TIN_INGOT, 0.7f, 100, "tin");
        offerBlasting(exporter, List.of(ModItems.LEAD_ORE), ModItems.LEAD_INGOT, 0.7f, 100, "lead");
        offerBlasting(exporter, List.of(ModItems.SILVER_ORE), ModItems.SILVER_INGOT, 0.8f, 100, "silver");
        offerBlasting(exporter, List.of(ModItems.TUNGSTEN_ORE), ModItems.TUNGSTEN_INGOT, 0.8f, 100, "tungsten");
        offerBlasting(exporter, List.of(ModItems.PLATINUM_ORE), ModItems.PLATINUM_INGOT, 1.0f, 100, "platinum");
        offerBlasting(exporter, List.of(ModItems.DEMONITE_ORE), ModItems.DEMONITE_INGOT, 1.5f, 200, "demonite");
        offerBlasting(exporter, List.of(ModItems.CRIMTANE_ORE), ModItems.CRIMTANE_INGOT, 1.5f, 200, "crimtane");
        offerBlasting(exporter, List.of(ModItems.METEORITE_ORE), ModItems.METEORITE_INGOT, 1.7f, 300, "meteorite");
    }
}
