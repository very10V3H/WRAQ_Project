package fun.wraq.process.system.randomStore;

import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.system.randomStore.networking.TradeListS2CPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class RandomStore {
    public static String villagerName = "RandomStore";
    public static Map<String, Integer> playerNextRefreshTick = new HashMap<>();
    public static Map<String, Map<ItemStack, List<ItemStack>>> playerTradeList = new HashMap<>();
    public static Map<ItemStack, List<ItemStack>> clientTradeList = new HashMap<>();
    public static Map<ItemStack, List<ItemStack>> defaultTradeList = new HashMap<>() {{
        put(new ItemStack(ModItems.ROSE_GOLD_COIN.get(), 2),
                List.of(new ItemStack(ModItems.GOLD_COIN.get(), 32),
                        new ItemStack(ModItems.silverCoin.get(), 32),
                        new ItemStack(ModItems.copperCoin.get(), 32)));
    }};

    public static void createRandomTradeList(Player player) {
        String name = player.getName().getString();
        if (!playerNextRefreshTick.containsKey(name) || playerNextRefreshTick.get(name) < Tick.get()) {
            Map<ItemStack, List<ItemStack>> tradeList = new HashMap<>();
            Random rand = new Random();
            defaultTradeList.forEach((targetItem, needItemList) -> {
                if (rand.nextBoolean()) {
                    List<ItemStack> newNeedItemList = new ArrayList<>();
                    for (ItemStack needItem : needItemList) {
                        int count = Math.max(1, (int) (needItem.getCount() * rand.nextDouble(0.75, 1.25)));
                        newNeedItemList.add(new ItemStack(needItem.getItem(), count));
                    }
                    tradeList.put(targetItem, newNeedItemList);
                }
            });
            playerTradeList.put(name, tradeList);
        }
    }

    public static void clearTradeList(Player player) {

    }

    public static void sendTradeListToPlayer(Player player) {
        String name = player.getName().getString();
        if (!playerTradeList.containsKey(name)) return;
        Map<ItemStack, List<ItemStack>> tradeList = playerTradeList.get(name);
        tradeList.forEach((targetItem, needItemList) -> {
            needItemList.forEach(needItem -> {
                ModNetworking.sendToClient(new TradeListS2CPacket(targetItem, needItem), (ServerPlayer) player);
            });
        });
    }
}
