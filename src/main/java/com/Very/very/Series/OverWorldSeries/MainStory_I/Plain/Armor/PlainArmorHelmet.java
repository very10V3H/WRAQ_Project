package com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.Armor;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ItemMaterial;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class PlainArmorHelmet extends ArmorItem {
    private final float defence = 10.0F;
    private final float healup = 20.0F;
    public PlainArmorHelmet(ItemMaterial Materrial, Type Slots)
    {
        super(Materrial,Slots,new Properties());
        Utils.Defence.put(this, defence);
        Utils.HealUp.put(this,healup);
        Utils.ArmorTag.put(this,1f);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("平原头盔").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal("头盔").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GREEN,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.EmojiDescriptionDefence(components,defence);
        Compute.EmojiDescriptionMaxHealth(components,healup);
        PlainArmorCommonDescription(components);
        super.appendHoverText(stack,level,components,flag);
    }
    public static void PlainArmorCommonDescription (List<Component> components) {
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GREEN,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.SuitDescription(components);
        components.add(Component.literal(" - ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).
                append(Component.literal("被动 ").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN)).
                append(Component.literal("平原生机:").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN)));
        Compute.DescriptionNum(components,"每秒回复",Compute.AttributeDescription.Health("0.5%+1"),"");
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GREEN,ChatFormatting.WHITE);
        components.add(Component.literal("PlainArmor-I").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryI(components);

    }
}

