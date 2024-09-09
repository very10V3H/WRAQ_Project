package com.very.wraq.process.system.bonuschest;

import net.minecraft.core.BlockPos;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum BonusChestInfo {

    ZONE0_0(new BlockPos(0, 0, 0), 0, 0, 0);

    public final BlockPos chestPos;
    public final int tier;
    public final int zone;
    public final int serial;

    BonusChestInfo(BlockPos blockPos, int tier, int zone, int serial) {
        this.chestPos = blockPos;
        this.tier = tier;
        this.zone = zone;
        this.serial = serial;
    }

    public static Map<BlockPos, BonusChestInfo> infoMap = new HashMap<>() {{
        Arrays.stream(BonusChestInfo.values())
                .forEach(info -> put(info.chestPos, info));
    }};

    public static int getZoneNum(BlockPos blockPos) {
        return infoMap.containsKey(blockPos) ? infoMap.get(blockPos).zone : -1;
    }

    public static int getSerialNum(BlockPos blockPos) {
        return infoMap.containsKey(blockPos) ? infoMap.get(blockPos).serial : -1;
    }

    public static int getTierNum(BlockPos blockPos) {
        return infoMap.containsKey(blockPos) ? infoMap.get(blockPos).tier : -1;
    }
}
