package fun.wraq.series.overworld.sakura.bunker.crest;

import fun.wraq.common.util.Utils;
import fun.wraq.process.system.element.Element;

public class BunkerManaCrest extends BunkerCrest {

    public BunkerManaCrest(Properties properties, int tier) {
        super(properties);
        Utils.xpLevelManaDamage.put(this, new double[]{0.3, 0.6, 0.9, 1.2, 2}[tier] * 2);
        Utils.levelRequire.put(this, 230);
        Element.fireElementValue.put(this, new double[]{0.05, 0.12, 0.2, 0.32, 0.5}[tier]);
    }
}
