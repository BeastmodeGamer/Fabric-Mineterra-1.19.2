package net.andychen.mineterra.mana;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;

@Environment(EnvType.CLIENT)
public enum ManaType {
    CONTAINER(0, false),
    NORMAL(2, true);

    private final int textureIndex;
    private final boolean hasBlinkingTexture;

    ManaType(int textureIndex, boolean hasBlinkingTexture) {
        this.textureIndex = textureIndex;
        this.hasBlinkingTexture = hasBlinkingTexture;
    }

    public int getU(boolean halfMana, boolean blinking) {
        int i;
        if (this == CONTAINER) {
            i = blinking ? 1 : 0;
        } else {
            int j = halfMana ? 1 : 0;
            int k = this.hasBlinkingTexture && blinking ? 2 : 0;
            i = j + k;
        }

        return (this.textureIndex * 2 + i) * 9;
    }

    public static ManaType fromPlayerState(PlayerEntity player) {
        ManaType manaType;
        manaType = NORMAL;

        return manaType;
    }
}
