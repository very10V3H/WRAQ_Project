package fun.wraq.series.overworld.sakura.bunker.crest;

import fun.wraq.common.util.Utils;

public class BunkerManaCrest extends BunkerCrest {

    public BunkerManaCrest(Properties properties, int tier) {
        super(properties);
        Utils.xpLevelManaDamage.put(this, new double[]{0.15, 0.3, 0.45, 0.6, 1}[tier] * 2);
        Utils.levelRequire.put(this, 230);
    }
}
