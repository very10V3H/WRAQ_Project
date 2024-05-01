package com.very.wraq.series.overWorld.MainStory_I.Snow;

import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class SnowShield extends Item{
    private final double Defence = 175;
    private final double MaxHealth = 350;
    private final double AttackDamage = 10;
    private final double CritRate = 0.05;
    private final double ExpUp = 0.75;

    public SnowShield() {
        super(new Properties().rarity(Utils.SnowItalic).stacksTo(1));
        Utils.Defence.put(this,Defence);
        Utils.MaxHealth.put(this,MaxHealth);
        Utils.AttackDamage.put(this,AttackDamage);
        Utils.CritRate.put(this,CritRate);
        Utils.ExpUp.put(this,ExpUp);
        Utils.OffHandTag.put(this,1d);
        Utils.WeaponList.add(this);
        Utils.ShieldTag.put(this,1d);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        Style MainStyle = CustomStyle.styleOfSnow;
        stack.setHoverName(Component.literal("玉山圆盾").withStyle(MainStyle).withStyle(ChatFormatting.BOLD));
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
        Compute.DescriptionPassive(components,Component.literal("破碎冰玉").withStyle(MainStyle));
        components.add(Component.literal(" 造成暴击后，击碎目标").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.Defence("25%")).
                append(Component.literal("并提升自身等额").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.Defence("")).
                append(Component.literal(" 持续2s").withStyle(ChatFormatting.WHITE)));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,MainStyle,ChatFormatting.WHITE);
        components.add(Component.literal("SnowShield").withStyle(MainStyle).withStyle(ChatFormatting.ITALIC));
        Compute.SuffixOfMainStoryI(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

}
