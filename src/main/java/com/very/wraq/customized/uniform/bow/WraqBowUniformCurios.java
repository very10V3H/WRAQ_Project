package com.very.wraq.customized.uniform.bow;

import com.very.wraq.common.Utils.Utils;
import com.very.wraq.customized.WraqUniformCurios;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Style;

public abstract class WraqBowUniformCurios extends WraqUniformCurios {

    public WraqBowUniformCurios(Properties properties) {
        super(properties);
        Utils.xpLevelAttackDamage.put(this, 2d);
        Utils.xpLevelDefencePenetration0.put(this, 2d);
        Utils.critDamage.put(this, 0.8);
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfFlexible;
    }

}
