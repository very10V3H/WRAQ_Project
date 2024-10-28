package fun.wraq.series.overworld.chapter1.Snow.Armor;

import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.registry.ItemMaterial;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.overworld.chapter1.Snow.SnowSuitDescription;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SnowArmor extends WraqArmor {

    public SnowArmor(ItemMaterial material, Type Slots) {
        super(material, Slots, new Properties().rarity(CustomStyle.SnowItalic));
        if (type.equals(Type.HELMET)) Utils.healthRecover.put(this, 30d);
        if (type.equals(Type.CHESTPLATE)) Utils.defence.put(this, 50d);
        if (type.equals(Type.LEGGINGS)) Utils.maxHealth.put(this, 2000d);
        if (type.equals(Type.BOOTS)) Utils.movementSpeedCommon.put(this, 0.35);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfSnow;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        SnowSuitDescription.ArmorCommonDescription(components);
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterII();
    }
}
