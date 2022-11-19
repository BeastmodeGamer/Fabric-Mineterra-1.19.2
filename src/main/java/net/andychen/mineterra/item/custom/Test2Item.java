package net.andychen.mineterra.item.custom;

import net.andychen.mineterra.mana.ManaManagerAccess;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class Test2Item extends Item {

    public Test2Item(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.isSneaking()) {
            if (world.isClient) {
                user.sendMessage(Text.translatable("Client Max Mana: " + ((ManaManagerAccess) user).getManaManager().getMaxMana()));
            } else {
                user.sendMessage(Text.translatable("Server Max Mana: " + ((ManaManagerAccess) user).getManaManager().getMaxMana()));
            }
        } else {
            if (world.isClient) {
                user.sendMessage(Text.translatable("Client Mana Regen Time: " + ((ManaManagerAccess) user).getManaManager().getManaRegenTime()));
            } else {
                user.sendMessage(Text.translatable("Server Mana Regen Time: " + ((ManaManagerAccess) user).getManaManager().getManaRegenTime()));
            }
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }
}
