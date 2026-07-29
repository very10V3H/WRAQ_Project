package fun.wraq.commands.stable.ops;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.Compute;
import fun.wraq.common.util.Utils;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.system.wayPoints.networking.TempWayPointToFileS2CPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

/**
 * AI-Generated, 2026-07-29
 * OP command: /vmd tempWayPointToFile
 * Sends a packet to the client to export Xaero waypoints named "Waypoint"
 * to the desktop file "临时路径点.txt".
 */
public class TempWayPointToFileCommand implements Command<CommandSourceStack> {
    public static TempWayPointToFileCommand instance = new TempWayPointToFileCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        ModNetworking.sendToClient(new TempWayPointToFileS2CPacket(), player);
        Compute.sendFormatMSG(player,
                Component.literal("TempWayPoint").withStyle(ChatFormatting.GOLD),
                Component.literal("已发送导出指令到客户端，请查看桌面 临时路径点.txt").withStyle(ChatFormatting.WHITE));
        return 0;
    }
}
