package com.very.wraq.common.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import java.util.HashMap;
import java.util.Map;

public class MarketProfitData extends SavedData {

    public static final String NAME = "market_profit_data";

    private final Map<String, Double> marketProfitInfo = new HashMap<>();

    // 注意setDirty方法的使用
    public Map<String, Double> getMarketProfitInfo() {
        return marketProfitInfo;
    }

    public static MarketProfitData create() {
        return new MarketProfitData();
    }

    private final String marketProfitInfoListKeyString = "market_profit_info_list";
    @Override
    public CompoundTag save(CompoundTag pCompoundTag) {
        CompoundTag tag = new CompoundTag();
        marketProfitInfo.entrySet().forEach(entry -> {
            tag.putDouble(entry.getKey(), entry.getValue());
        });
        pCompoundTag.put(marketProfitInfoListKeyString, tag);
        return pCompoundTag;
    }

    public MarketProfitData load(CompoundTag nbt) {
        MarketProfitData data = this.create();
        CompoundTag tag = nbt.getCompound(marketProfitInfoListKeyString);
        tag.getAllKeys().forEach(name -> {
            marketProfitInfo.put(name, tag.getDouble(name));
        });
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
