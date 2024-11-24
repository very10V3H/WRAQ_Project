package fun.wraq.process.func.rank.network;

import fun.wraq.process.func.rank.RankData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class RankChangeS2CPacket {

    private final UUID uuid;
    private final String rank;

    public RankChangeS2CPacket(UUID uuid, String rank) {
        this.uuid = uuid;
        this.rank = rank;
    }

    public RankChangeS2CPacket(FriendlyByteBuf buf) {
        this.uuid = buf.readUUID();
        this.rank = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(this.uuid);
        buf.writeUtf(this.rank);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            RankData.clientPlayerCurrentRankMap.put(uuid, rank);
        });
        return true;
    }
}
