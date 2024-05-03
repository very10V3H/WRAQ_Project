package com.very.wraq.series.overWorld.MainStory_I.Volcano.Bow;

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

public class VolcanoBow extends WraqBow {
    private final int Num;
    public VolcanoBow(Properties p_40524_, int Num) {
        super(p_40524_);
        this.Num = Num;
        Utils.AttackDamage.put(this, VolcanoBowAttributes.BaseDamage[Num]);
        Utils.DefencePenetration0.put(this,VolcanoBowAttributes.DefencePenetration0[Num]);
        Utils.CritRate.put(this,VolcanoBowAttributes.CriticalRate[Num]);
        Utils.CritDamage.put(this,VolcanoBowAttributes.CriticalDamage[Num]);
        Utils.MovementSpeed.put(this,VolcanoBowAttributes.SpeedUp[Num]);
        Element.FireElementValue.put(this, VolcanoBowAttributes.FireElementValue[Num]);
        Utils.MainHandTag.put(this,1d);
        Utils.WeaponList.add(this);
        Utils.BowTag.put(this,1.0d);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Compute.ForgingHoverName(stack,Component.literal("熔岩长弓" + VolcanoBowAttributes.Number[Num]).withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("长弓").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.YELLOW,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.YELLOW,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("熔岩涌动").withStyle(ChatFormatting.YELLOW));
        components.add(Component.literal("箭矢命中目标后获得持续2.5s的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.DefencePenetration("40%")));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.YELLOW,ChatFormatting.WHITE);
        components.add(Component.literal("Volcano-Bow" + VolcanoBowAttributes.Number[Num]).withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryI(components);
        super.appendHoverText(stack,level,components,flag);
    }
}
