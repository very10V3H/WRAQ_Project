package fun.wraq.common.util.struct;

import net.minecraft.world.item.ItemStack;

public class EffectTimeLast {
    public int lastTick;
    public int maxTick;
    public ItemStack itemStack;
    public int level;
    public boolean forever;

    public EffectTimeLast(ItemStack itemStack, int lastTick, int maxTick, int level) {
        this.itemStack = itemStack;
        this.lastTick = lastTick;
        this.maxTick = maxTick;
        this.level = level;
        this.forever = false;
    }

    public EffectTimeLast(ItemStack itemStack, int lastTick, int maxTick, int level, boolean forever) {
        this.itemStack = itemStack;
        this.lastTick = lastTick;
        this.maxTick = maxTick;
        this.level = level;
        this.forever = forever;
    }

}
