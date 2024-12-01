package fun.wraq.series.instance.series.warden.offhand;

import fun.wraq.common.util.Utils;
import fun.wraq.series.instance.series.warden.WardenOffhandItem;
import net.minecraft.network.chat.Component;

public class WardenShield extends WardenOffhandItem {
    public WardenShield(Properties properties, Component type) {
        super(properties, type);
        Utils.defence.put(this, 17d);
        Utils.maxHealth.put(this, 1377d);
        Utils.attackDamage.put(this, 377d);
        Utils.critDamage.put(this, 0.37);
        Utils.expUp.put(this, 0.88);
        Utils.shieldTag.put(this, 1d);
    }
}
