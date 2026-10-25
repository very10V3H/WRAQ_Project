package fun.wraq.series.overworld.chapter2.manaArmor.LifeMana;

import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.registry.ModArmorMaterials;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

public class LifeManaArmor extends WraqArmor {

    public LifeManaArmor(ModArmorMaterials material, Type type, Properties properties) {
        super(material, type, properties);
        if (type.equals(Type.HELMET)) {
            Utils.healthRecover.put(this, 8d);
            Utils.defence.put(this, 5d);
        }
        if (type.equals(Type.CHESTPLATE)) {
            Utils.defence.put(this, 10d);
            Utils.maxHealth.put(this, 400d);
        }
        if (type.equals(Type.LEGGINGS)) {
            Utils.maxHealth.put(this, 800d);
            Utils.defence.put(this, 5d);
        }
        if (type.equals(Type.BOOTS)) {
            Utils.movementSpeedCommon.put(this, 0.1);
            Utils.maxHealth.put(this, 400d);
        }
        Utils.manaDamage.put(this, 50d);
        Utils.maxMana.put(this, 10d);
        Utils.manaPenetration0.put(this, 1.5d);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfLife;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfChapterII();
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
