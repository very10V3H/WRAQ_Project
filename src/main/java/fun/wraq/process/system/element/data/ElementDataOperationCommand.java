package fun.wraq.process.system.element.data;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.player.Player;

public class ElementDataOperationCommand implements Command<CommandSourceStack> {
    public static ElementDataOperationCommand instance = new ElementDataOperationCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        if (player == null) return 0;
        String operation = StringArgumentType.getString(context, "operation");
        switch (operation) {

        }
        return 0;
    }
}
