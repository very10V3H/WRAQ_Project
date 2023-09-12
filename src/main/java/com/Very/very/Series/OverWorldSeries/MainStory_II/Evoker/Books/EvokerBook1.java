package com.Very.very.Series.OverWorldSeries.MainStory_II.Evoker.Books;

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

public class EvokerBook1 extends Item {
    protected float Healup = 100.0F;
    protected float Speedup = 0.2F;
    protected float ManaDamage = 25.0f;
    protected float ManaReply = 3.0f;
    protected float ManaBreakDefence = 0.15f;
    public EvokerBook1(Properties p_41383_) {
        super(p_41383_);
        Utils.HealUp.put(this,Healup);
        Utils.SpeedUp.put(this,Speedup);
        Utils.ManaDamage.put(this,ManaDamage);
        Utils.ManaReply.put(this,ManaReply);
        Utils.ManaBreakDefence.put(this,ManaBreakDefence);
        Utils.OffHandTag.put(this,1f);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.setHoverName(Component.literal("唤魔典籍-I").withStyle(Utils.styleOfMana).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("副手                   ").withStyle(ChatFormatting.GOLD).append(Component.literal("魔导书").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfMana)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfMana,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.EmojiDescriptionMovementSpeed(components,Speedup);
        Compute.EmojiDescriptionMaxHealth(components,Healup);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfMana,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.EmojiDescriptionManaAttackDamage(components,ManaDamage);
        Compute.EmojiDescriptionManaBreakDefence(components,ManaBreakDefence);
        Compute.EmojiDescriptionManaRecover(components,ManaReply);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,Utils.styleOfMana,ChatFormatting.WHITE);
        components.add(Component.literal("Evoker-Book-I").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryII(components);
        super.appendHoverText(stack,level,components,flag);
    }

}
