package fun.wraq.series.overworld.chapter1.snow;

import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.registry.ModArmorMaterials;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SnowArmor extends WraqArmor {

    public SnowArmor(ModArmorMaterials material, Type Slots) {
        super(material, Slots, new Properties().rarity(CustomStyle.SnowItalic));
        if (type.equals(Type.HELMET)) {
            Utils.healthRecover.put(this, 30d);
            Utils.defence.put(this, 15d);
        }
        if (type.equals(Type.CHESTPLATE)) {
            Utils.defence.put(this, 25d);
            Utils.maxHealth.put(this, 2000d);
        }
        if (type.equals(Type.LEGGINGS)) {
            Utils.maxHealth.put(this, 4000d);
            Utils.defence.put(this, 15d);
        }
        if (type.equals(Type.BOOTS)) {
            Utils.movementSpeedCommon.put(this, 0.1);
            Utils.maxHealth.put(this, 2000d);
        }
        Utils.levelRequire.put(this, 100);
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
