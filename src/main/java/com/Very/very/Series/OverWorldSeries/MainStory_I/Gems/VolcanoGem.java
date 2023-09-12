package com.Very.very.Series.OverWorldSeries.MainStory_I.Gems;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class VolcanoGem extends Item {
    private final float AttackDamage = 10;
    private final float CritDamage = 0.1f;
    public VolcanoGem(Properties p_41383_) {
        super(p_41383_);
        Utils.GemsAttackDamage.put("volcanoGem",AttackDamage);
        Utils.GemsCritDamage.put("volcanoGem",CritDamage);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        components.add(Component.literal("火山意志的具象，凝聚于此石。").withStyle(ChatFormatting.YELLOW));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.YELLOW,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.EmojiDescriptionBaseAttackDamage(components,AttackDamage);
        Compute.EmojiDescriptionCritDamage(components,CritDamage);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.YELLOW,ChatFormatting.WHITE);
        components.add(Component.literal("Volcano-Gem-0").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.UNDERLINE));
        components.add(Component.literal("MainStoryI").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
