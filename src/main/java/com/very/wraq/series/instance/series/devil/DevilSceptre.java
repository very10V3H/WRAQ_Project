package com.very.wraq.series.instance.series.devil;

import com.very.wraq.common.Utils.Utils;
import com.very.wraq.series.instance.series.ice.IceSceptre;

public class DevilSceptre extends IceSceptre {

    public DevilSceptre(Properties p_42964_) {
        super(p_42964_);
        Utils.manaDamage.put(this, 2000d);
        Utils.manaRecover.put(this, 30d);
        Utils.manaPenetration0.put(this, 2500d);
        Utils.movementSpeedWithoutBattle.put(this, 0.4);
        Utils.manaCost.put(this, 45d);
        Utils.coolDownDecrease.put(this, 0.5);
    }
}
