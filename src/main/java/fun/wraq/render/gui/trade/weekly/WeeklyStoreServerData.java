package fun.wraq.render.gui.trade.weekly;

import fun.wraq.common.fast.Tick;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import java.util.ArrayList;
import java.util.List;

public class WeeklyStoreServerData extends SavedData {

    public static final String NAME = "weekly_store_data";

    private final List<String> weeklyStoreDataList = new ArrayList<>();
    private String lastRefreshTime = "";
    private int issueCount = 0;

    // 注意setDirty方法的使用
    public List<String> getWeeklyStoreDataList() {
        return weeklyStoreDataList;
    }

    public String getLastRefreshTime() {
        return lastRefreshTime;
    }

    public void setLastRefreshTime(String lastRefreshTime) {
        this.lastRefreshTime = lastRefreshTime;
    }

    public int getIssueCount() {
        return issueCount;
    }

    public void setIssueCount(int issueCount) {
        this.issueCount = issueCount;
    }

    public static WeeklyStoreServerData create() {
        return new WeeklyStoreServerData();
    }

    private final String weeklyStoreInfoDataKey = "market_item_info";
    private final String weeklyStoreListDataKey = "market_item_info_list";
    private final String lastRefreshTimeDataKey = "lastRefreshTime";
    private final String issueCountDataKey = "issueCount";
    @Override
    public CompoundTag save(CompoundTag pCompoundTag) {
        ListTag listTag = new ListTag();
        weeklyStoreDataList.forEach(s -> {
            CompoundTag tag = new CompoundTag();
            tag.putString(weeklyStoreInfoDataKey, s);
            listTag.add(tag);
        });
        pCompoundTag.put(weeklyStoreListDataKey, listTag);
        pCompoundTag.putString(lastRefreshTimeDataKey, lastRefreshTime);
        pCompoundTag.putInt(issueCountDataKey, issueCount);
        return pCompoundTag;
    }

    public WeeklyStoreServerData load(CompoundTag nbt) {
        WeeklyStoreServerData data = this.create();
        ListTag listNBT = (ListTag) nbt.get(weeklyStoreListDataKey);
        if (listNBT != null) {
            for (Tag value : listNBT) {
                CompoundTag tag = (CompoundTag) value;
                weeklyStoreDataList.add(tag.getString(weeklyStoreInfoDataKey));
            }
        }
        lastRefreshTime = nbt.getString(lastRefreshTimeDataKey);
        issueCount = nbt.getInt(issueCountDataKey);
        return data;
    }

    // 下面的代码可以不修改

    public static WeeklyStoreServerData decode(CompoundTag tag){
        WeeklyStoreServerData marketItemData = WeeklyStoreServerData.create();
        marketItemData.load(tag);
        return marketItemData;
    }

    public static WeeklyStoreServerData get(Level worldIn) {
        if (!(worldIn instanceof ServerLevel)) {
            throw new RuntimeException("Attempted to get the data from a client world. This is wrong.");
        }
        ServerLevel world = worldIn.getServer().getLevel(ServerLevel.OVERWORLD);
        DimensionDataStorage dataStorage = world.getDataStorage();
        return dataStorage.computeIfAbsent(WeeklyStoreServerData::decode, WeeklyStoreServerData::create, WeeklyStoreServerData.NAME);
    }

    private static WeeklyStoreServerData instance;

    public static WeeklyStoreServerData getInstance() {
        if (instance == null) {
            instance = get(Tick.server.overworld());
        }
        return instance;
    }
}
