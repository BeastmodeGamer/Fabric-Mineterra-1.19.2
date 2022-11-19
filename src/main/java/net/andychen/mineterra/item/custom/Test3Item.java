package net.andychen.mineterra.item.custom;

import net.andychen.mineterra.mana.ManaManagerAccess;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class Test3Item extends Item {

    public Test3Item(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient) {
            user.sendMessage(Text.translatable("Client Mana: " + ((ManaManagerAccess) user).getManaManager().getMana()));
        } else {
            user.sendMessage(Text.translatable("Server Mana: " + ((ManaManagerAccess) user).getManaManager().getMana()));
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }
}
