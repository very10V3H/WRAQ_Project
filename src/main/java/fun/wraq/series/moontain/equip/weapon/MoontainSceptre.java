package fun.wraq.series.moontain.equip.weapon;

import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.equip.impl.ExBaseAttributeValueEquip;
import fun.wraq.common.equip.impl.WraqMainHandOrPassiveEquip;
import fun.wraq.common.equip.WraqSceptre;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Map;

public class MoontainSceptre extends WraqSceptre implements ExBaseAttributeValueEquip, WraqMainHandOrPassiveEquip {

    public MoontainSceptre(Properties properties) {
        super(properties);
        Utils.manaDamage.put(this, 3600d);
        Utils.manaRecover.put(this, 30d);
        Utils.manaPenetration0.put(this, 40d);
        Utils.movementSpeedWithoutBattle.put(this, 0.4);
        Utils.manaCost.put(this, 45d);
        Utils.coolDownDecrease.put(this, 0.2);
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
