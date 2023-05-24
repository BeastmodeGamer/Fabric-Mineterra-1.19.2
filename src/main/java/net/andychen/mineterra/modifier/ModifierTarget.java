package net.andychen.mineterra.modifier;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;

public enum ModifierTarget {
    UNIVERSAL {
        public boolean isAcceptableItem(Item item) {
            return item instanceof SwordItem;
        }
    },
    COMMON {
        public boolean isAcceptableItem(Item item) {
            return item instanceof SwordItem;
        }
    },
    MELEE {
        public boolean isAcceptableItem(Item item) {
            return item instanceof SwordItem;
        }
    },
    RANGED {
        public boolean isAcceptableItem(Item item) {
            return item instanceof BowItem;
        }
    },
    MAGIC {
        public boolean isAcceptableItem(Item item) {
            return item instanceof ArmorItem && ((ArmorItem) item).getSlotType() == EquipmentSlot.CHEST;
        }
    },
    SUMMONING {
        public boolean isAcceptableItem(Item item) {
            return item instanceof ArmorItem && ((ArmorItem) item).getSlotType() == EquipmentSlot.HEAD;
        }
    };

    ModifierTarget() {
    }

    public abstract boolean isAcceptableItem(Item item);
}
