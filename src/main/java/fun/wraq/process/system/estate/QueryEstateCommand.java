package fun.wraq.process.system.estate;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.player.Player;


public class QueryEstateCommand implements Command<CommandSourceStack> {
    public static QueryEstateCommand instance = new QueryEstateCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        if (player == null) {
            return 0;
        }
        EstateUtil.queryCurrentSoldEstate(player);
        return 0;
    }
}
