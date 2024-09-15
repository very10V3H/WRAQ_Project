package com.very.wraq.series.gems.MainStoryI;

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

public class MineGem extends Item {
    private final double Defence = 100;
    private final double MovementSpeed = -0.2;

    public MineGem(Properties p_41383_) {
        super(p_41383_);
        Utils.gemsDefence.put(StringUtils.GemName.MineGem, Defence);
        Utils.gemsSpeedUp.put(StringUtils.GemName.MineGem, MovementSpeed);
        Utils.defence.put(this, Defence);
        Utils.movementSpeedWithoutBattle.put(this, MovementSpeed);
        Utils.gemsTag.put(this, 1);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Style style = CustomStyle.styleOfMine;
        components.add(Component.literal("劳动意志的精华，凝聚成此石。").withStyle(style));
        ComponentUtils.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.DescriptionOfBasic(components);
/*        Compute.EmojiDescriptionDefence(components,Defence);
        Compute.EmojiDescriptionMovementSpeed(components,MovementSpeed);*/
        ComponentUtils.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.suffixOfChapterI(components);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
