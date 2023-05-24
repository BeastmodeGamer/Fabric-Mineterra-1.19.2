package net.andychen.mineterra.item.custom.bows;

import net.andychen.mineterra.entity.attribute.ModAttributes;
import net.andychen.mineterra.entity.custom.ranged.AmmoEntity;
import net.andychen.mineterra.item.custom.ammo.ModArrowItem;
import net.andychen.mineterra.util.TooltipUtil;
import net.andychen.mineterra.util.WeaponUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModBowItem extends BowItem {
    private double damage;
    private float crit;
    private float useTime;
    private float velocity;

    public ModBowItem(double damage, float crit, int useTime, float velocity, Settings settings) {
        super(settings);
        this.damage = damage;
        this.crit = crit; // float value from 0 - 1 range
        this.useTime = useTime; // EQUATION: 20 ticks / (60 / Terraria use time value)
        this.velocity = velocity;
        // In JSON file,
        // pulling_1 = ((useTime * 2) / 60) * 0.72
        // pulling_2 = ((useTime * 2) / 60)
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) user;
            boolean creativeMode = playerEntity.getAbilities().creativeMode;
            boolean infinity = EnchantmentHelper.getLevel(Enchantments.INFINITY, stack) > 0;
            ItemStack ammo = this.getAmmo(playerEntity);
            if (creativeMode || infinity || !ammo.isEmpty()) {
                if (ammo.isEmpty()) {
                    ammo = new ItemStack(Items.ARROW);
                }

                int i = this.getMaxUseTime(stack) - remainingUseTicks;
                float f = this.getPullProg(i);
                if (!((double) f < 0.1D)) {
                    boolean bl2 = creativeMode && infinity && ammo.isOf(Items.ARROW);
                    if (!world.isClient) {
                        if (ammo.getItem() instanceof ArrowItem) {
                            ArrowItem arrowItem = (ArrowItem) (ammo.getItem() instanceof ArrowItem ? ammo.getItem() : Items.ARROW);
                            PersistentProjectileEntity persistentProjectileEntity = arrowItem.createArrow(world, ammo, playerEntity);
                            persistentProjectileEntity.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, f * 3.0F, 1.0F);

                            double m = this.damage;
                            int j = EnchantmentHelper.getLevel(Enchantments.POWER, stack);
                            if (j > 0) {
                                m += (double) j * 0.5D + 0.5D;
                            }
                            if (WeaponUtil.calculateCritChance(this.crit)) {
                                persistentProjectileEntity.setCritical(true);
                                m *= 2;
                            }
                            persistentProjectileEntity.setDamage(persistentProjectileEntity.getDamage() + m);
                            //playerEntity.sendMessage(Text.translatable(String.valueOf((20 / (60 / (this.useTime * 2))))));

                            int k = EnchantmentHelper.getLevel(Enchantments.PUNCH, stack);
                            if (k > 0) {
                                persistentProjectileEntity.setPunch(k);
                            }

                            if (EnchantmentHelper.getLevel(Enchantments.FLAME, stack) > 0 || (stack.getItem() instanceof HellstoneBowItem && arrowItem == Items.ARROW)) {
                                persistentProjectileEntity.setOnFireFor(100);
                            }

                            stack.damage(1, playerEntity, (p) -> {
                                p.sendToolBreakStatus(playerEntity.getActiveHand());
                            });
                            if (bl2 || playerEntity.getAbilities().creativeMode && (ammo.isOf(Items.SPECTRAL_ARROW) || ammo.isOf(Items.TIPPED_ARROW))) {
                                persistentProjectileEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
                            }

                            world.spawnEntity(persistentProjectileEntity);
                        } else {
                            ModArrowItem modArrowItem = (ModArrowItem) ammo.getItem();
                            AmmoEntity arrowEntity = modArrowItem.createArrow(world, ammo, playerEntity);
                            arrowEntity.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, Math.max(f * 3.0F, f * ((this.velocity + modArrowItem.getVelocity()) * 0.3F * modArrowItem.getVelocityMultiplier())), 1.0F);

                            double m = this.damage;
                            int j = EnchantmentHelper.getLevel(Enchantments.POWER, stack);
                            if (j > 0) {
                                m += (double) j * 0.5D + 0.5D;
                            }
                            if (WeaponUtil.calculateCritChance(this.crit)) {
                                arrowEntity.setCritical(true);
                                m *= 2;
                            }
                            arrowEntity.setDamage(user.getAttributeValue(ModAttributes.GENERIC_RANGED_DAMAGE) + modArrowItem.getAmmoDamage() + m);
                            //playerEntity.sendMessage(Text.translatable(String.valueOf((20 / (60 / (this.useTime * 2))))));

                            stack.damage(1, playerEntity, (p) -> {
                                p.sendToolBreakStatus(playerEntity.getActiveHand());
                            });

                            world.spawnEntity(arrowEntity);
                        }
                    }

                    world.playSound((PlayerEntity) null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);

                    this.decrementAmmo(playerEntity, ammo);

                    playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
                }
            }
        }
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
        if (offHandStack.getItem() instanceof ArrowItem || offHandStack.getItem() instanceof ModArrowItem) { // Gets Ammo in Offhand
            return offHandStack;
        }
        for (int i = 0; i < user.getInventory().size(); ++i) { // Gets first Arrow in inventory
            ItemStack itemStack = user.getInventory().getStack(i);
            if (itemStack.getItem() instanceof ArrowItem || itemStack.getItem() instanceof ModArrowItem) {
                return itemStack;
            }
        }
        return user.getAbilities().creativeMode ? new ItemStack(Items.ARROW) : ItemStack.EMPTY; // If creative, get normal arrow
    }

    public float getPullProg(int useTicks) {
        float f = (float) useTicks / 20.0f; //(20 / (60 / (this.useTime * 2)));
        float j = (float) ((this.useTime * 2) / 60);
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > j) {
            f = 1.0F;
        }
        return f;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable(""));
        tooltip.add(Text.translatable("When in Main Hand:").formatted(Formatting.GRAY));
        tooltip.add(TooltipUtil.getRangedDamageText(stack, this.damage));
        tooltip.add(TooltipUtil.getCritText(this.crit));
        tooltip.add(TooltipUtil.getUseTimeText(this.useTime));
    }
}
