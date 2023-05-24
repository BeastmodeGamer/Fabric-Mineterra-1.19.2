package net.andychen.mineterra.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.andychen.mineterra.MineTerra;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.List;

public class HellforgeScreen extends HandledScreen<HellforgeScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(MineTerra.MOD_ID, "textures/gui/container/hellforge.png");

    public HellforgeScreen(HellforgeScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
        this.titleY = 0;
        this.playerInventoryTitleY += 7;
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int backgroundWidth = 176;
        int backgroundHeight = 180;
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);

        renderProgressArrow(matrices, x, y);
        renderFuelBar(matrices, x, y);
        renderProgressFlame(matrices, x, y);
    }

    private void renderProgressArrow(MatrixStack matrices, int x, int y) {
        if (handler.isCrafting()) {
            drawTexture(matrices, x + 106, y + 42, 176, 14, handler.getScaledProgress(), 17);
        }
    }

    private void renderFuelBar(MatrixStack matrices, int x, int y) {
        int k = handler.getScaledFuel();
        drawTexture(matrices, x + 12, y + 19 + 40 - k, 176, 70 - k + 1, 16, k);
    }

    private void renderProgressFlame(MatrixStack matrices, int x, int y) {
        if (handler.isCrafting()) {
            int k = handler.getScaledFlame();
            drawTexture(matrices, x + 85, y + 61 + k, 176, k, 14, 12 - k);
        }
    }

    private boolean isPointOverFuel(double pointX, double pointY) {
        return this.isPointWithinBounds(11, 11, 18, 42, pointX, pointY);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);

        if (this.isPointOverFuel((double)mouseX, (double)mouseY)) {
            renderTooltip(matrices, List.of(Text.translatable("Lava"), Text.translatable(handler.getPropertyDelegate().get(2) + "/" + handler.getPropertyDelegate().get(3)).formatted(Formatting.DARK_GRAY)), mouseX, mouseY);
        }
    }
}
