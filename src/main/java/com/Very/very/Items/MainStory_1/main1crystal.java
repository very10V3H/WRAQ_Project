package com.Very.very.Items.MainStory_1;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class main1crystal extends Item {

    public main1crystal(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("·材料").withStyle(ChatFormatting.AQUA));
        components.add(Component.literal(" "));
        components.add(Component.literal("天空城下的").withStyle(ChatFormatting.AQUA));
        components.add(Component.literal(" "));
        components.add(Component.literal("————风————").withStyle(ChatFormatting.GREEN));
        components.add(Component.literal("————林————").withStyle(ChatFormatting.DARK_GREEN));
        components.add(Component.literal("————露————").withStyle(ChatFormatting.BLUE));
        components.add(Component.literal("————火————").withStyle(ChatFormatting.YELLOW));
        components.add(Component.literal(" "));
        components.add(Component.literal("MainStoryI-Fin.").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
