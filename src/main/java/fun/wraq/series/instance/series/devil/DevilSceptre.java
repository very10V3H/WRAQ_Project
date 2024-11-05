package fun.wraq.series.instance.series.devil;

import fun.wraq.common.util.Utils;
import fun.wraq.series.instance.series.ice.weapon.IceSceptre;

public class DevilSceptre extends IceSceptre {

    public DevilSceptre(Properties p_42964_) {
        super(p_42964_);
        Utils.manaDamage.put(this, 2000d);
        Utils.manaRecover.put(this, 30d);
        Utils.manaPenetration0.put(this, 25d);
        Utils.manaCost.put(this, 45d);
        Utils.coolDownDecrease.put(this, 0.5);
    }
}
