/** AI-Generated, 2026-05-17 */
package fun.wraq.process.system.stock;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.TeamPackets.ScreenSetS2CPacket;
import fun.wraq.process.system.stock.networking.StockDataSyncS2CPacket;
import fun.wraq.process.system.stock.networking.StockPortfolioSyncS2CPacket;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;

public class StockCommand implements Command<CommandSourceStack> {
    public static StockCommand instance = new StockCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) return 0;
        // Sync market data and portfolio before opening screen
        StockDataSyncS2CPacket.sendTo(player);
        StockPortfolioSyncS2CPacket.sendTo(player);
        // Open screen (case 12)
        ModNetworking.sendToClient(new ScreenSetS2CPacket(12), player);
        return 0;
    }
}
