package fun.wraq.series.instance.series.devil;

import fun.wraq.common.util.Utils;
import fun.wraq.series.instance.series.ice.weapon.IceBow;

public class DevilBow extends IceBow {

    public DevilBow(Properties p_40524_) {
        super(p_40524_);
        Utils.attackDamage.put(this, 1000d);
        Utils.defencePenetration0.put(this, 25d);
        Utils.critRate.put(this, 0.25);
        Utils.critDamage.put(this, 1.35);
        Utils.levelRequire.put(this, 150);
    }
}
