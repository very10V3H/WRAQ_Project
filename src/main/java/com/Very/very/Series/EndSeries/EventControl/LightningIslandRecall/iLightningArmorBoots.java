package com.Very.very.Series.EndSeries.EventControl.LightningIslandRecall;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ItemMaterial;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class iLightningArmorBoots extends ArmorItem {
    float defence = 50.0F;
    float healUp = 200.0F;
    public iLightningArmorBoots(ItemMaterial Materrial, Type Slots)
    {
        super(Materrial,Slots,new Properties().rarity(Rarity.UNCOMMON));
        Utils.Defence.put(this, defence);
        Utils.HealUp.put(this, healUp);
        Utils.ArmorTag.put(this,1f);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("<能量激化>").withStyle(Utils.styleOfVolcano).append(Component.literal("唤雷之基").withStyle(Utils.styleOfLightingIsland).withStyle(ChatFormatting.BOLD)));
        components.add(Component.literal("防具                   ").withStyle(ChatFormatting.GRAY).append(Component.literal("靴子").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfLightingIsland)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfLightingIsland,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.EmojiDescriptionMaxHealth(components,healUp);
        Compute.EmojiDescriptionDefence(components,defence);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfLightingIsland,ChatFormatting.WHITE);
        iLightningArmorHelmet.iLightingArmorCommonDescription(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
