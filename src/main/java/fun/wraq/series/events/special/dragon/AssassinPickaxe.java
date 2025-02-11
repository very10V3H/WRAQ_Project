package fun.wraq.series.events.special.dragon;

import fun.wraq.common.equip.WraqPickaxe;
import fun.wraq.common.util.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class AssassinPickaxe extends WraqPickaxe {

    public AssassinPickaxe(Properties properties) {
        super(properties);
        Utils.critRate.put(this, 0.3);
        Utils.healthSteal.put(this, 0.3);
        Utils.xpLevelAttackDamage.put(this, 8d);
        Utils.xpLevelDefencePenetration0.put(this, 0.2);
    }

    @Override
    public Style getMainStyle() {
        return null;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        return List.of();
    }

    @Override
    public Component getSuffix() {
        return null;
    }
}
