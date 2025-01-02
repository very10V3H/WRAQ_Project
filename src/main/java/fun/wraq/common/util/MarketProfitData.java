package fun.wraq.common.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import java.util.ArrayList;
import java.util.List;

public class MarketProfitData extends SavedData {

    public static final String NAME = "market_profit_data";

    private final List<String> marketProfitInfo = new ArrayList<>();

    // 注意setDirty方法的使用
    public List<String> getMarketProfitInfo() {
        return marketProfitInfo;
    }

    public static MarketProfitData create() {
        return new MarketProfitData();
    }

    private final String marketProfitInfoListKeyString = "market_profit_info_list";
    private final String marketProfitInfoKeyString = "market_profit_info";

    @Override
    public CompoundTag save(CompoundTag pCompoundTag) {
        ListTag listTag = new ListTag();
        marketProfitInfo.forEach(info -> {
            CompoundTag tag = new CompoundTag();
            tag.putString(marketProfitInfoKeyString, info);
            listTag.add(tag);
        });
        pCompoundTag.put(marketProfitInfoListKeyString, listTag);
        return pCompoundTag;
    }

    public MarketProfitData load(CompoundTag nbt) {
        MarketProfitData data = this.create();
        ListTag listTag = (ListTag) nbt.get(marketProfitInfoListKeyString);
        if (listTag != null) {
            listTag.forEach(tag -> {
                marketProfitInfo.add(((CompoundTag) tag).getString(marketProfitInfoKeyString));
            });
        }
        return data;
    }

    public static MarketProfitData decode(CompoundTag tag){
        MarketProfitData marketItemData = MarketProfitData.create();
        marketItemData.load(tag);
        return marketItemData;
    }

    public static MarketProfitData get(Level worldIn) {
        if (!(worldIn instanceof ServerLevel)) {
            throw new RuntimeException("Attempted to get the data from a client world. This is wrong.");
        }
        ServerLevel world = worldIn.getServer().getLevel(ServerLevel.OVERWORLD);
        DimensionDataStorage dataStorage = world.getDataStorage();
        return dataStorage.computeIfAbsent(MarketProfitData::decode, MarketProfitData::create, MarketProfitData.NAME);
    }

    private static MarketProfitData instance;

    public static MarketProfitData getInstance(Level worldIn) {
        if (instance == null) instance = get(worldIn);
        return instance;
    }
}
