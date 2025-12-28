package fun.wraq.series.secret;

import fun.wraq.process.system.cooking.CookingItems;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.enhanceForge.Pearl;
import fun.wraq.process.system.forge.EquipPiece;
import fun.wraq.process.system.spur.Items.SpurItems;
import fun.wraq.series.comsumable.ComsumableItems;
import fun.wraq.series.crystal.CrystalItem;
import fun.wraq.series.events.SpecialEventItems;
import fun.wraq.series.holy.ice.IceHolyItems;
import fun.wraq.series.moontain.MoontainItems;
import fun.wraq.series.overworld.chapter1.mine.MineCrest;
import fun.wraq.series.overworld.chapter1.plain.PlainCrest;
import fun.wraq.series.overworld.chapter1.snow.SnowCrest;
import fun.wraq.series.overworld.chapter1.volcano.VolcanoCrest;
import fun.wraq.series.overworld.chapter1.waterSystem.crest.LakeCrest;
import fun.wraq.series.overworld.chapter2.evoker.ManaCrest;
import fun.wraq.series.overworld.chapter2.sky.Crest.SkyCrest;
import fun.wraq.series.overworld.extraordinary.ExtraordinaryItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static fun.wraq.common.registry.ModItems.*;

/**
 * 隐秘箱内容
 */
public class SecretChestContent {

    private static final Map<Item, Integer> valueMap = new HashMap<>();

    public static Map<Item, Integer> getValueMap() {
        if (valueMap.isEmpty()) {
            addCommon();
            addCrystal();
            addCrest();
            addSummerCandy();
            addBossChest();
            addLottery();
            addForgePiece();
            addElementPiece();
            addFoodPackage();
            addComsumableItems();
            addHolyChest();
            addSpur();
            addMoontain();
            addPearl();
            addForgePaper();
        }
        return valueMap;
    }

    private static void addCommon() {
        valueMap.put(COMPLETE_GEM.get(), 50000);
        valueMap.put(GOLD_COIN.get(), 144);
        valueMap.put(ExtraordinaryItems.DAZZLING_DIAMOND.get(), 880000);
        valueMap.put(SpecialEventItems.DRAGON_DIAMOND.get(), 880000);
        valueMap.put(WORLD_FORGE_STONE.get(), 200000);
        valueMap.put(FORGING_STONE_2.get(), 3000);
        valueMap.put(GOLDEN_BEANS.get(), 1440);
        valueMap.put(RAINBOW_POWDER.get(), 140);
        valueMap.put(RAINBOW_CRYSTAL.get(), 356860);
        valueMap.put(REPUTATION_MEDAL.get(), 1440);
        valueMap.put(RANDOM_EVENT_MEDAL.get(), 5760);
        valueMap.put(WORLD_SOUL_3.get(), 81920);
        valueMap.put(REFINED_PIECE.get(), 50);
    }

    private static void addCrystal() {
        for (CrystalItem crystalItem : CrystalItem.list) {
            valueMap.put(crystalItem, crystalItem.getPrice());
        }
    }

    private static void addCrest() {
        List<List<Item>> crestList = List.of(
                PlainCrest.crestList,
                LakeCrest.crestList,
                VolcanoCrest.crestList,
                MineCrest.crestList,
                SkyCrest.crestList,
                SnowCrest.crestList,
                ManaCrest.crestList
        );

        for (List<Item> items : crestList) {
            for (int i = 0; i < items.size(); i++) {
                Item item = items.get(i);
                valueMap.put(item, 200 * (int) Math.pow(16, i));
            }
        }
    }

    private static void addSummerCandy() {
        List<Item> candyList = List.of(
                SpecialEventItems.CANDY_ICE.get(),
                SpecialEventItems.CANDY_HOT.get(),
                SpecialEventItems.CANDY_SWEET.get()
        );

        for (Item item : candyList) {
            valueMap.put(item, 8000);
        }
    }

    private static void addBossChest() {
        List<Item> bossChestList = List.of(
                ICE_LOOT.get(),
                DEVIL_LOOT.get(),
                MOON_LOOT.get(),
                CASTLE_LOOT.get()
        );

        for (Item item : bossChestList) {
            valueMap.put(item, 8000);
        }
    }

    private static void addLottery() {
        valueMap.put(COMMON_LOTTERIES.get(), 10000);
        valueMap.put(UNCOMMON_LOTTERIES.get(), 25000);
    }

    private static void addForgePiece() {
        for (int i = 0; i < EquipPiece.list.size(); i++) {
            Item item = EquipPiece.list.get(i);
            valueMap.put(item, 300 * (int) Math.pow(4, i));
        }
    }

    private static void addElementPiece() {
        List<List<Item>> pieceList = List.of(
                Element.getPiece0Items(),
                Element.getPiece1Items(),
                Element.getPiece2Items()
        );

        int[] value = new int[]{20, 1280, 431920};

        for (int i = 0; i < pieceList.size(); i++) {
            List<Item> eachTierPieceList = pieceList.get(i);
            for (Item item : eachTierPieceList) {
                valueMap.put(item, value[i]);
            }
        }
    }

    private static void addFoodPackage() {
        valueMap.put(CookingItems.FOOD_PACKAGE_0.get(), 480);
        valueMap.put(CookingItems.FOOD_PACKAGE_1.get(), 1760);
        valueMap.put(CookingItems.FOOD_PACKAGE_2.get(), 3680);
        valueMap.put(CookingItems.FOOD_PACKAGE_3.get(), 17600);
    }

