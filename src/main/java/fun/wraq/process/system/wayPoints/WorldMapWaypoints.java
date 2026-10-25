/** AI-Generated, 2026-10-25 */
package fun.wraq.process.system.wayPoints;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射调用 Xaero 世界地图的路径点刷新。
 * <p>
 * 世界地图渲染的是 {@code SupportXaeroMinimap.convertWaypoints(...)} 生成的快照
 * （只在 GUI 初始化或请求刷新时转换），所以路径点增删/禁用后必须让它重新转换，
 * 否则不重开地图就看不到变化。反射调用可以避免把世界地图变成硬依赖。
 */
@OnlyIn(Dist.CLIENT)
public final class WorldMapWaypoints {

    private static Class<?> supportModsClass;
    private static Field xaeroMinimapField;
    private static Method requestRefreshMethod;

    private WorldMapWaypoints() {
    }

    public static void requestRefresh() {
        try {
            if (supportModsClass == null) {
                supportModsClass = Class.forName("xaero.map.mods.SupportMods");
                xaeroMinimapField = supportModsClass.getField("xaeroMinimap");
            }
            Object supportMods = xaeroMinimapField.get(null);
            if (supportMods == null) return;
            if (requestRefreshMethod == null) {
                requestRefreshMethod = supportMods.getClass().getMethod("requestWaypointsRefresh");
            }
            requestRefreshMethod.invoke(supportMods);
        } catch (Throwable ignored) {
            // 未安装世界地图时静默跳过
        }
    }
}
