package fun.wraq.series.moontain.equip.weapon;

import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.equip.impl.ExBaseAttributeValueEquip;
import fun.wraq.common.equip.WraqBow;
import fun.wraq.common.equip.impl.WraqMainHandOrPassiveEquip;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Map;

public class MoontainBow extends WraqBow implements ExBaseAttributeValueEquip, WraqMainHandOrPassiveEquip {

    public MoontainBow(Properties properties) {
        super(properties);
        Utils.attackDamage.put(this, 1800d);
        Utils.defencePenetration0.put(this, 40d);
        Utils.critRate.put(this, 0.25);
        Utils.critDamage.put(this, 1.55);
        Utils.movementSpeedWithoutBattle.put(this, 0.6);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfMoontain;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        return List.of();
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfMoontain();
    }

    @Override
    public double rate() {
        return 0.25;
    }

    @Override
    public Map<Map<Item, Double>, TagAndRate> getTagAndRateMap() {
        return Map.of();
    }
}
