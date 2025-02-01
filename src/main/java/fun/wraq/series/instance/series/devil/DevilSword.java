package fun.wraq.series.instance.series.devil;

import fun.wraq.common.util.Utils;
import fun.wraq.series.instance.series.ice.weapon.IceSword;

public class DevilSword extends IceSword {

    public DevilSword(Properties properties) {
        super(properties, 1);
        Utils.attackDamage.put(this, 1000d);
        Utils.defencePenetration0.put(this, 25d);
        Utils.healthSteal.put(this, 0.08);
        Utils.critRate.put(this, 0.3);
        Utils.levelRequire.put(this, 150);
    }
}
