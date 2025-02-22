package fun.wraq.events.mob.instance.instances.tower;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.WorldCommonData;
import fun.wraq.events.mob.instance.instances.tower.network.ManaTowerS2CPacket;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.system.tower.Tower;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ManaTowerData {
    // 形如ManaTowerRecord#Dev#2400
    public record TimeRecord(String playerName, int usedTick) {
    }

    public static List<TimeRecord> records;
    public static final String DATA_PREFIX = "ManaTowerRecord";

    public static List<TimeRecord> getRecords() {
        if (records == null) {
            records = new ArrayList<>();
            List<String> dataList = WorldCommonData.getInstance(Tick.server.overworld())
                    .getDataList()
                    .stream().filter(s -> s.startsWith(DATA_PREFIX)).toList();
            List<TimeRecord> recordList = dataList
                    .stream().map(ManaTowerData::getRecord)
                    .toList();
            records.addAll(recordList);
        }
        return records;
    }

    public static void removeRecords() {
        WorldCommonData instance = WorldCommonData.getInstance(Tick.server.overworld());
        instance.getDataList().removeIf(s -> s.startsWith(DATA_PREFIX));
        instance.setDirty();
        Compute.formatBroad(Te.s("炼魔塔", CustomStyle.MANA_TOWER_STYLE),
                Te.s("纪录已重置."));
    }

    public static int clientStartTick = 0;
    public static int clientExpiredTick = 0;
    public static int clientFloor = 0;

    public static void sendTickToClient(Player player, int startTick, int expiredTick, int clientFloor) {
        ModNetworking.sendToClient(new ManaTowerS2CPacket(startTick, expiredTick, clientFloor), player);
    }

    public static TimeRecord getRecord(String data) {
        String[] split = data.split("#");
        return new TimeRecord(split[1], Integer.parseInt(split[2]));
    }

    public static String getData(TimeRecord record) {
        return DATA_PREFIX + "#" + record.playerName + "#" + record.usedTick;
    }

    public static void writeToData() {
        WorldCommonData instance = WorldCommonData.getInstance(Tick.server.overworld());
        List<String> worldCommonDataList = instance.getDataList();
        worldCommonDataList.removeIf(s -> s.startsWith(DATA_PREFIX));
        getRecords().forEach(record -> worldCommonDataList.add(getData(record)));
        instance.setDirty();
    }

    public static void setPlayerManaTowerRecord(Player player, int usedTick) {
        Compute.getChallengeRecordData(player).putInt(DATA_PREFIX, usedTick);
    }

    public static int getPlayerManaTowerRecord(Player player) {
        CompoundTag recordData = Compute.getChallengeRecordData(player);
        return recordData.contains(DATA_PREFIX) ? recordData.getInt(DATA_PREFIX) : -1;
    }

    public static final String MANA_TOWER_DAILY_RECORD_DATA_KEY = "ManaTowerDailyRecord";

    public static void setPlayerManaTowerDailyRecord(Player player, int usedTick) {
        Compute.getChallengeRecordData(player).putInt(MANA_TOWER_DAILY_RECORD_DATA_KEY, usedTick);
    }

    public static int getPlayerManaTowerDailyRecord(Player player) {
        CompoundTag recordData = Compute.getChallengeRecordData(player);
        return recordData.contains(MANA_TOWER_DAILY_RECORD_DATA_KEY)
                ? recordData.getInt(MANA_TOWER_DAILY_RECORD_DATA_KEY) : -1;
    }

    public static void resetDailyRecord(Player player) {
        setPlayerManaTowerDailyRecord(player, -1);
    }

    public static int getRecordTier(int usedTick) {
        if (usedTick == -1) {
            return 0;
        }
        int seconds = usedTick / 20;
        if (seconds <= 60) {
            return 8;
        } else if (seconds <= 90) {
            return 7;
        } else if (seconds <= 120) {
            return 6;
        } else if (seconds <= 160) {
            return 5;
        } else if (seconds <= 180) {
            return 4;
        } else if (seconds <= 200) {
            return 3;
        } else if (seconds <= 220) {
            return 2;
        } else if (seconds <= 240) {
            return 1;
        } else {
            return 0;
        }
    }

    public static Component getRecordTimeDescription(int tier) {
        switch (tier) {
            case 8 -> {
                return Te.s("A+", ChatFormatting.RED);
            }
            case 7 -> {
                return Te.s("A", ChatFormatting.RED);
            }
            case 6 -> {
                return Te.s("A-", ChatFormatting.GOLD);
            }
            case 5 -> {
                return Te.s("B+", ChatFormatting.GOLD);
            }
            case 4 -> {
                return Te.s("B", ChatFormatting.LIGHT_PURPLE);
            }
            case 3 -> {
                return Te.s("B-", ChatFormatting.LIGHT_PURPLE);
            }
            case 2 -> {
                return Te.s("C+", ChatFormatting.AQUA);
            }
            case 1 -> {
                return Te.s("C", ChatFormatting.GREEN);
            }
            default -> {
                return Te.s("D", ChatFormatting.GRAY);
            }
        }
    }

    public static Component getRecordTickDescription(int tick) {
        if (tick == -1) {
            return Te.s("NULL");
        }
        double seconds = tick * 0.05;
        return Te.s(String.format("%.2fs", seconds), "(", CustomStyle.styleOfMoon,
                getRecordTimeDescription(getRecordTier(tick)), ")", CustomStyle.styleOfMoon);
    }

    public static void onFinishedReward(Player player, int usedTick) {
        int everRecord = getPlayerManaTowerDailyRecord(player);
        int everTier = getRecordTier(everRecord);
        int tier = getRecordTier(usedTick);
        if (tier > everTier) {
            Tower.givePlayerStar(player, (tier - everTier) * 5, "炼魔塔每日奖励");
        }
    }

    public static void onPlayerFinishedChallenge(Player player, int usedTick) {
        List<TimeRecord> recordList = getRecords();
        List<TimeRecord> oldList = new ArrayList<>(getRecords());
        boolean change = false;
        boolean refreshSelf = false;
        if (recordList.stream().anyMatch(timeRecord -> timeRecord.playerName.equals(Name.get(player)))) {
            TimeRecord timeRecord = recordList.stream().filter(record -> record.playerName.equals(Name.get(player)))
                    .findFirst().orElse(null);
            if (timeRecord != null) {
                if (timeRecord.usedTick > usedTick) {
                    recordList.remove(timeRecord);
                    recordList.add(new TimeRecord(Name.get(player), usedTick));
                    change = true;
                    refreshSelf = true;
                }
            }
        } else {
            if (recordList.size() < 8) {
                recordList.add(new TimeRecord(Name.get(player), usedTick));
                change = true;
            } else {
                int longestTickInRecord = -1;
                for (TimeRecord record : recordList) {
                    if (record.usedTick > longestTickInRecord) {
                        longestTickInRecord = record.usedTick;
                    }
                }
                if (longestTickInRecord > usedTick) {
                    change = true;
                }
                recordList.add(new TimeRecord(Name.get(player), usedTick));
            }
        }
        recordList.sort(new Comparator<TimeRecord>() {
            @Override
            public int compare(TimeRecord o1, TimeRecord o2) {
                return o1.usedTick - o2.usedTick;
            }
        });
        sendMSG(player, Te.s("本次挑战的纪录为:", getRecordTickDescription(usedTick)));
        int dailyRecord = getPlayerManaTowerDailyRecord(player);
        if (dailyRecord == -1 || dailyRecord > usedTick) {
            int everUsedTick = getPlayerManaTowerDailyRecord(player);
            onFinishedReward(player, usedTick);
            setPlayerManaTowerDailyRecord(player, usedTick);
            sendMSG(player, Te.s("你刷新了今天的纪录:", getRecordTickDescription(everUsedTick),
                    " -> ", getRecordTickDescription(usedTick)));

        }
        int historyRecord = getPlayerManaTowerRecord(player);
        if (historyRecord == -1 || historyRecord > usedTick) {
            int everUsedTick = getPlayerManaTowerRecord(player);
            setPlayerManaTowerRecord(player, usedTick);
            sendMSG(player, Te.s("你刷新了自己的最好纪录:", getRecordTickDescription(everUsedTick),
                    " -> ", getRecordTickDescription(usedTick)));
        }
        broad(Te.s(player, " 以", getRecordTickDescription(usedTick), "的成绩通关了 ",
                "炼魔塔", CustomStyle.MANA_TOWER_STYLE));
        if (change) {
            Compute.formatBroad(Te.s("炼魔塔", CustomStyle.MANA_TOWER_STYLE),
                    Te.s("炼魔塔", CustomStyle.MANA_TOWER_STYLE, "挑战纪录发生了变化!"));
            for (int i = 0; i < Math.min(8, recordList.size()) ; i++) {
                TimeRecord timeRecord = recordList.get(i);
                if (oldList.contains(timeRecord) || refreshSelf) {
                    int oldIndex = oldList.indexOf(timeRecord);
                    Component sign;
                    if (oldIndex == i) {
                        sign = Te.s("-", ChatFormatting.AQUA);
                    } else if (oldIndex > i) {
                        sign = Te.s("↑", ChatFormatting.GREEN);
                    } else {
                        sign = Te.s("↓", ChatFormatting.RED);
                    }
                    if (refreshSelf && timeRecord.playerName.equals(Name.get(player))) {
                        sign = Te.s("NR", ChatFormatting.GOLD);
                    }
                    Compute.broad(player.level(), Te.s(" ".repeat(8),
                            (i + 1) + ".", CustomStyle.MANA_TOWER_STYLE, timeRecord.playerName,
                            " - ", getRecordTickDescription(timeRecord.usedTick), " ", sign));
                } else {
                    Compute.broad(player.level(), Te.s(" ".repeat(8),
                            (i + 1) + ".", CustomStyle.MANA_TOWER_STYLE, timeRecord.playerName,
                            " - ", getRecordTickDescription(timeRecord.usedTick), " ", "new", ChatFormatting.GOLD));
                }
            }
        }
        writeToData();
    }

    public static void sendMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("炼魔塔", CustomStyle.MANA_TOWER_STYLE), content);
    }

    public static void broad(Component content) {
        Compute.formatBroad(Te.s("炼魔塔", CustomStyle.MANA_TOWER_STYLE), content);
    }
}
