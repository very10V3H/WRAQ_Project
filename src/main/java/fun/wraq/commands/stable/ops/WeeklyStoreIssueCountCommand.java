package fun.wraq.commands.stable.ops;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.render.gui.trade.weekly.WeeklyStore;
import fun.wraq.render.gui.trade.weekly.WeeklyStoreServerData;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.player.Player;

public class WeeklyStoreIssueCountCommand implements Command<CommandSourceStack> {
    public static WeeklyStoreIssueCountCommand instance = new WeeklyStoreIssueCountCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        if (player == null) {
            return 0;
        }
        int count = IntegerArgumentType.getInteger(context, "count");
        WeeklyStore.takeTurns();
        WeeklyStoreServerData serverData = WeeklyStoreServerData.getInstance();
        serverData.setIssueCount(count);
        return 0;
    }
}
