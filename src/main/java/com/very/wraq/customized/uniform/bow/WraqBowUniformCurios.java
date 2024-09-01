package com.very.wraq.customized.uniform.bow;

import com.very.wraq.common.Utils.Utils;
import com.very.wraq.projectiles.WraqUniformCurios;

public abstract class WraqBowUniformCurios extends WraqUniformCurios {

    public WraqBowUniformCurios(Properties properties) {
        super(properties);
        Utils.xpLevelAttackDamage.put(this, 2d);
        Utils.xpLevelDefencePenetration0.put(this, 2d);
        Utils.xpLevelCritDamage.put(this, 0.8);
    }

}
