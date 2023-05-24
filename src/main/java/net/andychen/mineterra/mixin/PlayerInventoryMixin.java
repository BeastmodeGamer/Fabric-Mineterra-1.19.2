package net.andychen.mineterra.mixin;

import net.andychen.mineterra.util.access.PlayerInventoryMixinAccess;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.tag.TagKey;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin implements PlayerInventoryMixinAccess {
    public DefaultedList<ItemStack> accessory;
    @Shadow
    @Final
    private List<DefaultedList<ItemStack>> combinedInventory;

    @Inject(method = "<init>", at = @At("TAIL"))
    protected void PlayerInventoryMixinConstructor(PlayerEntity player, CallbackInfo info) {
        this.accessory = DefaultedList.ofSize(5, ItemStack.EMPTY);
    }

    @Inject(method = "writeNbt", at = @At("TAIL"))
    protected void injectWriteNbt(NbtList nbtList, CallbackInfoReturnable cir) {
        int i;
        NbtCompound nbtCompound;
        for (i = 0; i < this.accessory.size(); ++i) {
            if (!((ItemStack) this.accessory.get(i)).isEmpty()) {
                nbtCompound = new NbtCompound();
                nbtCompound.putByte("Slot", (byte) (i + 200));
                ((ItemStack) this.accessory.get(i)).writeNbt(nbtCompound);
                nbtList.add(nbtCompound);
            }
        }
    }

    @Inject(method = "readNbt", at = @At("TAIL"))
    protected void injectReadNbtTail(NbtList nbtList, CallbackInfo info) {
        this.accessory.clear();
        for (int i = 0; i < nbtList.size(); ++i) {
            NbtCompound nbtCompound = nbtList.getCompound(i);
            int l = nbtCompound.getByte("Slot") & 255;
            ItemStack itemStack = ItemStack.fromNbt(nbtCompound);
            if (!itemStack.isEmpty()) {
                if (l >= 200 && l < this.accessory.size() + 200) {
                    this.accessory.set(l - 200, itemStack);
                }
            }
        }
    }

    @Inject(method = "size", at = @At("HEAD"), cancellable = true)
    protected void injectSize(CallbackInfoReturnable cir) {
        cir.setReturnValue(((PlayerInventory) (Object) this).main.size() + ((PlayerInventory) (Object) this).armor.size()
                + ((PlayerInventory) (Object) this).offHand.size() + this.accessory.size());
    }

    @Inject(method = "updateItems", at = @At("TAIL"))
    protected void updateItemsInjector(CallbackInfo info) {
        for (int i = 0; i < this.accessory.size(); ++i) {
            if (!((ItemStack) this.accessory.get(i)).isEmpty()) {
                ((ItemStack) this.accessory.get(i)).inventoryTick(((PlayerInventory) (Object) this).player.world, ((PlayerInventory) (Object) this).player, i, ((PlayerInventory) (Object) this).selectedSlot == i);
            }
        }
    }

    @Inject(method = "removeStack(II)Lnet/minecraft/item/ItemStack;", at = @At("HEAD"), cancellable = true)
    protected void removeStackInjector(int slot, int amount, CallbackInfoReturnable cir) {
        List<ItemStack> accessoryList = null;

        System.out.println(slot + " " + amount);

        if (slot > 40 && slot <= 45) {
            accessoryList = this.accessory;

            if (accessoryList != null && !((ItemStack) accessoryList.get(slot - 41)).isEmpty()) {
                ItemStack itemStack =  Inventories.splitStack(accessoryList, slot - 41, amount);
                System.out.println("removeStack(II): " + itemStack);
                cir.setReturnValue(itemStack);
            } else {
                cir.setReturnValue(ItemStack.EMPTY);
            }
        }
    }

    @Inject(method = "removeOne(Lnet/minecraft/item/ItemStack;)V", at = @At("HEAD"))
    protected void removeOneInjector(ItemStack stack, CallbackInfo info) {
        /*while (true) {
            for (int j = 0; j < this.accessory.size(); ++j) {
                if (this.accessory.get(j) == stack) {
                    System.out.println("removeOne(LItemStack): " + this.accessory.get(j));
                    this.accessory.set(j, ItemStack.EMPTY);
                    break;
                }
            }
            break;
        }*/
    }

    @Inject(method = "removeStack(I)Lnet/minecraft/item/ItemStack;", at = @At("HEAD"), cancellable = true)
    protected void removeStackInjector2(int slot, CallbackInfoReturnable cir) {
        /*DefaultedList<ItemStack> accessoryList = null;

        if (slot > 40 && slot <= 45) {
            accessoryList = this.accessory;
        }

        if (accessoryList != null && !((ItemStack) accessoryList.get(slot - 41)).isEmpty()) {
            ItemStack itemStack = (ItemStack) accessoryList.get(slot - 41);
            System.out.println("removeStack(I): " + accessoryList.get(slot - 41));
            accessoryList.set(slot - 41, ItemStack.EMPTY);
            cir.setReturnValue(itemStack);
        }

        DefaultedList<ItemStack> defaultedList = null;

        DefaultedList defaultedList2;
        for(Iterator var3 = this.combinedInventory.iterator(); var3.hasNext(); slot -= defaultedList2.size()) {
            defaultedList2 = (DefaultedList)var3.next();
            if (slot < defaultedList2.size()) {
                defaultedList = defaultedList2;
                break;
            }
        }

        if (defaultedList != null && !((ItemStack)defaultedList.get(slot)).isEmpty()) {
            ItemStack itemStack = (ItemStack)defaultedList.get(slot);
            System.out.println("DEFAULTED removeStack(I): " + defaultedList.get(slot));
            defaultedList.set(slot, ItemStack.EMPTY);
            cir.setReturnValue(itemStack);
        } else {
            cir.setReturnValue(ItemStack.EMPTY);
        }*/
    }

    @Inject(method = "setStack(ILnet/minecraft/item/ItemStack;)V", at = @At("HEAD"))
    protected void setStackInjector(int slot, ItemStack stack, CallbackInfo info) {
        DefaultedList<ItemStack> accessoryList = null;

        if (slot > 40 && slot <= 45) {
            accessoryList = this.accessory;
        }

        if (accessoryList != null) {
            accessoryList.set(slot - 41, stack);
            System.out.println("setStack: " + slot + " " + stack);
        }
    }

    @Inject(method = "setStack(ILnet/minecraft/item/ItemStack;)V", at = @At("TAIL"))
    protected void setStackInjector2(int slot, ItemStack stack, CallbackInfo info) {
        //System.out.println("setStack2: " + slot + " " + stack);
    }

    @Inject(method = "getStack(I)Lnet/minecraft/item/ItemStack;", at = @At("HEAD"), cancellable = true)
    protected void getStackInjector(int slot, CallbackInfoReturnable cir) {
        List<ItemStack> accessoryList = null;

        if (slot > 40 && slot <= 45) {
            accessoryList = this.accessory;
        }

        if (accessoryList != null) {
            cir.setReturnValue(accessoryList == null ? ItemStack.EMPTY : (ItemStack) accessoryList.get(slot - 41));
        }
    }

    @Inject(method = "dropAll()V", at = @At("HEAD"))
    protected void dropAllInjector(CallbackInfo info) {
        for (int j = 0; j < this.accessory.size(); ++j) {
            ItemStack itemStack = (ItemStack) this.accessory.get(j);
            if (!itemStack.isEmpty()) {
                ((PlayerInventory) (Object) this).player.dropItem(itemStack, true, false);
                this.accessory.set(j, ItemStack.EMPTY);
            }
        }
    }

    @Inject(method = "contains(Lnet/minecraft/item/ItemStack;)Z", at = @At("HEAD"), cancellable = true)
    protected void containsInjector(ItemStack stack, CallbackInfoReturnable cir) {
        /*Iterator accessoryItr = this.accessory.iterator();

        while (accessoryItr.hasNext()) {
            ItemStack itemStack = (ItemStack) accessoryItr.next();
            if (!itemStack.isEmpty() && itemStack.isItemEqualIgnoreDamage(stack)) {
                cir.setReturnValue(true);
            }
        }*/
    }

    @Inject(method = "contains(Lnet/minecraft/tag/TagKey;)Z", at = @At("HEAD"), cancellable = true)
    protected void containsInjector2(TagKey<Item> tag, CallbackInfoReturnable cir) {
        /*Iterator accessoryItr = this.accessory.iterator();

        while (accessoryItr.hasNext()) {
            ItemStack itemStack = (ItemStack) accessoryItr.next();
            if (!itemStack.isEmpty() && itemStack.isIn(tag)) {
                cir.setReturnValue(true);
            }
        }*/
    }

    @Inject(method = "clear()V", at = @At("TAIL"))
    protected void clearInjector(CallbackInfo info) {
        //this.accessory.clear();
    }

    @Inject(method = "isEmpty()Z", at = @At("HEAD"), cancellable = true)
    public void isEmptyInjector(CallbackInfoReturnable cir) {
        /*ItemStack itemStack;
        do {
            Iterator accessoryItr = this.accessory.iterator();

            do {
                if (!accessoryItr.hasNext()) {
                    cir.setReturnValue(true);
                }

                itemStack = (ItemStack) accessoryItr.next();
            } while (itemStack.isEmpty());

            cir.setReturnValue(false);

        } while (itemStack.isEmpty());
*/
    }

    @Inject (method = "dropSelectedItem(Z)Lnet/minecraft/item/ItemStack;", at = @At("HEAD"), cancellable = true)
    public void dropSelectedItemInjector(boolean entireStack, CallbackInfoReturnable cir) {
        ItemStack itemStack = ((PlayerInventory)(Object)this).getMainHandStack();
        System.out.println("Selected Slot: " + ((PlayerInventory)(Object)this).selectedSlot + " | Entire Stack?: " + entireStack);
        cir.setReturnValue(itemStack.isEmpty() ? ItemStack.EMPTY : ((PlayerInventory)(Object)this).removeStack(((PlayerInventory)(Object)this).selectedSlot, entireStack ? itemStack.getCount() : 1));
    }

    @Override
    public ItemStack getAccessoryStack(int slot) {
        return this.accessory.get(slot);
    }

    public DefaultedList<ItemStack> getAccessory() {
        return this.accessory;
    }
}
