package net.andychen.mineterra.item.custom.guns;

import net.andychen.mineterra.entity.custom.FallenStarEntity;
import net.andychen.mineterra.item.ModItems;
import net.andychen.mineterra.item.custom.FallenStarItem;
import net.andychen.mineterra.sounds.ModSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class StarCannonItem extends Item {

    public StarCannonItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        boolean creativeMode = user.getAbilities().creativeMode;
        ItemStack ammo = this.getAmmo(user);

        if (creativeMode || !this.getAmmo(user).isEmpty()) {
            if (!world.isClient) {
                FallenStarEntity fallenStarEntity = new FallenStarEntity(world, user);

                fallenStarEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 1.5f, 1.0f);
                world.spawnEntity(fallenStarEntity);
            }
            world.playSound(null, user.getX(), user.getY(), user.getZ(), ModSounds.ENTITY_FALLEN_STAR_FALLING, SoundCategory.PLAYERS, 1.0f, 1.0f);
            //this.decrementAmmo(user, ammo);

            user.incrementStat(Stats.USED.getOrCreateStat(this));
            user.setCurrentHand(hand);
            return TypedActionResult.pass(itemStack);
        }
        return TypedActionResult.fail(itemStack);
    }

    public ItemStack getAmmo(PlayerEntity user) {
        ItemStack offHandStack = user.getOffHandStack();
        if (offHandStack.getItem() instanceof FallenStarItem) { // Gets Ammo in Offhand
            return offHandStack;
        }
        for (int i = 0; i < user.getInventory().size(); ++i) { // Gets first Ammo in inventory
            ItemStack itemStack = user.getInventory().getStack(i);
            if (itemStack.getItem() instanceof FallenStarItem) {
                return itemStack;
            }
        }
        return user.getAbilities().creativeMode ? new ItemStack(ModItems.FALLEN_STAR) : ItemStack.EMPTY; // If creative, get basic bullet
    }
}
