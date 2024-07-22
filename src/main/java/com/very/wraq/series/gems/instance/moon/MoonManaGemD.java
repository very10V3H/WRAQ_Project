package com.very.wraq.series.gems.instance.moon;

import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Utils;
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

public class MoonManaGemD extends Item {
    private final double Defence = 400;
    private final double ManaDamage = 240;

    public MoonManaGemD(Properties p_41383_) {
        super(p_41383_);
        Utils.gemsDefence.put(StringUtils.GemName.MoonManaGemD, Defence);
        Utils.gemsManaDamage.put(StringUtils.GemName.MoonManaGemD, ManaDamage);
        Utils.defence.put(this, Defence);
        Utils.manaDamage.put(this, ManaDamage);
        Utils.gemsTag.put(this, 1);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Style style = CustomStyle.styleOfMoon1;
        components.add(Component.literal("尘月宫天镜之心").withStyle(style));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.SuffixOfMoon(components);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
