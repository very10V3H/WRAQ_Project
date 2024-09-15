package com.very.wraq.series.gems.MainStoryI;

import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.StringUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class FieldGem extends Item {

    private final double HealStrength = 0.1;
    private final double MaxHealth = 1000;

    public FieldGem(Properties p_41383_) {
        super(p_41383_);
        Utils.gemsHealStrength.put(StringUtils.GemName.FieldGem, HealStrength);
        Utils.gemsMaxHealth.put(StringUtils.GemName.FieldGem, MaxHealth);
        Utils.healEffectUp.put(this, HealStrength);
        Utils.maxHealth.put(this, MaxHealth);
        Utils.gemsTag.put(this, 1);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Style style = CustomStyle.styleOfField;
        components.add(Component.literal("原野意志的具象，凝聚成此石。").withStyle(style));
        ComponentUtils.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.DescriptionOfBasic(components);
        ComponentUtils.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.suffixOfChapterI(components);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
