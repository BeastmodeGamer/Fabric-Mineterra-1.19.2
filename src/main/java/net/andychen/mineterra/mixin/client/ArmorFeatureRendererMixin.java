package net.andychen.mineterra.mixin.client;

import net.andychen.mineterra.item.custom.armor.ModArmorItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ArmorFeatureRenderer.class)
public abstract class ArmorFeatureRendererMixin<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>> extends FeatureRenderer<T, M> {
    private final A innerModel;
    private final A outerModel;

    public ArmorFeatureRendererMixin(FeatureRendererContext<T, M> context, A innerModel, A outerModel) {
        super(context);
        this.innerModel = innerModel;
        this.outerModel = outerModel;
    }

    @Inject(method = "renderArmor", at = @At(value = "HEAD"), cancellable = true)
    private void renderArmorMixin(MatrixStack matrices, VertexConsumerProvider vertexConsumers, T entity, EquipmentSlot armorSlot, int light, A model, CallbackInfo info) {
        ItemStack itemStack2 = entity.getEquippedStack(armorSlot);
        if (itemStack2.getItem() instanceof ModArmorItem) {
            ModArmorItem modArmorItem = (ModArmorItem)itemStack2.getItem();
            if (modArmorItem.getSlotType() == armorSlot) {
                info.cancel();
                return;
            }
        }
    }
}
