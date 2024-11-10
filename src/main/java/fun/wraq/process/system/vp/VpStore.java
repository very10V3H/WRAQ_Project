package fun.wraq.process.system.vp;

import fun.wraq.common.registry.ModItems;
import fun.wraq.customized.UniformItems;
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
                    UniformItems.ATTACK_CURIOS_YXWG.get(), UniformItems.BOW_CURIOS_YXWG.get(), UniformItems.MANA_CURIOS_YXWG.get()
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
            priceMap.put(ModItems.gemPiece.get(), 5);
            priceMap.put(UniformItems.ATTACK_CURIOS_YXWG.get(), 300);
            priceMap.put(UniformItems.BOW_CURIOS_YXWG.get(), 300);
            priceMap.put(UniformItems.MANA_CURIOS_YXWG.get(), 300);

        }
        return priceMap;
    }

    public static Map<Item, Integer> getWorldSoul5Price() {
        if (worldSoul5PriceMap.isEmpty()) {
            worldSoul5PriceMap.put(ModItems.SwordLottery.get(), 40);
            worldSoul5PriceMap.put(ModItems.BowLottery.get(), 40);
            worldSoul5PriceMap.put(ModItems.SceptreLottery.get(), 40);
            worldSoul5PriceMap.put(ModItems.gemPiece.get(), 40);
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
            countMap.put(ModItems.gemPiece.get(), 64);
        }
        return countMap;
    }
}
