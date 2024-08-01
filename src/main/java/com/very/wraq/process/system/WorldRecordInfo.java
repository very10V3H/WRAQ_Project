package com.very.wraq.process.system;

import com.very.wraq.common.Compute;
import com.very.wraq.process.system.tower.Tower;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.*;

public class WorldRecordInfo {
    // 形如（tower1_1, #very_H#1200） //
    public static Map<String, String> recordInfoMap = new HashMap<>();

    public String type;
    public String playerName;
    public int count;
    public List<WorldRecordInfo> list = new ArrayList<>();

    public WorldRecordInfo(String type, String playerName, int count) {
        this.type = type;
        this.playerName = playerName;
        this.count = count;
    }

    public void refreshRecord(Player player, int tick) {
        WorldRecordInfo record = new WorldRecordInfo(type, player.getName().getString(), tick);
        boolean refreshRecord = false;
        WorldRecordInfo beatRecord = null;

        // 比较是否刷新某一个纪录
        int beatRank = 0;
        for (WorldRecordInfo worldRecordInfo : list) {
            if (record.count > worldRecordInfo.count) {
                refreshRecord = true;
                beatRecord = worldRecordInfo;
                beatRank = list.indexOf(worldRecordInfo);
                break;
            }
        }

        // 若刷新纪录 或 当前列表中对应纪录条数还没达到5条 则 进行调整
        if (refreshRecord || list.size() < 5) {
            list.add(record);
            list.sort(new Comparator<WorldRecordInfo>() {
                @Override
                public int compare(WorldRecordInfo o1, WorldRecordInfo o2) {
                    return o2.count - o1.count;
                }
            });
            while (list.size() > 5) list.remove(list.size() - 1);
            if (beatRecord != null) {
                Compute.formatBroad(player.level(), Component.literal("挑战").withStyle(ChatFormatting.RED),
                        Component.literal(player.getName().getString()).withStyle(CustomStyle.styleOfWorld).
                        append(Component.literal(" 在").withStyle(ChatFormatting.WHITE)).
                        append(Component.literal("?").withStyle(CustomStyle.styleOfWorld)).
                        append(Component.literal(" 的挑战中，超越").withStyle(ChatFormatting.WHITE)).
                        append(Component.literal(beatRecord.playerName).withStyle(CustomStyle.styleOfWorld)).
                        append(Component.literal(" 在排行榜上达到").withStyle(ChatFormatting.WHITE)).
                        append(Component.literal("Rank #" + beatRank).withStyle(CustomStyle.styleOfGold)));
            }
            Tower.towerTypeFormatBroad(player.level(), Component.literal("?").withStyle(CustomStyle.styleOfWorld).
                    append(Component.literal(" 的挑战排行榜如下:").withStyle(ChatFormatting.WHITE)));
            for (int i = 0; i < list.size(); i++) {
                WorldRecordInfo worldRecordInfo = list.get(i);
                Compute.broad(player.level(), Component.literal(" ".repeat(16) + "#" + (i + 1)).withStyle(CustomStyle.styleOfWorld).
                        append(Component.literal(" " + worldRecordInfo.playerName).withStyle(CustomStyle.styleOfWorld)).
                        append(Component.literal(" " + worldRecordInfo.count)));
            }
        }
    }

    public void readFromWorldRecordInfo() {
        for (int i = 1; i <= 5; i++) {
            String value;
            value = WorldRecordInfo.recordInfoMap.getOrDefault(type + "_" + i, null);
            if (value != null)
                list.add(new WorldRecordInfo(type, getNameFromString(value), getCountFromString(value)));
        }
    }

    // 形如（endless1_1, #very_H#1200), (endless1_2, #very_Q#1000)) //
    public void writeToWorldRecordInfo() {
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                if (i < 5) {
                    WorldRecordInfo worldRecordInfo = list.get(i);
                    String key = type + "_" + (i + 1);
                    String value = "#" + worldRecordInfo.playerName + "#" + worldRecordInfo.count;
                    WorldRecordInfo.recordInfoMap.put(key, value);
                }
            }
        }
    }

    private int getCountFromString(String value) {
        int index = 0;
        for (int i = value.length() - 1; i >= 0; i--) {
            if (value.charAt(i) == '#') {
                index = i;
                break;
            }
        }
        return Integer.parseInt(value.substring(index + 1));
    }

    private String getNameFromString(String value) {
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
