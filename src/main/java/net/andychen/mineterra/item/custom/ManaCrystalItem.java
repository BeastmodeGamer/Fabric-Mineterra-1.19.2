package net.andychen.mineterra.item.custom;

import net.andychen.mineterra.mana.ManaManager;
import net.andychen.mineterra.mana.ManaManagerAccess;
import net.andychen.mineterra.particle.ModParticles;
import net.andychen.mineterra.sounds.ModSounds;
import net.andychen.mineterra.util.access.ServerWorldMixinAccess;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ManaCrystalItem extends Item {

    public ManaCrystalItem(Settings settings) {
        super(settings);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 30;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        ManaManager manaManager = ((ManaManagerAccess) user).getManaManager();

        if (manaManager.getMaxMana() < 40) {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(itemStack);
        }
        return TypedActionResult.fail(itemStack);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        PlayerEntity player = (PlayerEntity) user;
        ServerWorldMixinAccess serverWorld = ((ServerWorldMixinAccess) ((ServerWorld) world));
        ManaManager manaManager = ((ManaManagerAccess) player).getManaManager();

        manaManager.setMaxMana(manaManager.getMaxMana() + 2);
        manaManager.setMana(manaManager.getMaxMana());

        for (double u = 14 * Math.PI / 9 ; u <= 2 * Math.PI * 1; u += 0.05) {
            float i = (float) (Math.cos(u)/4 + Math.cos(u)/4);
            float j = (float) (Math.cos(u)/7 + Math.cos(u)/7);
            serverWorld.addParticle(ModParticles.BLUE_MAGIC, true,
                    user.getX(), user.getBodyY(0.5D), user.getZ(), -i, i, -i, i, -i, i, 12);
            serverWorld.addParticle(ModParticles.BLUE_MAGIC, true,
                    user.getX(), user.getBodyY(0.5D), user.getZ(), -j, j, -j, j, -j, j, 12);
        }

        world.playSound(null, user.getX(), user.getY(), user.getZ(),
                ModSounds.ITEM_MANA_CRYSTAL_USE, SoundCategory.NEUTRAL, 1F, 1F);

        if (!player.getAbilities().creativeMode){
            stack.decrement(1);
        }

        player.incrementStat(Stats.USED.getOrCreateStat(this));
        return stack;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("Permanently increases maximum mana by 1 star").formatted(Formatting.ITALIC, Formatting.GRAY));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
