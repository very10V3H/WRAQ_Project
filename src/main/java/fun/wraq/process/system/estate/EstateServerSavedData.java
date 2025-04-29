package fun.wraq.process.system.estate;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import java.util.ArrayList;
import java.util.List;

public class EstateServerSavedData extends SavedData {

    public static final String NAME = "estate_server_data";

    private final List<String> estateInfoStringList = new ArrayList<>();

    // 注意setDirty方法的使用
    public List<String> getEstateInfoStringList() {
        return estateInfoStringList;
    }

    public static EstateServerSavedData create() {
        return new EstateServerSavedData();
    }

    private final String estateServerDataKeyString = "estate_server_data";
    private final String estateServerDataListKeyString = "estate_server_data_list";
    @Override
    public CompoundTag save(CompoundTag pCompoundTag) {
        ListTag listTag = new ListTag();
        estateInfoStringList.forEach(s -> {
            CompoundTag tag = new CompoundTag();
            tag.putString(estateServerDataKeyString, s);
            listTag.add(tag);
        });
        pCompoundTag.put(estateServerDataListKeyString, listTag);
        return pCompoundTag;
    }

    public EstateServerSavedData load(CompoundTag nbt) {
        EstateServerSavedData data = this.create();
        ListTag listNBT = (ListTag) nbt.get(estateServerDataListKeyString);
        if (listNBT != null) {
            for (Tag value : listNBT) {
                CompoundTag tag = (CompoundTag) value;
                estateInfoStringList.add(tag.getString(estateServerDataKeyString));
            }
        }
        return data;
    }

    public static EstateServerSavedData decode(CompoundTag tag){
        EstateServerSavedData marketItemData = EstateServerSavedData.create();
        marketItemData.load(tag);
        return marketItemData;
    }

    public static EstateServerSavedData get(Level worldIn) {
        if (!(worldIn instanceof ServerLevel)) {
            throw new RuntimeException("Attempted to get the data from a client world. This is wrong.");
        }
        ServerLevel world = worldIn.getServer().getLevel(ServerLevel.OVERWORLD);
        DimensionDataStorage dataStorage = world.getDataStorage();
        return dataStorage.computeIfAbsent(EstateServerSavedData::decode, EstateServerSavedData::create, EstateServerSavedData.NAME);
    }

    private static EstateServerSavedData instance;

    public static EstateServerSavedData getInstance(Level worldIn) {
        if (instance == null) instance = get(worldIn);
        return instance;
    }
}
