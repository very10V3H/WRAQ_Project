package fun.wraq.series.events.spring2025.curios;

import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class Spring2025Necklace extends WraqCurios {

    public Spring2025Necklace(Properties p_41383_, int tier) {
        super(p_41383_);
        Utils.manaDamage.put(this, new double[]{400, 800, 1200, 1600, 2000}[tier]);
        Utils.percentManaDamageEnhance.put(this, new double[]{0.01, 0.02, 0.03, 0.04, 0.05}[tier]);
        Utils.manaPenetration0.put(this, new double[]{15, 25, 35, 45, 55}[tier]);
        Utils.manaHealthSteal.put(this, new double[]{0.01, 0.02, 0.03, 0.04, 0.05}[tier]);
        Utils.manaRecover.put(this, new double[]{10, 20, 30, 40, 50}[tier]);
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
