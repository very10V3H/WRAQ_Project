package fun.wraq.common.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import java.util.ArrayList;
import java.util.List;

public class WorldCommonData extends SavedData {

    public static final String DAT_NAME = "wraq_world_common_data";
    private final String WORLD_COMMON_DATA_KEY = "world_common_data";
    private final String WORLD_COMMON_DATA_LIST_KEY = "world_common_data_list";

    private final List<String> dataList = new ArrayList<>();

    // 注意setDirty方法的使用
    public List<String> getDataList() {
        return dataList;
    }

    public static WorldCommonData create() {
        return new WorldCommonData();
    }

    @Override
    public CompoundTag save(CompoundTag pCompoundTag) {
        ListTag listTag = new ListTag();
        dataList.forEach(s -> {
            CompoundTag tag = new CompoundTag();
            tag.putString(WORLD_COMMON_DATA_KEY, s);
            listTag.add(tag);
        });
        pCompoundTag.put(WORLD_COMMON_DATA_LIST_KEY, listTag);
        return pCompoundTag;
    }

    public WorldCommonData load(CompoundTag nbt) {
        WorldCommonData data = this.create();
        ListTag listNBT = (ListTag) nbt.get(WORLD_COMMON_DATA_LIST_KEY);
        if (listNBT != null) {
            for (Tag value : listNBT) {
                CompoundTag tag = (CompoundTag) value;
                dataList.add(tag.getString(WORLD_COMMON_DATA_KEY));
            }
        }
        return data;
    }

    public static WorldCommonData decode(CompoundTag tag){
        WorldCommonData marketItemData = WorldCommonData.create();
        marketItemData.load(tag);
        return marketItemData;
    }

    public static WorldCommonData get(Level worldIn) {
        if (!(worldIn instanceof ServerLevel)) {
            throw new RuntimeException("Attempted to get the data from a client world. This is wrong.");
        }
        ServerLevel world = worldIn.getServer().getLevel(ServerLevel.OVERWORLD);
        DimensionDataStorage dataStorage = world.getDataStorage();
        return dataStorage.computeIfAbsent(WorldCommonData::decode, WorldCommonData::create, WorldCommonData.DAT_NAME);
    }

    private static WorldCommonData instance;

    public static WorldCommonData getInstance(Level worldIn) {
        if (instance == null) instance = get(worldIn);
        return instance;
    }
}
