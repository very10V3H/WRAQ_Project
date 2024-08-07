package com.very.wraq.series.overworld.chapter1.waterSystem.equip.armor;

import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.overworld.chapter1.waterSystem.LakeSuitDescription;
import com.very.wraq.common.BasicAttributeDescription;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.common.registry.ItemMaterial;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class LakeArmorHelmet extends ArmorItem {
    public LakeArmorHelmet(ItemMaterial itemMaterial, Type Slots) {
        super(itemMaterial, Slots, new Properties().rarity(CustomStyle.WaterItalic));
        Utils.coolDownDecrease.put(this, 0.1);
        Utils.movementSpeedWithoutBattle.put(this, 0.15);
        Utils.armorTag.put(this, 1d);
        Utils.armorList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Compute.forgingHoverName(stack);
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal("头盔").withStyle(CustomStyle.styleOfWater)));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfWater, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components, stack);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, CustomStyle.styleOfWater, ChatFormatting.WHITE);
        Compute.DescriptionOfAddition(components);
        LakeSuitDescription.LakeArmorCommonDescription(components);
        super.appendHoverText(stack, level, components, flag);
    }

    public static double exCooldown(Player player) {
        return Compute.ArmorCount.lakeCountWithoutCrest(player) > 0 ? Math.min(100, player.experienceLevel) * 0.0025 : 0;
    }
}
