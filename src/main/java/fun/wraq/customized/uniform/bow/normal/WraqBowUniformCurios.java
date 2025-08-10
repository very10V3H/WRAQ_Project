package fun.wraq.customized.uniform.bow.normal;

import fun.wraq.common.util.Utils;
import fun.wraq.customized.WraqUniformCurios;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public abstract class WraqBowUniformCurios extends WraqUniformCurios {

    public final static List<Item> BOW_CURIOS = new ArrayList<>();

    public WraqBowUniformCurios(Properties properties) {
        super(properties);
        Utils.xpLevelAttackDamage.put(this, 2d);
        Utils.xpLevelDefencePenetration0.put(this, 0.1d);
        Utils.critDamage.put(this, 0.08);
        BOW_CURIOS.add(this);
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfFlexible;
    }

    @Override
    public double getFinalDamageEnhanceRate() {
        return 0.5;
    }
}
