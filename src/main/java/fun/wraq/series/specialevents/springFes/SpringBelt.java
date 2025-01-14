package fun.wraq.series.specialevents.springFes;

import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class SpringBelt extends WraqCurios {

    public SpringBelt(Properties p_41383_, int tier) {
        super(p_41383_);
        Utils.attackDamage.put(this, new double[]{20, 40, 60, 80}[tier]);
        Utils.defencePenetration0.put(this, new double[]{1, 1, 1, 1}[tier]);
        Utils.swiftnessUp.put(this, new double[]{1, 2, 3, 4}[tier]);
        Utils.expUp.put(this, new double[]{0.3, 0.5, 0.7, 1.0}[tier]);
        Utils.levelRequire.put(this, new int[]{20, 40, 60, 80}[tier]);
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
        return ComponentUtils.getSuffixOfSpring2024();
    }
}
