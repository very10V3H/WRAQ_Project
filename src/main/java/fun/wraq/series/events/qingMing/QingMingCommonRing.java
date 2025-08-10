package fun.wraq.series.events.qingMing;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.onhit.OnHitDamageInfluenceCurios;
import fun.wraq.common.impl.onkill.OnKillEffectCurios;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QingMingCommonRing extends WraqCurios implements OnKillEffectCurios, OnHitDamageInfluenceCurios {

    public QingMingCommonRing(Properties properties) {
        super(properties);
        Utils.percentMaxHealthEnhance.put(this, 0.04);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getAttackTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("往生", hoverMainStyle()));
        Component countName = ComponentUtils.getRightAngleQuote("往生", hoverMainStyle());
        components.add(Te.s(" 每击杀50名敌人，将会获得", "持续5min", "的", countName));
        components.add(Te.s(" 在", countName, "的持续时间内，你对目标造成的伤害；"));
        components.add(Te.s(" 将基于目标的", ComponentUtils.AttributeDescription.health("已损失"),
                "提供至多", ComponentUtils.getCommonDamageEnhance("10%")));
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

    public static Map<String, Integer> killCountMap = new HashMap<>();
    public static Map<String, Integer> passiveExpireTickMap = new HashMap<>();
    public static final String TEXTURE_URL = "qing_ming_common_ring_1";
    @Override
    public void onKill(Player player, Mob mob, ItemStack stack) {
        killCountMap.compute(Name.get(player), (k, v) -> v == null ? 1 : Math.min(50, v + 1));
        int count = killCountMap.get(Name.get(player));
        if (count >= 50) {
            Compute.sendEffectLastTime(player, "item/" + TEXTURE_URL, Tick.min(5), 0, false);
            killCountMap.put(Name.get(player), 0);
            passiveExpireTickMap.put(Name.get(player), Tick.get() + Tick.min(5));
        } else {
            Compute.sendEffectLastTime(player, this, count, true);
        }
    }

    @Override
    public double modifyHitDamageRate(Player player, Mob mob) {
        return passiveExpireTickMap.getOrDefault(Name.get(player), 0) > Tick.get()
                ? 1 - mob.getHealth() / mob.getMaxHealth() : 0;
    }

    public static List<Component> getAttackAndDefenceCommonDescription() {
        List<Component> components = new ArrayList<>();
        Style style = CustomStyle.styleOfLife;
        ComponentUtils.descriptionPassive(components, Te.s("清明", style));
        Component countNameQing = ComponentUtils.getRightAngleQuote("清", style);
        Component countNameMing = ComponentUtils.getRightAngleQuote("明", style);
        components.add(Te.s(" 当你拥有", "10层", countNameQing, "与", countNameMing,
                "时，为你提供", "10%额外产出", ChatFormatting.GOLD));
        return components;
    }

    public static double getExHarvest(Player player) {
        String name = Name.get(player);
        if (QingMingAttackRing.countExpiredTickMap.getOrDefault(name, 0) > Tick.get()
                && QingMingAttackRing.countMap.getOrDefault(name, 0) >= 10
                && QingMingDefenceRing.countExpiredTickMap.getOrDefault(name, 0) > Tick.get()
                && QingMingDefenceRing.countMap.getOrDefault(name, 0) >= 10) {
            return 0.1;
        }
        return 0;
    }
}
