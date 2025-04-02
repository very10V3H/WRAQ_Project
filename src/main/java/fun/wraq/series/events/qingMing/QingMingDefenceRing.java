package fun.wraq.series.events.qingMing;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.equip.impl.RandomCurios;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.damage.OnWithStandDamageCurios;
import fun.wraq.common.impl.inslot.InCuriosOrEquipSlotAttributesModify;
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

public class QingMingDefenceRing extends WraqCurios implements OnWithStandDamageCurios,
        InCuriosOrEquipSlotAttributesModify, RandomCurios {

    public QingMingDefenceRing(Properties properties) {
        super(properties);
        Utils.percentHealthRecover.put(this, 0.005);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getDefenceTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("明 · 欲断魂", hoverMainStyle()));
        Component countName = ComponentUtils.getRightAngleQuote("明", hoverMainStyle());
        components.add(Te.s(" 每受到一次伤害，为你提供一层", countName));
        components.add(Te.s(" 持续10s，至多叠加至", "10层", hoverMainStyle()));
        components.add(Te.s(" 每层为你提供", ComponentUtils.AttributeDescription.defence("1%"),
                " + ", ComponentUtils.AttributeDescription.manaDefence("1%")));
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
    public List<InCuriosOrEquipSlotAttributesModify.Attribute> getAttributes(Player player) {
        return List.of(
                new InCuriosOrEquipSlotAttributesModify.Attribute(Utils.percentDefenceEnhance,
                        countMap.getOrDefault(Name.get(player), 0) / 10d * 0.1),
                new InCuriosOrEquipSlotAttributesModify.Attribute(Utils.percentManaDefenceEnhance,
                        countMap.getOrDefault(Name.get(player), 0) / 10d * 0.1)
        );
    }

    public static Map<String, Integer> countMap = new HashMap<>();
    public static Map<String, Integer> countExpiredTickMap = new HashMap<>();

    @Override
    public void onWithStandDamage(Player player, Mob mob, double damage) {
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
                    StringUtils.RandomCuriosAttribute.percentDefenceEnhance, fullRate(), 0.25, 1);
        } else {
            RandomCuriosAttributesUtil.provideSingleAttribute(stack,
                    StringUtils.RandomCuriosAttribute.percentManaDefenceEnhance, fullRate(), 0.25, 1);
        }
    }

    @Override
    public double fullRate() {
        return 1;
    }
}
