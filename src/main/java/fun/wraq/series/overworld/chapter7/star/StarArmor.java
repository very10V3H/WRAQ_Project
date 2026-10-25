package fun.wraq.series.overworld.chapter7.star;

import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.registry.ModArmorMaterials;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

public class StarArmor extends WraqArmor {

    public StarArmor(ModArmorMaterials Material, Type Slots, Properties itemProperties) {
        super(Material, Slots, itemProperties);
        if (type.equals(Type.LEGGINGS)) {
            Utils.maxHealth.put(this, 30000d);
            Utils.defence.put(this, 40d);
            Utils.manaDefence.put(this, 20d);
        }
        if (type.equals(Type.HELMET)) {
            Utils.percentHealthRecover.put(this, 0.01);
            Utils.healthRecover.put(this, 120d);
            Utils.defence.put(this, 40d);
            Utils.manaDefence.put(this, 20d);
        }
        Utils.levelRequire.put(this, 200);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfMoon1;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterStar();
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
