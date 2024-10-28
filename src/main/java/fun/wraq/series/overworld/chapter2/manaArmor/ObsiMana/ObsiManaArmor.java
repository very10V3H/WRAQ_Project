package fun.wraq.series.overworld.chapter2.manaArmor.ObsiMana;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqArmor;
import fun.wraq.common.registry.ItemMaterial;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ObsiManaArmor extends WraqArmor {

    public ObsiManaArmor(ItemMaterial material, Type type) {
        super(material, type, new Properties().rarity(CustomStyle.EvokerItalic));
        if (type.equals(Type.HELMET)) Utils.healthRecover.put(this, 5d);
        if (type.equals(Type.CHESTPLATE)) Utils.defence.put(this, 20d);
        if (type.equals(Type.LEGGINGS)) Utils.maxHealth.put(this, 300d);
        if (type.equals(Type.BOOTS)) Utils.movementSpeedCommon.put(this, 0.35);
        Utils.manaDamage.put(this, 75d);
        Utils.maxMana.put(this, 10d);
        Utils.manaPenetration0.put(this, 2d);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfMana;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        if (Screen.hasShiftDown()) ObsiManaSuitDescription.SuitDescription(components);
        else {
            ComponentUtils.suitDescription(components);
            components.add(Component.literal("[按住shift展开套装效果]").withStyle(ChatFormatting.GRAY));
        }
        return components;
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
