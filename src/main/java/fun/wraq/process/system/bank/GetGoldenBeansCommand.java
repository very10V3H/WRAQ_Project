package fun.wraq.process.system.bank;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.player.Player;

public class GetGoldenBeansCommand implements Command<CommandSourceStack> {
    public static GetGoldenBeansCommand instance = new GetGoldenBeansCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        if (player == null) return 0;
        int num = IntegerArgumentType.getInteger(context, "num");
        Bank.getGoldenBeans(player, num);
        return 0;
    }
}
