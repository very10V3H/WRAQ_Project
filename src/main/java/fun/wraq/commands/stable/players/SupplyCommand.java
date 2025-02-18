package fun.wraq.commands.stable.players;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.registry.ModItems;
import fun.wraq.process.func.item.InventoryOperation;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.List;

public class SupplyCommand implements Command<CommandSourceStack> {
    public static SupplyCommand instance = new SupplyCommand();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        List<ItemStack> supply = List.of(
                new ItemStack(ModItems.ID_Card.get())
        );
        supply.forEach(stack -> {
            InventoryOperation.giveItemStackWithMSG(player, stack);
        });
        ItemStack elyTra = Items.ELYTRA.getDefaultInstance();
        elyTra.enchant(Enchantments.UNBREAKING, 10);
        elyTra.getOrCreateTag().putBoolean("Unbreakable", true);
        InventoryOperation.giveItemStackWithMSG(player, elyTra);
        return 0;
    }
}
