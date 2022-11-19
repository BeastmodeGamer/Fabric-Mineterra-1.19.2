package net.andychen.mineterra.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class MeteoriteOreBlock extends Block {
    public MeteoriteOreBlock(Settings settings) {
        super(settings);
    }

    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!entity.isFireImmune() && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) entity)) {
            entity.damage(DamageSource.HOT_FLOOR, 1.0F);
        }

        super.onSteppedOn(world, pos, state, entity);
    }

    public void randDisplayTick(BlockState state, World world, BlockPos pos, Random rand) {
        if (rand.nextInt(5) == 0) {
            world.addParticle(ParticleTypes.FLAME, (float) pos.getX() + rand.nextFloat(),
                    (float) pos.getY() + 1.1F, (float) pos.getZ() + rand.nextFloat(), 0.0D, 0.0D,
                    0.0D);
        }
        if (rand.nextInt(15) == 0) {
            world.addParticle(ParticleTypes.FLAME, (float) pos.getX() + rand.nextFloat(),
                    (float) pos.getY() + 1.1F, (float) pos.getZ() + rand.nextFloat(), 0.0D, 0.0D,
                    0.0D);
        }
        if (rand.nextInt(25) == 0) {
            world.addParticle(ParticleTypes.FLAME, (float) pos.getX() + rand.nextFloat(),
                    (float) pos.getY() + 1.1F, (float) pos.getZ() + rand.nextFloat(), 0.0D, 0.0D,
                    0.0D);
        }
    }
}
