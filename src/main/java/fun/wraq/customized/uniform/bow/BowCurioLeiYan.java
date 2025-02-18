package fun.wraq.customized.uniform.bow;

import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.onhit.OnHitEffectCurios;
import fun.wraq.common.impl.onshoot.OnShootArrowCurios;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.customized.uniform.UnCommonUniform;
import fun.wraq.process.func.StableTierAttributeModifier;
import fun.wraq.process.system.element.RainbowCrystal;
import fun.wraq.series.instance.quiver.WraqQuiver;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BowCurioLeiYan extends WraqBowUniformCurios implements UnCommonUniform, OnHitEffectCurios,
        OnShootArrowCurios {

    public BowCurioLeiYan(Properties properties) {
        super(properties);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Component countName = ComponentUtils.getRightAngleQuote("赐福", hoverMainStyle());
        ComponentUtils.descriptionPassive(components, Te.s("精灵贤者"));
        components.add(Te.s(" 箭矢", hoverMainStyle(), "命中时为你提供一层", countName));
        components.add(Te.s(" 持续10s，最多叠加至", "30", hoverMainStyle(), "层"));
        components.add(Te.s(" 每层", countName, "提供:"));
        components.add(Te.s(" 1.", hoverMainStyle(),
                ComponentUtils.AttributeDescription.exAttackDamage("1% + 20")));
        components.add(Te.s(" 2.", hoverMainStyle(),
                ComponentUtils.AttributeDescription.defencePenetration("0.5% + 2")));
        components.add(Te.s(" 当拥有", "30", hoverMainStyle(), "层", countName, "后"));
        components.add(Te.s(" 普通箭矢攻击", hoverMainStyle(),
                "将额外释放一支", "25%基础伤害箭矢", hoverMainStyle()));
        return components;
    }

    @Override
    public Component getFirstPassiveName() {
        return RainbowCrystal.rainBowNameFourChar("雷炎之灵");
    }

    @Override
    public String getName() {
        return "Lei_yan233";
    }

    public static final String PASSIVE_TAG = "BowCurioLeiYanPassive";

    @Override
    public void onHit(Player player, Mob mob) {
        StableTierAttributeModifier.addM(player, StableTierAttributeModifier.playerExAttackDamage,
                PASSIVE_TAG, 20, Tick.get() + Tick.s(10), 30, "item/bow_curios_0");
        StableTierAttributeModifier.addM(player, StableTierAttributeModifier.playerAttackDamageEnhance,
                PASSIVE_TAG, 0.01, Tick.get() + Tick.s(10), 30);
        StableTierAttributeModifier.addM(player, StableTierAttributeModifier.playerDefencePenetration,
                PASSIVE_TAG, 0.005, Tick.get() + Tick.s(10), 30);
        StableTierAttributeModifier.addM(player, StableTierAttributeModifier.playerDefencePenetration0,
                PASSIVE_TAG, 2, Tick.get() + Tick.s(10), 30);
    }

    @Override
    public void onShoot(Player player) {
        if (getCount(player) >= 30) {
            WraqQuiver.addExShoot(player, 0.25);
        }
    }

    private int getCount(Player player) {
        return StableTierAttributeModifier
                .getAttributeModifierTier(player, StableTierAttributeModifier.playerExAttackDamage, PASSIVE_TAG);
    }
}
