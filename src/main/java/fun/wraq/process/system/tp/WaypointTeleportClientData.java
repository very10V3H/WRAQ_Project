/** AI-Generated, 2026-07-18 */
package fun.wraq.process.system.tp;

import java.util.HashMap;
import java.util.Map;

/**
 * 客户端存储的传送锚点解锁状态
 * 通过 WaypointTeleportS2CPacket 从服务端同步
 */
public class WaypointTeleportClientData {

    private static final Map<String, Boolean> unlockedStatus = new HashMap<>();

    public static void setStatus(String[] names, boolean[] unlocked) {
        unlockedStatus.clear();
        for (int i = 0; i < names.length; i++) {
            unlockedStatus.put(names[i], unlocked[i]);
        }
    }

    public static void setUnlocked(String name, boolean unlocked) {
        unlockedStatus.put(name, unlocked);
    }

    public static boolean isUnlocked(String name) {
        return unlockedStatus.getOrDefault(name, false);
    }

    public static Map<String, Boolean> getAllStatus() {
        return new HashMap<>(unlockedStatus);
    }
}
