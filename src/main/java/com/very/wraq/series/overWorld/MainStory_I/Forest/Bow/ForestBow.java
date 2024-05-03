package com.very.wraq.series.overWorld.MainStory_I.Forest.Bow;

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

public class ForestBow extends WraqBow {
    private final int Num;
    public ForestBow(Properties p_40524_, int Num) {
        super(p_40524_);
        this.Num = Num;
        Utils.AttackDamage.put(this, ForestBowAttributes.BaseDamage[Num]);
        Utils.DefencePenetration0.put(this,ForestBowAttributes.DefencePenetration0[Num]);
        Utils.CritRate.put(this,ForestBowAttributes.CriticalRate[Num]);
        Utils.CritDamage.put(this,ForestBowAttributes.CriticalDamage[Num]);
        Utils.MovementSpeed.put(this,ForestBowAttributes.SpeedUp[Num]);
        Element.LifeElementValue.put(this, ForestBowAttributes.LifeElementValue[Num]);
        Utils.MainHandTag.put(this,1d);
        Utils.WeaponList.add(this);
        Utils.BowTag.put(this,1.0d);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("森林长弓" + ForestBowAttributes.Number[Num]).withStyle(ChatFormatting.DARK_GREEN).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("长弓").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.DARK_GREEN,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.DARK_GREEN,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("森林祝福").withStyle(ChatFormatting.DARK_GREEN));
        components.add(Component.literal("箭矢命中目标后治疗自身").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Health("5%")));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GREEN,ChatFormatting.WHITE);
        components.add(Component.literal("Forest-Bow" + ForestBowAttributes.Number[Num]).withStyle(ChatFormatting.DARK_GREEN).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryI(components);
        super.appendHoverText(stack,level,components,flag);
    }

}
