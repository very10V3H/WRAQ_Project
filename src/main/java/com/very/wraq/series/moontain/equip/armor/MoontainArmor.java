package com.very.wraq.series.moontain.equip.armor;

import com.very.wraq.projectiles.ExBaseAttributeValueEquip;
import com.very.wraq.projectiles.WraqArmor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.Map;

public class MoontainArmor extends WraqArmor implements ExBaseAttributeValueEquip {

    public MoontainArmor(ArmorMaterial armorMaterial, Type type, Properties properties) {
        super(armorMaterial, type, properties);
    }

    @Override
    public Map<Map<Item, Double>, TagAndRate> getTagAndRateMap() {
        return Map.of();
    }

    @Override
    public Style getMainStyle() {
        return null;
    }

    @Override
    public List<Component> getAdditionalComponents() {
        return List.of();
    }

    @Override
    public Component getSuffix() {
        return null;
    }
}
