/** AI-Generated, 2026-05-17 */
package fun.wraq.process.func.guide.waypoint.networking;

import fun.wraq.process.func.guide.waypoint.WaypointClientManager;
import fun.wraq.process.func.guide.waypoint.WaypointData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class WaypointSyncS2CPacket {

    private final List<WaypointData> waypoints;

    public WaypointSyncS2CPacket(List<WaypointData> waypoints) {
        this.waypoints = waypoints;
    }

    public WaypointSyncS2CPacket(FriendlyByteBuf buf) {
        int count = buf.readInt();
        waypoints = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            waypoints.add(new WaypointData(
                    buf.readUtf(),
                    buf.readDouble(),
                    buf.readDouble(),
                    buf.readDouble(),
                    buf.readUtf()
            ));
        }
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(waypoints.size());
        for (WaypointData wp : waypoints) {
            buf.writeUtf(wp.name);
            buf.writeDouble(wp.x);
            buf.writeDouble(wp.y);
            buf.writeDouble(wp.z);
            buf.writeUtf(wp.dimension);
        }
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> WaypointClientManager.setWaypoints(waypoints));
        return true;
    }
}
