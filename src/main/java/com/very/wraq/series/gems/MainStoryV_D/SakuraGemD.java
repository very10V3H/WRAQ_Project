package com.very.wraq.series.gems.MainStoryV_D;

import com.very.wraq.render.ToolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.StringUtils;
import com.very.wraq.valueAndTools.Utils.Utils;
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
        Utils.GemsHealthSteal.put(StringUtils.GemName.SakuraGemD,HealthSteal);
        Utils.GemsManaHealthSteal.put(StringUtils.GemName.SakuraGemD,ManaHealthSteal);
        Utils.GemsLuckyUp.put(StringUtils.GemName.SakuraGemD,LuckyUp);
        Utils.HealthSteal.put(this,HealthSteal);
        Utils.ManaHealthSteal.put(this,ManaHealthSteal);
        Utils.LuckyUp.put(this,LuckyUp);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        Style style = CustomStyle.styleOfSakura;
        components.add(Component.literal("樱花所凝聚而成。").withStyle(style));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
/*        Compute.EmojiDescriptionHealSteal(components,HealthSteal);
        Compute.EmojiDescriptionManaHealSteal(components,ManaHealthSteal);
        Compute.EmojiDescriptionLuckyUp(components,LuckyUp);*/
        Compute.DescriptionDash(components,ChatFormatting.WHITE,style,ChatFormatting.WHITE);
        Compute.SuffixOfMainStoryV(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
