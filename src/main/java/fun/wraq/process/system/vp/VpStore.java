package fun.wraq.process.system.vp;

import fun.wraq.common.registry.ModItems;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VpStore {

    public static List<Item> goodsList = new ArrayList<>();
    public static Map<Item, Integer> priceMap = new HashMap<>();
    public static Map<Item, Integer> countMap = new HashMap<>();
    public static Map<Item, Integer> worldSoul5PriceMap = new HashMap<>();

    public static List<Item> getGoodsList() {
        if (goodsList.isEmpty()) {
            Item[] items = new Item[]{
                    ModItems.SwordLottery.get(), ModItems.BowLottery.get(), ModItems.SceptreLottery.get(),
                    ModItems.simpleTier1Paper.get(), ModItems.simpleTier2Paper.get(), ModItems.simpleTier3Paper.get(),
                    ModItems.SENIOR_POTION_SUPPLY.get(), ModItems.ORE_SUPPLY.get(),
                    ModItems.JUNIOR_SUPPLY.get(), ModItems.SENIOR_SUPPLY.get()
            };
            goodsList.addAll(List.of(items));
        }
        return goodsList;
    }

    public static Map<Item, Integer> getPriceMap() {
        if (priceMap.isEmpty()) {
            priceMap.put(ModItems.SwordLottery.get(), 5);
            priceMap.put(ModItems.BowLottery.get(), 5);
            priceMap.put(ModItems.SceptreLottery.get(), 5);
            priceMap.put(ModItems.simpleTier1Paper.get(), 68);
            priceMap.put(ModItems.simpleTier2Paper.get(), 168);
            priceMap.put(ModItems.simpleTier3Paper.get(), 298);
            priceMap.put(ModItems.ORE_SUPPLY.get(), 25);
            priceMap.put(ModItems.SENIOR_POTION_SUPPLY.get(), 15);
            priceMap.put(ModItems.JUNIOR_SUPPLY.get(), 15);
            priceMap.put(ModItems.SENIOR_SUPPLY.get(), 45);
        }
        return priceMap;
    }

    public static Map<Item, Integer> getWorldSoul5Price() {
        if (worldSoul5PriceMap.isEmpty()) {
            worldSoul5PriceMap.put(ModItems.SwordLottery.get(), 40);
            worldSoul5PriceMap.put(ModItems.BowLottery.get(), 40);
            worldSoul5PriceMap.put(ModItems.SceptreLottery.get(), 40);
            worldSoul5PriceMap.put(ModItems.GEM_PIECE.get(), 40);
        }
        return worldSoul5PriceMap;
    }

    public static Map<Item, Integer> getCountMap() {
        if (countMap.isEmpty()) {
            countMap.put(ModItems.SwordLottery.get(), 1);
            countMap.put(ModItems.BowLottery.get(), 1);
            countMap.put(ModItems.SceptreLottery.get(), 1);
            countMap.put(ModItems.simpleTier1Paper.get(), 1);
            countMap.put(ModItems.simpleTier2Paper.get(), 1);
            countMap.put(ModItems.simpleTier3Paper.get(), 1);
            countMap.put(ModItems.ORE_SUPPLY.get(), 1);
            countMap.put(ModItems.SENIOR_POTION_SUPPLY.get(), 1);
            countMap.put(ModItems.JUNIOR_SUPPLY.get(), 1);
            countMap.put(ModItems.SENIOR_SUPPLY.get(), 1);
        }
        return countMap;
    }
}
