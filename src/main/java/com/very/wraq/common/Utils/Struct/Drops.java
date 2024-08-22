package com.very.wraq.common.Utils.Struct;

import com.very.wraq.common.Utils.StringUtils;
import net.minecraft.nbt.CompoundTag;

public class Drops {
    public static void KillCount(CompoundTag data, String s) {
        if (!data.contains(StringUtils.KillCount.Total)) data.putInt(StringUtils.KillCount.Total, 1);
        else data.putInt(StringUtils.KillCount.Total, data.getInt(StringUtils.KillCount.Total) + 1);
        if (!data.contains(s)) data.putInt(s, 1);
        else data.putInt(s, data.getInt(s) + 1);
    }
}
