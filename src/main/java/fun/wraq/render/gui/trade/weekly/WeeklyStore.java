package fun.wraq.render.gui.trade.weekly;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.events.core.InventoryCheck;
import fun.wraq.render.gui.villagerTrade.TradeList;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.RandomUtils;

import java.text.ParseException;
import java.util.*;

public class WeeklyStore {
    public static List<WeeklyStoreRecipe> recipes = new ArrayList<>();
    public static int issueCount = 0;
    public static Set<Item> productSet = new HashSet<>();

    public static void handleServerTick() throws ParseException {
        if (recipes.isEmpty()) {
            init();
        } else {
            WeeklyStoreServerData serverData = WeeklyStoreServerData.getInstance();
            Calendar lastRefreshTime = Compute.StringToCalendar(serverData.getLastRefreshTime());
            Calendar calendar = Calendar.getInstance();
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY
                    && calendar.get(Calendar.DATE) != lastRefreshTime.get(Calendar.DATE)) {
                serverData.setLastRefreshTime(Compute.CalendarToString(calendar));
                takeTurns();
            }
        }
    }

    public static void init() {
        WeeklyStoreServerData serverData = WeeklyStoreServerData.getInstance();
        if (serverData.getWeeklyStoreDataList().isEmpty()) {
            takeTurns();
        } else {
            serverData.getWeeklyStoreDataList().forEach(s -> {
                WeeklyStoreRecipe weeklyStoreRecipe = WeeklyStoreRecipe.getRecipeByString(s);
                recipes.add(weeklyStoreRecipe);
                productSet.add(weeklyStoreRecipe.product.getItem());
            });
            issueCount = serverData.getIssueCount();
        }
    }

    public static String WEEKLY_STORE_DATA_KEY = "WeeklyStoreData";

    public static void takeTurns() {
        recipes.clear();
        List<ItemStack> basicMaterials = getRandomly(WeeklyStorePools.getBasicMaterialList(), 5);
        List<ItemStack> seniorMaterials = getRandomly(WeeklyStorePools.getSeniorMaterialList(), 3);
        List<ItemStack> specialMaterials = getRandomly(WeeklyStorePools.getSpecialMaterialList(), 1);
        List<ItemStack> basicRewards = getRandomly(WeeklyStorePools.getBasicRewardList(), 10);
        List<ItemStack> seniorRewards = getRandomly(WeeklyStorePools.getSeniorRewardList(), 5);
        List<ItemStack> specialRewards = getRandomly(WeeklyStorePools.getSpecialRewardList(), 5);
        basicRewards.forEach(reward -> {
            recipes.add(new WeeklyStoreRecipe(getRandomly(basicMaterials, 3), reward, 4));
            productSet.add(reward.getItem());
        });
        seniorRewards.forEach(reward -> {
            List<ItemStack> materialList = new ArrayList<>();
            materialList.addAll(getRandomly(basicMaterials, 1, 2));
            materialList.addAll(getRandomly(seniorMaterials, 2));
            recipes.add(new WeeklyStoreRecipe(materialList, reward, 2));
            productSet.add(reward.getItem());
        });
        specialRewards.forEach(reward -> {
            List<ItemStack> materialList = new ArrayList<>();
            materialList.addAll(getRandomly(basicMaterials, 1, 3));
            materialList.addAll(getRandomly(seniorMaterials, 1, 2));
            materialList.addAll(getRandomly(specialMaterials, 1, 1));
            recipes.add(new WeeklyStoreRecipe(materialList, reward, 1));
            productSet.add(reward.getItem());
        });
        WeeklyStoreServerData serverData = WeeklyStoreServerData.getInstance();
        serverData.getWeeklyStoreDataList().clear();
        recipes.forEach(recipe -> {
            serverData.getWeeklyStoreDataList().add(recipe.toString());
            TradeList.tradeRecipeMap.put(recipe.product, recipe.getMaterialList());
        });
        TradeList.tradeContent.put(WEEKLY_STORE_DATA_KEY, recipes.stream().map(recipe -> recipe.product).toList());
        serverData.setIssueCount(serverData.getIssueCount() + 1);
        issueCount = serverData.getIssueCount();
        serverData.setDirty();
        Compute.formatBroad(Te.s("商店", CustomStyle.styleOfGold),
                Te.s("商店已轮换.", CustomStyle.styleOfGold));
    }

    public static List<ItemStack> getRandomly(List<ItemStack> from, int num, int count) {
        List<ItemStack> target = new ArrayList<>();
        if (from.size() < num) {
            return from;
        }
        Set<Integer> indexSet = new HashSet<>();
        while (indexSet.size() < num) {
            indexSet.add(RandomUtils.nextInt(0, from.size()));
        }
        for (Integer i : indexSet) {
            target.add(new ItemStack(from.get(i).getItem(), from.get(i).getCount() * count));
        }
        return target;
    }

    public static List<ItemStack> getRandomly(List<ItemStack> from, int num) {
        return getRandomly(from, num, 1);
    }

    public static boolean canPlayerBuy(Player player, ItemStack product, List<ItemStack> material) {
        if (!productSet.contains(product.getItem()) || material.size() > 3) {
            return true;
        } else {
            WeeklyStoreRecipe weeklyStoreRecipe = recipes.stream()
                    .filter(recipe -> recipe.isSameRecipe(material, product))
                    .findAny().orElse(null);
            if (weeklyStoreRecipe == null) {
                return false;
            }
            return WeeklyStorePlayerData
                    .getSpecificRecipeCount(player, weeklyStoreRecipe.toString()) < weeklyStoreRecipe.count;
        }
    }

    public static void afterPlayerBuy(Player player, ItemStack product, List<ItemStack> material) {
        if (!productSet.contains(product.getItem()) || material.size() > 3) {
            return;
        } else {
            WeeklyStoreRecipe weeklyStoreRecipe = recipes.stream()
                    .filter(recipe -> recipe.isSameRecipe(material, product))
                    .findAny().orElse(null);
            if (weeklyStoreRecipe == null) {
                return;
            }
            WeeklyStorePlayerData.incrementSpecificRecipeCount(player, weeklyStoreRecipe.toString());
            WeeklyStorePlayerData.sendDataToClient(player);
            InventoryCheck.addOwnerTagToItemStack(player, product);
        }
    }
}
