package com.very.wraq.common.Utils.Struct;

import net.minecraft.world.item.ItemStack;

public class EffectTimeLast {
    public int TickCount;
    public int MaxCount;
    public ItemStack itemStack;
    public int level;
    public boolean NoTime;

    public EffectTimeLast(ItemStack itemStack, int tickCount, int maxCount, int level) {
        this.itemStack = itemStack;
        this.TickCount = tickCount;
        this.MaxCount = maxCount;
        this.level = level;
        this.NoTime = false;
    }

    public EffectTimeLast(ItemStack itemStack, int tickCount, int maxCount, int level, boolean noTime) {
        this.itemStack = itemStack;
        this.TickCount = tickCount;
        this.MaxCount = maxCount;
        this.level = level;
        this.NoTime = noTime;
    }

}
