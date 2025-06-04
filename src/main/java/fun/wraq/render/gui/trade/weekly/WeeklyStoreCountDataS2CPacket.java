package fun.wraq.render.gui.trade.weekly;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.Map;
import java.util.function.Supplier;

public class WeeklyStoreCountDataS2CPacket {

    private final Map<String, Integer> clientData;
    private final int issueCount;

    public WeeklyStoreCountDataS2CPacket(Map<String, Integer> clientData, int issueCount) {
        this.clientData = clientData;
        this.issueCount = issueCount;
    }

    public WeeklyStoreCountDataS2CPacket(FriendlyByteBuf buf) {
        this.clientData = buf.readMap(FriendlyByteBuf::readUtf, FriendlyByteBuf::readInt);
        this.issueCount = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeMap(this.clientData, FriendlyByteBuf::writeUtf, FriendlyByteBuf::writeInt);
        buf.writeInt(this.issueCount);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            WeeklyStorePlayerData.clientPlayerChangedCount.clear();
            WeeklyStorePlayerData.clientPlayerChangedCount.putAll(clientData);
            WeeklyStorePlayerData.clientIssueCount = issueCount;
        });
        return true;
    }
}
