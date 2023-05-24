package net.andychen.mineterra.entity.client.render;

import net.andychen.mineterra.MineTerra;
import net.andychen.mineterra.entity.client.ModEntityModelLayerRegistry;
import net.andychen.mineterra.entity.client.model.LanternEntityModel;
import net.andychen.mineterra.entity.custom.LanternEntity;
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

import java.util.Random;

public class LanternEntityRenderer extends EntityRenderer<LanternEntity> {
    private static final Identifier TEXTURE = new Identifier(MineTerra.MOD_ID, "textures/entity/lantern.png");
    private static final RenderLayer LAYER;
    private final LanternEntityModel<LanternEntity> model;
    Random random = new Random();
    private final float uniqueOffset = this.random.nextFloat() * 3.1415927F * 2.0F;

    public LanternEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new LanternEntityModel(context.getPart(ModEntityModelLayerRegistry.LANTERN));
    }

    protected int getBlockLight(LanternEntity lanternEntity, BlockPos blockPos) {
        float h = (lanternEntity.getAge() / 6);
        int i = (int) h % 16;
        if (Math.ceil((h / 16) % 2) == 2) {
            i = 16 - i;
        } else if ((h / 16) % 2 == 1) {
            i = 15;
        } else if ((h / 16) % 2 == 0) {
            i = 1;
        }
        return i;
    }

    public void render(LanternEntity lanternEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        VertexConsumer vertexConsumer2 = vertexConsumerProvider.getBuffer(LAYER);

        float n = ((float) lanternEntity.age + g) / 20.0F + this.uniqueOffset;
        matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(180.0F));
        matrixStack.multiply(Vec3f.POSITIVE_Y.getRadialQuaternion(n * 0.25F));
        matrixStack.multiply(Vec3f.POSITIVE_X.getRadialQuaternion(MathHelper.sin(n) * 0.15F));
        matrixStack.multiply(Vec3f.POSITIVE_Z.getRadialQuaternion(MathHelper.cos(n) * 0.15F));

        matrixStack.push();
        this.model.render(matrixStack, vertexConsumer2, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 0.75F);
        matrixStack.pop();


        matrixStack.pop();
        super.render(lanternEntity, f, g, matrixStack, vertexConsumerProvider, i);

    }

    @Override
    public Identifier getTexture(LanternEntity entity) {
        int i = entity.getAge() % 4;
        if (i == 0) {
            return new Identifier(MineTerra.MOD_ID, "textures/entity/lantern_0.png");
        } else if (i == 1) {
            return new Identifier(MineTerra.MOD_ID, "textures/entity/lantern_1.png");
        } else if (i == 2) {
            return new Identifier(MineTerra.MOD_ID, "textures/entity/lantern_2.png");
        } else if (i == 3) {
            return new Identifier(MineTerra.MOD_ID, "textures/entity/lantern_3.png");
        }
        return TEXTURE;
    }

    static {
        LAYER = RenderLayer.getEntityTranslucent(TEXTURE);
    }
}
