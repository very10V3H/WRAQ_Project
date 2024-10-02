package fun.wraq.commands.stable.ops;

import com.google.common.collect.ImmutableMap;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.equip.impl.RandomCurios;
import fun.wraq.common.registry.ModItems;
import fun.wraq.process.func.item.InventoryOperation;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

public class CuriosCommand implements Command<CommandSourceStack> {
    public static CuriosCommand instance = new CuriosCommand();

    public static Map<String, Item> map = ImmutableMap.of(
            "NetherHand", ModItems.netherHand.get(),
            "PlainNecklace", ModItems.plainNecklace.get(),
            "CastleNecklace", ModItems.CastleNecklace.get(),
            "IceBelt", ModItems.iceBelt.get(),
            "LavenderBracelet", ModItems.lavenderBracelet.get());

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        if (player == null) return 0;
        String type = StringArgumentType.getString(context, "type").toLowerCase();
        int num = IntegerArgumentType.getInteger(context, "num");
        map.forEach((key, item) -> {
            if (key.toLowerCase().contains(type) && item instanceof RandomCurios randomCurios) {
                for (int i = 0 ; i < num ; i ++) {
                    ItemStack curios = new ItemStack(item);
                    randomCurios.setAttribute(curios);
                    InventoryOperation.itemStackGive(player, curios);
                }
            }
        });
        return 0;
    }
}
