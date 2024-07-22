package com.very.wraq.commands.stable.ops;

import com.very.wraq.events.core.LoginInEvent;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Compute;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.io.IOException;
import java.util.List;

public class ResetDailyCommand implements Command<CommandSourceStack> {
    public static ResetDailyCommand instance = new ResetDailyCommand();


    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        List<ServerPlayer> playerList = player.getServer().getPlayerList().getPlayers();
        playerList.forEach(serverPlayer -> {
            try {
                LoginInEvent.DailyRefreshContent(serverPlayer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Compute.formatMSGSend(serverPlayer, Component.literal("日常").withStyle(CustomStyle.styleOfHealth),
                    Component.literal(" 你的日常活动已被刷新！").withStyle(ChatFormatting.WHITE));
        });
        return 0;
    }
}
