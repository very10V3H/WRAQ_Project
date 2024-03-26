package com.Very.very.Series.OverWorldSeries.MainStory_I.Plain;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class PlainBracelet extends Item{

    private final double ExpUp = 0.3F;
    private final double CriticalRate = 0.05F;
    private final double MaxHealth = 50.0d;
    private final double HealReply = 1.0d;
    public PlainBracelet() {
        super(new Properties().rarity(Utils.PlainItalic));
        Utils.CritRate.put(this,CriticalRate);
        Utils.MaxHealth.put(this,MaxHealth);
        Utils.ExpUp.put(this,ExpUp);
        Utils.HealthRecover.put(this,HealReply);
        Utils.OffHandTag.put(this,1d);
        Utils.WeaponList.add(this);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        ChatFormatting chatFormatting = ChatFormatting.GREEN;
        stack.setHoverName(Component.literal("平原手环").withStyle(chatFormatting).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("副手                   ").withStyle(ChatFormatting.GOLD).append(Component.literal("手环").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,chatFormatting,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,chatFormatting,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        PlainSuitDescription.SuitDescription(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,chatFormatting,ChatFormatting.WHITE);
        components.add(Component.literal("Plain_Bracelet").withStyle(chatFormatting).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryI(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

}
