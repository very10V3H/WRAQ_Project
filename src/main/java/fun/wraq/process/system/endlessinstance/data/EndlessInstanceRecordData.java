package fun.wraq.process.system.endlessinstance.data;

import com.mojang.logging.LogUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class EndlessInstanceRecordData extends SavedData {

    private static final String NAME = "endless_instance_data";

    private final List<String> endlessInstanceInfoStringList = new ArrayList<>();

    // 注意setDirty方法的使用
    private List<String> getEndlessInstanceInfoStringList() {
        return endlessInstanceInfoStringList;
    }

    private static EndlessInstanceRecordData create() {
        return new EndlessInstanceRecordData();
    }

    private final String marketItemInfoKeyString = "endless_instance_info";
    private final String marketItemInfoListKeyString = "endless_instance_info_list";
    @Override
    public CompoundTag save(CompoundTag pCompoundTag) {
        ListTag listTag = new ListTag();
        endlessInstanceInfoStringList.forEach(s -> {
            CompoundTag tag = new CompoundTag();
            tag.putString(marketItemInfoKeyString, s);
            listTag.add(tag);
        });
        pCompoundTag.put(marketItemInfoListKeyString, listTag);
        LogUtils.getLogger().info("Endless instance info saved!");
        return pCompoundTag;
    }

    private EndlessInstanceRecordData load(CompoundTag nbt) {
        EndlessInstanceRecordData data = this.create();
        ListTag listNBT = (ListTag) nbt.get(marketItemInfoListKeyString);
        if (listNBT != null) {
            for (Tag value : listNBT) {
                CompoundTag tag = (CompoundTag) value;
                endlessInstanceInfoStringList.add(tag.getString(marketItemInfoKeyString));
            }
        }
        return data;
    }

    private static EndlessInstanceRecordData decode(CompoundTag tag){
        EndlessInstanceRecordData marketItemData = EndlessInstanceRecordData.create();
        marketItemData.load(tag);
        return marketItemData;
    }

    private static EndlessInstanceRecordData get(Level worldIn) {
        if (!(worldIn instanceof ServerLevel)) {
            throw new RuntimeException("Attempted to get the data from a client world. This is wrong.");
        }
        ServerLevel world = worldIn.getServer().getLevel(ServerLevel.OVERWORLD);
        DimensionDataStorage dataStorage = world.getDataStorage();
        return dataStorage.computeIfAbsent(EndlessInstanceRecordData::decode, EndlessInstanceRecordData::create, EndlessInstanceRecordData.NAME);
    }

    private static EndlessInstanceRecordData instance;

    private static EndlessInstanceRecordData getInstance(Level worldIn) {
        if (instance == null) instance = get(worldIn);
        return instance;
    }

    public static void record(String instanceName, ServerPlayer player, int killCount) {
        EndlessInstanceRecordData instanceRecordData = getInstance(player.getServer().getLevel(Level.OVERWORLD));
        List<String> dataList = instanceRecordData.getEndlessInstanceInfoStringList();
        List<RecordData> recordDataList = transformDataList(dataList);
        List<RecordData> eachInstanceRecordData = recordDataList.stream()
                .filter(data -> data.instanceName.equals(instanceName)).toList();
        RecordData recordData = new RecordData(instanceName, player.getName().getString(), killCount);

        // 先查找榜中是否有自己的记录
        RecordData selfData = eachInstanceRecordData.stream()
                .filter(data -> data.name.equals(player.getName().getString())).findAny().orElse(null);
        String dataString = recordData.instanceName + "#" + recordData.name + "#" + recordData.killCount;
        if (selfData != null) {
            if (selfData.killCount < recordData.killCount) {
                recordDataList.remove(selfData);
                dataList.add(dataString);
                instanceRecordData.setDirty();
            }
        } else {
            if (eachInstanceRecordData.size() < 8) {
                recordDataList.add(recordData);
                dataList.add(dataString);
                instanceRecordData.setDirty();
            } else {
                PriorityQueue<RecordData> priorityQueue = new PriorityQueue<>(new Comparator<RecordData>() {
                    @Override
                    public int compare(RecordData o1, RecordData o2) {
                        return o1.killCount - o2.killCount;
                    }
                });
                priorityQueue.addAll(eachInstanceRecordData);
                if (priorityQueue.peek() != null && priorityQueue.peek().killCount < recordData.killCount) {
                    RecordData removedData = priorityQueue.remove();
                    priorityQueue.add(recordData);
                    dataList.removeIf(s -> {
                        String[] split = s.split("#");
                        return split[0].equals(removedData.instanceName) && split[1].equals(removedData.name)
                                && split[2].equals(String.valueOf(removedData.killCount));
                    });
                    dataList.add(dataString);
                    instanceRecordData.setDirty();
                }
            }
        }
    }

    private record RecordData(String instanceName, String name, int killCount) {}
    private static List<RecordData> transformDataList(List<String> dataList) {
        return new ArrayList<>(dataList.stream()
                .map(s -> {
                    String[] split = s.split("#");
                    return new RecordData(split[0], split[1], Integer.parseInt(split[2]));
                }).toList());
    }
}
