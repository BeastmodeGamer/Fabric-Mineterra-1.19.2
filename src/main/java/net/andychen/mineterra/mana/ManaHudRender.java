package net.andychen.mineterra.mana;

import com.mojang.blaze3d.systems.RenderSystem;
import net.andychen.mineterra.MineTerra;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

@Environment(EnvType.CLIENT)
public class ManaHudRender {
    public static final Identifier MANA_ICONS = new Identifier(MineTerra.MOD_ID, "textures/gui/mana.png");
    private static int lastManaValue;
    private static int renderManaValue;
    private static long lastManaCheckTime;
    private static long manaJumpEndTick;

    public static void renderManaBar(MatrixStack matrices, PlayerEntity player, int scaledWidth, int scaledHeight, int ticks) {
        if (player != null && !player.getAbilities().creativeMode) {
            ManaManager manaManager = ((ManaManagerAccess) player).getManaManager();
            Random random = new Random();

            int lastMana = MathHelper.ceil(manaManager.getMana());
            boolean blinking = manaJumpEndTick > (long)ticks && (manaJumpEndTick - (long)ticks) / 3L % 2L == 1L;
            random.setSeed((long)(ticks * 312871));
            long l = Util.getMeasuringTimeMs();
            if (lastMana < lastManaValue && manaManager.getManaRegenTime() > 0) { // If ? && if regenerating mana
                lastManaCheckTime = l;
                manaJumpEndTick = (long)(ticks + 20);
            } else if (lastMana > lastManaValue && manaManager.getManaRegenTime() > 0) {
                lastManaCheckTime = l;
                manaJumpEndTick = (long)(ticks + 10);
            }
            if (l - lastManaCheckTime > 1000L) {
                lastManaValue = lastMana;
                renderManaValue = lastMana;
                lastManaCheckTime = l;
            }

            lastManaValue = lastMana;
            int mana = renderManaValue;

            int width = scaledWidth / 2 - 91;
            int height = scaledHeight - 39;

            float maxMana = Math.max((float) manaManager.getMaxMana(), (float) Math.max(mana, lastMana));
            int j = MathHelper.ceil(maxMana / 2.0F / 10.0F);
            int lines = Math.max(10 - (j - 2), 3); // Gets the number of rows of stars
            int regeneratingManaIndex = -1;
            // Code for mana regeneration stars moving up and down
            /*if (playerEntity.hasStatusEffect(ModStatusEffects.MANA_REGENERATION)) {
                regeneratingManaIndex = ticks % MathHelper.ceil(maxMana + 5.0F);
            }*/

            RenderSystem.setShaderTexture(0, MANA_ICONS);
            ManaType manaType = ManaType.fromPlayerState(player);
            int textureYCoord = 0; // The y coord for the corner of the texture icon
            int maxManaStars = MathHelper.ceil((double)maxMana / 2.0D); // 10 stars; 20 half stars
            for(int m = maxManaStars - 1; m >= 0; --m) {
                int n = m / 10;
                int o = m % 10;
                int p = (height - o * 8) + 81; // X placement of bar
                int q = (width - n * lines) + 82; // Y placement of bar

                if (lastMana <= 4 && maxMana > 10) {
                    q += random.nextInt(2);
                }
                if (m < mana && m == regeneratingManaIndex) {
                    q -= 2;
                }
                DrawableHelper.drawTexture(matrices, p, q, ManaType.CONTAINER.getU(false, blinking), textureYCoord, 9, 9, 256, 256);
                int r = m * 2;

                boolean halfManaStar;
                if (blinking && r < mana) {
                    halfManaStar = r + 1 == mana;
                    DrawableHelper.drawTexture(matrices, p, q, manaType.getU(halfManaStar, true), textureYCoord, 9, 9, 256, 256);
                }

                if (r < lastMana) {
                    halfManaStar = r + 1 == lastMana;
                    DrawableHelper.drawTexture(matrices, p, q, manaType.getU(halfManaStar, false), textureYCoord, 9, 9, 256, 256);
                }
            }
            RenderSystem.setShaderTexture(0, DrawableHelper.GUI_ICONS_TEXTURE);
        }
    }
}
