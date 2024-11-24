package fun.wraq.series.overworld.sun;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.PlayerAttributes;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.onhit.OnArrowHitEffectCurios;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class FrameArrow extends WraqCurios implements OnArrowHitEffectCurios {

    private final int tier;
    public FrameArrow(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
        Utils.critDamage.put(this, 0.1);
    }

    private final double[] effectRate = new double[]{1, 1.5, 2, 2.5};

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getAttackTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("红莲弓矢", hoverMainStyle()));
        components.add(Te.s(" 你的", "箭矢", CustomStyle.styleOfFlexible, "在命中目标时，会产生", "范围爆炸", hoverMainStyle()));
        components.add(Te.s(" 爆炸", hoverMainStyle(), "会对目标范围内的所有敌人造成",
                String.format("%.0f%%", effectRate[tier] * 100) + "物理伤害", CustomStyle.styleOfPower));
        components.add(Te.s(" 并施加", "1s减速", CustomStyle.styleOfStone));
        components.add(ComponentUtils.getCritDamageInfluenceDescription());
        ComponentUtils.coolDownTimeDescription(components, 3);
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfRed;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfSunIsland();
    }

    public static Map<Player, Integer> nextAllowReleaseTick = new WeakHashMap<>();
    @Override
    public void onHit(Player player, Mob mob) {
        if (nextAllowReleaseTick.getOrDefault(player, 0) < Tick.get()) {
            nextAllowReleaseTick.put(player, Tick.get() + 60);
            Compute.sendCoolDownTime(player, "item/frame_arrow", 60);

            MySound.soundToNearPlayer(mob.level(), mob.position(), SoundEvents.GENERIC_EXPLODE);
            ParticleProvider.createSingleParticleToNearPlayer(player, player.level(),
                    mob.position(), ParticleTypes.EXPLOSION_EMITTER);
            ParticleProvider.RandomMoveParticle(mob, 1, 1, 24, ParticleTypes.ASH);
            ParticleProvider.RandomMoveParticle(mob, 1, 1, 24, ParticleTypes.LAVA);
            double attackDamage = PlayerAttributes.attackDamage(player);
            mob.level().getEntitiesOfClass(Mob.class, AABB.ofSize(mob.position(), 10, 10, 10))
                    .forEach(eachMob -> {
                        Damage.causeAdDamageToMonsterWithCritJudge(player, eachMob, attackDamage * effectRate[tier]);
                        Compute.addSlowDownEffect(eachMob, 20, 0);
                    });
        }
    }
}
