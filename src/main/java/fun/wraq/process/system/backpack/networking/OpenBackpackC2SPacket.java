package fun.wraq.process.system.backpack.networking;

import fun.wraq.process.system.backpack.BackpackData;
import fun.wraq.process.system.backpack.BackpackFileManager;
import fun.wraq.process.system.backpack.BackpackMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;

import java.util.function.Supplier;

/**
 * AI-Generated, 2026-07-12
 * 客户端按下 B 键后发送到此包 → 服务端打开背包 GUI。
 */
public class OpenBackpackC2SPacket {

    public OpenBackpackC2SPacket() {
    }

    public OpenBackpackC2SPacket(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;

            BackpackData data = BackpackFileManager.get(player.getUUID());

            NetworkHooks.openScreen(player, new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return Component.literal("§8个人背包");
                }

                @Override
                public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
                    return BackpackMenu.server(id, inv, data);
                }
            }, buf -> {
                buf.writeInt(data.getSlotCount());
                buf.writeInt(data.getSlotLimitTier());
            });
        });
        return true;
    }
}
