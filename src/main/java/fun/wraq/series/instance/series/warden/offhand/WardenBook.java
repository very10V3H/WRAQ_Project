package fun.wraq.series.instance.series.warden.offhand;

import fun.wraq.common.util.Utils;
import fun.wraq.series.instance.series.warden.WardenOffhandItem;
import net.minecraft.network.chat.Component;

public class WardenBook extends WardenOffhandItem {
    public WardenBook(Properties properties, Component type) {
        super(properties, type);
        Utils.manaDamage.put(this, 577d);
        Utils.manaPenetration0.put(this, 17d);
        Utils.maxMana.put(this, 87d);
        Utils.coolDownDecrease.put(this, 0.23);
        Utils.expUp.put(this, 0.88);
    }
}
