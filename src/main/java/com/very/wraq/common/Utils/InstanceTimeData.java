package com.very.wraq.common.Utils;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InstanceTimeData extends SavedData {

    public record EachTime(String name, int tick) {
    }

    public Map<String, List<EachTime>> map = new HashMap<>();

    public InstanceTimeData() {
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag) {
        return compoundTag;
    }
}
