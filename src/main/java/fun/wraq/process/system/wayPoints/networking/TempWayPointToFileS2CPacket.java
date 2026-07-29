package fun.wraq.process.system.wayPoints.networking;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import xaero.common.XaeroMinimapSession;
import xaero.common.minimap.waypoints.Waypoint;
import xaero.common.minimap.waypoints.WaypointsManager;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.function.Supplier;

/**
 * AI-Generated, 2026-07-29
 * Server-to-Client packet: triggers writing all "Waypoint"-named waypoints
 * from Xaero's minimap to a desktop text file.
 */
public class TempWayPointToFileS2CPacket {

    public TempWayPointToFileS2CPacket() {
    }

    public TempWayPointToFileS2CPacket(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            try {
                XaeroMinimapSession minimapSession = XaeroMinimapSession.getCurrentSession();
                if (minimapSession == null) return;
                WaypointsManager waypointsManager = minimapSession.getWaypointsManager();
                if (waypointsManager == null
                        || waypointsManager.getCurrentWorld() == null
                        || waypointsManager.getCurrentWorld().getCurrentSet() == null) {
                    return;
                }

                List<Waypoint> list = waypointsManager.getCurrentWorld().getCurrentSet().getList();
                List<Waypoint> filtered = list.stream()
                        .filter(p -> p.getName().equals("Waypoint"))
                        .toList();

                String desktopPath = System.getProperty("user.home") + "/Desktop/临时路径点.txt";
                try (PrintWriter writer = new PrintWriter(new FileWriter(desktopPath))) {
                    for (Waypoint wp : filtered) {
                        writer.println(wp.getX() + "," + wp.getY() + "," + wp.getZ());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return true;
    }
}
