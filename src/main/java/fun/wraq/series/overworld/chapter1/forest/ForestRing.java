package fun.wraq.series.overworld.chapter1.forest;

import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ForestRing extends WraqCurios {

    public ForestRing(Properties p_41383_) {
        super(p_41383_);
        Utils.critDamage.put(this, 0.08);
        Utils.manaPenetration0.put(this, 2d);
        Utils.expUp.put(this, 0.1);
        Utils.percentDefenceEnhance.put(this, 0.1);
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
        return CustomStyle.styleOfForest;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }
}
