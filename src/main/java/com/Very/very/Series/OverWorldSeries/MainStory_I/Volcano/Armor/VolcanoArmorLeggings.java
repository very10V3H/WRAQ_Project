package com.Very.very.Series.OverWorldSeries.MainStory_I.Volcano.Armor;

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

public class VolcanoArmorLeggings extends ArmorItem {
    float defence = 25.0F;
    float healup = 80.0F;
    public VolcanoArmorLeggings(ItemMaterial Materrial, Type Slots)
    {
        super(Materrial,Slots,new Properties());
        Utils.Defence.put(this, defence);
        Utils.HealUp.put(this,healup);
        Utils.ArmorTag.put(this,1f);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("火山护腿").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal("护腿").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.YELLOW)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.YELLOW,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.EmojiDescriptionDefence(components,defence);
        Compute.EmojiDescriptionMaxHealth(components,healup);
        VolcanoArmorHelmet.VolcanoArmorCommonDescription(components);
        super.appendHoverText(stack,level,components,flag);
    }
}
