package net.andychen.mineterra.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class HellforgeScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final PropertyDelegate propertyDelegate;

    public HellforgeScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(6), new ArrayPropertyDelegate(4));
    }

    public HellforgeScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate delegate) {
        super(ModScreenHandlers.HELLFORGE_SCREEN_HANDLER, syncId);
        checkSize(inventory, 6);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);
        this.propertyDelegate = delegate;

        this.addSlot(new Slot(inventory, 0, 12, 56) {
            public boolean canInsert(ItemStack stack) {
                return stack.getItem() == Items.LAVA_BUCKET || stack.getItem() == Items.BUCKET;
            }
        });
        this.addSlot(new Slot(inventory, 1, 50, 11));
        this.addSlot(new Slot(inventory, 2, 43, 34));
        this.addSlot(new Slot(inventory, 3, 50, 57));
        this.addSlot(new Slot(inventory, 4, 84, 34));
        this.addSlot(new Slot(inventory, 5, 142, 35) {
            public boolean canInsert(ItemStack stack) {
                return false;
            }
        });

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        addProperties(delegate);
    }

    public PropertyDelegate getPropertyDelegate() {
        return this.propertyDelegate;
    }

    public boolean isCrafting() {
        return propertyDelegate.get(0) > 0;
    }

    public int getScaledProgress() {
        int cookTime = this.propertyDelegate.get(0);
        int maxCookTime = this.propertyDelegate.get(1);
        int progressArrowSize = 24; // This is the width in pixels of your arrow

        return maxCookTime != 0 && cookTime != 0 ? cookTime * progressArrowSize / maxCookTime : 0;
    }

    public int getScaledFuel() {
        int fuel = this.propertyDelegate.get(2);
        int maxFuel = this.propertyDelegate.get(3);
        int fuelBarSize = 40; // This is the height in pixels of the fuel level

        return maxFuel != 0 && fuel != 0 ? (int) Math.ceil((double) fuel * fuelBarSize / maxFuel) : 0;
    }

    public int getScaledFlame() {
        int cookTime = this.propertyDelegate.get(0);
        int maxCookTime = this.propertyDelegate.get(1);
        int progressFlameSize = 14; // This is the height in pixels of the flame

        return maxCookTime != 0 && cookTime != 0 ? (int) Math.ceil((double) cookTime * progressFlameSize / maxCookTime) : 0;
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 91 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 149));
        }
    }
}
