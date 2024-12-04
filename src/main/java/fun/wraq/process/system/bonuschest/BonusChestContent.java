package fun.wraq.process.system.bonuschest;

import com.mojang.datafixers.util.Pair;
import fun.wraq.common.registry.ModItems;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.forge.ForgeEquipUtils;
import fun.wraq.process.system.ore.OreItems;
import fun.wraq.process.system.point.Point;
import fun.wraq.process.system.potion.PotionBag;
import fun.wraq.process.system.spur.Items.SpurItems;
import fun.wraq.process.system.spur.events.MineSpur;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class BonusChestContent {
    public record ItemAndMaxNum(Item item, int maxNum) {}

    private final static List<ItemAndMaxNum> tier0Loot = List.of(
            new ItemAndMaxNum(ModItems.RevelationBook.get(), 3),
            new ItemAndMaxNum(ModItems.GOLD_COIN.get(), 1),
            new ItemAndMaxNum(Items.IRON_INGOT, 2),
            new ItemAndMaxNum(ModItems.worldSoul5.get(), 1),
            new ItemAndMaxNum(ModItems.gemPiece.get(), 4),
            new ItemAndMaxNum(ModItems.LifeElementPiece0.get(), 4),
            new ItemAndMaxNum(ModItems.ATTACK_UP_POTION_BAG.get(), 2),
            new ItemAndMaxNum(ModItems.ForgingStone0.get(), 2)
    );

    private final static List<ItemAndMaxNum> tier1Loot = List.of(
            new ItemAndMaxNum(OreItems.WRAQ_ORE_1_ITEM.get(), 2),
            new ItemAndMaxNum(SpurItems.minePiece.get(), 2),
            new ItemAndMaxNum(ModItems.ForgingStone1.get(), 2),
            new ItemAndMaxNum(ModItems.equipPiece0.get(), 2),
            new ItemAndMaxNum(ModItems.worldSoul5.get(), 2),
            new ItemAndMaxNum(ModItems.WorldSoul2.get(), 2),
            new ItemAndMaxNum(ModItems.REFINED_PIECE.get(), 1)
    );

    private final static List<ItemAndMaxNum> tier2Loot = List.of(
            new ItemAndMaxNum(ModItems.MopUpPaperLoot.get(), 1),
            new ItemAndMaxNum(ModItems.KillPaperLoot.get(), 1),
            new ItemAndMaxNum(ModItems.UnCommonLotteries.get(), 1),
            new ItemAndMaxNum(ModItems.GoldCoinBag.get(), 1),
            new ItemAndMaxNum(ModItems.ForgeEnhance3.get(), 1),
            new ItemAndMaxNum(ModItems.ForgingStone2.get(), 1),
            new ItemAndMaxNum(ModItems.Pearl1.get(), 1),
            new ItemAndMaxNum(ModItems.worldSoul5.get(), 3),
            new ItemAndMaxNum(ModItems.REFINED_PIECE.get(), 2)
    );

    private final static List<ItemAndMaxNum> tier3Loot = List.of(
            new ItemAndMaxNum(ModItems.WORLD_FORGE_STONE.get(), 1),
            new ItemAndMaxNum(ModItems.worldSoul5.get(), 4),
            new ItemAndMaxNum(ModItems.REFINED_PIECE.get(), 4),
            new ItemAndMaxNum(ModItems.ForgeEnhance3.get(), 1),
            new ItemAndMaxNum(ModItems.ForgingStone2.get(), 1),
            new ItemAndMaxNum(ModItems.Pearl1.get(), 1)
    );

    private final static Map<Item, List<Item>> specificItemMap = new HashMap<>() {{
        put(Items.IRON_INGOT, MineSpur.getVanillaIngotItems());
        put(ModItems.LifeElementPiece0.get(), Element.getPiece0Items());
        put(ModItems.ATTACK_UP_POTION_BAG.get(), PotionBag.getPotionBagItems());
        put(OreItems.WRAQ_ORE_1_ITEM.get(), List.of(
                OreItems.WRAQ_ORE_1_ITEM.get(),
                OreItems.WRAQ_ORE_2_ITEM.get(),
                OreItems.WRAQ_ORE_3_ITEM.get(),
                OreItems.WRAQ_ORE_4_ITEM.get()
        ));
        put(SpurItems.minePiece.get(), List.of(
                SpurItems.minePiece.get(),
                SpurItems.cropPiece.get(),
                SpurItems.logPiece.get(),
                SpurItems.seaPiece.get()
        ));
        put(ModItems.equipPiece0.get(), ForgeEquipUtils.getEquipPieceList().subList(0, 6));
    }};

    private static @NotNull List<ItemStack> getItemStacks(int count, List<ItemAndMaxNum> loot) {
        Set<Integer> randomList = new HashSet<>();
        Random random = new Random();
        while (randomList.size() < count) {
            randomList.add(random.nextInt(loot.size()));
        }
        List<ItemStack> contents = new ArrayList<>();
        randomList.forEach(index -> {
            ItemAndMaxNum itemAndMaxNum = loot.get(index);
            Item item = itemAndMaxNum.item;
            if (specificItemMap.containsKey(item)) {
                List<Item> itemList = specificItemMap.get(item);
                contents.add(new ItemStack(itemList.get(random.nextInt(itemList.size())),
                        random.nextInt(1, itemAndMaxNum.maxNum + 1)));
            } else {
                contents.add(new ItemStack(item, random.nextInt(1, itemAndMaxNum.maxNum + 1)));
            }
        });
        return contents;
    }

    public static List<Pair<ItemStack, Integer>> getBonusContent(int tier) {
        List<Pair<ItemStack, Integer>> slotStacks = new ArrayList<>();

        List<ItemStack> contents = new ArrayList<>();
        if (tier >= 0) {
            contents.addAll(getItemStacks(6, tier0Loot));
        }
        if (tier >= 1) {
            contents.addAll(getItemStacks(2, tier1Loot));
        }
        if (tier >= 2) {
            contents.addAll(getItemStacks(3, tier2Loot));
        }
        if (tier >= 3) {
            contents.addAll(getItemStacks(2, tier3Loot));
        }
        Set<Integer> indexSet = new HashSet<>();
        Random random = new Random();
        while (indexSet.size() < contents.size()) {
            indexSet.add(random.nextInt(27));
        }
        List<Integer> indexList = new ArrayList<>(indexSet);
        for (int i = 0 ; i < indexList.size() ; i++) {
            slotStacks.add(new Pair<>(contents.get(i), indexList.get(i)));
        }
        return slotStacks;
    }

    public static String getZoneToPointType(int zoneId) {
        Map<Integer, String> itemMap = new HashMap<>() {{
            put(0, Point.DSPT);
            put(1, Point.ELPT);
            put(2, Point.NTPT);
            put(3, Point.ELPT);
            put(4, Point.ELPT);
            put(5, Point.SKPT);
            put(6, Point.ELPT);
            put(7, Point.EDPT);
            put(8, Point.MTPT);
            put(9, Point.MTPT);
        }};
        return itemMap.getOrDefault(zoneId, Point.EXPT);
    }
}
