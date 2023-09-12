package com.Very.very.Series.NetherSeries.Equip;

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

public class NetherPower extends Item {
    protected float ManaDamage = 40;
    protected float ManaBreakDenfence = 0.2f;
    protected float ManaReply = 5;
    protected float Speedup = 0.2f;
    protected float CoolDown = 0.1f;
    public NetherPower(Properties p_41383_) {
        super(p_41383_);
        Utils.ManaDamage.put(this,ManaDamage);
        Utils.ManaBreakDefence.put(this,ManaBreakDenfence);
        Utils.ManaReply.put(this,ManaReply);
        Utils.SpeedUp.put(this,Speedup);
        Utils.CoolDownDecrease.put(this,CoolDown);
        Utils.OffHandTag.put(this,1f);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.setHoverName(Component.literal("下界能量矩阵").withStyle(Utils.styleOfNether).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("副手                   ").withStyle(ChatFormatting.GOLD).append(Component.literal("能量矩阵").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfNether,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.EmojiDescriptionManaAttackDamage(components,ManaDamage);
        Compute.EmojiDescriptionManaBreakDefence(components,ManaBreakDenfence);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfNether,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.EmojiDescriptionManaRecover(components,ManaReply);
        Compute.EmojiDescriptionMovementSpeed(components,Speedup);
        Compute.EmojiDescriptionCoolDown(components,CoolDown);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfNether,ChatFormatting.WHITE);
        components.add(Component.literal("Nether-Star").withStyle(Utils.styleOfNether).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryIII(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
