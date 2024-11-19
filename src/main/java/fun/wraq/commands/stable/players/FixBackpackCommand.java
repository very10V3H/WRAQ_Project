package fun.wraq.commands.stable.players;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.fast.Te;
import fun.wraq.events.core.InventoryCheck;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems;
import net.p3pp3rf1y.sophisticatedcore.inventory.InventoryHandler;
import net.p3pp3rf1y.sophisticatedcore.util.InventoryHelper;

import java.util.UUID;

public class FixBackpackCommand implements Command<CommandSourceStack> {
    public static FixBackpackCommand instance = new FixBackpackCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        if (player.getMainHandItem().is(ModItems.NETHERITE_BACKPACK.get())) {
            ItemStack oldBackpack = player.getMainHandItem();
            BackpackWrapper wrapper = new BackpackWrapper(oldBackpack);

            UUID uuid = wrapper.getContentsUuid().get();
            InventoryHandler inventoryHandler = wrapper.getInventoryHandler();
            InventoryHelper.iterate(inventoryHandler, ((integer, itemStack) -> {
                itemStack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
                if (!InventoryCheck.getBoundingList().contains(itemStack.getItem())) {
                    InventoryCheck.removeOwnerTagDirect(itemStack);
                }
            }));
            wrapper.setContentsUuid(uuid);

            player.setItemInHand(InteractionHand.MAIN_HAND, wrapper.getBackpack());

            player.sendSystemMessage(Te.s("已重置背包"));
        } else {
            player.sendSystemMessage(Te.s("仅接受下界合金背包"));
        }
        return 0;
    }
}
