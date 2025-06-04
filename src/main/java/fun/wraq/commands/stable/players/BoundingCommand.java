package fun.wraq.commands.stable.players;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.events.core.InventoryCheck;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.player.Player;


public class BoundingCommand implements Command<CommandSourceStack> {
    public static BoundingCommand instance = new BoundingCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        if (player == null) {
            return 0;
        }
        InventoryCheck.addOwnerTagToItemStack(player, player.getMainHandItem());
        Compute.sendFormatMSG(player, Te.s("安全", CustomStyle.styleOfHealth),
                Te.s("已将", player.getMainHandItem(), "绑定."));
        return 0;
    }
}
