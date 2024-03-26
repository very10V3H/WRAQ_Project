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

public class MagmaGemD extends Item {
    private final double ManaPenetration0 = 200;
    private final double MaxHealth = - 1000;

    public MagmaGemD(Properties p_41383_) {
        super(p_41383_);
        Utils.GemsManaPenetration0.put(StringUtils.GemName.MagmaGemD,ManaPenetration0);
        Utils.GemsMaxHealth.put(StringUtils.GemName.MagmaGemD,MaxHealth);
        Utils.ManaPenetration0.put(this,ManaPenetration0);
        Utils.MaxHealth.put(this,MaxHealth);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Style style = Utils.styleOfPower;
        components.add(Component.literal("用封装下界熔岩能量所打造而成。").withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
/*        Compute.EmojiDescriptionManaPenetration0(components,ManaPenetration0);
        Compute.EmojiDescriptionMaxHealth(components,MaxHealth);*/
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.SuffixOfMainStoryIII(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
