package fun.wraq.commands.stable.ops;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fun.wraq.common.equip.impl.RandomCurios;
import fun.wraq.common.registry.ModItems;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.series.instance.series.harbinger.HarbingerItems;
import fun.wraq.series.moontain.MoontainItems;
import fun.wraq.series.overworld.cold.SuperColdItems;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class CuriosCommand implements Command<CommandSourceStack> {
    public static CuriosCommand instance = new CuriosCommand();

    public static Map<String, Item> map = new HashMap<>() {{
        put("NetherHand", ModItems.NETHER_HAND.get());
        put("PlainNecklace", ModItems.PLAIN_NECKLACE.get());
        put("CastleNecklace", ModItems.CASTLE_NECKLACE.get());
        put("IceBelt", ModItems.ICE_BELT.get());
        put("LavenderBracelet", ModItems.LAVENDER_BRACELET.get());
        put("MoontainChest", MoontainItems.CHEST_CURIOS.get());
        put("MoontainBracelet", MoontainItems.BRACELET.get());
        put("MoontainHand", MoontainItems.HAND.get());
        put("MoontainRing", MoontainItems.RING.get());
        put("HarbingerCurio", HarbingerItems.HARBINGER_CURIO.get());
        put("SuperColdDragonCurio", SuperColdItems.DRAGON_CURIO.get());
    }};

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = context.getSource().getPlayer();
        if (player == null) return 0;
        String type = StringArgumentType.getString(context, "type").toLowerCase();
        int num = IntegerArgumentType.getInteger(context, "num");
        map.forEach((key, item) -> {
            if (key.toLowerCase().contains(type) && item instanceof RandomCurios randomCurios) {
                for (int i = 0; i < num; i++) {
                    ItemStack curios = new ItemStack(item);
                    randomCurios.setAttribute(curios);
                    InventoryOperation.giveItemStack(player, curios);
                }
            }
        });
        return 0;
    }
}
