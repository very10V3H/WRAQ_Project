package com.very.wraq.series.overworld.chapter1.volcano.rune;

import com.very.wraq.common.util.ComponentUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class VolcanoRune0 extends Item {
    public VolcanoRune0(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.YELLOW, ChatFormatting.WHITE);
        components.add(Component.literal("符石属性:"));
        components.add(Component.literal("当你击杀一个单位时，回复你12%已损失生命值，并提供一个银币。"));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.YELLOW, ChatFormatting.WHITE);
        components.add(Component.literal("Runes-III").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }
}
