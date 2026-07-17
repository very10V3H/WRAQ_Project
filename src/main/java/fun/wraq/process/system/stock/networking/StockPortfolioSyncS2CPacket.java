/** AI-Generated, 2026-05-17 */
package fun.wraq.process.system.stock.networking;

import fun.wraq.networking.ModNetworking;
import fun.wraq.process.system.stock.StockIndex;
import fun.wraq.process.system.stock.StockPlayerData;
import fun.wraq.process.system.stock.StockPosition;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class StockPortfolioSyncS2CPacket {

    private final List<StockPosition> positions;

    public StockPortfolioSyncS2CPacket(List<StockPosition> positions) {
        this.positions = positions;
    }

    public StockPortfolioSyncS2CPacket(FriendlyByteBuf buf) {
        int count = buf.readInt();
        positions = new ArrayList<>();
        StockIndex[] indices = StockIndex.values();
        for (int i = 0; i < count; i++) {
            int indexOrdinal = buf.readInt();
            int leverage = buf.readInt();
            double buyPrice = buf.readDouble();
            int quantity = buf.readInt();
            byte currencyByte = buf.readByte();
            long buyTimestamp = buf.readLong();
            double investment = buf.readDouble();
            if (indexOrdinal >= 0 && indexOrdinal < indices.length) {
                String currencyType = currencyByte == 0 ? "VB" : "VP";
                positions.add(new StockPosition(indices[indexOrdinal], leverage, buyPrice, quantity,
                        currencyType, buyTimestamp, investment));
            }
        }
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(positions.size());
        for (StockPosition pos : positions) {
            buf.writeInt(pos.getIndex().ordinal());
            buf.writeInt(pos.getLeverage());
            buf.writeDouble(pos.getBuyPrice());
            buf.writeInt(pos.getQuantity());
            buf.writeByte((byte) ("VB".equals(pos.getCurrencyType()) ? 0 : 1));
            buf.writeLong(pos.getBuyTimestamp());
            buf.writeDouble(pos.getInvestment());
        }
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientStockData.positions.clear();
            ClientStockData.positions.addAll(positions);
        });
        return true;
    }

    public static void sendTo(ServerPlayer player) {
        List<StockPosition> posList = StockPlayerData.getPositions(player);
        ModNetworking.sendToClient(new StockPortfolioSyncS2CPacket(posList), player);
    }
}
