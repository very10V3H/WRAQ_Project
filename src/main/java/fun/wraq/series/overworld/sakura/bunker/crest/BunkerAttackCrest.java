package fun.wraq.series.overworld.sakura.bunker.crest;

import fun.wraq.common.util.Utils;
import fun.wraq.process.system.element.Element;

public class BunkerAttackCrest extends BunkerCrest {

    public BunkerAttackCrest(Properties properties, int tier) {
        super(properties);
        Utils.xpLevelAttackDamage.put(this, new double[]{0.3, 0.6, 0.9, 1.2, 2}[tier]);
        Utils.levelRequire.put(this, 230);
        Element.fireElementValue.put(this, new double[]{0.05, 0.12, 0.2, 0.32, 0.5}[tier]);
    }
}
