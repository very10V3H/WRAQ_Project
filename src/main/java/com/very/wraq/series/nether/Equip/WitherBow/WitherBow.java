package com.very.wraq.series.nether.Equip.WitherBow;

import com.very.wraq.projectiles.WraqBow;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.BasicAttributeDescription;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class WitherBow extends WraqBow {
    public final int Num;
    public WitherBow(Properties p_40524_,int Num) {
        super(p_40524_);
        this.Num = Num;
        Utils.AttackDamage.put(this,WitherBowAttributes.BaseDamage[Num]);
        Utils.DefencePenetration0.put(this,WitherBowAttributes.DefencePenetration0[Num]);
        Utils.CritRate.put(this,WitherBowAttributes.CriticalRate[Num]);
        Utils.CritDamage.put(this,WitherBowAttributes.CriticalDamage[Num]);
        Utils.MovementSpeed.put(this,WitherBowAttributes.SpeedUp[Num]);
        Utils.MainHandTag.put(this,1d);
        Utils.WeaponList.add(this);
        Utils.BowTag.put(this,1.0d);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style style = CustomStyle.styleOfWither;
        Compute.ForgingHoverName(stack,Component.literal("凋零长弓" + WitherBowAttributes.Number[Num]).withStyle(style).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("主手                   ").withStyle(ChatFormatting.AQUA).append(Component.literal("长弓").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        BasicAttributeDescription.BasicAttributeCommonDescription(components,stack);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfAddtion(components);
        Compute.DescriptionPassive(components,Component.literal("凋零增幅").withStyle(style));
        components.add(Component.literal("箭矢命中后:"));
        components.add(Component.literal("损失").withStyle(ChatFormatting.RED).
                append(Compute.AttributeDescription.MaxHealth("7%")));
        components.add(Component.literal("获得").withStyle(ChatFormatting.GREEN).
                append(Compute.AttributeDescription.DefencePenetration(WitherBowAttributes.Effect[Num])));
        components.add(Component.literal("持续时间: 5s"));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        components.add(Component.literal("Wither-Bow" + WitherBowAttributes.Number[Num]).withStyle(style).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.UNDERLINE));
        Compute.SuffixOfMainStoryIII(components);
        super.appendHoverText(stack,level,components,flag);
    }


}
