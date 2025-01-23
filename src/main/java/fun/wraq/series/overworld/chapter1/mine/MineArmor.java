package fun.wraq.series.overworld.chapter1.mine;

import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MineArmor extends WraqArmor {

    public MineArmor(ArmorMaterial armorMaterial, Type type) {
        super(armorMaterial, type, new Properties().rarity(CustomStyle.MineItalic));
        if (type.equals(Type.BOOTS)) {
            Utils.movementSpeedWithoutBattle.put(this, -0.2);
        }
        Utils.defence.put(this, 5d);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfMine;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        MineSuitDescription.ArmorCommonDescription(components);
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }
}
