package com.Very.very.Items.gems;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class main1Cord3 extends Item {
    protected float CriticalRate = 0.1F;
    protected float Speedup = 0.2F;
    protected float Healup = 100.0F;
    public main1Cord3(Properties p_41383_) {
        super(p_41383_);
        Utils.CriticalHitRate.put(this,CriticalRate);
        Utils.SpeedUp.put(this,Speedup);
        Utils.HealUp.put(this,Healup);
        Utils.OffHandTag.put(this,1f);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.setHoverName(Component.literal("四元之证-湖泊").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("副手                   ").withStyle(ChatFormatting.GOLD).append(Component.literal("传说之证").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.BLUE,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.EmojiDescriptionCritRate(components,CriticalRate);
        Compute.EmojiDescriptionMaxHealth(components,Healup);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.BLUE,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.EmojiDescriptionMovementSpeed(components,Speedup);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.BLUE,ChatFormatting.WHITE);
        components.add(Component.literal("Lake-Star").withStyle(ChatFormatting.DARK_BLUE).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("MainStoryI-Fin.").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack,level,components,flag);
    }
    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
