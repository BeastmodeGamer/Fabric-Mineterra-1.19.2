package net.andychen.mineterra.entity.client.render;

import net.andychen.mineterra.MineTerra;
import net.andychen.mineterra.entity.client.ModEntityModelLayerRegistry;
import net.andychen.mineterra.entity.client.model.ChaosBallEntityModel;
import net.andychen.mineterra.entity.custom.ChaosBallEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class ChaosBallEntityRenderer extends EntityRenderer<ChaosBallEntity> {
    private static final Identifier TEXTURE = new Identifier(MineTerra.MOD_ID, "textures/entity/chaos_ball.png");
    private static final RenderLayer LAYER;
    private final ChaosBallEntityModel<ChaosBallEntity> model;

    public ChaosBallEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new ChaosBallEntityModel(context.getPart(ModEntityModelLayerRegistry.CHAOS_BALL));
    }

    protected int getBlockLight(ChaosBallEntity chaosBallEntity, BlockPos blockPos) {
        return 15;
    }

    public void render(ChaosBallEntity chaosBallEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        float h = MathHelper.lerpAngle(chaosBallEntity.prevYaw, chaosBallEntity.getYaw(), g);
        float j = MathHelper.lerp(g, chaosBallEntity.prevPitch, chaosBallEntity.getPitch());
        float k = ((float) chaosBallEntity.age + g);
        matrixStack.translate(0.0D, 0.15000000596046448D, 0.0D);
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.sin(k * 0.1F) * 180.0F));
        matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(MathHelper.cos(k * 0.1F) * 180.0F));
        matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.sin(k * 0.15F) * 360.0F));
        matrixStack.scale(-0.5F, -0.5F, 0.5F);
        this.model.setAngles(chaosBallEntity, 0.0F, 0.0F, 0.0F, h, j);
        VertexConsumer vertexConsumer2 = vertexConsumerProvider.getBuffer(LAYER);
        this.model.render(matrixStack, vertexConsumer2, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 0.65F);
        float l = 1.8F * MathHelper.sin(k / 20);
        matrixStack.scale(l, l, l);
        this.model.render(matrixStack, vertexConsumer2, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 0.15F);
        matrixStack.pop();
        super.render(chaosBallEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(ChaosBallEntity entity) {
        return TEXTURE;
    }

    static {
        LAYER = RenderLayer.getEntityTranslucent(TEXTURE);
    }
}
