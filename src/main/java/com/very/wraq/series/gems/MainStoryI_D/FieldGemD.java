package com.very.wraq.series.gems.MainStoryI_D;

import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.StringUtils;
import com.very.wraq.common.Utils.Utils;
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
        Utils.gemsHealStrength.put(StringUtils.GemName.FieldGemD, HealStrength);
        Utils.gemsMaxHealth.put(StringUtils.GemName.FieldGemD, MaxHealth);
        Utils.healEffectUp.put(this, HealStrength);
        Utils.maxHealth.put(this, MaxHealth);
        Utils.gemsTag.put(this, 1);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Style style = CustomStyle.styleOfField;
        components.add(Component.literal("原野意志的具象，凝聚成此石。").withStyle(style));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.SuffixOfMainStoryI(components);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
