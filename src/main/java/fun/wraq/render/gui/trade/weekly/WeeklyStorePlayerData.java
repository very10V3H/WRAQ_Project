package fun.wraq.render.gui.trade.weekly;

import fun.wraq.common.Compute;
import fun.wraq.networking.ModNetworking;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;

public class WeeklyStorePlayerData {
    private static final String DATA_KEY = "WeeklyStoreData";
    public static CompoundTag getData(Player player) {
        return Compute.getPlayerSpecificKeyCompoundTagData(player, DATA_KEY);
    }

    private static final String ISSUE_COUNT_KEY = "IssueCount";
    public static int getIssueCount(Player player) {
        return getData(player).getInt(ISSUE_COUNT_KEY);
    }

    public static void setIssueCount(Player player, int count) {
        getData(player).putInt(ISSUE_COUNT_KEY, count);
    }

    public static int getSpecificRecipeCount(Player player, String recipe) {
        WeeklyStoreServerData serverData = WeeklyStoreServerData.getInstance();
        if (serverData.getIssueCount() != getIssueCount(player)) {
            player.getPersistentData().remove(DATA_KEY);
            setIssueCount(player, serverData.getIssueCount());
        }
        return getData(player).getInt(recipe);
    }

    public static void incrementSpecificRecipeCount(Player player, String recipe) {
        getData(player).putInt(recipe, getSpecificRecipeCount(player, recipe) + 1);
    }

    public static Map<String, Integer> clientPlayerChangedCount = new HashMap<>();
    public static void sendDataToClient(Player player) {
        Map<String, Integer> map = new HashMap<>();
        WeeklyStore.recipes.forEach(recipe -> {
            map.put(recipe.toString(), getSpecificRecipeCount(player, recipe.toString()));
        });
        ModNetworking.sendToClient(new WeeklyStoreCountDataS2CPacket(map, getIssueCount(player)), player);
        ModNetworking.sendToClient(new WeeklyStoreRecipeDataS2CPacket(WeeklyStore.recipes), player);
    }

    public static int clientIssueCount = 0;
}
