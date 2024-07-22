package com.very.wraq.series.overworld.chapter1.forest.armor;

import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.overworld.chapter1.forest.ForestSuitDescription;
import com.very.wraq.common.BasicAttributeDescription;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.registry.ItemMaterial;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class ForestArmorBoots extends ArmorItem {
    public ForestArmorBoots(ItemMaterial Materrial, Type Slots) {
        super(Materrial, Slots, new Properties().rarity(CustomStyle.ForestItalic));
        Utils.defence.put(this, 120d);
        Utils.movementSpeedCommon.put(this, 0.25);
        Utils.armorTag.put(this, 1d);
        Utils.armorList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Compute.forgingHoverName(stack);
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal("靴子").withStyle(ChatFormatting.DARK_GREEN)));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, ChatFormatting.DARK_GREEN, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components, stack);
        ForestSuitDescription.ArmorCommonDescription(components);
        super.appendHoverText(stack, level, components, flag);
    }
}
