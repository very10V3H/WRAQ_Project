/**
 * AI-Generated, 2026-07-20
 * 灰色史莱姆实体 — 与原版史莱姆的唯一区别是颜色为灰色
 */
package fun.wraq.entities.entities.GraySlime;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class GraySlime extends Slime {
    public GraySlime(EntityType<? extends Slime> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier setAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.ATTACK_DAMAGE, 20)
                .add(Attributes.MOVEMENT_SPEED, 0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1)
                .build();
    }

    /**
     * 禁用原版绿色粒子，由 {@link #aiStep()} 自行召唤灰色粒子
     */
    @Override
    protected boolean spawnCustomParticles() {
        return true;
    }

    @Override
    public void aiStep() {
        /* 在 super.aiStep() 前自行记录落地前的 onGround 状态。
           原版 wasOnGround 在 Slime 中为私有，故无法直接访问。 */
        boolean prevOnGround = this.onGround();
        super.aiStep();
        if (this.onGround() && !prevOnGround) {
            int size = this.getSize();
            for (int j = 0; j < size * 8; ++j) {
                float f = this.random.nextFloat() * ((float) Math.PI * 2F);
                float f1 = this.random.nextFloat() * 0.5F + 0.5F;
                float f2 = Mth.sin(f) * (float) size * 0.5F * f1;
                float f3 = Mth.cos(f) * (float) size * 0.5F * f1;
                this.level().addParticle(
                        new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Items.GRAY_DYE)),
                        this.getX() + (double) f2, this.getY(), this.getZ() + (double) f3,
                        0.0D, 0.0D, 0.0D);
            }
        }
    }
}
