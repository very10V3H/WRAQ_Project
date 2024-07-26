package com.very.wraq.events.instance;

import com.very.wraq.events.core.LoginInEvent;
import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Random;

public class Devil {
    public static void SingleRewardToPlayer(Player player, int difficultyEnhanceRate, int playerNum, boolean isMopUp) {
        Random random = new Random();
        if (random.nextDouble() <= 0.25 * difficultyEnhanceRate) {
            Compute.itemStackGive(player, new ItemStack(ModItems.DevilLoot.get(), 1));
        }
        if (!isMopUp) {
            if (random.nextDouble() <= 0.025 * (playerNum - 1) * difficultyEnhanceRate) {
                Compute.sendFormatMSG(player, Component.literal("额外奖励").withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal("你通过组队挑战副本，额外获得了:").withStyle(ChatFormatting.WHITE).
                                append(ModItems.DevilLoot.get().getDefaultInstance().getDisplayName()));
                Compute.itemStackGive(player, new ItemStack(ModItems.DevilLoot.get(), 1));
            }
        }
        if (LoginInEvent.playerDailyInstanceReward(player, 6)) {
            Compute.sendFormatMSG(player, Component.literal("额外奖励").withStyle(ChatFormatting.LIGHT_PURPLE),
                    Component.literal("每日首次通关副本，额外获得了:").withStyle(ChatFormatting.WHITE).
                            append(ModItems.DevilLoot.get().getDefaultInstance().getDisplayName()));
            Compute.itemStackGive(player, new ItemStack(ModItems.DevilLoot.get(), 6));
        }
    }
}












