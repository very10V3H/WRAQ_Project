package fun.wraq.process.system.vp;

import fun.wraq.common.registry.ModItems;
import fun.wraq.customized.UniformItems;
import fun.wraq.series.dragon.SilverDragonItems;
import fun.wraq.series.events.dragonboat.DragonBoatFes;
import fun.wraq.series.events.labourDay.LabourDayOldCoin;
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
            List<Item> items = new ArrayList<>() {{
                addAll(List.of(
                        ModItems.SWORD_LOTTERY.get(), ModItems.SWORD_LOTTERY_1.get(),
                        ModItems.BOW_LOTTERY.get(), ModItems.BOW_LOTTERY_1.get(),
                        ModItems.SCEPTRE_LOTTERY.get(), ModItems.SCEPTRE_LOTTERY_1.get(),
                        ModItems.SIMPLE_TIER_1_PAPER.get(),
                        ModItems.SIMPLE_TIER_2_PAPER.get(),
                        ModItems.SIMPLE_TIER_3_PAPER.get(),
                        SilverDragonItems.SILVER_DRAGON_SWORD_LOTTERY.get(),
                        SilverDragonItems.SILVER_DRAGON_BOW_LOTTERY.get(),
                        SilverDragonItems.SILVER_DRAGON_SCEPTRE_LOTTERY.get(),
                        ModItems.SENIOR_POTION_SUPPLY.get(), ModItems.ORE_SUPPLY.get(),
                        ModItems.JUNIOR_SUPPLY.get(), ModItems.SENIOR_SUPPLY.get(),
                        ModItems.FORGE_SUPPLY.get(), ModItems.WORLD_SOUL_5.get()
                ));
            }};
            if (DragonBoatFes.isInActivate()) {
                items.addAll(List.of(
                        UniformItems.ATTACK_CURIOS_0.get(),
                        UniformItems.ATTACK_CURIOS_1.get(),
                        UniformItems.ATTACK_CURIOS_2.get(),
                        UniformItems.ATTACK_CURIOS_3.get(),
                        UniformItems.ATTACK_CURIOS_4.get(),
                        UniformItems.ATTACK_CURIOS_5.get(),
                        UniformItems.ATTACK_CURIOS_YXWG.get(),
                        UniformItems.ATTACK_CURIO_LX.get(),
                        UniformItems.BOW_CURIOS_0.get(),
                        UniformItems.BOW_CURIOS_1.get(),
                        UniformItems.BOW_CURIOS_2.get(),
                        UniformItems.BOW_CURIOS_3.get(),
                        UniformItems.BOW_CURIOS_4.get(),
                        UniformItems.BOW_CURIOS_5.get(),
                        UniformItems.BOW_CURIOS_YXWG.get(),
                        UniformItems.BOW_CURIO_LEI_YAN.get(),
                        UniformItems.MANA_CURIOS_0.get(),
                        UniformItems.MANA_CURIOS_1.get(),
                        UniformItems.MANA_CURIOS_2.get(),
                        UniformItems.MANA_CURIOS_3.get(),
                        UniformItems.MANA_CURIOS_4.get(),
                        UniformItems.MANA_CURIOS_5.get(),
                        UniformItems.MANA_CURIOS_YXWG.get(),
                        UniformItems.MANA_CURIO_TABOO.get()
                ));
            }
            goodsList.addAll(items);
        }
        return goodsList;
    }

    public static Map<Item, Integer> getPriceMap() {
        if (priceMap.isEmpty()) {
            priceMap.put(ModItems.SWORD_LOTTERY.get(), 5);
            priceMap.put(ModItems.SWORD_LOTTERY_1.get(), 5);
            priceMap.put(ModItems.BOW_LOTTERY.get(), 5);
            priceMap.put(ModItems.BOW_LOTTERY_1.get(), 5);
            priceMap.put(ModItems.SCEPTRE_LOTTERY.get(), 5);
            priceMap.put(ModItems.SCEPTRE_LOTTERY_1.get(), 5);
            priceMap.put(SilverDragonItems.SILVER_DRAGON_SWORD_LOTTERY.get(), 5);
            priceMap.put(SilverDragonItems.SILVER_DRAGON_BOW_LOTTERY.get(), 5);
            priceMap.put(SilverDragonItems.SILVER_DRAGON_SCEPTRE_LOTTERY.get(), 5);
            priceMap.put(ModItems.SIMPLE_TIER_1_PAPER.get(), 68);
            priceMap.put(ModItems.SIMPLE_TIER_2_PAPER.get(), 168);
            priceMap.put(ModItems.SIMPLE_TIER_3_PAPER.get(), 298);
            priceMap.put(ModItems.ORE_SUPPLY.get(), 8);
            priceMap.put(ModItems.SENIOR_POTION_SUPPLY.get(), 8);
            priceMap.put(ModItems.JUNIOR_SUPPLY.get(), 8);
            priceMap.put(ModItems.SENIOR_SUPPLY.get(), 12);
            priceMap.put(ModItems.FORGE_SUPPLY.get(), 5);
            priceMap.put(ModItems.WORLD_SOUL_5.get(), 5);
            getGoodsList().forEach(item -> {
                if (!priceMap.containsKey(item)) {
                    priceMap.put(item, 300);
                }
            });
        }
        if (LabourDayOldCoin.isInActivityDate()) {
            priceMap.put(ModItems.SIMPLE_TIER_1_PAPER.get(), 54);
            priceMap.put(ModItems.SIMPLE_TIER_2_PAPER.get(), 134);
            priceMap.put(ModItems.SIMPLE_TIER_3_PAPER.get(), 238);
        }
        return priceMap;
    }

    public static Map<Item, Integer> getWorldSoul5Price() {
        if (worldSoul5PriceMap.isEmpty()) {
            worldSoul5PriceMap.put(ModItems.SWORD_LOTTERY.get(), 40);
            worldSoul5PriceMap.put(ModItems.SWORD_LOTTERY_1.get(), 40);
            worldSoul5PriceMap.put(ModItems.BOW_LOTTERY.get(), 40);
            worldSoul5PriceMap.put(ModItems.BOW_LOTTERY_1.get(), 40);
            worldSoul5PriceMap.put(ModItems.SCEPTRE_LOTTERY.get(), 40);
            worldSoul5PriceMap.put(ModItems.SCEPTRE_LOTTERY_1.get(), 40);
            worldSoul5PriceMap.put(SilverDragonItems.SILVER_DRAGON_SWORD_LOTTERY.get(), 40);
            worldSoul5PriceMap.put(SilverDragonItems.SILVER_DRAGON_BOW_LOTTERY.get(), 40);
            worldSoul5PriceMap.put(SilverDragonItems.SILVER_DRAGON_SCEPTRE_LOTTERY.get(), 40);
            worldSoul5PriceMap.put(ModItems.GEM_PIECE.get(), 40);
        }
        return worldSoul5PriceMap;
    }

    public static Map<Item, Integer> getCountMap() {
        if (countMap.isEmpty()) {
            countMap.put(ModItems.SWORD_LOTTERY.get(), 1);
            countMap.put(ModItems.BOW_LOTTERY.get(), 1);
            countMap.put(ModItems.SCEPTRE_LOTTERY.get(), 1);
            countMap.put(ModItems.SWORD_LOTTERY_1.get(), 1);
            countMap.put(ModItems.BOW_LOTTERY_1.get(), 1);
            countMap.put(ModItems.SCEPTRE_LOTTERY_1.get(), 1);
            countMap.put(SilverDragonItems.SILVER_DRAGON_SWORD_LOTTERY.get(), 1);
            countMap.put(SilverDragonItems.SILVER_DRAGON_BOW_LOTTERY.get(), 1);
            countMap.put(SilverDragonItems.SILVER_DRAGON_SCEPTRE_LOTTERY.get(), 1);
            countMap.put(ModItems.SIMPLE_TIER_1_PAPER.get(), 1);
            countMap.put(ModItems.SIMPLE_TIER_2_PAPER.get(), 1);
            countMap.put(ModItems.SIMPLE_TIER_3_PAPER.get(), 1);
            countMap.put(ModItems.ORE_SUPPLY.get(), 1);
            countMap.put(ModItems.SENIOR_POTION_SUPPLY.get(), 1);
            countMap.put(ModItems.JUNIOR_SUPPLY.get(), 1);
            countMap.put(ModItems.SENIOR_SUPPLY.get(), 1);
            countMap.put(ModItems.FORGE_SUPPLY.get(), 1);
            countMap.put(ModItems.WORLD_SOUL_5.get(), 40);
        }
        goodsList.forEach(item -> {
            if (!countMap.containsKey(item)) {
                countMap.put(item, 1);
            }
        });
        return countMap;
    }
}
