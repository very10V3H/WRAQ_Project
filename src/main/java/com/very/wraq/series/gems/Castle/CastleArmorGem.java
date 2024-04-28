package com.very.wraq.series.gems.Castle;

import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class CastleArmorGem extends Item {
    private final double DefencePenetration0 = 750;
    private final double ManaPenetration0 = 750;

    public CastleArmorGem(Properties p_41383_) {
        super(p_41383_);
        Utils.GemsDefencePenetration0.put(StringUtils.GemName.CastleArmorGem,DefencePenetration0);
        Utils.GemsManaPenetration0.put(StringUtils.GemName.CastleArmorGem,ManaPenetration0);
        Utils.DefencePenetration0.put(this,DefencePenetration0);
        Utils.ManaPenetration0.put(this,ManaPenetration0);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Style style = CustomStyle.styleOfCastle;
        components.add(Component.literal("暗黑蕴魔注能").withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        components.add(Component.literal(" 仅可用于防具").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.SuffixOfCastle(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
