package fun.wraq.render.gui;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;

public class SingleItemChangePurchaseLimit {

    public static Map<String, Integer> clientDataMap = new HashMap<>();

    private static final String PURCHASE_LIMIT = "purchase_limit";

    public static class Type {
        public static final String NULL = "purchase_limit_null";
        public static final String WEEKLY = "purchase_limit_weekly";
    }

    private static CompoundTag getPlayerPurchaseLimitData(Player player) {
        CompoundTag tag = player.getPersistentData();
        if (!tag.contains(PURCHASE_LIMIT)) {
            tag.put(PURCHASE_LIMIT, new CompoundTag());
        }
        return tag.getCompound(PURCHASE_LIMIT);
    }

    public static CompoundTag getPlayerPurchaseLimitData(Player player, String type) {
        CompoundTag tag = getPlayerPurchaseLimitData(player);
        if (!tag.contains(type)) {
            tag.put(type, new CompoundTag());
        }
        return tag.getCompound(type);
    }

    public static boolean check(Player player, SingleItemChangeRecipe recipe) {
        if (recipe.getDataKey().equals(Type.NULL)) return true;
        String dataKey = recipe.getDataKey();
        return getPlayerPurchaseLimitData(player, recipe.limitType()).getInt(dataKey) < recipe.limitTimes();
    }

    public static int getTimes(Player player, SingleItemChangeRecipe recipe) {
        return getPlayerPurchaseLimitData(player, recipe.limitType()).getInt(recipe.getDataKey());
    }

    public static void addTimes(Player player, SingleItemChangeRecipe recipe) {
        String dataKey = recipe.getDataKey();
        CompoundTag data = getPlayerPurchaseLimitData(player, recipe.limitType());
        data.putInt(dataKey, data.getInt(dataKey) + 1);
    }

    public static void sendSingleRecipeTimes(Player player, SingleItemChangeRecipe recipe) {
        // send para (String, Integer) network pack

    }

    public static void sendAllRecipeTimes(Player player) {
        // send map

    }

    public static Map<String, Integer> getPlayerDataMap(Player player) {
        Map<String, Integer> map = new HashMap<>();
        SingleItemChangeRecipe.getRecipeList().forEach(recipe -> {
            map.put(recipe.getDataKey(), SingleItemChangePurchaseLimit.getTimes(player, recipe));
        });
        return map;
    }
}
