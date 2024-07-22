package com.very.wraq.commands.stable.players;

import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.Utils;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ZsCommand implements Command<CommandSourceStack> {
    public static ZsCommand instance = new ZsCommand();


    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        PlayerList list = player.level().getServer().getPlayerList();
        List<ServerPlayer> list1 = list.getPlayers();
        if (Utils.mainHandTag.containsKey(itemStack.getItem()) || Utils.armorTag.containsKey(itemStack.getItem()))
            Compute.forgingHoverName(itemStack);

        for (ServerPlayer TmpPlayer : list1) {
            TmpPlayer.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY)
                    .append(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA))
                    .append("]").withStyle(ChatFormatting.GRAY)
                    .append(Component.literal(player.getScoreboardName() + "展示了")).append(itemStack.getDisplayName()));
        }

        return 0;
    }
}
