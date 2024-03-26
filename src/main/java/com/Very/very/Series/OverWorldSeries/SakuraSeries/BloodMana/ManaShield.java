package com.Very.very.Series.OverWorldSeries.SakuraSeries.BloodMana;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class ManaShield extends Item{
    private final double Defence = 150;
    private final double ManaDefence = 300;
    private final double MaxHealth = 300;
    private final double AttackDamage = 40;
    private final double ManaDamage = 160;
    private final double HealthSteal = 0.04;
    private final double ExpUp = 1;

    public ManaShield() {
        super(new Properties().rarity(Utils.BloodManaItalic).stacksTo(1));
        Utils.Defence.put(this,Defence);
        Utils.ManaDefence.put(this,ManaDefence);
        Utils.MaxHealth.put(this,MaxHealth);
        Utils.AttackDamage.put(this,AttackDamage);
        Utils.ManaDamage.put(this,ManaDamage);
        Utils.HealthSteal.put(this,HealthSteal);
        Utils.ExpUp.put(this,ExpUp);
        Utils.OffHandTag.put(this,1d);
        Utils.WeaponList.add(this);
        Utils.ShieldTag.put(this,1d);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style MainStyle = Utils.styleOfBloodMana;
        stack.setHoverName(Component.literal("封魔者法盾").withStyle(MainStyle).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("副手                   ").withStyle(ChatFormatting.GOLD).append(Component.literal("手盾").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,MainStyle,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,MainStyle,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("盾击").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY));
        components.add(Component.literal("每").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Defence("100")).
                append(Component.literal("，将为你提供10%近战攻击增幅。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionPassive(components,Component.literal("旧世封魔遗志").withStyle(ChatFormatting.RESET).withStyle(MainStyle));
        components.add(Component.literal(" 当拥有高于").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Health("50%")).
                append(Component.literal("时，将你的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.HealthSteal("")).
                append(Component.literal("以1:15全部转化为").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.CritDamage("")));
        components.add(Component.literal(" 当拥有低于").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Health("50%")).
                append(Component.literal("时，将你的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.CritDamage("")).
                append(Component.literal("以15:1全部转化为").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.HealthSteal("")));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,MainStyle,ChatFormatting.WHITE);
        components.add(Component.literal("BloodManaShield").withStyle(MainStyle).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryV(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

}
