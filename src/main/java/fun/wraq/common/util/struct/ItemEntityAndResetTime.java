package fun.wraq.common.util.struct;

import net.minecraft.world.entity.item.ItemEntity;

public class ItemEntityAndResetTime {
    private final ItemEntity itemEntity;
    private final int resetTick;

    public ItemEntityAndResetTime(ItemEntity itemEntity, int resetTick) {
        this.itemEntity = itemEntity;
        this.resetTick = resetTick;
    }

    public ItemEntity getItemEntity() {
        return itemEntity;
    }

    public int getResetTick() {
        return resetTick;
    }

}
