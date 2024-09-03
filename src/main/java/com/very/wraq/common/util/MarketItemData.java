package com.very.wraq.common.util;

import com.mojang.logging.LogUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import java.util.ArrayList;
import java.util.List;

public class MarketItemData extends SavedData {

    public static final String NAME = "market_item_data";

    private final List<String> marketInfoStringList = new ArrayList<>();

    // 注意setDirty方法的使用
    public List<String> getMarketInfoStringList() {
        return marketInfoStringList;
    }

    public static MarketItemData create() {
        return new MarketItemData();
    }

    private final String marketItemInfoKeyString = "market_item_info";
    private final String marketItemInfoListKeyString = "market_item_info_list";
    @Override
    public CompoundTag save(CompoundTag pCompoundTag) {
        ListTag listTag = new ListTag();
        marketInfoStringList.forEach(s -> {
            CompoundTag tag = new CompoundTag();
            tag.putString(marketItemInfoKeyString, s);
            listTag.add(tag);
        });
        pCompoundTag.put(marketItemInfoListKeyString, listTag);
        LogUtils.getLogger().info("Market info saved!");
        return pCompoundTag;
    }

    public MarketItemData load(CompoundTag nbt) {
        MarketItemData data = this.create();
        ListTag listNBT = (ListTag) nbt.get(marketItemInfoListKeyString);
        if (listNBT != null) {
            for (Tag value : listNBT) {
                CompoundTag tag = (CompoundTag) value;
                marketInfoStringList.add(tag.getString(marketItemInfoKeyString));
            }
        }
        return data;
    }

    public static MarketItemData decode(CompoundTag tag){
        MarketItemData marketItemData = MarketItemData.create();
        marketItemData.load(tag);
        return marketItemData;
    }

    public static MarketItemData get(Level worldIn) {
        if (!(worldIn instanceof ServerLevel)) {
            throw new RuntimeException("Attempted to get the data from a client world. This is wrong.");
        }
        ServerLevel world = worldIn.getServer().getLevel(ServerLevel.OVERWORLD);
        DimensionDataStorage dataStorage = world.getDataStorage();
        return dataStorage.computeIfAbsent(MarketItemData::decode, MarketItemData::create, MarketItemData.NAME);
    }

    private static MarketItemData instance;

    public static MarketItemData getInstance(Level worldIn) {
        if (instance == null) instance = get(worldIn);
        return instance;
    }
}
