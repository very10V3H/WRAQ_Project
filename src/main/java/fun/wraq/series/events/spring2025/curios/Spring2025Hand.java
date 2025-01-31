package fun.wraq.series.events.spring2025.curios;

import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class Spring2025Hand extends WraqCurios {

    public Spring2025Hand(Properties p_41383_, int tier) {
        super(p_41383_);
        Utils.attackDamage.put(this, new double[]{200, 400, 600, 800, 1000}[tier]);
        Utils.percentAttackDamageEnhance.put(this, new double[]{0.01, 0.02, 0.03, 0.04, 0.05}[tier]);
        Utils.defencePenetration0.put(this, new double[]{15, 25, 35, 45, 55}[tier]);
        Utils.critDamage.put(this, new double[]{0.01, 0.02, 0.03, 0.04, 0.05}[tier]);
        Utils.healthSteal.put(this, new double[]{0.01, 0.02, 0.03, 0.04, 0.05}[tier]);
        Utils.levelRequire.put(this, new int[]{50, 100, 150, 200, 250}[tier]);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getAttackTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        return List.of();
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfSpring;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfSpring2025();
    }
}
