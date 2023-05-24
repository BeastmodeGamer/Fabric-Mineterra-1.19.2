package net.andychen.mineterra.item.custom.guns;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.andychen.mineterra.entity.attribute.ModAttributes;
import net.andychen.mineterra.entity.custom.ranged.BulletEntity;
import net.andychen.mineterra.item.ModItems;
import net.andychen.mineterra.item.custom.ammo.BulletItem;
import net.andychen.mineterra.sounds.ModSounds;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import java.util.UUID;

public class GunItem extends Item {
    private double rangedDamage;
    private double critChance;
    protected static final UUID RANGED_DAMAGE_MODIFIER_ID = UUID.fromString("aa195585-8719-4ee1-92d7-4bb7a153fb1d");
    protected static final UUID CRIT_CHANCE_MODIFIER_ID = UUID.fromString("b0c5e8d3-0f72-4fc1-b367-184d71734971");
    private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

    public GunItem(double rangedDamage, double critChance, Settings settings) {
        super(settings);
        this.rangedDamage = rangedDamage;
        this.critChance = critChance;
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(ModAttributes.GENERIC_RANGED_DAMAGE, new EntityAttributeModifier(RANGED_DAMAGE_MODIFIER_ID, "Weapon modifier", this.rangedDamage, EntityAttributeModifier.Operation.ADDITION));
        builder.put(ModAttributes.GENERIC_CRIT_CHANCE, new EntityAttributeModifier(CRIT_CHANCE_MODIFIER_ID, "Weapon modifier", this.critChance, EntityAttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(slot);
    }

    /*public double getRangedDamage() {
        return this.rangedDamage;
    }

    public double getCritChance() {
        return this.critChance;
    }*/

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.NONE;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemInHand = user.getStackInHand(hand);

        boolean creativeMode = user.getAbilities().creativeMode;
        ItemStack ammo = this.getAmmo(user);

        if (creativeMode || !ammo.isEmpty()) {
            if (!world.isClient) {
                BulletItem bulletItem = (BulletItem) (ammo.getItem() instanceof BulletItem ? ammo.getItem() : ModItems.MUSKET_BALL);
                BulletEntity bulletEntity = bulletItem.createAmmo(world, ammo, user);

                bulletEntity.setDamage(user.getAttributeValue(ModAttributes.GENERIC_RANGED_DAMAGE) + bulletItem.getAmmoDamage());
                bulletEntity.calcCritical(user.getAttributeValue(ModAttributes.GENERIC_CRIT_CHANCE));
                bulletEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 3.0f, 1.0f);

                world.spawnEntity(bulletEntity);
            }
            world.playSound(null, user.getX(), user.getY(), user.getZ(), ModSounds.ITEM_GUN_SHOOT, SoundCategory.PLAYERS, 1.0f, 1.0f);

            this.decrementAmmo(user, ammo);

            user.incrementStat(Stats.USED.getOrCreateStat(this));
            //user.getItemCooldownManager().set(this, this.useTime);
            user.setCurrentHand(hand);
            return TypedActionResult.pass(itemInHand);
        }
        return TypedActionResult.fail(itemInHand);
    }

    public void decrementAmmo(PlayerEntity user, ItemStack stack) {
        if (!user.getAbilities().creativeMode) {
            stack.decrement(1);
            if (stack.isEmpty()) {
                user.getInventory().removeOne(stack);
            }
        }
    }

    public ItemStack getAmmo(PlayerEntity user) {
        ItemStack offHandStack = user.getOffHandStack();
        if (offHandStack.getItem() instanceof BulletItem) { // Gets Ammo in Offhand
            return offHandStack;
        }
        for (int i = 0; i < user.getInventory().size(); ++i) { // Gets first Ammo in inventory
            ItemStack itemStack = user.getInventory().getStack(i);
            if (itemStack.getItem() instanceof BulletItem) {
                return itemStack;
            }
        }
        return user.getAbilities().creativeMode ? new ItemStack(ModItems.MUSKET_BALL) : ItemStack.EMPTY; // If creative, get basic bullet
    }
}
