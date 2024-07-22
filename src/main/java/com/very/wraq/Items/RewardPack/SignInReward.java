package com.very.wraq.Items.RewardPack;

import com.very.wraq.common.Compute;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SignInReward extends Item {
    public SignInReward(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        player.playSound(SoundEvents.PLAYER_LEVELUP);
        if (!level.isClientSide && interactionHand.equals(InteractionHand.MAIN_HAND)) {
            Compute.playerItemUseWithRecord(player);
        }
        return InteractionResultHolder.pass(player.getItemInHand(interactionHand));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        components.add(Component.literal("右键打开奖励包!"));
        components.add(Component.literal("奖池:").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.BOLD));
        components.add(Component.literal("3*").append(Component.literal("金币").withStyle(ChatFormatting.GOLD)).append(" 5%"));
        components.add(Component.literal("2*").append(Component.literal("金币").withStyle(ChatFormatting.GOLD)).append(" 10%"));
        components.add(Component.literal("1*").append(Component.literal("金币").withStyle(ChatFormatting.GOLD)).append(" 15%"));
        components.add(Component.literal("5*").append(Component.literal("银币").withStyle(ChatFormatting.GRAY)).append(" 70%"));
        components.add(Component.literal(" "));
        components.add(Component.literal(" "));
        components.add(Component.literal("SignAward").withStyle(ChatFormatting.AQUA).withStyle(ChatFormatting.ITALIC));
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
}
