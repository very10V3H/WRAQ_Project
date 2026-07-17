/** AI-Generated, 2026-05-17 */
package fun.wraq.process.system.stock.networking;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.process.system.stock.StockIndex;
import fun.wraq.process.system.stock.StockTradingSystem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class StockBuyC2SPacket {
    private final int stockIndexOrdinal;
    private final int leverage;
    private final int quantity;
    private final int currencyType; // 0=VB, 1=VP

    public StockBuyC2SPacket(int stockIndexOrdinal, int leverage, int quantity, int currencyType) {
        this.stockIndexOrdinal = stockIndexOrdinal;
        this.leverage = leverage;
        this.quantity = quantity;
        this.currencyType = currencyType;
    }

    public StockBuyC2SPacket(FriendlyByteBuf buf) {
        this.stockIndexOrdinal = buf.readInt();
        this.leverage = buf.readInt();
        this.quantity = buf.readInt();
        this.currencyType = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(stockIndexOrdinal);
        buf.writeInt(leverage);
        buf.writeInt(quantity);
        buf.writeInt(currencyType);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;
            StockIndex[] indices = StockIndex.values();
            if (stockIndexOrdinal < 0 || stockIndexOrdinal >= indices.length) {
                Compute.sendFormatMSG(player, Te.s("股市"), Te.s("无效的指数选择"));
                return;
            }
            StockIndex index = indices[stockIndexOrdinal];
            String currency = currencyType == 0 ? "VB" : "VP";
            String result = StockTradingSystem.buy(player, index, leverage, quantity, currency);
            player.sendSystemMessage(Te.s(result));
            StockPortfolioSyncS2CPacket.sendTo(player);
        });
        return true;
    }
}
