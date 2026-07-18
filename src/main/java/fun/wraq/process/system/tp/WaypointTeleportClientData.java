/** AI-Generated, 2026-07-18 */
package fun.wraq.process.system.tp;

import net.minecraft.network.chat.Style;

import java.util.HashMap;
import java.util.Map;

/**
 * 客户端存储的传送锚点解锁状态与颜色
 * 通过 WaypointTeleportS2CPacket 从服务端同步
 */
public class WaypointTeleportClientData {

    private static final Map<String, Boolean> unlockedStatus = new HashMap<>();
    private static final Map<String, Integer> waypointColors = new HashMap<>();

    public static void setStatus(String[] names, boolean[] unlocked, int[] colors) {
        unlockedStatus.clear();
        waypointColors.clear();
        for (int i = 0; i < names.length; i++) {
            unlockedStatus.put(names[i], unlocked[i]);
            waypointColors.put(names[i], colors[i]);
        }
    }

    public static void setUnlocked(String name, boolean unlocked) {
        unlockedStatus.put(name, unlocked);
    }

    public static boolean isUnlocked(String name) {
        return unlockedStatus.getOrDefault(name, false);
    }

    /** 获取锚点名称对应的 Style（含颜色） */
    public static Style getWaypointStyle(String name) {
        Integer color = waypointColors.get(name);
        if (color != null) {
            return Style.EMPTY.withColor(color);
        }
        return Style.EMPTY.withColor(0xFFD700);
    }
}
