package com.very.wraq.series.gems.MainStoryIII_D;

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

public class PiglinGemD extends Item {
    private final double ExpUp = 0.4;
    private final double HealthRecover = -10;

    public PiglinGemD(Properties p_41383_) {
        super(p_41383_);
        Utils.gemsExpUp.put(StringUtils.GemName.PiglinGemD, ExpUp);
        Utils.gemsHealthRecover.put(StringUtils.GemName.PiglinGemD, HealthRecover);
        Utils.expUp.put(this, ExpUp);
        Utils.healthRecover.put(this, HealthRecover);
        Utils.gemsTag.put(this, 1);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Style style = CustomStyle.styleOfGold;
        components.add(Component.literal("用猪灵之好所打造而成。").withStyle(style));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
/*        Compute.EmojiDescriptionExpUp(components,ExpUp);
        Compute.EmojiDescriptionHealthRecover(components,HealthRecover);*/
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.SuffixOfMainStoryIII(components);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