    private static void addComsumableItems() {
        List<Item> attackComsumableItems = List.of(
                ComsumableItems.WHETSTONE_ATTACK_0.get(),
                ComsumableItems.WHETSTONE_PENETRATION_0.get(),
                ComsumableItems.WHETSTONE_PENETRATION0_0.get(),
                ComsumableItems.QUIVER_ATTACK_0.get(),
                ComsumableItems.QUIVER_PENETRATION_0.get(),
                ComsumableItems.QUIVER_PENETRATION0_0.get(),
                ComsumableItems.MIXTURE_ATTACK_0.get(),
                ComsumableItems.MIXTURE_PENETRATION_0.get(),
                ComsumableItems.MIXTURE_PENETRATION0_0.get()
        );

        for (Item item : attackComsumableItems) {
            valueMap.put(item, 200000);
        }

        valueMap.put(ComsumableItems.HEAT_INJECTION_0.get(), 100000);
        valueMap.put(ComsumableItems.HEAT_INJECTION_1.get(), 200000);
        valueMap.put(ComsumableItems.HEAT_INJECTION_2.get(), 300000);
        valueMap.put(ComsumableItems.HEAT_INJECTION_3.get(), 400000);

        valueMap.put(ComsumableItems.HEAT_DEVICE_0.get(), 100000);
        valueMap.put(ComsumableItems.HEAT_DEVICE_1.get(), 200000);
        valueMap.put(ComsumableItems.HEAT_DEVICE_2.get(), 300000);
    }

    private static void addHolyChest() {
        valueMap.put(IceHolyItems.CHEST.get(), 20000);
        valueMap.put(IceHolyItems.PIECE_CHEST_0.get(), 10000);
        valueMap.put(IceHolyItems.PIECE_CHEST_1.get(), 90000);
        valueMap.put(IceHolyItems.PIECE_CHEST_2.get(), 810000);
        valueMap.put(IceHolyItems.PIECE_CHEST_3.get(), 7290000);
    }

    private static void addSpur() {
        valueMap.put(SpurItems.CROP_PIECE.get(), 200);
        valueMap.put(SpurItems.CROP_PIECE_1.get(), 12800);
        valueMap.put(SpurItems.LOG_PIECE.get(), 200);
        valueMap.put(SpurItems.LOG_PIECE_1.get(), 12800);
        valueMap.put(SpurItems.SEA_PIECE.get(), 200);
        valueMap.put(SpurItems.SEA_PIECE_1.get(), 12800);
        valueMap.put(SpurItems.MINE_PIECE.get(), 200);
        valueMap.put(SpurItems.MINE_PIECE_1.get(), 12800);
    }

    private static void addMoontain() {
        valueMap.put(MoontainItems.WEAPON_ENHANCER.get(), 500000);
        valueMap.put(MoontainItems.ARMOR_ENHANCER.get(), 500000);
        valueMap.put(MoontainItems.CURIOS_RATE_ENHANCER.get(), 300000);
        valueMap.put(MoontainItems.CURIOS_FULL_RATE_ENHANCER.get(), 600000);
        valueMap.put(MoontainItems.HEART.get(), 300000);
    }

    private static void addPearl() {
        for (Item item : Pearl.list) {
            valueMap.put(item, 33333);
        }
    }

    private static void addForgePaper() {
        valueMap.put(FORGE_ENHANCE_0.get(), 144);
        valueMap.put(FORGE_ENHANCE_1.get(), 576);
        valueMap.put(FORGE_ENHANCE_2.get(), 50000);
        valueMap.put(FORGE_ENHANCE_3.get(), 200000);
    }

    // 因为钥匙的价值是固定的，低于钥匙价值的物品也是固定的，所以可以进行缓存。
    private static final Map<Integer, List<Item>> valueBelowItems = new HashMap<>();

    private static final Map<Integer, List<Item>> valueUpperItems = new HashMap<>();

    /**
     * 生成物品
     * @param count 种类
     * @param valueUpper 每种物品的价值不低于
     * @param valueBelow 每种物品的价值不超过
     * @param itemNumValueBelow 单物品数量计算时的价值不超过
     * @return 生成的物品列表
     */
    public static List<ItemStack> generateContent(int count, int valueUpper, int valueBelow, int itemNumValueBelow) {
        if (!valueUpperItems.containsKey(valueBelow)) {
            List<Item> items = getValueMap().keySet().stream()
                    .filter(item -> getValueMap().get(item) >= valueUpper)
                    .collect(Collectors.toList());
            valueUpperItems.put(valueBelow, items);
        }

        List<Item> upperItems = valueUpperItems.get(valueBelow);

        if (!valueBelowItems.containsKey(valueBelow)) {
            List<Item> items = getValueMap().keySet().stream()
                    .filter(item -> getValueMap().get(item) <= valueBelow)
                    .collect(Collectors.toList());
            valueBelowItems.put(valueBelow, items);
        }

        List<Item> belowItems = valueBelowItems.get(valueBelow);

        // 取交集
        belowItems.retainAll(upperItems);

        List<ItemStack> result = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            int itemIndex = RandomUtils.nextInt(0, belowItems.size());
            Item item = belowItems.get(itemIndex);
            int value = getValueMap().get(item);
            int num = RandomUtils.nextInt(1, Math.max(1, itemNumValueBelow / value));
            result.add(new ItemStack(item, num));
        }

        return result;
    }

    /**
     * 生成物品
     * @param count 种类
     * @param valueBelow 每种物品的价值不超过
     * @return 生成的物品列表
     */
    public static List<ItemStack> generateContent(int count, int valueBelow) {
        return generateContent(count, 0, valueBelow, valueBelow);
    }
}
