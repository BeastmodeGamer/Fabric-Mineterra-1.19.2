package net.andychen.mineterra.item;

import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Lazy;

import java.util.function.Supplier;

public enum ModToolMaterial implements ToolMaterial {
    WOOD(0, 59, 2.0F, 0.0F, 15, () -> Ingredient.fromTag(ItemTags.PLANKS)),
    STONE(1, 131, 4.0F, 1.0F, 5, () -> Ingredient.fromTag(ItemTags.STONE_TOOL_MATERIALS)),
    IRON(2, 250, 6.0F, 2.0F, 14, () -> Ingredient.ofItems(new ItemConvertible[]{Items.IRON_INGOT})),
    DIAMOND(3, 1561, 8.0F, 3.0F, 10, () -> Ingredient.ofItems(new ItemConvertible[]{Items.DIAMOND})),
    GOLD(0, 32, 12.0F, 0.0F, 22, () -> Ingredient.ofItems(new ItemConvertible[]{Items.GOLD_INGOT})),
    NETHERITE(4, 2031, 9.0F, 4.0F, 15, () -> Ingredient.ofItems(new ItemConvertible[]{Items.NETHERITE_INGOT})),

    // MOD MATERIALS
    COPPER(1, 200, 5.0F, 1.5F, 20, () -> {
        return Ingredient.ofItems(new ItemConvertible[]{ModItems.CRIMTANE_INGOT}); // Stronger & Faster than Stone
    }),
    TIN(1, 200, 5.5F, 1.5F, 20, () -> {
        return Ingredient.ofItems(new ItemConvertible[]{ModItems.TUNGSTEN_INGOT}); // Faster than Stone & Tin
    }),
    LEAD(2, 250, 6.5F, 2.0F, 14, () -> {
        return Ingredient.ofItems(new ItemConvertible[]{ModItems.LEAD_INGOT}); // Faster than Iron
    }),
    SILVER(2, 500, 7.0f, 2.5F, 16, () -> {
        return Ingredient.ofItems(new ItemConvertible[]{ModItems.SILVER_INGOT}); // Stronger and Faster than Iron
    }),
    TUNGSTEN(2, 500, 7.5f, 2.5F, 16, () -> {
        return Ingredient.ofItems(new ItemConvertible[]{ModItems.TUNGSTEN_INGOT}); // Faster than Iron & Silver
    }),
    PLATINUM(3, 1561, 8.5F, 3.0F, 12, () -> {
        return Ingredient.ofItems(new ItemConvertible[]{ModItems.PLATINUM_INGOT}); // Faster than Diamond
    }),
    DEMONITE(5, 2031, 9.5F, 4.0F, 10, () -> {
        return Ingredient.ofItems(new ItemConvertible[]{ModItems.DEMONITE_INGOT}); // Faster than Netherite & Crimtane
    }),
    CRIMTANE(5, 2031, 9.0F, 4.5F, 10, () -> {
        return Ingredient.ofItems(new ItemConvertible[]{ModItems.CRIMTANE_INGOT}); // Stronger than Netherite & Demonite
    }),
    METEORITE(4, 1315, 8.5F, 4.5F, 25, () -> {
        return Ingredient.ofItems(new ItemConvertible[]{ModItems.METEORITE_INGOT}); // Stronger than Platinum
    }),
    HELLSTONE(6, 2131, 10.0F, 4.5F, 8, () -> {
        return Ingredient.ofItems(new ItemConvertible[]{ModItems.HELLSTONE_INGOT}); // Stronger & Faster than Demonite/Crimtane
    });

    private final int miningLevel;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Lazy<Ingredient> repairIngredient;

    private ModToolMaterial(int miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantability, Supplier<Ingredient> repairIngredient) {
        this.miningLevel = miningLevel;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = new Lazy(repairIngredient);
    }

    public int getDurability() {
        return this.itemDurability;
    }

    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    public float getAttackDamage() {
        return this.attackDamage;
    }

    public int getMiningLevel() {
        return this.miningLevel;
    }

    public int getEnchantability() {
        return this.enchantability;
    }

    public Ingredient getRepairIngredient() {
        return (Ingredient)this.repairIngredient.get();
    }
}
