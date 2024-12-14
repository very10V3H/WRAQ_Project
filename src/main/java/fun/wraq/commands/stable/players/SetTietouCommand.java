package fun.wraq.commands.stable.players;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.entities.entities.Civil.Civil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.player.Player;


public class SetTietouCommand implements Command<CommandSourceStack> {
    public static SetTietouCommand instance = new SetTietouCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        String attribute = StringArgumentType.getString(context, "key");
        double value = DoubleArgumentType.getDouble(context, "num");
        Civil.setCivilAttributes(player, attribute, value);
        return 0;
    }
}
