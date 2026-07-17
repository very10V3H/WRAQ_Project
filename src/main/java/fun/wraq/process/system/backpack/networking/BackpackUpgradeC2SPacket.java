package fun.wraq.process.system.backpack.networking;

import fun.wraq.process.system.backpack.BackpackData;
import fun.wraq.process.system.backpack.BackpackFileManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * AI-Generated, 2026-07-12
 * 客户端请求升级背包（扩容格子 or 堆叠上限）。
 * type=0 → 增加一行（9 格）
 * type=1 → 堆叠上限升一级
 */
public class BackpackUpgradeC2SPacket {

    private final int upgradeType;

    public BackpackUpgradeC2SPacket(int upgradeType) {
        this.upgradeType = upgradeType;
    }

    public BackpackUpgradeC2SPacket(FriendlyByteBuf buf) {
        this.upgradeType = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(upgradeType);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;

            BackpackData data = BackpackFileManager.get(player.getUUID());
            boolean success = false;
            String msg = "";

            if (upgradeType == 0) {
                // 扩容一行（9 格）
                if (data.getRowCount() < 9) {
                    data.expandRow();
                    BackpackFileManager.markDirty(player.getUUID());
                    success = true;
                    msg = "背包已扩容至 " + data.getRowCount() + " 行（" + data.getSlotCount() + " 格）";
                } else {
                    msg = "背包已达到最大行数！";
                }
            } else if (upgradeType == 1) {
                // 堆叠上限升级
                if (data.getSlotLimitTier() < 4) {
                    data.upgradeSlotLimit();
                    BackpackFileManager.markDirty(player.getUUID());
                    success = true;
                    msg = "堆叠上限已提升至 " + (64 * (1 << data.getSlotLimitTier())) + "！";
                } else {
                    msg = "堆叠上限已达到最大等级！";
                }
            }

            if (player.containerMenu instanceof fun.wraq.process.system.backpack.BackpackMenu menu) {
                menu.syncData(data);
            }

            player.sendSystemMessage(Component.literal("§7[背包] §" + (success ? "a" : "c") + msg));
        });
        return true;
    }
}
