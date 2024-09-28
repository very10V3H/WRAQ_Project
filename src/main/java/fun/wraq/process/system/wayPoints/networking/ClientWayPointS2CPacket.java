package fun.wraq.process.system.wayPoints.networking;

import fun.wraq.process.system.wayPoints.MyWayPoint;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientWayPointS2CPacket {

    public ClientWayPointS2CPacket() {

    }

    public ClientWayPointS2CPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            MyWayPoint.setClientWaypoints(MyWayPoint.overworldPointList);
        });
        return true;
    }
}
