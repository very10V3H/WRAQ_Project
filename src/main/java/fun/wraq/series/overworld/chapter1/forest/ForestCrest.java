package fun.wraq.series.overworld.chapter1.forest;

import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.equip.impl.RepeatableCurios;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

public class ForestCrest extends WraqCurios implements RepeatableCurios {

    public ForestCrest(Properties p_41383_, int tier) {
        super(p_41383_, 16);
        Utils.maxHealth.put(this, new double[]{400, 800, 1200, 1600, 6400}[tier]);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getDefenceTypeDescriptionOfCurios();
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfForest;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
