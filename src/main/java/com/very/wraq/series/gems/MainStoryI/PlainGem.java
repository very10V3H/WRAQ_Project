package com.very.wraq.series.gems.MainStoryI;

import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class PlainGem extends Item {

    private final double MaxHealth = 100;
    private final double HealthReply = 5;

    public PlainGem(Properties p_41383_) {
        super(p_41383_);
        Utils.gemsMaxHealth.put("plainGem", MaxHealth);
        Utils.gemsHealthRecover.put("plainGem", HealthReply);
        Utils.maxHealth.put(this, MaxHealth);
        Utils.healthRecover.put(this, HealthReply);
        Utils.gemsTag.put(this, 1);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        components.add(Component.literal("平原意志的具象，凝聚于此石。").withStyle(ChatFormatting.GREEN));
        ComponentUtils.DescriptionDash(components, ChatFormatting.WHITE, ChatFormatting.GREEN, ChatFormatting.WHITE);
        ComponentUtils.DescriptionOfBasic(components);
/*        Compute.EmojiDescriptionMaxHealth(components, MaxHealth);
        Compute.EmojiDescriptionHealthRecover(components,HealthReply);*/
        ComponentUtils.DescriptionDash(components, ChatFormatting.WHITE, ChatFormatting.GREEN, ChatFormatting.WHITE);
        components.add(Component.literal("MainStoryI").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
