package fun.wraq.series.moontain.equip.armor;

import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.equip.impl.ExBaseAttributeValueEquip;
import fun.wraq.common.equip.WraqArmor;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.Map;

public class MoontainArmor extends WraqArmor implements ExBaseAttributeValueEquip {

    public MoontainArmor(ArmorMaterial armorMaterial, Type type, Properties properties) {
        super(armorMaterial, type, properties);
        Utils.maxHealth.put(this, 10925d);
        Utils.attackDamage.put(this, 1814.5);
        Utils.manaDamage.put(this, 3629d);
        Utils.levelRequire.put(this, 240);
    }

    @Override
    public Map<Map<Item, Double>, TagAndRate> getTagAndRateMap() {
        return Map.of();
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfMoontain;
    }

    @Override
    public List<Component> getAdditionalComponents() {
        return List.of();
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfMoontain();
    }
}
