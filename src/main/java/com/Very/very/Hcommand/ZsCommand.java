package com.Very.very.Hcommand;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.StringUtils;
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
        CompoundTag data = player.getPersistentData();
        if (data.getString(StringUtils.Login.Status).equals(StringUtils.Login.Online)) {
            ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            PlayerList list =  player.level().getServer().getPlayerList();
            List<ServerPlayer> list1 = list.getPlayers();
            ListIterator<ServerPlayer> iterator = list1.listIterator();
            while(iterator.hasNext())
            {
                Player TmpPlayer = iterator.next();
                if(!TmpPlayer.level().isClientSide) TmpPlayer.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.GRAY).append(Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA)).append("]").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GRAY).append(Component.literal(player.getScoreboardName()+"展示了")).append(itemStack.getDisplayName()));
            }
        }
        else {
            Compute.FormatMSGSend(player,Component.literal("维瑞阿契").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.AQUA),
                    Component.literal("请先登录。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE));
        }
        return 0;
    }
}
