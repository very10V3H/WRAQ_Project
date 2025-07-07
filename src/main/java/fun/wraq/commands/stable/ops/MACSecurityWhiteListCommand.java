package fun.wraq.commands.stable.ops;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.Compute;
import fun.wraq.process.func.security.mac.MacServer;
import net.minecraft.commands.CommandSourceStack;

public class MACSecurityWhiteListCommand implements Command<CommandSourceStack> {
    public static MACSecurityWhiteListCommand instance = new MACSecurityWhiteListCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        String playerName = StringArgumentType.getString(context, "playerName");
        MacServer.WHITE_LIST.add(playerName);
        Compute.sendCommandOpMSG(context.getSource().getPlayer(), "已添加" + playerName +"至MAC白名单");
        return 0;
    }
}
