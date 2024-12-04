package fun.wraq.series.specialevents.springFes;

import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class SpringNecklace extends WraqCurios {

    public SpringNecklace(Properties p_41383_, int tier) {
        super(p_41383_);
        Utils.manaDamage.put(this, new double[]{40, 80, 120, 160}[tier]);
        Utils.manaPenetration0.put(this, new double[]{1, 1, 2, 2}[tier]);
        Utils.manaRecover.put(this, new double[]{1, 2, 3, 4}[tier]);
        Utils.maxMana.put(this, new double[]{10, 20, 30, 40}[tier]);
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
