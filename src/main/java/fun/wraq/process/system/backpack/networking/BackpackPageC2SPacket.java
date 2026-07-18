package fun.wraq.process.system.backpack.networking;

import fun.wraq.process.system.backpack.BackpackData;
import fun.wraq.process.system.backpack.BackpackFileManager;
import fun.wraq.process.system.backpack.BackpackMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;

import java.util.function.Supplier;

/**
 * AI-Generated, 2026-07-18
 * 客户端请求翻页。服务端收到后在目标页打开新背包菜单。
 */
public class BackpackPageC2SPacket {

    private final int targetPage;

    public BackpackPageC2SPacket(int targetPage) {
        this.targetPage = targetPage;
    }

    public BackpackPageC2SPacket(FriendlyByteBuf buf) {
        this.targetPage = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(targetPage);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;

            BackpackData data = BackpackFileManager.get(player.getUUID());
            int maxPage = data.getMaxPage();
            int page = Math.max(0, Math.min(targetPage, maxPage));
            int finalPage = page;

            NetworkHooks.openScreen(player, new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return Component.literal("§8个人背包");
                }

                @Override
                public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
                    return BackpackMenu.server(id, inv, data, finalPage);
                }
            }, buf -> {
                buf.writeInt(data.getSlotCount());
                buf.writeInt(data.getSlotLimitTier());
                buf.writeInt(finalPage);
            });
        });
        return true;
    }
}
