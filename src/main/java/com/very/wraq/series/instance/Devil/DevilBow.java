package com.very.wraq.series.instance.Devil;

import com.very.wraq.common.Utils.Utils;
import com.very.wraq.series.instance.Ice.IceBow;

public class DevilBow extends IceBow {

    public DevilBow(Properties p_40524_) {
        super(p_40524_);
        Utils.attackDamage.put(this, 1000d);
        Utils.defencePenetration0.put(this, 2500d);
        Utils.critRate.put(this, 0.25);
        Utils.critDamage.put(this, 1.35);
        Utils.movementSpeedWithoutBattle.put(this, 0.6);
    }
}
