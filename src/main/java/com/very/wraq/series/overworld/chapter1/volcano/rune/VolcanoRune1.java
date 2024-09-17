package com.very.wraq.series.overworld.chapter1.volcano.rune;

import com.very.wraq.common.Compute;
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

public class VolcanoRune1 extends Item {
    public VolcanoRune1(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.YELLOW, ChatFormatting.WHITE);
        components.add(Component.literal("符石属性:"));
        components.add(Component.literal("根据已损失生命值提供至多").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ExAttackDamage("25%")));
        ComponentUtils.descriptionDash(components, ChatFormatting.WHITE, ChatFormatting.YELLOW, ChatFormatting.WHITE);
        components.add(Component.literal("Runes-III").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {

        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }
}
