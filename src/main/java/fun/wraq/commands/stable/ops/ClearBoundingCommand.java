package fun.wraq.commands.stable.ops;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.events.core.InventoryCheck;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;


public class ClearBoundingCommand implements Command<CommandSourceStack> {
    public static ClearBoundingCommand instance = new ClearBoundingCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        if (player == null) {
            return 0;
        }

        Inventory inventory = player.getInventory();
        for (int i = 0 ; i < inventory.getContainerSize() ; i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (InventoryCheck.containOwnerTag(itemStack)) {
                InventoryCheck.removeOwnerTagDirect(itemStack);
            }
        }
        return 0;
    }
}
