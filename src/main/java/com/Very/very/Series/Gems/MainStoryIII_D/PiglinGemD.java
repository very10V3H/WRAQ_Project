package com.Very.very.Series.Gems.MainStoryIII_D;

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

public class PiglinGemD extends Item {
    private final double ExpUp = 0.4;
    private final double HealthRecover = - 10;

    public PiglinGemD(Properties p_41383_) {
        super(p_41383_);
        Utils.GemsExpUp.put(StringUtils.GemName.PiglinGemD,ExpUp);
        Utils.GemsHealthRecover.put(StringUtils.GemName.PiglinGemD,HealthRecover);
        Utils.ExpUp.put(this,ExpUp);
        Utils.HealthRecover.put(this,HealthRecover);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Style style = Utils.styleOfGold;
        components.add(Component.literal("用猪灵之好所打造而成。").withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
/*        Compute.EmojiDescriptionExpUp(components,ExpUp);
        Compute.EmojiDescriptionHealthRecover(components,HealthRecover);*/
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.SuffixOfMainStoryIII(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
