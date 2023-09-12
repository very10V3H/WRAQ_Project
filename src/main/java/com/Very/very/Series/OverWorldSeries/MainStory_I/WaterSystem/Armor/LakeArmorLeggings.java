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

public class LakeArmorLeggings extends ArmorItem {
    float defence = 20.0F;
    float healup = 60.0F;
    public LakeArmorLeggings(ItemMaterial Materrial, Type Slots)
    {
        super(Materrial,Slots,new Item.Properties());
        Utils.Defence.put(this, defence);
        Utils.HealUp.put(this,healup);
        Utils.ArmorTag.put(this,1f);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("湖泊护腿").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal("护腿").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.BLUE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.BLUE,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.EmojiDescriptionDefence(components,defence);
        Compute.EmojiDescriptionMaxHealth(components,healup);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.BLUE,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        LakeArmorHelmet.LakeArmorCommonDescription(components);
        super.appendHoverText(stack,level,components,flag);
    }
}
