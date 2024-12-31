package fun.wraq.commands.stable.players;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.TeamPackets.ScreenSetS2CPacket;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;

public class OpenScreenOperationCommand implements Command<CommandSourceStack> {
    public static OpenScreenOperationCommand instance = new OpenScreenOperationCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer serverPlayer = context.getSource().getPlayer();
        if (serverPlayer == null) return 0;
        int type = IntegerArgumentType.getInteger(context, "type");
        ModNetworking.sendToClient(new ScreenSetS2CPacket(type), serverPlayer);
        return 0;
    }
}
