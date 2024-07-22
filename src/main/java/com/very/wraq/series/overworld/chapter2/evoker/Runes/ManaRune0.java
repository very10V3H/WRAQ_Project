package com.very.wraq.series.overworld.chapter2.evoker.Runes;

import com.very.wraq.common.Compute;
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

public class ManaRune0 extends Item {
    public ManaRune0(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        Compute.DescriptionDash(components, ChatFormatting.WHITE, ChatFormatting.LIGHT_PURPLE, ChatFormatting.WHITE);
        components.add(Component.literal("符石属性:"));
        components.add(Component.literal("基于已损失法力值提供").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.movementSpeedWithoutBattle("")));
        Compute.DescriptionDash(components, ChatFormatting.WHITE, ChatFormatting.LIGHT_PURPLE, ChatFormatting.WHITE);
        components.add(Component.literal("Runes-Mana").withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {

        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }
}
