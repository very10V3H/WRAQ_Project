package com.Very.very.Series.Gems.MainStoryII_D;

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

public class LifeManaGemD extends Item {
    private final double ManaRecover = 2;
    private final double ManaHealSteal = 0.02;

    public LifeManaGemD(Properties p_41383_) {
        super(p_41383_);
        Utils.GemsManaRecover.put(StringUtils.GemName.LifeManaGemD,ManaRecover);
        Utils.GemsManaHealthSteal.put(StringUtils.GemName.LifeManaGemD,ManaHealSteal);
        Utils.ManaRecover.put(this,ManaRecover);
        Utils.ManaHealthSteal.put(this,ManaHealSteal);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Style style = Utils.styleOfHealth;
        components.add(Component.literal("平原和森林的意志具象，凝聚成此石。").withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
/*        Compute.EmojiDescriptionManaRecover(components,ManaRecover);
        Compute.EmojiDescriptionManaHealSteal(components,ManaHealSteal);*/
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.SuffixOfMainStoryII(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
