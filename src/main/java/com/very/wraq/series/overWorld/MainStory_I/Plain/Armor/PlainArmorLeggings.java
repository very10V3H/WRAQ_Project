package com.very.wraq.series.overWorld.MainStory_I.Plain.Armor;

import com.very.wraq.series.overWorld.MainStory_I.Plain.PlainSuitDescription;
import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ItemMaterial;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class PlainArmorLeggings extends ArmorItem {
    double Defence = 10.0d;
    double MaxHealth = 20.0d;
    public PlainArmorLeggings(ItemMaterial Material, Type Slots)
    {
        super(Material,Slots,new Properties().rarity(Utils.PlainItalic));
        Utils.Defence.put(this, Defence);
        Utils.MaxHealth.put(this,MaxHealth);
        Utils.ArmorTag.put(this,1d);
        Utils.ArmorList.add(this);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("平原护腿").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal("护腿").withStyle(ChatFormatting.GREEN)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GREEN,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        PlainSuitDescription.ArmorCommonDescription(components);
        super.appendHoverText(stack,level,components,flag);
    }
}
