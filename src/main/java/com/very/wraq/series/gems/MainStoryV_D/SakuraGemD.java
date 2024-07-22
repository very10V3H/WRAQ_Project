package com.very.wraq.series.gems.MainStoryV_D;

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

public class SakuraGemD extends Item {
    private final double HealthSteal = 0.02;
    private final double ManaHealthSteal = 0.02;
    private final double LuckyUp = 0.2;

    public SakuraGemD(Properties p_41383_) {
        super(p_41383_);
        Utils.gemsHealthSteal.put(StringUtils.GemName.SakuraGemD, HealthSteal);
        Utils.gemsManaHealthSteal.put(StringUtils.GemName.SakuraGemD, ManaHealthSteal);
        Utils.gemsLuckyUp.put(StringUtils.GemName.SakuraGemD, LuckyUp);
        Utils.healthSteal.put(this, HealthSteal);
        Utils.manaHealthSteal.put(this, ManaHealthSteal);
        Utils.luckyUp.put(this, LuckyUp);
        Utils.gemsTag.put(this, 1);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Style style = CustomStyle.styleOfSakura;
        components.add(Component.literal("樱花所凝聚而成。").withStyle(style));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
/*        Compute.EmojiDescriptionHealSteal(components,HealthSteal);
        Compute.EmojiDescriptionManaHealSteal(components,ManaHealthSteal);
        Compute.EmojiDescriptionLuckyUp(components,LuckyUp);*/
        Compute.DescriptionDash(components, ChatFormatting.WHITE, style, ChatFormatting.WHITE);
        Compute.SuffixOfMainStoryV(components);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
