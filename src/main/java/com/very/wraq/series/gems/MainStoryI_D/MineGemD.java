package com.very.wraq.series.gems.MainStoryI_D;

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

public class MineGemD extends Item {
    private final double Defence = 200;
    private final double MovementSpeed = -0.4;

    public MineGemD(Properties p_41383_) {
        super(p_41383_);
        Utils.gemsDefence.put(StringUtils.GemName.MineGemD, Defence);
        Utils.gemsSpeedUp.put(StringUtils.GemName.MineGemD, MovementSpeed);
        Utils.defence.put(this, Defence);
        Utils.movementSpeedWithoutBattle.put(this, MovementSpeed);
        Utils.gemsTag.put(this, 1);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Style style = CustomStyle.styleOfMine;
        components.add(Component.literal("劳动意志的精华，凝聚成此石。").withStyle(style));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
/*        Compute.EmojiDescriptionDefence(components,Defence);
        Compute.EmojiDescriptionMovementSpeed(components,MovementSpeed);*/
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.SuffixOfMainStoryI(components);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
