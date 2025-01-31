package fun.wraq.series.events.spring2025.curios;

import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.equip.WraqPickaxe;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class Spring2025Ring extends WraqCurios {

    public Spring2025Ring(Properties p_41383_, int tier) {
        super(p_41383_);
        Utils.movementSpeedCommon.put(this, new double[]{0.02, 0.04, 0.06, 0.08, 0.1}[tier]);
        Utils.coolDownDecrease.put(this, new double[]{0.03, 0.06, 0.09, 0.12, 0.15}[tier]);
        Utils.expUp.put(this, new double[]{0.04, 0.08, 0.12, 0.16, 0.2}[tier]);
        WraqPickaxe.mineSpeed.put(this, new double[]{0.04, 0.08, 0.12, 0.16, 0.2}[tier]);
        Utils.toughness.put(this, new double[]{0.02, 0.04, 0.06, 0.08, 0.1}[tier]);
        Utils.levelRequire.put(this, new int[]{50, 100, 150, 200, 250}[tier]);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getFuncTypeDescriptionOfCurios();
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
