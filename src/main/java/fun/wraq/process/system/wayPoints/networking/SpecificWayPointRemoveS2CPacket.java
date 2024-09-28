package fun.wraq.process.system.wayPoints.networking;

import fun.wraq.process.system.wayPoints.MyWayPoint;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SpecificWayPointRemoveS2CPacket {

    private final String name;

    public SpecificWayPointRemoveS2CPacket(String name) {
        this.name = name;
    }

    public SpecificWayPointRemoveS2CPacket(FriendlyByteBuf buf) {
        this.name = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(name);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            MyWayPoint.removeWaypointByName(name);
        });
        return true;
    }
}
