package com.very.wraq.series.overWorld.SakuraSeries.Boss2;

import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class GoldShield extends Item{
    private final double Defence = 500;
    private final double MaxHealth = 500;
    private final double AttackDamage = 50;
    private final double CritDamage = 0.15;
    private final double ExpUp = 1.25;

    public GoldShield() {
        super(new Properties().rarity(Utils.GoldItalic).stacksTo(1));
        Utils.Defence.put(this,Defence);
        Utils.MaxHealth.put(this,MaxHealth);
        Utils.AttackDamage.put(this,AttackDamage);
        Utils.CritDamage.put(this,CritDamage);
        Utils.ExpUp.put(this,ExpUp);
        Utils.OffHandTag.put(this,1d);
        Utils.WeaponList.add(this);
        Utils.ShieldTag.put(this,1d);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style MainStyle = CustomStyle.styleOfGold;
        stack.setHoverName(Component.literal("黄金圣盾").withStyle(MainStyle).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("副手                   ").withStyle(ChatFormatting.GOLD).append(Component.literal("手盾").withStyle(ChatFormatting.GRAY)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,MainStyle,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,MainStyle,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("盾击").withStyle(ChatFormatting.GRAY));
        components.add(Component.literal(" 基于").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Defence("")).
                append(Component.literal("为你至多提供").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("100%近战攻击增幅").withStyle(CustomStyle.styleOfPower)));
        Compute.DescriptionPassive(components,Component.literal("华贵金属").withStyle(MainStyle));
        components.add(Component.literal(" 每").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Defence("200")).
                append(Component.literal("将为你提供").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.CritRate("1%")).
                append(Component.literal("，当你的拥有高于").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.CritRate("100%")).
                append(Component.literal("，使溢出部分以1:2转化为").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.CritDamage("")));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,MainStyle,ChatFormatting.WHITE);
        components.add(Component.literal("GoldenShield").withStyle(MainStyle).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryV(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

}
