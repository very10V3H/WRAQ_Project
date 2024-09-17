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

public class ForestGem extends Item {
    private final double MaxHealth = 150;
    private final double Defence = 25;

    public ForestGem(Properties p_41383_) {
        super(p_41383_);
        Utils.gemsMaxHealth.put("forestGem", MaxHealth);
        Utils.gemsDefence.put("forestGem", Defence);
        Utils.maxHealth.put(this, MaxHealth);
        Utils.defence.put(this, Defence);
        Utils.gemsTag.put(this, 1);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        components.add(Component.literal("森林意志的具象，凝聚于此石。").withStyle(ChatFormatting.DARK_GREEN));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.DARK_GREEN, ChatFormatting.WHITE);
        ComponentUtils.descriptionOfBasic(components);
/*        Compute.EmojiDescriptionMaxHealth(components,MaxHealth);
        Compute.EmojiDescriptionDefence(components,Defence);*/
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.DARK_GREEN, ChatFormatting.WHITE);
        ComponentUtils.suffixOfChapterI(components);
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
