package net.andychen.mineterra.util;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class TooltipUtil {

    public static Text getRangedDamageText(ItemStack stack, double damage) {
        int j = EnchantmentHelper.getLevel(Enchantments.POWER, stack);
        return Text.translatable(" " + translateDamageText(damage + (double) j * 0.5D + 0.5D) + " Ranged Damage").formatted(Formatting.DARK_GREEN);
    }

    public static Text getCritText(float crit) {
        return Text.translatable(" " + crit + "% Critical Strike Chance").formatted(Formatting.DARK_GREEN);
    }

    public static Text getUseTimeText(float useTime) {
        String useTimeLevel= "Empty";
        float i = (20 / (60 / (useTime)));
        if (i < 3) {
            useTimeLevel = "Insanely fast speed";
        } else if (3 <= i && i < 7) {
            useTimeLevel = "Very fast speed";
        } else if (7 <= i && i < 8) {
            useTimeLevel = "Fast speed";
        } else if (8 <= i && i < 10) {
            useTimeLevel = "Average speed";
        } else if (10 <= i && i < 12) {
            useTimeLevel = "Slow speed";
        } else if (12 <= i && i < 15) {
            useTimeLevel = "Very slow speed";
        } else if (15 <= i && i < 18) {
            useTimeLevel = "Extremely slow speed";
        } else if (i > 18) {
            useTimeLevel = "Snail speed";
        }
        return Text.translatable(" " + useTimeLevel).formatted(Formatting.DARK_GREEN);
    }

    public static Text getKnockbackText(float knockback) {
        String knockbackLevel = "Empty";
        float i = knockback;
        if (i == 0) {
            knockbackLevel = "No knockback";
        } else if (0.1 <= i && i < 0.43) {
            knockbackLevel = "Extremely weak knockback";
        } else if (0.43 <= i && i < 0.86) {
            knockbackLevel = "Very weak knockback";
        } else if (0.86 <= i && i < 1.29) {
            knockbackLevel = "Weak knockback";
        } else if (1.29 <= i && i < 1.72) {
            knockbackLevel = "Average knockback";
        } else if (1.72 <= i && i < 2.15) {
            knockbackLevel = "Strong knockback";
        } else if (2.15 <= i && i < 2.58) {
            knockbackLevel = "Very strong knockback";
        } else if (2.58 <= i && i < 3) {
            knockbackLevel = "Extremely strong knockback";
        } else if (i > 3) {
            knockbackLevel = "Insane knockback";
        }
        return Text.translatable(" " + knockbackLevel).formatted(Formatting.DARK_GREEN);
    }

    public static String translateDamageText(double damage) {
        String i = String.valueOf(damage);
        if (i.indexOf(".0") > 0) {
            i = i.replace(".0", "");
        }
        return i;
    }

}
