package com.very.wraq.customized.uniform.attack;

import com.very.wraq.common.Utils.Utils;
import com.very.wraq.projectiles.WraqUniformCurios;

public abstract class WraqAttackUniformCurios extends WraqUniformCurios {

    public WraqAttackUniformCurios(Properties properties) {
        super(properties);
        Utils.xpLevelAttackDamage.put(this, 2d);
        Utils.xpLevelDefencePenetration0.put(this, 2d);
        Utils.critDamage.put(this, 0.8);
    }

}
