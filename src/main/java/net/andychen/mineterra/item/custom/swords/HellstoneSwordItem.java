package net.andychen.mineterra.item.custom.swords;

import net.andychen.mineterra.particle.ModParticles;
import net.andychen.mineterra.sounds.ModSounds;
import net.andychen.mineterra.util.access.ServerWorldMixinAccess;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.Iterator;
import java.util.List;

public class HellstoneSwordItem extends SwordItem {
    private final float attackDamage;

    public HellstoneSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
        this.attackDamage = (float) attackDamage + toolMaterial.getAttackDamage();
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        World world = attacker.getWorld();
        Vec3d vec3d = new Vec3d(target.getX(), target.getY(), target.getZ());

        target.damage(DamageSource.player((PlayerEntity) attacker), (float) (this.attackDamage * 0.75F));
        target.setOnFireFor(3);
        if (!target.isImmuneToExplosion()) {
            target.addVelocity(0, 0.3D, 0);
        }

        List<LivingEntity> list = world.getNonSpectatingEntities(LivingEntity.class, target.getBoundingBox().expand(1.0D, 1.5D, 1.0D)); // Get mobs around target
        Iterator var19 = list.iterator(); // Iterate through mobs

        label166:
        while (true) {
            LivingEntity livingEntity;
            do {
                do {
                    do {
                        do {
                            if (!var19.hasNext()) { // Reach last mob in list, play sound and spawn explosion particles, break loop
                                target.playSound(ModSounds.ITEM_EXPLOSIVE_TRAP_EXPLODE,1.0F, 1.0F);
                                this.addExplosionParticles(world, target);

                                break label166;
                            }

                            livingEntity = (LivingEntity) var19.next();
                        } while (livingEntity == (PlayerEntity) attacker);
                    } while (livingEntity == target);
                } while (((PlayerEntity) attacker).isTeammate(livingEntity));
            } while (livingEntity instanceof ArmorStandEntity && ((ArmorStandEntity) livingEntity).isMarker());

            float q = (1 * 2.0F);
            double w = Math.sqrt(livingEntity.squaredDistanceTo(vec3d)) / (double) q;
            if (w <= 1.0D) {
                double x = livingEntity.getX() - target.getX();
                double y = livingEntity.getEyeY() - target.getY();
                double z = livingEntity.getZ() - target.getZ();
                double aa = Math.sqrt(x * x + y * y + z * z);
                if (aa != 0.0D) {
                    x /= aa;
                    y /= aa;
                    z /= aa;
                    double ab = (double) Explosion.getExposure(vec3d, livingEntity);
                    double ac = (1.0D - w) * ab;
                    livingEntity.damage(DamageSource.player((PlayerEntity) attacker), (float) (this.attackDamage * 0.75F));
                    livingEntity.setOnFireFor(3);
                    double ad = ac;
                    if (livingEntity instanceof LivingEntity) {
                        ad = ProtectionEnchantment.transformExplosionKnockback((LivingEntity) livingEntity, ac);
                    }
                    if (!livingEntity.isImmuneToExplosion()) {
                        livingEntity.setVelocity(livingEntity.getVelocity().add(x * ad * 0.75, y * ad * 1.5, z * ad * 0.75));
                    }
                }
            }
        }
        return super.postHit(stack, target, attacker);
    }

    public void addExplosionParticles(World world, LivingEntity livingEntity) {
        ServerWorldMixinAccess serverWorld = (ServerWorldMixinAccess) ((ServerWorld) world);
        serverWorld.addParticle(ParticleTypes.LAVA, true, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(),
                -0.5D, 0.5D, 0, 0.75D, -0.5D, 0.5D, 80);
        serverWorld.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, true, livingEntity.getX(), livingEntity.getY(),
                livingEntity.getZ(), -0.1D, 0.1D, 0, 0.5D, -0.1D, 0.1D,15);
        serverWorld.addParticle(ModParticles.FIRE, true, livingEntity.getX(), livingEntity.getBodyY(0.5D),
                livingEntity.getZ(), -0.5D, 0.5D, 0, 0.5D, -0.5D, 0.5D, 50);
    }
}
