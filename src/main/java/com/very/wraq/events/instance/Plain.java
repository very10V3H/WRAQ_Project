package com.very.wraq.events.instance;

import com.very.wraq.events.core.LoginInEvent;
import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.util.Random;

public class Plain {
    public static void SingleRewardToPlayer(Player player, int difficultyEnhanceRate, int playerNum, boolean isMopUp) {
        Random random = new Random();
        Level level = player.level();
        ItemStack itemStack = new ItemStack(Items.AIR);
        if (random.nextDouble() < 0.05 * difficultyEnhanceRate) {
            switch (random.nextInt(4)) {
                case 0 -> itemStack = new ItemStack(ModItems.PlainAttackRing0.get());
                case 1 -> itemStack = new ItemStack(ModItems.PlainManaAttackRing0.get());
                case 2 -> itemStack = new ItemStack(ModItems.PlainHealthRing0.get());
                case 3 -> itemStack = new ItemStack(ModItems.PlainDefenceRing0.get());
            }
        }

        if (!isMopUp) {
            if (random.nextDouble() <= 0.025 * (playerNum - 1) * difficultyEnhanceRate) {
                Compute.sendFormatMSG(player, Component.literal("额外奖励").withStyle(ChatFormatting.LIGHT_PURPLE),
                        Component.literal("你通过组队挑战副本，额外获得了:").withStyle(ChatFormatting.WHITE).
                                append(ModItems.PlainBossSoul.get().getDefaultInstance().getDisplayName()));
                Compute.itemStackGive(player, new ItemStack(ModItems.PlainBossSoul.get(), 1));
            }
        }

        if (LoginInEvent.playerDailyInstanceReward(player, 0)) {
            Compute.sendFormatMSG(player, Component.literal("额外奖励").withStyle(ChatFormatting.LIGHT_PURPLE),
                    Component.literal("每日首次通关副本，额外获得了:").withStyle(ChatFormatting.WHITE).
                            append(ModItems.PlainBossSoul.get().getDefaultInstance().getDisplayName()));

            Compute.itemStackGive(player, new ItemStack(ModItems.PlainBossSoul.get(), 8));

        }

        Compute.givePercentExpToPlayer(player, 0.3 * difficultyEnhanceRate, 0, 20);
        if (!itemStack.is(Items.AIR)) {
            Compute.formatBroad(level, Component.literal("副本").withStyle(ChatFormatting.RED),
                    Component.literal("").
                            append(player.getDisplayName()).
                            append(Component.literal("获得了:").withStyle(ChatFormatting.WHITE)).
                            append(itemStack.getDisplayName()));
            Compute.itemStackGive(player, itemStack);
        }
        Compute.itemStackGive(player, new ItemStack(ModItems.PlainBossSoul.get()));
    }
}
