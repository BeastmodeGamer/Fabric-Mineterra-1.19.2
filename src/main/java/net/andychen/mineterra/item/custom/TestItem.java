package net.andychen.mineterra.item.custom;

import net.andychen.mineterra.mana.ManaManagerAccess;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class TestItem extends Item {

    public TestItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.isSneaking()) {
            ((ManaManagerAccess) user).getManaManager().removeMana(1);
        } else {
            ((ManaManagerAccess) user).getManaManager().addMana(1);
        }
        if (world.isClient) {
            user.sendMessage(Text.translatable("Client: " + ((ManaManagerAccess) user).getManaManager().getMana()));
        } else {
            user.sendMessage(Text.translatable("Server: " + ((ManaManagerAccess) user).getManaManager().getMana()));
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }
}
