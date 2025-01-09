package fun.wraq.commands.stable.ops;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.Compute;
import fun.wraq.events.server.LoginInEvent;
import fun.wraq.render.toolTip.CustomStyle;
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
                LoginInEvent.refreshDailyContent(serverPlayer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Compute.sendFormatMSG(serverPlayer, Component.literal("日常").withStyle(CustomStyle.styleOfHealth),
                    Component.literal(" 你的日常活动已被刷新！").withStyle(ChatFormatting.WHITE));
        });
        return 0;
    }
}
