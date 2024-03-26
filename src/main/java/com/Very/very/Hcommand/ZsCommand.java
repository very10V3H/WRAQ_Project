package com.Very.very.Hcommand;

import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.NetWorking.Packets.ItemStackNameS2CPacket;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.ListIterator;

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
                    .append(Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA))
                    .append("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY)
                    .append(Component.literal(player.getScoreboardName() + "展示了")).append(itemStack.getDisplayName()));
        }

        return 0;
    }
}
