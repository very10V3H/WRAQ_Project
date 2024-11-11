package fun.wraq.commands.stable.ops;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.process.system.tower.Tower;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.player.Player;


public class ResetTowerDataCommand implements Command<CommandSourceStack> {
    public static ResetTowerDataCommand instance = new ResetTowerDataCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        Tower.resetData(player);
        return 0;
    }
}
