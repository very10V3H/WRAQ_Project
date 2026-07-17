package fun.wraq.commands.stable.players;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.process.system.afk.AfkSystem;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;

/**
 * 打开AFK挂机扫荡界面
 * AI-Generated, 2026-05-17
 */
public class AfkCommand implements Command<CommandSourceStack> {
    public static AfkCommand instance = new AfkCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayer();
        AfkSystem.syncToClient(player);
        return 0;
    }
}
