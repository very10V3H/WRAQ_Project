package fun.wraq.process.system.backpack;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;

/**
 * AI-Generated, 2026-07-12
 * 背包 Container Menu。支持分页，每页固定 4 行（36 格）。
 * 翻页栏位于背包区域与玩家背包之间。
 * 服务端持有 BackpackData 引用，客户端从 buffer 重建尺寸与页码。
 */
public class BackpackMenu extends AbstractContainerMenu {

    private static final int PAGE_SIZE = 36;       // 每页 4 行 × 9 列
    private static final int PLAYER_INV_GAP = 14;
    private static final int HOTBAR_GAP = 4;
    private static final int PAGE_BAR_HEIGHT = 16;

    private final BackpackData backpackData;
    private final int slotCount;                   // 总格数
    private final int pageOffset;                  // 当前页码（0-indexed）
    private final int slotsOnThisPage;             // 本页实际显示的格数

    /** 服务端构造 */
    public static BackpackMenu server(int id, Inventory inv, BackpackData data, int pageOffset) {
        return new BackpackMenu(id, inv, data, pageOffset);
    }

    /** 客户端构造（从 buffer 读取） */
    public BackpackMenu(int id, Inventory inv, FriendlyByteBuf buf) {
        this(id, inv, new BackpackData(buf.readInt(), buf.readInt()), buf.readInt());
    }

    private BackpackMenu(int id, Inventory inv, BackpackData data, int pageOffset) {
        super(BackpackMenuTypes.BACKPACK_MENU.get(), id);
        this.backpackData = data;
        this.slotCount = data.getSlotCount();
        this.pageOffset = pageOffset;

        IItemHandlerModifiable handler = data.getHandler();
        int startIndex = pageOffset * PAGE_SIZE;
        this.slotsOnThisPage = Math.min(PAGE_SIZE, Math.max(0, slotCount - startIndex));
        int rowsOnThisPage = (slotsOnThisPage + 8) / 9;

        // 背包格子（居中排列，每行 9 格）
        for (int row = 0; row < rowsOnThisPage; row++) {
            for (int col = 0; col < 9; col++) {
                int localIndex = row * 9 + col;
                int handlerIndex = startIndex + localIndex;
                if (handlerIndex >= slotCount) break;
                int x = 11 + col * 19;
                int y = 21 + row * 19;
                this.addSlot(new SlotItemHandler(handler, handlerIndex, x, y));
            }
        }

        // 玩家背包（27 格）
        int backpackEndY = 21 + rowsOnThisPage * 19;
        int playerInvStartY = backpackEndY + PLAYER_INV_GAP + PAGE_BAR_HEIGHT + PLAYER_INV_GAP;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(inv, col + row * 9 + 9, 11 + col * 19, playerInvStartY + row * 19));
            }
        }

        // 快捷栏（9 格）
        int hotbarY = playerInvStartY + 3 * 19 + 4;
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(inv, col, 11 + col * 19, hotbarY - 2));
        }
    }

    public int getSlotCount() {
        return slotCount;
    }

    public int getSlotsOnThisPage() {
        return slotsOnThisPage;
    }

    public int getPageOffset() {
        return pageOffset;
    }

    public int getSlotLimitTier() {
        return backpackData.getSlotLimitTier();
    }

    /** 服务端用：菜单打开后同步数据（用于升级后刷新） */
    public void syncData(BackpackData data) {
        // 重新广播容器内容会在下次 tick 自动完成
    }

    /* ---------- 快捷移动 ---------- */

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INV_ROW = 3;
    private static final int PLAYER_INV_COL = 9;

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = this.slots.get(index);
        if (slot == null || !slot.hasItem()) return ItemStack.EMPTY;
        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();

        int backpackSlotEnd = slotsOnThisPage;
        int playerInvStart = backpackSlotEnd;
        int playerInvEnd = backpackSlotEnd + 27;
        int hotbarEnd = playerInvEnd + 9;

        if (index < backpackSlotEnd) {
            // 背包 → 玩家背包
            if (!moveItemStackTo(stack, playerInvStart, hotbarEnd, true)) {
                return ItemStack.EMPTY;
            }
        } else if (index < playerInvEnd) {
            // 玩家背包 → 背包 / 快捷栏
            if (!moveItemStackTo(stack, 0, backpackSlotEnd, false)) {
                if (!moveItemStackTo(stack, playerInvEnd, hotbarEnd, false)) {
                    return ItemStack.EMPTY;
                }
            }
        } else if (index < hotbarEnd) {
            // 快捷栏 → 背包 / 玩家背包
            if (!moveItemStackTo(stack, 0, backpackSlotEnd, false)) {
                if (!moveItemStackTo(stack, playerInvStart, playerInvEnd, false)) {
                    return ItemStack.EMPTY;
                }
            }
        }

        if (stack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }
        return copy;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    /** 菜单关闭时自动保存 */
    @Override
    public void removed(Player player) {
        super.removed(player);
        if (!player.level().isClientSide) {
            BackpackFileManager.markDirty(player.getUUID());
        }
    }
}
