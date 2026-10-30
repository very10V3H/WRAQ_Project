package fun.wraq.series.overworld.chapter2.sky;

import fun.wraq.render.gui.testAndHelper.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkHooks;

/**
 * AI-Generated, 2026-10-30
 * Sky-Line 天空科技配置菜单：四个可识别意志槽 + 玩家背包。
 */
public class SkyLineMenu extends AbstractContainerMenu {

    private final ItemStack device;
    private final SimpleContainer container;

    /** 服务端/客户端统一从主手读取设备。 */
    public SkyLineMenu(int id, Inventory inventory) {
        this(id, inventory, inventory.player.getMainHandItem());
    }

    /** IForgeMenuType 客户端构造。 */
    public SkyLineMenu(int id, Inventory inventory, FriendlyByteBuf buf) {
        this(id, inventory, inventory.player.getMainHandItem());
    }

    private SkyLineMenu(int id, Inventory inventory, ItemStack device) {
        super(ModMenuTypes.SKY_LINE_MENU.get(), id);
        this.device = device;
        this.container = new SimpleContainer(SkyLineData.SLOT_COUNT);
        for (int i = 0; i < SkyLineData.SLOT_COUNT; i++) {
            container.setItem(i, SkyLineData.getItems(device).get(i));
        }
        container.addListener(c -> save());

        for (int i = 0; i < SkyLineData.SLOT_COUNT; i++) {
            int x = 44 + i * 24;
            this.addSlot(new Slot(container, i, x, 28) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return SkyLineData.isRecognized(stack);
                }

                @Override
                public int getMaxStackSize() {
                    return 1;
                }
            });
        }

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(inventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(inventory, col, 8 + col * 18, 142));
        }
    }

    public static void open(ServerPlayer player) {
        NetworkHooks.openScreen(player, new SimpleMenuProvider(
                (id, inventory, p) -> new SkyLineMenu(id, inventory),
                Component.literal("Sky-Line 天空科技")));
    }

    private void save() {
        if (device.isEmpty()) {
            return;
        }
        SkyLineData.setItems(device, java.util.List.of(
                container.getItem(0), container.getItem(1),
                container.getItem(2), container.getItem(3)));
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = this.slots.get(index);
        if (slot == null || !slot.hasItem()) {
            return ItemStack.EMPTY;
        }
        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();
        int configEnd = SkyLineData.SLOT_COUNT;
        int playerInvEnd = configEnd + 27;
        int hotbarEnd = playerInvEnd + 9;

        if (index < configEnd) {
            if (!moveItemStackTo(stack, configEnd, hotbarEnd, true)) {
                return ItemStack.EMPTY;
            }
        } else if (index < playerInvEnd) {
            if (!moveItemStackTo(stack, 0, configEnd, false)) {
                if (!moveItemStackTo(stack, playerInvEnd, hotbarEnd, false)) {
                    return ItemStack.EMPTY;
                }
            }
        } else if (index < hotbarEnd) {
            if (!moveItemStackTo(stack, 0, configEnd, false)) {
                if (!moveItemStackTo(stack, configEnd, playerInvEnd, false)) {
                    return ItemStack.EMPTY;
                }
            }
        } else {
            return ItemStack.EMPTY;
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
        return !device.isEmpty() && player.getMainHandItem().is(device.getItem());
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        save();
    }
}
