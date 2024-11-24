package fun.wraq.process.func.rank.network;

import fun.wraq.process.func.rank.RankData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

public class RankDataS2CPacket {

    private final Map<UUID, String> clientData;

    public RankDataS2CPacket(Map<UUID, String> clientData) {
        this.clientData = clientData;
    }

    public RankDataS2CPacket(FriendlyByteBuf buf) {
        this.clientData = buf.readMap(FriendlyByteBuf::readUUID, FriendlyByteBuf::readUtf);
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeMap(this.clientData, FriendlyByteBuf::writeUUID, FriendlyByteBuf::writeUtf);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            RankData.clientPlayerCurrentRankMap = clientData;
        });
        return true;
    }
}
