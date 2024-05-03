package com.very.wraq.series.overWorld.MainStory_I.Plain.Bow;

import com.very.wraq.process.element.Element;
import com.very.wraq.projectiles.WraqBow;
import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class PlainBow extends WraqBow {
    private final int Num;
    public PlainBow(Properties p_40524_, int Num) {
        super(p_40524_);
        this.Num = Num;
        Utils.AttackDamage.put(this,PlainBowAttributes.BaseDamage[Num]);
        Utils.DefencePenetration0.put(this,PlainBowAttributes.DefencePenetration0[Num]);
        Utils.CritRate.put(this,PlainBowAttributes.CriticalRate[Num]);
        Utils.CritDamage.put(this,PlainBowAttributes.CriticalDamage[Num]);
        Utils.MovementSpeed.put(this,PlainBowAttributes.SpeedUp[Num]);
        Element.LifeElementValue.put(this, PlainBowAttributes.LifeElementValue[Num]);
        Utils.MainHandTag.put(this,1d);
        Utils.WeaponList.add(this);
        Utils.BowTag.put(this,1.0d);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("平原长弓" + PlainBowAttributes.Number[Num]).withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("长弓").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GREEN,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GREEN,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("平原祝福").withStyle(ChatFormatting.GREEN));
        components.add(Component.literal("箭矢命中目标后提供持续2s的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MovementSpeed("40%")));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GREEN,ChatFormatting.WHITE);
        components.add(Component.literal("Plain-Bow" + PlainBowAttributes.Number[Num]).withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryI(components);
        super.appendHoverText(stack,level,components,flag);
    }
}
