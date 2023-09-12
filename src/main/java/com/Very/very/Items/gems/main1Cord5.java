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

public class main1Cord5 extends Item {
    protected float CriticalRate = 0.25F;
    protected float HealReply = 1.0F;
    protected float Healup = 250.0F;
    protected float Defence = 50.0F;
    protected float Speedup = 0.2F;
    protected float Damage = 20.0F;
    public main1Cord5(Properties p_41383_) {
        super(p_41383_);
        Utils.CriticalHitRate.put(this,CriticalRate);
        Utils.HealReply.put(this,HealReply);
        Utils.HealUp.put(this,Healup);
        Utils.Defence.put(this,Defence);
        Utils.BaseDamage.put(this,Damage);
        Utils.SpeedUp.put(this,Speedup);
        Utils.OffHandTag.put(this,1f);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.setHoverName(Component.literal("四元传说之证").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("副手                   ").withStyle(ChatFormatting.GOLD).append(Component.literal("传说之证").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.LIGHT_PURPLE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GOLD,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.EmojiDescriptionCritRate(components,CriticalRate);
        Compute.EmojiDescriptionMaxHealth(components,Healup);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GOLD,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.EmojiDescriptionHealthRecover(components,HealReply);
        Compute.EmojiDescriptionDefence(components,Defence);
        Compute.EmojiDescriptionMovementSpeed(components,Speedup);
        Compute.EmojiDescriptionExAttackDamage(components,Damage);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GOLD,ChatFormatting.WHITE);
        components.add(Component.literal("Legend-Star-Fin.").withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.ITALIC));
        components.add(Component.literal("MainStoryI-Fin.").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
