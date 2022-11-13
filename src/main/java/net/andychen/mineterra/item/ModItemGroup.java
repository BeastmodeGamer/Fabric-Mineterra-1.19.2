package net.andychen.mineterra.item;


import net.andychen.mineterra.MineTerra;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModItemGroup {
    public static final ItemGroup MINETERRA = FabricItemGroupBuilder.build(new Identifier(MineTerra.MOD_ID, "mineterra"),
            () -> new ItemStack(ModItems.HELLSTONE_INGOT));
}
