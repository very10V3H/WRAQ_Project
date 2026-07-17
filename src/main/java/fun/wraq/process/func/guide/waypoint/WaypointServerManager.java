/** AI-Generated, 2026-05-17 */
package fun.wraq.process.func.guide.waypoint;

import fun.wraq.networking.ModNetworking;
import fun.wraq.process.func.guide.waypoint.networking.WaypointSyncS2CPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class WaypointServerManager {

    private static final String KEY = "GuideWaypoints";

    public static List<WaypointData> getWaypoints(Player player) {
        List<WaypointData> list = new ArrayList<>();
        CompoundTag data = player.getPersistentData();
        if (data.contains(KEY)) {
            CompoundTag waypoints = data.getCompound(KEY);
            for (String key : waypoints.getAllKeys()) {
                list.add(WaypointData.fromNbt(waypoints.getCompound(key)));
            }
        }
        return list;
    }

    public static void addWaypoint(Player player, WaypointData waypoint) {
        CompoundTag data = player.getPersistentData();
        CompoundTag waypoints = data.contains(KEY) ? data.getCompound(KEY) : new CompoundTag();
        int index = waypoints.size();
        waypoints.put(String.valueOf(index), waypoint.toNbt());
        data.put(KEY, waypoints);
    }

    public static void removeWaypoint(Player player, String name) {
        CompoundTag data = player.getPersistentData();
        if (!data.contains(KEY)) return;
        CompoundTag waypoints = data.getCompound(KEY);
        CompoundTag reindexed = new CompoundTag();
        int newIndex = 0;
        for (String key : waypoints.getAllKeys()) {
            WaypointData wp = WaypointData.fromNbt(waypoints.getCompound(key));
            if (!wp.name.equals(name)) {
                reindexed.put(String.valueOf(newIndex++), waypoints.getCompound(key));
            }
        }
        data.put(KEY, reindexed);
    }

    public static void clearWaypoints(Player player) {
        player.getPersistentData().remove(KEY);
    }

    public static void syncToClient(Player player) {
        List<WaypointData> waypoints = getWaypoints(player);
        ModNetworking.sendToClient(new WaypointSyncS2CPacket(waypoints), (ServerPlayer) player);
    }
}
