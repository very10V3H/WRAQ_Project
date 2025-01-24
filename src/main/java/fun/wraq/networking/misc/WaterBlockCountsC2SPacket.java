package fun.wraq.networking.misc;

import fun.wraq.series.overworld.sakura.Ship.ShipSceptre;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class WaterBlockCountsC2SPacket {
    private final int counts;

    public WaterBlockCountsC2SPacket(int counts) {
        this.counts = counts;
    }

    public WaterBlockCountsC2SPacket(FriendlyByteBuf buf) {
        this.counts = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.counts);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            ShipSceptre.waterBlockCount.put(serverPlayer, counts);
        });
        return true;
    }
}
