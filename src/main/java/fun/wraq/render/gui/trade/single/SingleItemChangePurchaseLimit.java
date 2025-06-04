package fun.wraq.render.gui.trade.single;

import com.google.common.collect.ImmutableMap;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.networking.ModNetworking;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SingleItemChangePurchaseLimit {

    public static Map<String, Integer> clientDataMap = new HashMap<>();

    private static final String PURCHASE_LIMIT = "purchase_limit";

    public static class Type {
        public static final String NULL = "purchase_limit_null";
        public static final String WEEKLY = "purchase_limit_weekly";
        public static final String DAILY = "purchase_limit_daily";
    }

    public static Map<String, Component> TYPE_DESCRIPTION_MAP = ImmutableMap.of(
            Type.NULL, Te.s("无限制"),
            Type.WEEKLY, Te.s("每周", CustomStyle.styleOfLife),
            Type.DAILY, Te.s("每天", CustomStyle.styleOfSunIsland)
    );

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
        if (recipe.limitType.equals(Type.NULL)) return true;
        String dataKey = recipe.getDataKey();
        return getPlayerPurchaseLimitData(player, recipe.limitType).getInt(dataKey) < recipe.limitTimes;
    }

    public static int getTimes(Player player, SingleItemChangeRecipe recipe) {
        return getPlayerPurchaseLimitData(player, recipe.limitType).getInt(recipe.getDataKey());
    }

    public static void addTimes(Player player, SingleItemChangeRecipe recipe) {
        String dataKey = recipe.getDataKey();
        CompoundTag data = getPlayerPurchaseLimitData(player, recipe.limitType);
        data.putInt(dataKey, data.getInt(dataKey) + 1);
        sendSingleRecipeTimes(player, recipe);
    }

    public static void setTimes(Player player, SingleItemChangeRecipe recipe, int times) {
        String dataKey = recipe.getDataKey();
        CompoundTag data = getPlayerPurchaseLimitData(player, recipe.limitType);
        data.putInt(dataKey, 0);
        sendSingleRecipeTimes(player, recipe);
    }

    private static void refresh(Player player, String type) {
        SingleItemChangeRecipe.getRecipeList().forEach(recipe -> {
            if (recipe.limitType.equals(type)) {
                setTimes(player, recipe, 0);
            }
        });
    }

    public static void refreshDaily(Player player) {
        refresh(player, Type.DAILY);
        Compute.sendFormatMSG(player, Te.s("交易", CustomStyle.styleOfSunIsland),
                Te.s("每日限购次数已刷新!"));
    }

    public static void refreshWeekly(Player player) {
        refresh(player, Type.WEEKLY);
        Compute.sendFormatMSG(player, Te.s("交易", CustomStyle.styleOfSunIsland),
                Te.s("每周限购次数已刷新!"));
    }

    public static void sendSingleRecipeTimes(Player player, SingleItemChangeRecipe recipe) {
        // send para (String, Integer) network pack
        ModNetworking.sendToClient(
                new SingleItemChangeSingleRecipeTimeS2CPacket(recipe.getDataKey(), getTimes(player, recipe)),
                (ServerPlayer) player);
    }

    public static void sendAllRecipeTimes(Player player) {
        // send map
        ModNetworking.sendToClient(new SingleItemChangeFullDataS2CPacket(getPlayerDataMap(player)),
                (ServerPlayer) player);
    }

    public static Map<String, Integer> getPlayerDataMap(Player player) {
        Map<String, Integer> map = new HashMap<>();
        SingleItemChangeRecipe.getRecipeList().forEach(recipe -> {
            if (!Objects.equals(recipe.limitType, Type.NULL)) {
                map.put(recipe.getDataKey(), SingleItemChangePurchaseLimit.getTimes(player, recipe));
            }
        });
        return map;
    }
}
