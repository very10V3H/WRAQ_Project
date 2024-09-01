package com.very.wraq.customized.uniform.attack;

import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.projectiles.WraqUniformCurios;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

public abstract class WraqAttackUniformCurios extends WraqUniformCurios {

    public WraqAttackUniformCurios(Properties properties) {
        super(properties);
        Utils.xpLevelAttackDamage.put(this, 2d);
        Utils.xpLevelDefencePenetration0.put(this, 2d);
        Utils.critDamage.put(this, 0.8);
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfPower;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getUniformSuffix();
    }
}
