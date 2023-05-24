package net.andychen.mineterra.mixin;

import com.mojang.datafixers.util.Pair;
import net.andychen.mineterra.MineTerra;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerScreenHandler.class)
public abstract class PlayerScreenHandlerMixin extends AbstractRecipeScreenHandler<CraftingInventory> {
    private static final Identifier EMPTY_ACCESSORY_SLOT = new Identifier(MineTerra.MOD_ID, "gui/empty_accessory_slot");

    public PlayerScreenHandlerMixin(ScreenHandlerType<?> screenHandlerType, int i) {
        super(screenHandlerType, i);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    protected void PlayerScreenHandlerMixinConstructor(PlayerInventory inventory, boolean onServer, PlayerEntity owner, CallbackInfo info) {
        int k;
        for(k = 0; k < 5; ++k) {
            this.addSlot(new Slot(inventory, 45 - k, 8 + k * 18, -20) {
                /*public int getMaxItemCount() {
                    return 64;
                }*/

                /*public boolean canInsert(ItemStack stack) {
                    return true;
                }*/

                public Pair<Identifier, Identifier> getBackgroundSprite() {
                    return Pair.of(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, EMPTY_ACCESSORY_SLOT);
                }
            });
        }
    }

    /*@Inject(method = "transferSlot", at = @At("HEAD"), cancellable = true)
    protected void transferSlotInjector(PlayerEntity player, int index, CallbackInfoReturnable cir) {
        ItemStack itemStack1 = ItemStack.EMPTY;
        Slot slot = (Slot)this.slots.get(index);
        if (slot != null && slot.hasStack()) {
            ItemStack itemStack3 = slot.getStack();
            itemStack1 = itemStack3.copy();
            EquipmentSlot equipmentSlot = MobEntity.getPreferredEquipmentSlot(itemStack1);
            if (index == 0) { // Crafting Output Index
                if (!this.insertItem(itemStack3, 9, 45, true)) { // Can insert into Inventory + Hotbar?
                    cir.setReturnValue(ItemStack.EMPTY);
                }

                slot.onQuickTransfer(itemStack3, itemStack1);
            } else if (index >= 1 && index < 5) { // Crafting Input Indexes
                if (!this.insertItem(itemStack3, 9, 45, false)) { // Can insert into Inventory + Hotbar?
                    cir.setReturnValue(ItemStack.EMPTY);
                }
            } else if (index >= 5 && index < 9) { // Armor Item Indexes
                if (!this.insertItem(itemStack3, 9, 45, false)) { // Can insert into Inventory + Hotbar?
                    cir.setReturnValue(ItemStack.EMPTY);
                }
            } else if (equipmentSlot.getType() == EquipmentSlot.Type.ARMOR && !((Slot)this.slots.get(8 - equipmentSlot.getEntitySlotId())).hasStack()) { // If item is Armor Item AND specific Armor Type Index DOESN'T have item
                int i = 8 - equipmentSlot.getEntitySlotId();
                if (!this.insertItem(itemStack3, i, i + 1, false)) { // Can insert into specific Armor Type Index?
                    cir.setReturnValue(ItemStack.EMPTY);
                }
            } else if (equipmentSlot == EquipmentSlot.OFFHAND && !((Slot)this.slots.get(45)).hasStack()) { // If  item is Offhand Item (Shield Item) AND Offhand Index DOESN'T have item
                if (!this.insertItem(itemStack3, 45, 46, false)) { // Can insert into Offhand?
                    cir.setReturnValue(ItemStack.EMPTY);
                }
            } else if (index >= 9 && index < 36) { // Inventory Indexes
                if (itemStack3.getItem() instanceof FallenStarItem) {
                    if (!this.insertItem(itemStack3, 46, 51, false) && !this.insertItem(itemStack3, 36, 45, false)) { // Can insert into Accessory Indexes?
                        cir.setReturnValue(ItemStack.EMPTY);
                    }
                } else if (!this.insertItem(itemStack3, 36, 45, false)) { // Can insert into Hotbar?
                    cir.setReturnValue(ItemStack.EMPTY);
                }
            } else if (index >= 36 && index < 45) { // Hotbar Indexes
                if (itemStack3.getItem() instanceof FallenStarItem) {
                    if (!this.insertItem(itemStack3, 46, 51, false) && !this.insertItem(itemStack3, 9, 36, false)) { // Can insert into Accessory Indexes?
                        cir.setReturnValue(ItemStack.EMPTY);
                    }
                } else if (!this.insertItem(itemStack3, 9, 36, false)) { // Can insert into Inventory?
                    cir.setReturnValue(ItemStack.EMPTY);
                }
            } else if (index >= 46 && index < 51) { // Accessory Indexes
                if (!this.insertItem(itemStack3, 36, 45, false)) { // Can insert into Hotbar?
                    cir.setReturnValue(ItemStack.EMPTY);
                }
            } else if (index >= 46 && index < 51) { // Accessory Indexes
                if (!this.insertItem(itemStack3, 9, 36, false)) { // Can insert into Inventory?
                    cir.setReturnValue(ItemStack.EMPTY);
                }
            } else if (!this.insertItem(itemStack3, 9, 45, false)) { // Can insert into Inventory + Hotbar?
                cir.setReturnValue(ItemStack.EMPTY);
            }

            if (itemStack3.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }

            if (itemStack3.getCount() == itemStack1.getCount()) {
                cir.setReturnValue(ItemStack.EMPTY);
            }

            slot.onTakeItem(player, itemStack3);
            if (index == 0) {
                player.dropItem(itemStack3, false);
            }
        }
        cir.setReturnValue(itemStack1);
    }*/

    @Inject(method = "transferSlot", at = @At("TAIL"))
    protected void transferSlotTest(PlayerEntity player, int index, CallbackInfoReturnable cir) {
        //Slot slot2 = (Slot)this.slots.get(index);
        //System.out.println(this.slots);
        for (int i = 0; i < this.slots.size(); i++) {
            System.out.println("Test Slot " + i + " Index: " + this.slots.get(i) + " | Slot Item: " + this.slots.get(i).getStack());
        }
    }

}