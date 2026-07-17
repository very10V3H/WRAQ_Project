package fun.wraq.process.system.backpack;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandlerModifiable;

/**
 * AI-Generated, 2026-07-12
 * 每个玩家的背包数据封装。
 * - 自定义 ItemStackHandler（可升级格数和每格上限）
 * - 存档 / 读档
 */
public class BackpackData implements INBTSerializable<CompoundTag> {

    private BackpackItemStackHandler handler;

    public BackpackData() {
        this.handler = new BackpackItemStackHandler(27, 0);
    }

    public BackpackData(int slots, int slotLimitTier) {
        this.handler = new BackpackItemStackHandler(slots, slotLimitTier);
    }

    public IItemHandlerModifiable getHandler() {
        return handler;
    }

    public BackpackItemStackHandler getBackpackHandler() {
        return handler;
    }

    /* ---------- 升级接口 ---------- */

    /** 堆叠上限升一级 */
    public boolean upgradeSlotLimit() {
        int next = handler.getSlotLimitTier() + 1;
        if (next > 4) return false; // 最多 64*2^4 = 1024
        handler.setSlotLimitTier(next);
        return true;
    }

    /** 增加一行（9 格），最多 9 行 */
    public boolean expandRow() {
        return handler.expandRows(1);
    }

    public int getSlotLimitTier() {
        return handler.getSlotLimitTier();
    }

    public int getRowCount() {
        return handler.getRowCount();
    }

    public int getSlotCount() {
        return handler.getSlotCount();
    }

    /* ---------- NBT 序列化 ---------- */

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("slots", handler.getSlotCount());
        tag.putInt("slotLimitTier", handler.getSlotLimitTier());
        ListTag itemsList = new ListTag();
        for (int i = 0; i < handler.getSlotCount(); i++) {
            ItemStack stack = handler.getStackInSlot(i);
            if (!stack.isEmpty()) {
                CompoundTag slotTag = new CompoundTag();
                slotTag.putInt("Slot", i);
                stack.save(slotTag);
                itemsList.add(slotTag);
            }
        }
        tag.put("Items", itemsList);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        int slots = tag.getInt("slots");
        int slotLimitTier = tag.getInt("slotLimitTier");
        handler = new BackpackItemStackHandler(slots, slotLimitTier);

        ListTag itemsList = tag.getList("Items", Tag.TAG_COMPOUND);
        for (int i = 0; i < itemsList.size(); i++) {
            CompoundTag slotTag = itemsList.getCompound(i);
            int slot = slotTag.getInt("Slot");
            if (slot >= 0 && slot < handler.getSlotCount()) {
                handler.setStackInSlot(slot, ItemStack.of(slotTag));
            }
        }
    }

    /**
     * 归零式反序列化：直接覆盖现有内容。
     * 用于从文件完整加载。
     */
    public void load(CompoundTag tag) {
        deserializeNBT(tag);
    }
}
