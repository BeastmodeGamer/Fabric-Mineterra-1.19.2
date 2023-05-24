package net.andychen.mineterra.util.access;

import net.minecraft.item.ItemStack;

public interface PlayerInventoryMixinAccess {
    ItemStack getAccessoryStack(int slot);
}
