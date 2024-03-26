package com.Very.very.Series.Gems.MainStoryI_D;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class PlainGemD extends Item {

    private final double MaxHealth = 200;
    private final double HealthReply = 10;
    public PlainGemD(Properties p_41383_) {
        super(p_41383_);
        Utils.GemsMaxHealth.put(StringUtils.GemName.PlainGemD,MaxHealth);
        Utils.GemsHealthRecover.put(StringUtils.GemName.PlainGemD,HealthReply);
        Utils.MaxHealth.put(this,MaxHealth);
        Utils.HealthRecover.put(this,HealthReply);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        components.add(Component.literal("平原意志的具象，凝聚于此石。").withStyle(ChatFormatting.GREEN));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GREEN,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
/*        Compute.EmojiDescriptionMaxHealth(components, MaxHealth);
        Compute.EmojiDescriptionHealthRecover(components,HealthReply);*/
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.GREEN,ChatFormatting.WHITE);
        components.add(Component.literal("MainStoryI").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
