package fun.wraq.series.events.midautumn;

import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.fast.Te;
import fun.wraq.common.impl.onhit.OnHitDamageInfluenceCurios;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MidAutumnLetterCurio extends WraqCurios implements OnHitDamageInfluenceCurios {

    public static Set<Item> items = new HashSet<>();

    private final int tier;

    public MidAutumnLetterCurio(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
        items.add(this);
        Utils.levelRequire.put(this, new int[]{0, 80, 150, 200, 250}[tier]);
        Utils.elementStrength.put(this, new double[]{0.08, 0.11, 0.14, 0.17, 0.2}[tier]);
        Utils.toughness.put(this, new double[]{0.08, 0.11, 0.14, 0.17, 0.2}[tier]);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getComprehensiveTypeDescriptionOfCurios();
    }

    private double getRate() {
        return new double[]{0.1, 0.12, 0.15, 0.18, 0.25}[tier];
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Te.s("太阴之境", hoverMainStyle()));
        components.add(Te.s(" 在战斗状态下 ", "-83%重力", hoverMainStyle()));
        components.add(Te.s(" 基于与目标的相对地面高差绝对值，提供至多",
                Te.percent(getRate()) + "伤害提升", hoverMainStyle()));
        components.add(Te.s(" 高度差在", "4格", "时提供最大提升"));
        components.add(Te.s(" 在空中时，", "近战攻击距离", CustomStyle.styleOfSea, "会得到提升."));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfMoon;
    }

    @Override
    public Component suffix() {
        return MidAutumnUtil.getSuffix();
    }

    @Override
    public double modifyHitDamageRate(Player player, Mob mob) {
        double heightDifference = Math.abs(player.position().y - mob.position().y);
        return getRate() * heightDifference / 4;
    }
}
