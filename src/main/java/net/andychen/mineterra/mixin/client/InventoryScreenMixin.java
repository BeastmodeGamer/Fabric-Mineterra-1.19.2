package net.andychen.mineterra.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.andychen.mineterra.MineTerra;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends AbstractInventoryScreen<PlayerScreenHandler> implements RecipeBookProvider {
    private final Identifier ACCESSORY_TEXTURE = new Identifier(MineTerra.MOD_ID, "textures/gui/container/accessory.png");

    public InventoryScreenMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }

    @Inject(method = "drawBackground", at = @At("TAIL"))
    protected void drawBackgroundInjector(MatrixStack matrices, float delta, int mouseX, int mouseY, CallbackInfo info) {
        this.drawAccessoryBar(matrices, delta, mouseX, mouseY);
    }

    public void drawAccessoryBar(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, ACCESSORY_TEXTURE);
        int i = this.x;
        int j = this.y - 28;
        this.drawTexture(matrices, i, j, 0, 0, 104, 32, 104, 32);
    }
}