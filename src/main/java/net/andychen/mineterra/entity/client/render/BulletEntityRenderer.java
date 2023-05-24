package net.andychen.mineterra.entity.client.render;

import net.andychen.mineterra.MineTerra;
import net.andychen.mineterra.entity.client.ModEntityModelLayerRegistry;
import net.andychen.mineterra.entity.client.model.BulletEntityModel;
import net.andychen.mineterra.entity.custom.ranged.BulletEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

@Environment(value= EnvType.CLIENT)
public class BulletEntityRenderer extends EntityRenderer<BulletEntity> {
    private static final Identifier TEXTURE = new Identifier(MineTerra.MOD_ID, "textures/entity/bullet.png");
    private final BulletEntityModel<BulletEntity> model;

    public BulletEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new BulletEntityModel(context.getPart(ModEntityModelLayerRegistry.BULLET));
    }

    public void render(BulletEntity bulletEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.translate(0.0D, 0.15000000596046448D, 0.0D);
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.lerp(g, bulletEntity.prevYaw, bulletEntity.getYaw()) - 90.0F));
        matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.lerp(g, bulletEntity.prevPitch, bulletEntity.getPitch())));
        this.model.setAngles(bulletEntity, g, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.model.getLayer(TEXTURE));
        this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.pop();
        super.render(bulletEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }


    protected int getBlockLight(BulletEntity bulletEntity, BlockPos blockPos) {
        int i = (int) MathHelper.clampedLerp(0.0f, 15.0f, 1.0f);
        if (i == 15) {
            return 15;
        }
        return Math.max(i, super.getBlockLight(bulletEntity, blockPos));
    }

    @Override
    public Identifier getTexture(BulletEntity entity) {
        return TEXTURE;
    }
}
