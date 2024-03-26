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

public class ManaKnife extends Item{
    private final double AttackDamage = 40;
    private final double DefencePenetration0 = 400;
    private final double CritRate = 0.2;
    private final double CritDamage = 0.8;
    private final double HealthSteal = 0.04;
    private final double ExpUp = 1;

    public ManaKnife() {
        super(new Properties().rarity(Utils.BloodManaItalic).stacksTo(1));
        Utils.AttackDamage.put(this,AttackDamage);
        Utils.DefencePenetration0.put(this,DefencePenetration0);
        Utils.CritRate.put(this,CritRate);
        Utils.CritDamage.put(this,CritDamage);
        Utils.HealthSteal.put(this,HealthSteal);
        Utils.ExpUp.put(this,ExpUp);
        Utils.OffHandTag.put(this,1d);
        Utils.WeaponList.add(this);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style MainStyle = Utils.styleOfBloodMana;
        stack.setHoverName(Component.literal("猎魔者小刀").withStyle(MainStyle).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("副手                   ").withStyle(ChatFormatting.GOLD).append(Component.literal("小刀").withStyle(ChatFormatting.RESET).withStyle(MainStyle)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,MainStyle,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,MainStyle,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("旧世猎魔遗忆").withStyle(ChatFormatting.RESET).withStyle(MainStyle));
        components.add(Component.literal(" 当你的箭矢命中目标后，为你提供").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.AttackDamage("5%")).
                append(Compute.AttributeDescription.Health("")));
        Compute.DescriptionPassive(components,Component.literal("新世猎魔传技").withStyle(ChatFormatting.RESET).withStyle(MainStyle));
        components.add(Component.literal(" 将你的").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.HealthSteal("")).
                append(Component.literal("以1:8全部转化为").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.CritDamage("")));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,MainStyle,ChatFormatting.WHITE);
        components.add(Component.literal("BloodManaKnife").withStyle(MainStyle).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryV(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

}
