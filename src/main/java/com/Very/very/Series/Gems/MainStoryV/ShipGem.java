package com.Very.very.Series.Gems.MainStoryV;

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

public class ShipGem extends Item {
    private final double DefencePenetration = 0;
    private final double ManaPenetration =  0;

    public ShipGem(Properties p_41383_) {
        super(p_41383_);
        Utils.GemsDefencePenetration.put(StringUtils.GemName.ShipGem,DefencePenetration);
        Utils.GemsManaPenetration.put(StringUtils.GemName.ShipGem,ManaPenetration);
        Utils.DefencePenetration.put(this,DefencePenetration);
        Utils.ManaPenetration.put(this,ManaPenetration);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Style style = Utils.styleOfShip;
        components.add(Component.literal("用破损船锚碎片所打造而成。").withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
/*        Compute.EmojiDescriptionDefencePenetration(components,DefencePenetration);
        Compute.EmojiDescriptionManaPenetration(components,ManaPenetration);*/
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.SuffixOfMainStoryV(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
