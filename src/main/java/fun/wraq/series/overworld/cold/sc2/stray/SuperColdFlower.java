package fun.wraq.series.overworld.cold.sc2.stray;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.fast.Te;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.WraqItem;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SuperColdFlower extends WraqItem implements ActiveItem {

    public SuperColdFlower(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Te.s(" · 消耗品", CustomStyle.styleOfMine));
        components.add(Te.s(" 使用来释放一阵", "刺骨寒气", CustomStyle.styleOfIce));
        components.add(Te.s(" 对周围敌方造成", "2s禁锢", CustomStyle.styleOfIce));
        components.add(Te.s(" 释放的寒气可以清除附近敌人的", "所有投射物", CustomStyle.styleOfFlexible));
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    @Override
    public void active(Player player) {
        InventoryOperation.removeItem(player, this, 1);
        ParticleProvider.createBallDisperseParticle(ParticleTypes.SNOWFLAKE, (ServerLevel) player.level(),
                player.position().add(0, 1, 0), 1, 40);
        Compute.getNearMob(player, 12).forEach(mob -> {
            mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 99));
            Compute.createIceParticle(mob);
        });
        player.level().getEntitiesOfClass(Projectile.class,
                        AABB.ofSize(player.position(), 24, 24, 24))
                .stream().filter(projectile -> {
                    return projectile.distanceTo(player) <= 12
                            && projectile.getOwner() != null && !(projectile.getOwner() instanceof Player);
                }).forEach(projectile -> {
                    projectile.remove(Entity.RemovalReason.KILLED);
                    Compute.createIceParticle(projectile);
                });
    }

    @Override
    public double manaCost(Player player) {
        return 0;
    }
}
