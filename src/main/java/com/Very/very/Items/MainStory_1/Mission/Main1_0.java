package com.Very.very.Items.MainStory_1.Mission;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Main1_0 extends Item{
    public Main1_0(Item.Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("MainStoryI").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC).append(Component.literal("围绕前言与维瑞阿契平原展开。").withStyle(ChatFormatting.RESET)));
        components.add(Component.literal("这一章节为起始章节，也是为探索维瑞阿契做前置准备。"));
        components.add(Component.literal("本章作者：").append(Component.literal("very_H").withStyle(ChatFormatting.AQUA)));
        components.add(Component.literal(" "));
        components.add(Component.literal(" "));
        components.add(Component.literal("MainStoryI-O").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
}
