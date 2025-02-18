package fun.wraq.events.mob.instance.instances.tower.network;

import fun.wraq.events.mob.instance.instances.tower.ManaTowerData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ManaTowerS2CPacket {

    private final int startTick;
    private final int expiredTick;
    private final int floor;

    public ManaTowerS2CPacket(int startTick, int expiredTick, int floor) {
        this.startTick = startTick;
        this.expiredTick = expiredTick;
        this.floor = floor;
    }

    public ManaTowerS2CPacket(FriendlyByteBuf buf) {
        this.startTick = buf.readInt();
        this.expiredTick = buf.readInt();
        this.floor = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(startTick);
        buf.writeInt(expiredTick);
        buf.writeInt(floor);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ManaTowerData.clientStartTick = startTick;
            ManaTowerData.clientExpiredTick = expiredTick;
            ManaTowerData.clientFloor = floor;
        });
        return true;
    }
}
