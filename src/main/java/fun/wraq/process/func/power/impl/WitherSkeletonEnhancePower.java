package fun.wraq.process.func.power.impl;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.process.func.power.WraqPower;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class WitherSkeletonEnhancePower extends WraqPower {

    public WitherSkeletonEnhancePower(Properties properties) {
        super(properties);
    }

    @Override
    public Component getActiveName() {
        return Te.s("凋零之息", CustomStyle.styleOfWither);
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        components.add(Te.s(" 选定", "准星", ChatFormatting.AQUA, "或", "自身半径6格内", CustomStyle.styleOfStone,
                "的一个目标"));
        components.add(Te.s(" 对其造成", ComponentUtils.AttributeDescription.manaDamageValue("540%")));
        components.add(Te.s(" 若其与你的距离", "小于6格", CustomStyle.styleOfStone,
                "则造成削减其", ComponentUtils.AttributeDescription.manaDefence("20%")));
        components.add(Te.s(" 持续", "3s", CustomStyle.styleOfWorld));
        return components;
    }

    @Override
    public int getCoolDownSecond() {
        return 3;
    }

    @Override
    public double getManaCost() {
        return 360;
    }

    @Override
    public Component getSuffix() {
        return null;
    }

    @Override
    public void release(Player player) {
        Compute.playerItemCoolDown(player,this, getCoolDownSecond());;
        Mob mob = Compute.detectPlayerPickMob(player);
        if (mob == null) {
            List<Mob> mobList = Compute.getNearEntity(player, Mob.class, 6)
                    .stream().map(eachMob -> (Mob) eachMob).toList();
            if (!mobList.isEmpty()) {
                double distance = Double.MAX_VALUE;
                for (Mob mob1 : mobList) {
                    if (mob1.distanceTo(player) < distance) {
                        mob = mob1;
                        distance = mob1.distanceTo(player);
                    }
                }
            }
        }
        if (mob == null) return;
        Damage.causeManaDamageToMonster_RateApDamage(player, mob, 5.4, true);
        ParticleProvider.createLineEffectParticle(player.level(), (int) mob.distanceTo(player) * 5,
                player.getEyePosition(), mob.getEyePosition(), CustomStyle.styleOfWither);
        if (mob.distanceTo(player) < 6) {
            StableAttributesModifier.addM(mob, StableAttributesModifier.mobPercentManaDefenceModifier,
                    "WitherSkeletonEnhancePowerManaDefenceDecrease", -0.2, Tick.get() + 60, this);
        }
    }
}
