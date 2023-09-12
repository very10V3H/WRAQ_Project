package com.Very.very.Series.OverWorldSeries.MainStory_I.WaterSystem.Armor;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ItemMaterial;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class LakeArmorHelmet extends ArmorItem {
    float defence = 20.0F;
    float healup = 60.0F;
    public LakeArmorHelmet(ItemMaterial Materrial, Type Slots)
    {
        super(Materrial,Slots,new Item.Properties());
        Utils.Defence.put(this, defence);
        Utils.HealUp.put(this,healup);
        Utils.ArmorTag.put(this,1f);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("湖泊头盔").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal("头盔").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.BLUE,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.EmojiDescriptionDefence(components,defence);
        Compute.EmojiDescriptionMaxHealth(components,healup);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.BLUE,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        LakeArmorHelmet.LakeArmorCommonDescription(components);
        super.appendHoverText(stack,level,components,flag);
    }
    public static void LakeArmorCommonDescription (List<Component> components) {
        Compute.DescriptionOfAddtion(components);
        Compute.SuitDescription(components);
        Compute.DescriptionPassive(components,Component.literal("冷却剂").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE));
        components.add(Component.literal("获得").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.CoolDown("25%")));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.BLUE,ChatFormatting.WHITE);
        components.add(Component.literal("LakeArmor-I").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryI(components);
    }
}
