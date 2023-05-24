package net.andychen.mineterra.entity.client.render;

import net.andychen.mineterra.MineTerra;
import net.andychen.mineterra.entity.custom.FallenStarEntity;
import net.andychen.mineterra.item.ModItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.math.random.Random;

@Environment(EnvType.CLIENT)
public class FallenStarEntityRenderer extends EntityRenderer<FallenStarEntity> {
    private static final Identifier TEXTURE = new Identifier(MineTerra.MOD_ID, "textures/item/fallen_star.png");
    private final ItemRenderer itemRenderer;
    private final Random random = Random.create();

    public FallenStarEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.shadowRadius = 0.15F;
        this.shadowOpacity = 0.75F;
    }

    public void render(FallenStarEntity fallenStarEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        ItemStack itemStack = ModItems.FALLEN_STAR.getDefaultStack();
        int j = itemStack.isEmpty() ? 187 : Item.getRawId(itemStack.getItem()) + itemStack.getDamage();
        this.random.setSeed((long) j);
        BakedModel bakedModel = this.itemRenderer.getModel(itemStack, fallenStarEntity.world, (LivingEntity) null, fallenStarEntity.getId());
        boolean bl = bakedModel.hasDepth();
        int k = 1;
        float h = 0.25F;
        float l = MathHelper.sin(((float) fallenStarEntity.getLife() + g) / 10.0F + fallenStarEntity.uniqueOffset) * 0.1F + 0.1F;
        float m = bakedModel.getTransformation().getTransformation(ModelTransformation.Mode.GROUND).scale.getY();
        if (fallenStarEntity.isInGround()) {
            matrixStack.translate(0.0D, (l - 0.15) + 0.3F * m, 0.0D);
        } else {
            matrixStack.translate(0.0D, 0.15F, 0.0D);
        }
        float n = fallenStarEntity.getRotation(g);
        if (fallenStarEntity.isInGround()) {
            matrixStack.multiply(Vec3f.POSITIVE_X.getRadialQuaternion(n));
            matrixStack.multiply(Vec3f.POSITIVE_Y.getRadialQuaternion(n));
            matrixStack.multiply(Vec3f.POSITIVE_Z.getRadialQuaternion(n));
        } else {
            matrixStack.multiply(Vec3f.POSITIVE_Y.getRadialQuaternion(n * 60));
        }
        float o = bakedModel.getTransformation().ground.scale.getX();
        float p = bakedModel.getTransformation().ground.scale.getY();
        float q = bakedModel.getTransformation().ground.scale.getZ();
        float s;
        float t;
        if (!bl) {
            float r = -0.0F * (float) (k - 1) * 0.5F * o;
            s = -0.0F * (float) (k - 1) * 0.5F * p;
            t = -0.09375F * (float) (k - 1) * 0.5F * q;
            matrixStack.translate((double) r, (double) s, (double) t);
        }

        matrixStack.push();
        if (bl) {
            s = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
            t = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
            float v = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
            matrixStack.translate((double) s, (double) t, (double) v);
        } else {
            s = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
            t = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
            matrixStack.translate((double) s, (double) t, 0.0D);
        }

        this.itemRenderer.renderItem(itemStack, ModelTransformation.Mode.GROUND, false, matrixStack, vertexConsumerProvider, i, OverlayTexture.DEFAULT_UV, bakedModel);
        matrixStack.pop();
        if (!bl) {
            matrixStack.translate((double) (0.0F * o), (double) (0.0F * p), (double) (0.09375F * q));
        }

        matrixStack.pop();
        super.render(fallenStarEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    protected int getBlockLight(FallenStarEntity fallenStarEntity, BlockPos blockPos) {
        return 15;
    }

    @Override
    public Identifier getTexture(FallenStarEntity entity) {
        return TEXTURE;
    }
}
