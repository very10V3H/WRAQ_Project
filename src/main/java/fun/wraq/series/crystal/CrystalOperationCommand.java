package fun.wraq.series.crystal;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.networking.ModNetworking;
import fun.wraq.networking.misc.TeamPackets.ScreenSetS2CPacket;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.player.Player;

public class CrystalOperationCommand implements Command<CommandSourceStack> {
    public static CrystalOperationCommand instance = new CrystalOperationCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        if (player == null) return 0;
        String operation = StringArgumentType.getString(context, "operation");
        switch (operation) {
            case "trade" -> {
                ModNetworking.sendToClient(new ScreenSetS2CPacket(11), player);
            }
            case "buyStone" -> {
                OriginStone.tryToBuy(player);
            }
        }
        return 0;
    }
}
