package com.very.wraq.series.instance.series.devil;

import com.very.wraq.common.Utils.Utils;
import com.very.wraq.series.instance.series.ice.IceSword;

public class DevilSword extends IceSword {

    public DevilSword(Properties properties) {
        super(properties);
        Utils.attackDamage.put(this, 1000d);
        Utils.defencePenetration0.put(this, 2500d);
        Utils.healthSteal.put(this, 0.08);
        Utils.critRate.put(this, 0.3);
        Utils.critDamage.put(this, 0.8);
        Utils.movementSpeedWithoutBattle.put(this, 0.5);
    }
}
