package fun.wraq.series.events.spring2025.curios;

import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class Spring2025Belt extends WraqCurios {

    public Spring2025Belt(Properties p_41383_, int tier) {
        super(p_41383_);
        Utils.percentMaxHealthEnhance.put(this, new double[]{0.01, 0.02, 0.03, 0.04, 0.05}[tier]);
        Utils.healingAmplification.put(this, new double[]{0.03, 0.06, 0.09, 0.12, 0.15}[tier]);
        Utils.defence.put(this, new double[]{20, 40, 60, 80, 100}[tier]);
        Utils.manaDefence.put(this, new double[]{20, 40, 60, 80, 100}[tier]);
        Utils.percentHealthRecover.put(this, new double[]{0.0015, 0.0025, 0.0035, 0.0045, 0.006}[tier]);
        Utils.levelRequire.put(this, new int[]{50, 100, 150, 200, 250}[tier]);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getDefenceTypeDescriptionOfCurios();
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
        return ComponentUtils.getSuffixOfSpring2024();
    }
}
