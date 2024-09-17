package com.very.wraq.series.overworld.chapter1.waterSystem.equip.armor;

import com.very.wraq.common.Compute;
import com.very.wraq.common.attribute.BasicAttributeDescription;
import com.very.wraq.common.registry.ItemMaterial;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.overworld.chapter1.waterSystem.LakeSuitDescription;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class LakeArmorLeggings extends ArmorItem {
    public LakeArmorLeggings(ItemMaterial itemMaterial, Type Slots) {
        super(itemMaterial, Slots, new Properties().rarity(CustomStyle.WaterItalic));
        Utils.coolDownDecrease.put(this, 0.14);
        Utils.movementSpeedWithoutBattle.put(this, 0.15);
        Utils.armorTag.put(this, 1d);
        Utils.armorList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Compute.forgingHoverName(stack);
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal("护腿").withStyle(CustomStyle.styleOfWater)));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfWater, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components, stack);
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfWater, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfAddition(components);
        LakeSuitDescription.LakeArmorCommonDescription(components);
        super.appendHoverText(stack, level, components, flag);
    }
}
