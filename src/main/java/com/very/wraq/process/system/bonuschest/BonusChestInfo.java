package com.very.wraq.process.system.bonuschest;

import net.minecraft.core.BlockPos;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 奖励箱位置、等阶、所需区域枚举与一些方法
 */
public enum BonusChestInfo {

    DRUMSTICK_0(new BlockPos(958, 224, 17), 0, Util.DRUMSTICK_ZONE_NUM),
    DRUMSTICK_1(new BlockPos(958, 225, 17), 1, Util.DRUMSTICK_ZONE_NUM),

    ELEMENT_CONTINENT_0(new BlockPos(958, 226, 17), 0, Util.ELEMENT_CONTINENT_ZONE_NUM),
    ELEMENT_CONTINENT_1(new BlockPos(958, 227, 17), 1, Util.ELEMENT_CONTINENT_ZONE_NUM),

    NETHER_0(new BlockPos(958, 228, 17), 2, Util.NETHER_ZONE_NUM);

    public final BlockPos chestPos;
    public final int tier;
    public final int zone;

    BonusChestInfo(BlockPos blockPos, int tier, int zone) {
        this.chestPos = blockPos;
        this.tier = tier;
        this.zone = zone;
    }

    // 获取序列号偏移位
    public static class Util {
        public static final int DRUMSTICK_ZONE_NUM = 0;
        public static final int ELEMENT_CONTINENT_ZONE_NUM = 1;
        public static final int NETHER_ZONE_NUM = 2;
    }

    public static final int DRUMSTICK_ZONE_COUNT_OFFSET = 0;
    public static int ELEMENT_CONTINENT_COUNT_OFFSET = 0;
    public static int NETHER_COUNT_OFFSET = 0;
    public static final Map<Integer, Integer> ZONE_OFFSET_MAP = new HashMap<>();
    static {
        for (BonusChestInfo value : BonusChestInfo.values()) {
            if (value.zone < Util.ELEMENT_CONTINENT_ZONE_NUM) ELEMENT_CONTINENT_COUNT_OFFSET ++;
            if (value.zone < Util.NETHER_ZONE_NUM) NETHER_COUNT_OFFSET ++;
        }
        ZONE_OFFSET_MAP.put(Util.DRUMSTICK_ZONE_NUM, DRUMSTICK_ZONE_COUNT_OFFSET);
        ZONE_OFFSET_MAP.put(Util.ELEMENT_CONTINENT_ZONE_NUM, ELEMENT_CONTINENT_COUNT_OFFSET);
        ZONE_OFFSET_MAP.put(Util.NETHER_ZONE_NUM, NETHER_COUNT_OFFSET);
    }

    private static final Map<BlockPos, BonusChestInfo> infoMap = new HashMap<>() {{
        Arrays.stream(BonusChestInfo.values())
                .forEach(info -> put(info.chestPos, info));
    }};

    // 对外方法
    public static BonusChestInfo getBonusChestInfo(BlockPos pos) {
        return infoMap.getOrDefault(pos, null);
    }

    public static int getZoneNum(BlockPos blockPos) {
        return infoMap.containsKey(blockPos) ? infoMap.get(blockPos).zone : -1;
    }

    public static int getSerialNum(BlockPos blockPos) {
        BonusChestInfo bonusChestInfo = infoMap.get(blockPos);
        return bonusChestInfo.ordinal() - ZONE_OFFSET_MAP.get(bonusChestInfo.zone);
    }

    public static int getTierNum(BlockPos blockPos) {
        return infoMap.containsKey(blockPos) ? infoMap.get(blockPos).tier : -1;
    }
}
