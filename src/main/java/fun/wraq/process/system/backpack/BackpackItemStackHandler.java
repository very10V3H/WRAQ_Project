package fun.wraq.process.system.backpack;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

/**
 * AI-Generated, 2026-07-12
 * 可升级每格堆叠上限的 ItemStackHandler。
 * 基础 64，每升一级 slotLimitTier 增加一倍（64→128→256…）。
 */
public class BackpackItemStackHandler extends ItemStackHandler {

    private int slotLimitTier = 0; // 0→64, 1→128, 2→256 …

    public BackpackItemStackHandler() {
        super(27); // 默认 27 格（3 行）
    }

    public BackpackItemStackHandler(int slots, int slotLimitTier) {
        super(slots);
        this.slotLimitTier = slotLimitTier;
    }

    @Override
    public int getSlotLimit(int slot) {
        return 64 * (1 << slotLimitTier); // 64 → 128 → 256 → 512 …
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        // MVP 阶段不限制物品类型
        return true;
    }

    public int getSlotLimitTier() {
        return slotLimitTier;
    }

    public void setSlotLimitTier(int tier) {
        this.slotLimitTier = Math.max(0, tier);
    }

    /** 扩展行数（每行 9 格），最多 9 行（81 格） */
    public boolean expandRows(int additionalRows) {
        int targetSlots = Math.min(stacks.size() + additionalRows * 9, 81);
        if (targetSlots <= stacks.size()) return false;
        int oldSize = stacks.size();
        NonNullList<ItemStack> newStacks = NonNullList.withSize(targetSlots, ItemStack.EMPTY);
        for (int i = 0; i < oldSize; i++) {
            newStacks.set(i, stacks.get(i));
        }
        stacks = newStacks;
        onLoad();
        return true;
    }

    /** 当前行数 */
    public int getRowCount() {
        return stacks.size() / 9;
    }

    /** 总格子数 */
    public int getSlotCount() {
        return stacks.size();
    }
}
