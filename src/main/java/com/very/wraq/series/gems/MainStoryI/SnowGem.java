package com.very.wraq.series.gems.MainStoryI;

import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class SnowGem extends Item {
    private final double CritRate = 0.05f;
    private final double CritDamage = 0.05f;

    public SnowGem(Properties p_41383_) {
        super(p_41383_);
        Utils.gemsCritRate.put("snowGem", CritRate);
        Utils.gemsCritDamage.put("snowGem", CritDamage);
        Utils.critRate.put(this, CritRate);
        Utils.critDamage.put(this, CritDamage);
        Utils.gemsTag.put(this, 1);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        stack.getOrCreateTagElement(Utils.MOD_ID);
        components.add(Component.literal("冰川意志的具象，凝聚于此石。").withStyle(ChatFormatting.AQUA));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, ChatFormatting.DARK_AQUA, ChatFormatting.WHITE);
        Compute.DescriptionOfBasic(components);
        Compute.DescriptionDash(components, ChatFormatting.WHITE, ChatFormatting.DARK_AQUA, ChatFormatting.WHITE);
        components.add(Component.literal("MainStoryI").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
