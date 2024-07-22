package com.very.wraq.series.overworld.chapter2.manaArmor.LifeMana;

import com.very.wraq.projectiles.WraqArmor;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.registry.ItemMaterial;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class LifeManaArmor extends WraqArmor {

    public LifeManaArmor(ItemMaterial material, Type type, Properties properties) {
        super(material, type, properties);
        Utils.defence.put(this, 70d);
        Utils.manaDamage.put(this, 50d);
        Utils.maxHealth.put(this, 40d);
        Utils.maxMana.put(this, 10d);
        Utils.manaPenetration0.put(this, 15d);
        if (type.equals(Type.BOOTS)) Utils.movementSpeedCommon.put(this, 0.3);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfLife;
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        if (Screen.hasShiftDown()) LifeManaSuitDescription.SuitDescription(components);
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

    public static int getPlayerLifeManaArmorCount(Player player) {
        int count = 0;
        for (ItemStack armorSlot : player.getArmorSlots()) {
            count += armorSlot.getItem() instanceof LifeManaArmor ? 1 : 0;
        }
        return count;
    }
}
