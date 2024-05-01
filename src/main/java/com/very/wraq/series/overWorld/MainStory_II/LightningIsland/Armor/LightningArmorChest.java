package com.very.wraq.series.overWorld.MainStory_II.LightningIsland.Armor;

import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.registry.ItemMaterial;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class LightningArmorChest extends ArmorItem {
    double Defence = 110;
    double MaxHealth = 1000;
    public LightningArmorChest(ItemMaterial Materrial, Type Slots)
    {
        super(Materrial,Slots,new Properties().rarity(Utils.LightningItalic));
        Utils.Defence.put(this, Defence);
        Utils.MaxHealth.put(this, MaxHealth);
        Utils.ArmorTag.put(this,1d);
        Utils.ArmorList.add(this);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("唤雷之心").withStyle(CustomStyle.styleOfLightingIsland).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal("胸甲").withStyle(CustomStyle.styleOfLightingIsland)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfLightingIsland,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,CustomStyle.styleOfLightingIsland,ChatFormatting.WHITE);
        LightningArmorHelmet.LightningArmorCommonDescription(components);
        super.appendHoverText(stack,level,components,flag);
    }
    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
