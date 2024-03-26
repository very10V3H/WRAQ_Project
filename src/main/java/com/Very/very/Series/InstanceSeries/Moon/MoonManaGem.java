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

public class MoonManaGem extends Item {
    private final double Defence = 200;
    private final double ManaDamage = 240;

    public MoonManaGem(Properties p_41383_) {
        super(p_41383_);
        Utils.GemsDefence.put(StringUtils.GemName.MoonManaGem,Defence);
        Utils.GemsManaDamage.put(StringUtils.GemName.MoonManaGem,ManaDamage);
        Utils.Defence.put(this,Defence);
        Utils.ManaDamage.put(this,ManaDamage);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Style style = Utils.styleOfMoon1;
        components.add(Component.literal("尘月宫天镜之心").withStyle(style));
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
