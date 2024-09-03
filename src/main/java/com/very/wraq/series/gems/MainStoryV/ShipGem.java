package com.very.wraq.series.gems.MainStoryV;

import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.util.StringUtils;
import com.very.wraq.common.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class ShipGem extends Item {
    private final double DefencePenetration = 0;
    private final double ManaPenetration = 0;

    public ShipGem(Properties p_41383_) {
        super(p_41383_);
        Utils.gemsDefencePenetration.put(StringUtils.GemName.ShipGem, DefencePenetration);
        Utils.gemsManaPenetration.put(StringUtils.GemName.ShipGem, ManaPenetration);
        Utils.defencePenetration.put(this, DefencePenetration);
        Utils.manaPenetration.put(this, ManaPenetration);
        Utils.gemsTag.put(this, 1);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Style style = CustomStyle.styleOfShip;
        components.add(Component.literal("用破损船锚碎片所打造而成。").withStyle(style));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
/*        Compute.EmojiDescriptionDefencePenetration(components,DefencePenetration);
        Compute.EmojiDescriptionManaPenetration(components,ManaPenetration);*/
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.SuffixOfMainStoryV(components);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
