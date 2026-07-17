/** AI-Generated, 2026-05-17 */
package fun.wraq.process.system.stock.networking;

import fun.wraq.common.fast.Te;
import fun.wraq.process.system.stock.StockTradingSystem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class StockSellC2SPacket {
    private final int positionIndex;

    public StockSellC2SPacket(int positionIndex) {
        this.positionIndex = positionIndex;
    }

    public StockSellC2SPacket(FriendlyByteBuf buf) {
        this.positionIndex = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(positionIndex);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;
            String result = StockTradingSystem.sell(player, positionIndex);
            player.sendSystemMessage(Te.s(result));
            StockPortfolioSyncS2CPacket.sendTo(player);
        });
        return true;
    }
}
