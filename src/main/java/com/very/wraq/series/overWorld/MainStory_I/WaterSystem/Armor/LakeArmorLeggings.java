package com.very.wraq.series.overWorld.MainStory_I.WaterSystem.Armor;

import com.very.wraq.series.overWorld.MainStory_I.WaterSystem.LakeSuitDescription;
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

public class LakeArmorLeggings extends ArmorItem {
    double Defence = 20.0d;
    double MaxHealth = 60.0d;
    public LakeArmorLeggings(ItemMaterial Materrial, Type Slots)
    {
        super(Materrial,Slots,new Item.Properties().rarity(Utils.LakeItalic));
        Utils.Defence.put(this, Defence);
        Utils.MaxHealth.put(this,MaxHealth);
        Utils.ArmorTag.put(this,1d);
        Utils.ArmorList.add(this);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("湖泊护腿").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal("护腿").withStyle(ChatFormatting.BLUE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.BLUE,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.BLUE,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        LakeSuitDescription.LakeArmorCommonDescription(components);
        super.appendHoverText(stack,level,components,flag);
    }
}
