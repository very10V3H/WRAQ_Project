package com.Very.very.Items.LevelReward.VariousBag;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BackPackTicket extends Item {

    public BackPackTicket(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("于维瑞库尤交易所兑换背包。").withStyle(ChatFormatting.UNDERLINE).withStyle(ChatFormatting.GOLD));
        components.add(Component.literal(" "));
        components.add(Component.literal("Items-BackPack").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA));
        super.appendHoverText(itemStack, level, components, flag);
    }

}
