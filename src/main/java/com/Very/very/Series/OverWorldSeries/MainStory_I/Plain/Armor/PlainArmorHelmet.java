package com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.Armor;

import com.Very.very.Series.OverWorldSeries.MainStory_I.Plain.PlainSuitDescription;
import com.Very.very.ValueAndTools.BasicAttributeDescription;
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
    private final double Defence = 10.0d;
    private final double MaxHealth = 20.0d;
    public PlainArmorHelmet(ItemMaterial Material, Type Slots)
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
        Compute.ForgingHoverName(stack,Component.literal("平原头盔").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal("头盔").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GREEN,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
/*        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.EmojiDescriptionMaxHealth(components,MaxHealth);*/
        PlainSuitDescription.ArmorCommonDescription(components);
        super.appendHoverText(stack,level,components,flag);
    }
}

