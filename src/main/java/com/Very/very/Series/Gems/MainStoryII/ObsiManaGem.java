package com.Very.very.Series.Gems.MainStoryII;

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

public class ObsiManaGem extends Item {
    private final double ManaDamage = 100;
    private final double CoolDown = 0.05;

    public ObsiManaGem(Properties p_41383_) {
        super(p_41383_);
        Utils.GemsManaDamage.put(StringUtils.GemName.ObsiManaGem,ManaDamage);
        Utils.GemsCoolDown.put(StringUtils.GemName.ObsiManaGem,CoolDown);
        Utils.ManaDamage.put(this,ManaDamage);
        Utils.CoolDownDecrease.put(this,CoolDown);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Style style = Utils.styleOfMana;
        components.add(Component.literal("湖泊与火山的意志具象，凝聚成此石。").withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
/*        Compute.EmojiDescriptionManaAttackDamage(components,ManaDamage);
        Compute.EmojiDescriptionCoolDown(components,CoolDown);*/
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.SuffixOfMainStoryII(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
