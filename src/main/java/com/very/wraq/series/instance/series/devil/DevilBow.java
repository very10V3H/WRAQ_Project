package com.very.wraq.series.instance.series.devil;

import com.very.wraq.common.util.Utils;
import com.very.wraq.series.instance.series.ice.IceBow;

public class DevilBow extends IceBow {

    public DevilBow(Properties p_40524_) {
        super(p_40524_);
        Utils.attackDamage.put(this, 1000d);
        Utils.defencePenetration0.put(this, 25d);
        Utils.critRate.put(this, 0.25);
        Utils.critDamage.put(this, 1.35);
        Utils.movementSpeedWithoutBattle.put(this, 0.6);
    }
}
