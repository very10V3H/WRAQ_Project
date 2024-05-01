package com.very.wraq.commands.stable;

import com.very.wraq.netWorking.ModNetworking;
import com.very.wraq.netWorking.misc.ItemStackNameS2CPacket;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
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
        if (Utils.MainHandTag.containsKey(itemStack.getItem()) || Utils.ArmorTag.containsKey(itemStack.getItem()))
            Compute.ForgingHoverName(itemStack,Component.literal("").withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD));

        for (ServerPlayer TmpPlayer : list1) {
            ModNetworking.sendToClient(new ItemStackNameS2CPacket(itemStack), TmpPlayer);
            TmpPlayer.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY)
                    .append(Component.literal("维瑞阿契").withStyle(ChatFormatting.AQUA))
                    .append("]").withStyle(ChatFormatting.GRAY)
                    .append(Component.literal(player.getScoreboardName() + "展示了")).append(itemStack.getDisplayName()));
        }

        return 0;
    }
}
