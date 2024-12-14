package fun.wraq.commands.stable.players;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.entities.entities.Civil.Civil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.player.Player;

public class TietouCommand implements Command<CommandSourceStack> {
    public static TietouCommand instance = new TietouCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        Civil.spawnToTestDps(player);
        return 0;
    }
}
