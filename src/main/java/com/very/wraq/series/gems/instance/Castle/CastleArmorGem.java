package com.very.wraq.series.gems.instance.Castle;

import com.very.wraq.common.Compute;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.StringUtils;
import com.very.wraq.common.util.Utils;
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

public class CastleArmorGem extends Item {
    private final double DefencePenetration0 = 750;
    private final double ManaPenetration0 = 750;

    public CastleArmorGem(Properties p_41383_) {
        super(p_41383_);
        Utils.gemsDefencePenetration0.put(StringUtils.GemName.CastleArmorGem, DefencePenetration0);
        Utils.gemsManaPenetration0.put(StringUtils.GemName.CastleArmorGem, ManaPenetration0);
        Utils.defencePenetration0.put(this, DefencePenetration0);
        Utils.manaPenetration0.put(this, ManaPenetration0);
        Utils.gemsTag.put(this, 1);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Style style = CustomStyle.styleOfCastle;
        components.add(Component.literal("暗黑蕴魔注能").withStyle(style));
        ComponentUtils.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.DescriptionOfBasic(components);
        components.add(Component.literal(" 仅可用于防具").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        ComponentUtils.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.SuffixOfCastle(components);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
