package com.Very.very.Series.InstanceSeries.Moon;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
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

public class MoonAttackGemD extends Item {
    private final double ManaDefence = 400;
    private final double AttackDamage = 120;

    public MoonAttackGemD(Properties p_41383_) {
        super(p_41383_);
        Utils.GemsManaDefence.put(StringUtils.GemName.MoonAttackGemD,ManaDefence);
        Utils.GemsAttackDamage.put(StringUtils.GemName.MoonAttackGemD,AttackDamage);
        Utils.ManaDefence.put(this,ManaDefence);
        Utils.AttackDamage.put(this,AttackDamage);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Style style = Utils.styleOfMoon;
        components.add(Component.literal("尘月宫明镜之心").withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.SuffixOfMoon(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
