package net.andychen.mineterra.entity.client.render;

import net.andychen.mineterra.MineTerra;
import net.andychen.mineterra.entity.client.ModEntityModelLayerRegistry;
import net.andychen.mineterra.entity.client.model.SpikyBallEntityModel;
import net.andychen.mineterra.entity.custom.SpikyBallEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class SpikyBallEntityRenderer  extends EntityRenderer<SpikyBallEntity> {
    private static final Identifier TEXTURE = new Identifier(MineTerra.MOD_ID, "textures/entity/spiky_ball.png");
    private final SpikyBallEntityModel<SpikyBallEntity> model;

    public SpikyBallEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new SpikyBallEntityModel(context.getPart(ModEntityModelLayerRegistry.SPIKY_BALL));
    }

    public void render(SpikyBallEntity spikyBallEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.model.getLayer(TEXTURE));
        matrixStack.translate(0.0D, 0.15000000596046448D, 0.0D);

        Vec3d vec3d = spikyBallEntity.getVelocity();
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.lerp(g, spikyBallEntity.prevYaw, spikyBallEntity.getYaw()) - 90.0F));
        matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(-(float) Math.abs(vec3d.x + vec3d.z) * MathHelper.lerp(g, spikyBallEntity.prevPitch, spikyBallEntity.getPitch()) * 180.0F));

        this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.pop();
        super.render(spikyBallEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(SpikyBallEntity entity) {
        return TEXTURE;
    }

}
