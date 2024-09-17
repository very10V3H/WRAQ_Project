package com.very.wraq.series.gems.MainStoryII;

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

public class LifeManaGem extends Item {
    private final double ManaRecover = 1;
    private final double ManaHealSteal = 0.01;

    public LifeManaGem(Properties p_41383_) {
        super(p_41383_);
        Utils.gemsManaRecover.put(StringUtils.GemName.LifeManaGem, ManaRecover);
        Utils.gemsManaHealthSteal.put(StringUtils.GemName.LifeManaGem, ManaHealSteal);
        Utils.manaRecover.put(this, ManaRecover);
        Utils.manaHealthSteal.put(this, ManaHealSteal);
        Utils.gemsTag.put(this, 1);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Style style = CustomStyle.styleOfHealth;
        components.add(Component.literal("平原和森林的意志具象，凝聚成此石。").withStyle(style));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
/*        Compute.EmojiDescriptionManaRecover(components,ManaRecover);
        Compute.EmojiDescriptionManaHealSteal(components,ManaHealSteal);*/
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        ComponentUtils.suffixOfChapterII(components);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
