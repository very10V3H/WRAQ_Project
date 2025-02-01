package fun.wraq.series.instance.series.devil;

import fun.wraq.common.util.Utils;
import fun.wraq.series.instance.series.ice.weapon.IceSceptre;

public class DevilSceptre extends IceSceptre {

    public DevilSceptre(Properties properties) {
        super(properties, 1);
        Utils.manaDamage.put(this, 2000d);
        Utils.manaRecover.put(this, 22d);
        Utils.manaPenetration0.put(this, 25d);
        Utils.coolDownDecrease.put(this, 0.3);
        Utils.levelRequire.put(this, 150);
    }
}
