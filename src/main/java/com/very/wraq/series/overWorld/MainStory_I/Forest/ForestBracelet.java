package com.very.wraq.series.overWorld.MainStory_I.Forest;

import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class ForestBracelet extends Item{
    private final double ExpUp = 0.3F;
    private final double CriticalRate = 0.05F;
    private final double MaxHealth = 50.0d;

    private final double Defence = 25F;
    public ForestBracelet() {
        super(new Properties().rarity(Utils.ForestItalic));
        Utils.CritRate.put(this,CriticalRate);
        Utils.MaxHealth.put(this,MaxHealth);
        Utils.ExpUp.put(this,ExpUp);
        Utils.Defence.put(this,Defence);
        Utils.OffHandTag.put(this,1d);
        Utils.WeaponList.add(this);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        ChatFormatting chatFormatting = ChatFormatting.DARK_GREEN;
        stack.setHoverName(Component.literal("森林手环").withStyle(chatFormatting).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("副手                   ").withStyle(ChatFormatting.GOLD).append(Component.literal("手环").withStyle(ChatFormatting.GRAY)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,chatFormatting,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,chatFormatting,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        ForestSuitDescription.SuitDescription(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,chatFormatting,ChatFormatting.WHITE);
        components.add(Component.literal("Forest_Bracelet").withStyle(chatFormatting).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryI(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

}
