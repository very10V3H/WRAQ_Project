package com.Very.very.Series.Gems.MainStoryI;

import com.Very.very.ValueAndTools.BasicAttributeDescription;
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

public class ForestGem extends Item {
    private final double MaxHealth = 150;
    private final double Defence = 25;
    public ForestGem(Properties p_41383_) {
        super(p_41383_);
        Utils.GemsMaxHealth.put("forestGem",MaxHealth);
        Utils.GemsDefence.put("forestGem",Defence);
        Utils.MaxHealth.put(this,MaxHealth);
        Utils.Defence.put(this,Defence);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        components.add(Component.literal("森林意志的具象，凝聚于此石。").withStyle(ChatFormatting.DARK_GREEN));
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.DARK_GREEN,ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
/*        Compute.EmojiDescriptionMaxHealth(components,MaxHealth);
        Compute.EmojiDescriptionDefence(components,Defence);*/
        Compute.DescriptionDash(components,ChatFormatting.WHITE,ChatFormatting.DARK_GREEN,ChatFormatting.WHITE);
        Compute.SuffixOfMainStoryI(components);
        super.appendHoverText(stack,level,components,flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
