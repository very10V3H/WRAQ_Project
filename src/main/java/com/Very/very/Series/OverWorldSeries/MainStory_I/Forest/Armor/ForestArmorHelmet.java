package com.Very.very.Series.OverWorldSeries.MainStory_I.Forest.Armor;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ItemMaterial;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class ForestArmorHelmet extends ArmorItem {
    float defence = 15.0F;
    float healup = 40.0F;
    public ForestArmorHelmet(ItemMaterial Materrial, Type Slots)
    {
        super(Materrial,Slots,new Item.Properties());
        Utils.Defence.put(this, defence);
        Utils.HealUp.put(this,healup);
        Utils.ArmorTag.put(this,1f);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("森林头盔").withStyle(ChatFormatting.DARK_GREEN).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal("头盔").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.DARK_GREEN)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.DARK_GREEN,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.EmojiDescriptionDefence(components,defence);
        Compute.EmojiDescriptionMaxHealth(components,healup);
        ForestArmorCommonDescription(components);
        super.appendHoverText(stack,level,components,flag);
    }
    public static void ForestArmorCommonDescription (List<Component> components) {
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.DARK_GREEN,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.SuitDescription(components);
        Compute.DescriptionPassive(components,Component.literal("森林生机").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.DARK_GREEN));
        Compute.DescriptionNum(components,"提升",Compute.AttributeDescription.Defence("25%"),"");
        Compute.DescriptionNum(components,"提升",Compute.AttributeDescription.HealAmplification("50%"),"");
        Compute.DescriptionNum(components,"在白天，提供",Compute.AttributeDescription.HealthRecover("0.8"),"");
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.DARK_GREEN,ChatFormatting.WHITE);
        components.add(Component.literal("ForestArmor-I").withStyle(ChatFormatting.DARK_GREEN).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryI(components);
    }
}
