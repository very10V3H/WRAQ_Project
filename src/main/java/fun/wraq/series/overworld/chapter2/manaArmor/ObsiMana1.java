package fun.wraq.series.overworld.chapter2.manaArmor;

import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModArmorMaterials;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.equip.WraqArmor;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ObsiMana1 extends WraqArmor {

    public ObsiMana1(ModArmorMaterials Material, Type Slots, Properties itemProperties) {
        super(Material, Slots, itemProperties);
        if (type.equals(Type.HELMET)) Utils.healthRecover.put(this, 10d);
        if (type.equals(Type.CHESTPLATE)) Utils.defence.put(this, 25d);
        if (type.equals(Type.LEGGINGS)) Utils.maxHealth.put(this, 2000d);
        if (type.equals(Type.BOOTS)) Utils.movementSpeedCommon.put(this, 0.1);
        Utils.manaDamage.put(this, 150d);
        Utils.maxMana.put(this, 20d);
        Utils.manaPenetration0.put(this, 3d);
        Utils.manaRecover.put(this, 5d);
        Utils.coolDownDecrease.put(this, 0.1);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfMana;
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
