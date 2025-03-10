package fun.wraq.commands.stable.players;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.events.mob.instance.instances.tower.ManaTowerData;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.player.Player;

public class ShowManaTowerRecordCommand implements Command<CommandSourceStack> {
    public static ShowManaTowerRecordCommand instance = new ShowManaTowerRecordCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        if (player == null) return 0;
        ManaTowerData.showRecords();
        return 0;
    }
}
