package com.very.wraq.series.overWorld.MainStory_I.Forest.Armor;

import com.very.wraq.series.overWorld.MainStory_I.Forest.ForestSuitDescription;
import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import com.very.wraq.valueAndTools.registry.ItemMaterial;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class ForestArmorHelmet extends ArmorItem {
    double Defence = 15.0d;
    double MaxHealth = 40.0d;
    public ForestArmorHelmet(ItemMaterial Materrial, Type Slots)
    {
        super(Materrial,Slots,new Item.Properties().rarity(Utils.ForestItalic));
        Utils.Defence.put(this, Defence);
        Utils.MaxHealth.put(this,MaxHealth);
        Utils.ArmorTag.put(this,1d);
        Utils.ArmorList.add(this);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("森林头盔").withStyle(ChatFormatting.DARK_GREEN).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal("头盔").withStyle(ChatFormatting.DARK_GREEN)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.DARK_GREEN,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        ForestSuitDescription.ArmorCommonDescription(components);
        super.appendHoverText(stack,level,components,flag);
    }
}
