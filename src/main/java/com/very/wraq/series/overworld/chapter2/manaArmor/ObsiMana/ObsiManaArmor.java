package com.very.wraq.series.overworld.chapter2.manaArmor.ObsiMana;

import com.very.wraq.projectiles.WraqArmor;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.common.registry.ItemMaterial;
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
        Utils.defence.put(this, 100d);
        Utils.manaDamage.put(this, 75d);
        Utils.maxHealth.put(this, 80d);
        Utils.maxMana.put(this, 10d);
        Utils.manaPenetration0.put(this, 12d);
        if (type.equals(Type.BOOTS)) Utils.movementSpeedCommon.put(this, 0.3);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfMana;
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        if (Screen.hasShiftDown()) ObsiManaSuitDescription.SuitDescription(components);
        else {
            Compute.SuitDescription(components);
            components.add(Component.literal("[按住shift展开套装效果]").withStyle(ChatFormatting.GRAY));
        }
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixChapterII();
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

}
