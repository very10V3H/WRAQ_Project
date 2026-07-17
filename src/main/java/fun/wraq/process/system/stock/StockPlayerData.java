/** AI-Generated, 2026-05-17 */
package fun.wraq.process.system.stock;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class StockPlayerData {

    private static final String STOCK_DATA_KEY = "StockData";
    private static final String POSITIONS_KEY = "positions";

    public static List<StockPosition> getPositions(Player player) {
        List<StockPosition> positions = new ArrayList<>();
        CompoundTag stockData = getOrCreateStockData(player);
        ListTag list = stockData.getList(POSITIONS_KEY, Tag.TAG_COMPOUND);
        for (int i = 0; i < list.size(); i++) {
            CompoundTag tag = list.getCompound(i);
            StockIndex index;
            try {
                index = StockIndex.valueOf(tag.getString("index"));
            } catch (IllegalArgumentException e) {
                continue;
            }
            int leverage = tag.getInt("leverage");
            double buyPrice = tag.getDouble("buyPrice");
            int quantity = tag.getInt("quantity");
            String currencyType = tag.getString("currencyType");
            long buyTimestamp = tag.getLong("buyTimestamp");
            double investment = tag.getDouble("investment");
            positions.add(new StockPosition(index, leverage, buyPrice, quantity,
                    currencyType, buyTimestamp, investment));
        }
        return positions;
    }

    public static void addPosition(Player player, StockPosition position) {
        CompoundTag stockData = getOrCreateStockData(player);
        ListTag list = stockData.getList(POSITIONS_KEY, Tag.TAG_COMPOUND);
        CompoundTag tag = new CompoundTag();
        tag.putString("index", position.getIndex().name());
        tag.putInt("leverage", position.getLeverage());
        tag.putDouble("buyPrice", position.getBuyPrice());
        tag.putInt("quantity", position.getQuantity());
        tag.putString("currencyType", position.getCurrencyType());
        tag.putLong("buyTimestamp", position.getBuyTimestamp());
        tag.putDouble("investment", position.getInvestment());
        list.add(tag);
        stockData.put(POSITIONS_KEY, list);
        saveStockData(player, stockData);
    }

    public static void removePosition(Player player, int positionIndex) {
        CompoundTag stockData = getOrCreateStockData(player);
        ListTag list = stockData.getList(POSITIONS_KEY, Tag.TAG_COMPOUND);
        if (positionIndex >= 0 && positionIndex < list.size()) {
            list.remove(positionIndex);
            stockData.put(POSITIONS_KEY, list);
            saveStockData(player, stockData);
        }
    }

    public static void clearAllPositions(Player player) {
        CompoundTag stockData = getOrCreateStockData(player);
        stockData.put(POSITIONS_KEY, new ListTag());
        saveStockData(player, stockData);
    }

    private static CompoundTag getOrCreateStockData(Player player) {
        CompoundTag persistentData = player.getPersistentData();
        if (!persistentData.contains(STOCK_DATA_KEY)) {
            CompoundTag stockData = new CompoundTag();
            stockData.put(POSITIONS_KEY, new ListTag());
            persistentData.put(STOCK_DATA_KEY, stockData);
        }
        return persistentData.getCompound(STOCK_DATA_KEY);
    }

    private static void saveStockData(Player player, CompoundTag stockData) {
        player.getPersistentData().put(STOCK_DATA_KEY, stockData);
    }
}
