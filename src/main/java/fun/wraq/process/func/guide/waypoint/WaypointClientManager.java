/** AI-Generated, 2026-05-17 */
package fun.wraq.process.func.guide.waypoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WaypointClientManager {

    private static List<WaypointData> waypoints = new ArrayList<>();

    public static List<WaypointData> getWaypoints() {
        return Collections.unmodifiableList(waypoints);
    }

    public static void setWaypoints(List<WaypointData> list) {
        waypoints = new ArrayList<>(list);
    }

    public static void clear() {
        waypoints.clear();
    }
}
