package fun.wraq.series.overworld.chapter2.manaArmor;

import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.registry.ModArmorMaterials;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class LifeMana1 extends WraqArmor {

    public LifeMana1(ModArmorMaterials Material, Type Slots, Properties itemProperties) {
        super(Material, Slots, itemProperties);
        if (type.equals(Type.HELMET)) {
            Utils.healthRecover.put(this, 15d);
            Utils.defence.put(this, 5d);
        }
        if (type.equals(Type.CHESTPLATE)) {
            Utils.defence.put(this, 15d);
            Utils.maxHealth.put(this, 800d);
        }
        if (type.equals(Type.LEGGINGS)) {
            Utils.maxHealth.put(this, 1600d);
            Utils.defence.put(this, 5d);
        }
        if (type.equals(Type.BOOTS)) {
            Utils.movementSpeedCommon.put(this, 0.1);
            Utils.maxHealth.put(this, 800d);
        }
        Utils.manaDamage.put(this, 100d);
        Utils.maxMana.put(this, 20d);
        Utils.manaPenetration0.put(this, 2d);
        Utils.manaRecover.put(this, 7d);
        Utils.coolDownDecrease.put(this, 0.08);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfHealth;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        return List.of();
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
