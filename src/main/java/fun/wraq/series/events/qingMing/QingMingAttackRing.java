package fun.wraq.series.events.qingMing;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.equip.impl.RandomCurios;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.inslot.InCuriosOrEquipSlotAttributesModify;
import fun.wraq.common.impl.onkill.OnKillEffectCurios;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.series.castle.RandomCuriosAttributesUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QingMingAttackRing extends WraqCurios implements OnKillEffectCurios, InCuriosOrEquipSlotAttributesModify,
        RandomCurios {

    public QingMingAttackRing(Properties properties) {
        super(properties);
        Utils.healthSteal.put(this, 0.04);
        Utils.manaHealthSteal.put(this, 0.04);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getAttackTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("清 · 雨纷纷", hoverMainStyle()));
        Component countName = ComponentUtils.getRightAngleQuote("清", hoverMainStyle());
        components.add(Te.s(" 击杀一名敌人，获得一层", countName));
        components.add(Te.s(" 持续10s，至多叠加至", "10层", hoverMainStyle()));
        components.add(Te.s(" 每层清为你提供", ComponentUtils.AttributeDescription.attackDamage("0.5%"),
                " + ", ComponentUtils.AttributeDescription.manaDamage("0.5%")));
        components.addAll(QingMingCommonRing.getAttackAndDefenceCommonDescription());
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfLife;
    }

    @Override
    public Component suffix() {
        return QingTuan.getQingMingSuffix();
    }

    @Override
    public List<Attribute> getAttributes(Player player, ItemStack stack) {
        return List.of(
                new Attribute(Utils.percentAttackDamageEnhance,
                        countMap.getOrDefault(Name.get(player), 0) / 10d * 0.05),
                new Attribute(Utils.percentManaDamageEnhance,
                        countMap.getOrDefault(Name.get(player), 0) / 10d * 0.05)
        );
    }

    public static Map<String, Integer> countMap = new HashMap<>();
    public static Map<String, Integer> countExpiredTickMap = new HashMap<>();
    @Override
    public void onKill(Player player, Mob mob, ItemStack stack) {
        if (countExpiredTickMap.getOrDefault(Name.get(player), 0) < Tick.get()) {
            countMap.put(Name.get(player), 1);
        } else {
            countMap.compute(Name.get(player), (k, v) -> v == null ? 1 : Math.min(10, v + 1));
        }
        countExpiredTickMap.put(Name.get(player), Tick.get() + Tick.s(10));
        Compute.sendEffectLastTime(player, this, Tick.s(10), countMap.get(Name.get(player)), false);
    }

    @Override
    public void setAttribute(ItemStack stack) {
        if (RandomUtils.nextBoolean()) {
            RandomCuriosAttributesUtil.provideSingleAttribute(stack,
                    StringUtils.RandomCuriosAttribute.percentAttackDamage, fullRate(), 0.25, 1);
        } else {
            RandomCuriosAttributesUtil.provideSingleAttribute(stack,
                    StringUtils.RandomCuriosAttribute.percentManaDamageEnhance, fullRate(), 0.25, 1);
        }
    }

    @Override
    public double fullRate() {
        return 1;
    }
}
