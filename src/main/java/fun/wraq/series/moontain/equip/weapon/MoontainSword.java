package fun.wraq.series.moontain.equip.weapon;

import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.equip.impl.ExBaseAttributeValueEquip;
import fun.wraq.common.equip.impl.WraqMainHandOrPassiveEquip;
import fun.wraq.common.equip.WraqSword;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.moontain.equip.weapon.MoontainUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoontainSword extends WraqSword implements ExBaseAttributeValueEquip, WraqMainHandOrPassiveEquip {

    public MoontainSword(Properties properties) {
        super(properties);
        Utils.attackDamage.put(this, 1800d);
        Utils.defencePenetration0.put(this, 40d);
        Utils.healthSteal.put(this, 0.08);
        Utils.critRate.put(this, 0.30d);
        Utils.critDamage.put(this, 1d);
        Utils.movementSpeedWithoutBattle.put(this, 0.5);
        Utils.levelRequire.put(this, 240);
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
        return new HashMap<>() {{
            put(Utils.attackDamage, new TagAndRate(MoontainUtils.MOONTAIN_ATTACK_TAG_KEY, 100));
        }};
    }
}
