package com.very.wraq.valueAndTools.Utils.Struct;

import java.util.UUID;

public class Waltz {
    private final UUID uuid;
    private final int index;
    public Waltz(UUID uuid, int index)
    {
        this.uuid = uuid;
        this.index = index;
    }
    public UUID getUUID()
    {
        return this.uuid;
    }
    public int getIndex()
    {
        return this.index;
    }
}
