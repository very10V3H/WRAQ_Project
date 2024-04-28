package com.very.wraq.series.gems.MainStoryI_D;

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

public class MineGemD extends Item {
    private final double Defence = 200;
    private final double MovementSpeed = - 0.4;

    public MineGemD(Properties p_41383_) {
        super(p_41383_);
        Utils.GemsDefence.put(StringUtils.GemName.MineGemD,Defence);
        Utils.GemsSpeedUp.put(StringUtils.GemName.MineGemD,MovementSpeed);
        Utils.Defence.put(this,Defence);
        Utils.MovementSpeed.put(this,MovementSpeed);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Style style = CustomStyle.styleOfMine;
        components.add(Component.literal("劳动意志的精华，凝聚成此石。").withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
/*        Compute.EmojiDescriptionDefence(components,Defence);
        Compute.EmojiDescriptionMovementSpeed(components,MovementSpeed);*/
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.SuffixOfMainStoryI(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
