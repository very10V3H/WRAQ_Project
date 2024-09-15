package com.very.wraq.process.system.tower;

import com.very.wraq.common.Compute;
import com.very.wraq.process.system.WorldRecordInfo;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.*;

public record TowerTimeRecord(int floor, String name, int tick) {


    public static void refreshRecord(int floor, Player player, int tick) {
        List<TowerTimeRecord> list = eachFloorRecord.get(floor);
        TowerTimeRecord record = new TowerTimeRecord(floor, player.getName().getString(), tick);
        boolean refreshRecord = false;
        TowerTimeRecord beatRecord = null;
        int beatRank = 0;
        for (TowerTimeRecord towerTimeRecord : list) {
            if (record.tick < towerTimeRecord.tick) {
                refreshRecord = true;
                beatRecord = towerTimeRecord;
                beatRank = list.indexOf(towerTimeRecord);
                break;
            }
        }
        if (refreshRecord || list.size() < 5) {
            list.add(record);
            list.sort(new Comparator<TowerTimeRecord>() {
                @Override
                public int compare(TowerTimeRecord o1, TowerTimeRecord o2) {
                    return o1.tick - o2.tick;
                }
            });
            while (list.size() > 5) list.remove(list.size() - 1);
            if (beatRecord != null) {
                Tower.towerTypeFormatBroad(player.level(),
                        Component.literal(player.getName().getString()).withStyle(CustomStyle.styleOfWorld).
                                append(Component.literal(" 在").withStyle(ChatFormatting.WHITE)).
                                append(Component.literal("本源回廊 - " + Tower.numToRoma[floor - 1]).withStyle(CustomStyle.styleOfWorld)).
                                append(Component.literal(" 的挑战中，超越").withStyle(ChatFormatting.WHITE)).
                                append(Component.literal(beatRecord.name).withStyle(CustomStyle.styleOfWorld)).
                                append(Component.literal(" 在排行榜上达到").withStyle(ChatFormatting.WHITE)).
                                append(Component.literal("Rank #" + beatRank).withStyle(CustomStyle.styleOfGold)));
            }
            Tower.towerTypeFormatBroad(player.level(), Component.literal("本源回廊 - " + Tower.numToRoma[floor - 1]).withStyle(CustomStyle.styleOfWorld).
                    append(Component.literal(" 的挑战排行榜如下:").withStyle(ChatFormatting.WHITE)));
            for (int i = 0; i < list.size(); i++) {
                TowerTimeRecord towerTimeRecord = list.get(i);
                Compute.broad(player.level(), Component.literal(" ".repeat(16) + "#" + (i + 1)).withStyle(CustomStyle.styleOfWorld).
                        append(Component.literal(" " + towerTimeRecord.name).withStyle(CustomStyle.styleOfWorld)).
                        append(Component.literal(" " + String.format("%.2f", towerTimeRecord.tick * 0.05) + "s")));
            }
        }
    }

    public static Map<Integer, List<TowerTimeRecord>> eachFloorRecord = new HashMap<>();

    public static void readFromWorldRecordInfo() {
        for (int i = 1; i <= 6; i++) {
            eachFloorRecord.put(i, new ArrayList<>());
            List<TowerTimeRecord> list = eachFloorRecord.get(i);
            for (int j = 1; j <= 5; j++) {
                String value;
                value = WorldRecordInfo.recordInfoMap.getOrDefault("tower" + i + "_" + j, null);
                if (value != null) list.add(new TowerTimeRecord(i, getNameFromString(value), getTickFromString(value)));
            }
            list.sort(new Comparator<TowerTimeRecord>() {
                @Override
                public int compare(TowerTimeRecord o1, TowerTimeRecord o2) {
                    return o1.tick - o2.tick;
                }
            });
        }
    }

    // 形如（tower1_1, #very_H#1200） //
    public static void writeToWorldRecordInfo() {
        for (int i = 1; i <= 6; i++) {
            List<TowerTimeRecord> list = eachFloorRecord.getOrDefault(i, null);
            if (list != null) {
                for (int j = 0; j < list.size(); j++) {
                    if (j < 5) {
                        TowerTimeRecord towerTimeRecord = list.get(j);
                        String key = "tower" + i + "_" + (j + 1);
                        String value = "#" + towerTimeRecord.name + "#" + towerTimeRecord.tick;
                        WorldRecordInfo.recordInfoMap.put(key, value);
                    }
                }
            }
        }
    }

    public static int getTickFromString(String value) {
        int index = 0;
        for (int i = value.length() - 1; i >= 0; i--) {
            if (value.charAt(i) == '#') {
                index = i;
                break;
            }
        }
        return Integer.parseInt(value.substring(index + 1));
    }

    public static String getNameFromString(String value) {
        int index = 0;
        for (int i = value.length() - 1; i >= 0; i--) {
            if (value.charAt(i) == '#') {
                index = i;
                break;
            }
        }
        return value.substring(1, index);
    }
}
