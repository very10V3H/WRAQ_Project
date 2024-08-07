package com.very.wraq.series.gems.instance.Castle;

import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class CastleWeaponGem extends Item {
    private final double DefencePenetration = 0.09;
    private final double ManaPenetration = 0.09;

    public CastleWeaponGem(Properties p_41383_) {
        super(p_41383_);
        Utils.gemsDefencePenetration.put(StringUtils.GemName.CastleWeaponGem, DefencePenetration);
        Utils.gemsManaPenetration.put(StringUtils.GemName.CastleWeaponGem, ManaPenetration);
        Utils.defencePenetration.put(this, DefencePenetration);
        Utils.manaPenetration.put(this, ManaPenetration);
        Utils.gemsTag.put(this, 1);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Style style = CustomStyle.styleOfCastle;
        components.add(Component.literal("暗黑魔能凝聚").withStyle(style));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        components.add(Component.literal(" 仅可用于武器").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.SuffixOfCastle(components);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
