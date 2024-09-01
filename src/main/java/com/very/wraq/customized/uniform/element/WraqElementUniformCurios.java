package com.very.wraq.customized.uniform.element;

import com.very.wraq.common.Utils.Utils;
import com.very.wraq.projectiles.WraqUniformCurios;

public abstract class WraqElementUniformCurios extends WraqUniformCurios {

    public WraqElementUniformCurios(Properties properties) {
        super(properties);
        Utils.xpLevelDefence.put(this, 2d);
        Utils.xpLevelManaDefence.put(this, 2d);
        Utils.xpLevelDefencePenetration0.put(this, 2d);
        Utils.xpLevelManaPenetration0.put(this, 2d);
        Utils.coolDownDecrease.put(this, 0.25);
    }
}
