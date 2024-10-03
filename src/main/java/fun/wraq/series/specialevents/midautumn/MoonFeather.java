package fun.wraq.series.specialevents.midautumn;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.common.impl.onhit.OnHitEffectCurios;
import fun.wraq.common.impl.onkill.OnKillEffectCurios;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MoonFeather extends WraqCurios implements OnHitEffectCurios, OnKillEffectCurios {

    private final int tier;
    private final double[] rate = new double[]{0.05, 0.08, 0.12, 0.15};
    private final double[] seconds = new double[]{1, 1.5, 2, 2.5};
    public MoonFeather(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
        Utils.levelRequire.put(this, new int[]{160, 180, 200, 220}[tier]);
        Utils.xpLevelDefence.put(this, new double[]{1, 2, 2, 3}[tier]);
        Utils.xpLevelManaDefence.put(this, new double[]{1, 2, 2, 3}[tier]);
    }

    @Override
    public void onHit(Player player, Mob mob) {
        Random random = new Random();
        if (random.nextDouble() <= rate[tier]) {
            mob.addEffect(new MobEffectInstance(MobEffects.LEVITATION, (int) (seconds[tier] * 20)));
            Compute.sendMobEffectHudToNearPlayer(mob, this,
                    "moonFeatherPassiveLevitationEffect", (int) (seconds[tier] * 20), 0, false);
        }
    }

    @Override
    public void onKill(Player player, Mob mob) {
        if (mob.getEffect(MobEffects.LEVITATION) != null) {
            StableAttributesModifier.addAttributeModifier(player, StableAttributesModifier.playerMovementSpeedModifier,
                    new StableAttributesModifier("moonFeatherPassiveEffect", 0.3, Tick.get() + 200));
            StableAttributesModifier.addAttributeModifier(player, StableAttributesModifier.playerCommonDamageEnhance,
                    new StableAttributesModifier("moonFeatherPassiveEffect", 0.25, Tick.get() + 200));
            Compute.sendEffectLastTime(player, this, 200);
        }
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getComprehensiveTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Component.literal("永升月引").withStyle(CustomStyle.styleOfMoon));
        components.add(Te.m(" 普通攻击", CustomStyle.styleOfMoon).
                append(Te.m("造成伤害时，有")).
                append(Te.m(String.format("%.0f%%", rate[tier] * 100), CustomStyle.styleOfMoon1)).
                append(Te.m("概率使目标受到持续")).
                append(Te.m(BigDecimal.valueOf(seconds[tier]).stripTrailingZeros() + "s", CustomStyle.styleOfMoon1)).
                append(Te.m("的")).
                append(Te.m("漂浮效果", CustomStyle.styleOfMoon)));
        ComponentUtils.descriptionPassive(components, Te.m("永耀月辉", CustomStyle.styleOfMoon));
        components.add(Te.m(" 击杀正处于").
                append(Te.m("漂浮状态", CustomStyle.styleOfMoon)).
                append(Te.m("的目标后，获得持续10s的:")));
        components.add(Te.m(" 1.", CustomStyle.styleOfMoon1).
                append(ComponentUtils.AttributeDescription.movementSpeed("30%")));
        components.add(Te.m(" 2.", CustomStyle.styleOfMoon1).
                append(ComponentUtils.getCommonDamageEnhance("25%")));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfMoon;
    }

    @Override
    public Component suffix() {
        return Te.m("「中秋 - 月荧」", CustomStyle.styleOfMoon);
    }
}
