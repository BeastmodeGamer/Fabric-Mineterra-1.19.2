package net.andychen.mineterra.entity.client.armor;

import com.google.common.collect.Maps;
import net.andychen.mineterra.MineTerra;
import net.andychen.mineterra.entity.client.model.ModArmorModel;
import net.andychen.mineterra.item.custom.armor.ModArmorItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

@Environment(EnvType.CLIENT)
public class ModArmorFeatureRenderer<T extends LivingEntity, M extends BipedEntityModel<T>, A extends ModArmorModel<T>> extends FeatureRenderer<T, M> {
    private static final Map<String, Identifier> ARMOR_TEXTURE_CACHE = Maps.newHashMap();
    private final A armorModel;

    public ModArmorFeatureRenderer(FeatureRendererContext<T, M> context, A armorModel) {
        super(context);
        this.armorModel = armorModel;
    }

    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l) {
        this.renderArmor(matrixStack, vertexConsumerProvider, livingEntity, EquipmentSlot.CHEST, i, this.getModel());
        this.renderArmor(matrixStack, vertexConsumerProvider, livingEntity, EquipmentSlot.LEGS, i, this.getModel());
        this.renderArmor(matrixStack, vertexConsumerProvider, livingEntity, EquipmentSlot.FEET, i, this.getModel());
        this.renderArmor(matrixStack, vertexConsumerProvider, livingEntity, EquipmentSlot.HEAD, i, this.getModel());
    }

    private void renderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, T entity, EquipmentSlot armorSlot, int light, A model) {
        ItemStack itemStack = entity.getEquippedStack(armorSlot);
        if (itemStack.getItem() instanceof ModArmorItem) {
            ModArmorItem armorItem = (ModArmorItem)itemStack.getItem();
            if (armorItem.getSlotType() == armorSlot) {
                this.getContextModel().setAttributes(model);
                this.setVisible(model, armorSlot);
                boolean bl2 = itemStack.hasGlint();
                this.renderArmorParts(matrices, vertexConsumers, light, armorItem, bl2, model,1.0F, 1.0F, 1.0F, (String)null);
            }
        }
    }

    protected void setVisible(A modArmorModel, EquipmentSlot slot) {
        modArmorModel.setVisible(false);
        switch(slot) {
            case HEAD:
                modArmorModel.helmet.visible = true;
                modArmorModel.hatLayer.visible = true;
                break;
            case CHEST:
                modArmorModel.chestplate.visible = true;
                modArmorModel.rightArmor.visible = true;
                modArmorModel.leftArmor.visible = true;
                break;
            case LEGS:
                modArmorModel.belt.visible = true;
                modArmorModel.rightLegging.visible = true;
                modArmorModel.leftLegging.visible = true;
                break;
            case FEET:
                modArmorModel.rightBoot.visible = true;
                modArmorModel.leftBoot.visible = true;
        }

    }

    private void renderArmorParts(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ModArmorItem item, boolean usesSecondLayer, A model, float red, float green, float blue, @Nullable String overlay) {
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(this.getArmorTexture(item, overlay)), false, usesSecondLayer);
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, red, green, blue, 1.0F);
    }

    private A getModel() {
        return this.armorModel;
    }

    private Identifier getArmorTexture(ModArmorItem item, @Nullable String overlay) {
        String var10000 = item.getMaterial().getName();
        String string = MineTerra.MOD_ID + ":textures/models/armor/" + var10000 + "_armor" + ".png";
        return ARMOR_TEXTURE_CACHE.computeIfAbsent(string, Identifier::new);
    }
}
