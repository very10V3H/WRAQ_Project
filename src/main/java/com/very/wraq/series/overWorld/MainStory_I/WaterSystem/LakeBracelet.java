package com.very.wraq.series.overWorld.MainStory_I.WaterSystem;

import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class LakeBracelet extends Item{
    private final double ExpUp = 0.3F;
    private final double CriticalRate = 0.05F;
    private final double MaxHealth = 50.0d;

    private final double CoolDown = 0.1F;
    public LakeBracelet() {
        super(new Properties().rarity(Utils.LakeItalic));
        Utils.CritRate.put(this,CriticalRate);
        Utils.MaxHealth.put(this,MaxHealth);
        Utils.ExpUp.put(this,ExpUp);

        Utils.CoolDownDecrease.put(this,CoolDown);

        Utils.OffHandTag.put(this,1d);
        Utils.WeaponList.add(this);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        ChatFormatting chatFormatting = ChatFormatting.BLUE;
        stack.setHoverName(Component.literal("湖泊手环").withStyle(chatFormatting).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("副手                   ").withStyle(ChatFormatting.GOLD).append(Component.literal("手环").withStyle(ChatFormatting.GRAY)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,chatFormatting,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,chatFormatting,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        LakeSuitDescription.LakeSuitDescription(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,chatFormatting,ChatFormatting.WHITE);
        components.add(Component.literal("Lake_Bracelet").withStyle(chatFormatting).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryI(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

}
