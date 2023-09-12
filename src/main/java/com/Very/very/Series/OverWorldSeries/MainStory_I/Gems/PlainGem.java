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

public class PlainGem extends Item {

    private final float MaxHealth = 100;
    private final float HealthReply = 1;
    public PlainGem(Properties p_41383_) {
        super(p_41383_);
        Utils.GemsHealUp.put("plainGem",MaxHealth);
        Utils.GemsHealReply.put("plainGem",HealthReply);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        components.add(Component.literal("平原意志的具象，凝聚于此石。").withStyle(ChatFormatting.GREEN));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GREEN,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.EmojiDescriptionMaxHealth(components, MaxHealth);
        Compute.EmojiDescriptionHealthRecover(components,HealthReply);
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GREEN,ChatFormatting.WHITE);
        components.add(Component.literal("Plain-Gem-0").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.UNDERLINE));
        components.add(Component.literal("MainStoryI").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
