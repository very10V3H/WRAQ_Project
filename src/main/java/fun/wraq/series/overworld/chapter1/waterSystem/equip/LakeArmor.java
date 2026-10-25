package fun.wraq.series.overworld.chapter1.waterSystem.equip;

import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ArmorMaterial;

public class LakeArmor extends WraqArmor {

    public LakeArmor(ArmorMaterial armorMaterial, Type type) {
        super(armorMaterial, type, new Properties().rarity(CustomStyle.LakeItalic));
        switch (type) {
            case HELMET -> {
                Utils.coolDownDecrease.put(this, 0.1);
            }
            case CHESTPLATE -> {
                Utils.coolDownDecrease.put(this, 0.16);
            }
            case LEGGINGS -> {
                Utils.coolDownDecrease.put(this, 0.14);
            }
            case BOOTS -> {
                Utils.coolDownDecrease.put(this, 0.08);
                Utils.movementSpeedCommon.put(this, 0.05);
            }
        }
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfLake;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }
}
