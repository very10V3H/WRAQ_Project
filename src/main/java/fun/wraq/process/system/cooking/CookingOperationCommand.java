package fun.wraq.process.system.cooking;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.player.Player;

public class CookingOperationCommand implements Command<CommandSourceStack> {
    public static CookingOperationCommand instance = new CookingOperationCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        if (player == null) return 0;
        String operation = StringArgumentType.getString(context, "operation");
        switch (operation) {
            case "sellAllFood" -> {
                CookingValue.sellAll(player);
            }
            case "cancelEntrustment" -> {
                CookingVillager.cancelEntrustment(player);
            }
            case "acceptEntrustment" -> {
                CookingVillager.acceptEntrustment(player);
            }
        }
        return 0;
    }
}
