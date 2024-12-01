package fun.wraq.series.instance.series.warden.offhand;

import fun.wraq.common.util.Utils;
import fun.wraq.series.instance.series.warden.WardenOffhandItem;
import net.minecraft.network.chat.Component;

public class WardenKnife extends WardenOffhandItem {
    public WardenKnife(Properties properties, Component type) {
        super(properties, type);
        Utils.attackDamage.put(this, 377d);
        Utils.defencePenetration0.put(this, 17d);
        Utils.critRate.put(this, 0.17);
        Utils.critDamage.put(this, 0.7);
        Utils.expUp.put(this, 0.88);
    }
}
