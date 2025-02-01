package fun.wraq.customized.uniform.bow;

import fun.wraq.common.util.Utils;
import fun.wraq.customized.WraqUniformCurios;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Style;

public abstract class WraqBowUniformCurios extends WraqUniformCurios {

    public WraqBowUniformCurios(Properties properties) {
        super(properties);
        Utils.xpLevelAttackDamage.put(this, 2d);
        Utils.xpLevelDefencePenetration0.put(this, 0.1d);
        Utils.critDamage.put(this, 0.08);
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfFlexible;
    }

}
