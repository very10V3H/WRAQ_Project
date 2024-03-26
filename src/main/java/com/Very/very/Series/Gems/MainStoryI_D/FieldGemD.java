package com.Very.very.Series.Gems.MainStoryI_D;

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

public class FieldGemD extends Item {
    private final double HealStrength = 0.2;
    private final double MaxHealth = 2000;

    public FieldGemD(Properties p_41383_) {
        super(p_41383_);
        Utils.GemsHealStrength.put(StringUtils.GemName.FieldGemD,HealStrength);
        Utils.GemsMaxHealth.put(StringUtils.GemName.FieldGemD,MaxHealth);
        Utils.HealEffectUp.put(this,HealStrength);
        Utils.MaxHealth.put(this,MaxHealth);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Style style = Utils.styleOfField;
        components.add(Component.literal("原野意志的具象，凝聚成此石。").withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.SuffixOfMainStoryI(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
