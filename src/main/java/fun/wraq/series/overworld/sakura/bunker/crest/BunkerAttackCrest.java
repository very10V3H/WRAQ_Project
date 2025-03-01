package fun.wraq.series.overworld.sakura.bunker.crest;

import fun.wraq.common.util.Utils;

public class BunkerAttackCrest extends BunkerCrest {

    public BunkerAttackCrest(Properties properties, int tier) {
        super(properties);
        Utils.xpLevelAttackDamage.put(this, new double[]{0.15, 0.3, 0.45, 0.6, 1}[tier]);
        Utils.levelRequire.put(this, 230);
    }
}
