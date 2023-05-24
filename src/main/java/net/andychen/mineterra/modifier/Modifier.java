package net.andychen.mineterra.modifier;

import com.google.common.collect.Maps;
import net.andychen.mineterra.MineTerra;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public abstract class Modifier {
    public final ModifierTarget type;
    private final EquipmentSlot[] slotTypes;
    private final Modifier.Rarity rarity;
    @Nullable
    protected String translationKey;

    @Nullable
    public static Modifier byRawId(int id) {
        return (Modifier) FabricRegistryBuilder.createSimple(Modifier.class, new Identifier(MineTerra.MOD_ID, "modifier"))
                .attribute(RegistryAttribute.MODDED)
                .buildAndRegister().get(id);
    }

    protected Modifier(Modifier.Rarity weight, ModifierTarget type, EquipmentSlot[] slotTypes) {
        this.rarity = weight;
        this.type = type;
        this.slotTypes = slotTypes;
    }

    public Map<EquipmentSlot, ItemStack> getEquipment(LivingEntity entity) {
        Map<EquipmentSlot, ItemStack> map = Maps.newEnumMap(EquipmentSlot.class);
        EquipmentSlot[] var3 = this.slotTypes;
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            EquipmentSlot equipmentSlot = var3[var5];
            ItemStack itemStack = entity.getEquippedStack(equipmentSlot);
            if (!itemStack.isEmpty()) {
                map.put(equipmentSlot, itemStack);
            }
        }

        return map;
    }

    public Modifier.Rarity getRarity() {
        return this.rarity;
    }

    public float getAttackDamage() {
        return 0.0F;
    }

    protected String getOrCreateTranslationKey() {
        if (this.translationKey == null) {
            this.translationKey = Util.createTranslationKey("modifier", FabricRegistryBuilder.createSimple(Modifier.class, new Identifier(MineTerra.MOD_ID, "modifier"))
                    .attribute(RegistryAttribute.MODDED)
                    .buildAndRegister().getId(this));
        }

        return this.translationKey;
    }

    public String getTranslationKey() {
        return this.getOrCreateTranslationKey();
    }

    public Text getName() {
        MutableText mutableText = Text.translatable(this.getTranslationKey());
        if (this.isBad()) {
            mutableText.formatted(Formatting.RED);
        } else {
            mutableText.formatted(Formatting.GRAY);
        }

        return mutableText;
    }

    public boolean isAcceptableItem(ItemStack stack) {
        return this.type.isAcceptableItem(stack.getItem());
    }

    public boolean isBad() {
        return false;
    }

    public enum Rarity {
        COMMON(10),
        UNCOMMON(5),
        RARE(2),
        VERY_RARE(1);

        private final int weight;

        Rarity(int weight) {
            this.weight = weight;
        }

        public int getWeight() {
            return this.weight;
        }
    }
}
