package net.andychen.mineterra.block.custom;

import net.andychen.mineterra.particle.ModParticles;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class MeteoriteOreBlock extends Block {
    public MeteoriteOreBlock(Settings settings) {
        super(settings);
    }

    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!entity.isFireImmune() && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity) entity)) {
            entity.damage(DamageSource.ON_FIRE, 1.0F);
            entity.setOnFireFor(3);
        }

        super.onSteppedOn(world, pos, state, entity);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(5) == 0) {
            world.addParticle(ModParticles.FIRE, (float) pos.getX() + random.nextFloat(),
                    (float) pos.getY() + 1.1F, (float) pos.getZ() + random.nextFloat(), 0.0D, 0.0D,
                    0.0D);
        }
        if (random.nextInt(15) == 0) {
            world.addParticle(ModParticles.FIRE, (float) pos.getX() + random.nextFloat(),
                    (float) pos.getY() + 1.1F, (float) pos.getZ() + random.nextFloat(), 0.0D, 0.0D,
                    0.0D);
        }
        if (random.nextInt(25) == 0) {
            world.addParticle(ModParticles.FIRE, (float) pos.getX() + random.nextFloat(),
                    (float) pos.getY() + 1.1F, (float) pos.getZ() + random.nextFloat(), 0.0D, 0.0D,
                    0.0D);
        }
    }
}
