package com.very.wraq.series.overworld.chapter1.plain.runes;

import com.very.wraq.common.util.ComponentUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Profile extends Item {
    public Profile(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        stack.setHoverName(Component.literal("符石简介").withStyle(ChatFormatting.GREEN));
        components.add(Component.literal(" "));
        ComponentUtils.DescriptionDash(components, ChatFormatting.WHITE, ChatFormatting.GREEN, ChatFormatting.WHITE);
        components.add(Component.literal("符石是维瑞阿契中，被动为你增加各种属性的物品。"));
        components.add(Component.literal("在维瑞阿契的世界中，有多种类型符石。"));
        components.add(Component.literal("但是，请注意，同色(同类型)的符石在同一时间至多存在一个效果。"));
        ComponentUtils.DescriptionDash(components, ChatFormatting.WHITE, ChatFormatting.GREEN, ChatFormatting.WHITE);
        components.add(Component.literal(" "));
        components.add(Component.literal("Runes").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
}
