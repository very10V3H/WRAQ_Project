/** AI-Generated, 2026-10-18 */
package fun.wraq.process.system.wayPoints;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.loading.FMLPaths;
import org.slf4j.Logger;
import xaero.common.XaeroMinimapSession;
import xaero.common.minimap.waypoints.Waypoint;
import xaero.common.minimap.waypoints.WaypointSet;
import xaero.common.minimap.waypoints.WaypointsManager;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 世界地图缩放到较远时，把“非白名单”路径点标记为 Xaero 的 disabled 状态：
 * 地图上不再绘制、列表里仍然保留并显示为“已禁用”，关闭地图或缩回来会自动恢复。
 * <p>
 * 阈值与白名单由 config/wraq-waypoint-zoom.json 配置（首次运行自动生成）。
 * 隐藏期间会把被我们改过的路径点名写进 config/wraq-waypoint-zoom-hidden.json，
 * 万一游戏异常退出导致 disabled 状态落盘，下次进入世界时据此恢复。
 * <p>
 * 注意：Xaero 世界地图渲染的是 {@code xaero.map.mods.gui.Waypoint} 快照（由
 * {@code SupportXaeroMinimap.convertWaypoints} 在 GUI 初始化/请求刷新时生成，
 * 转换时会跳过 disabled 的点），所以每次状态变化后都要让世界地图重新转换一次。
 */
@OnlyIn(Dist.CLIENT)
public class WaypointZoomHider {

    private static final Logger LOGGER = LogUtils.getLogger();

    public static final Path CONFIG_PATH = FMLPaths.CONFIGDIR.get().resolve("wraq-waypoint-zoom.json");
    public static final Path CRASH_CACHE_PATH = FMLPaths.CONFIGDIR.get().resolve("wraq-waypoint-zoom-hidden.json");

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private static final double DEFAULT_HIDE_ZOOM = 0.25D;
    private static final List<String> DEFAULT_KEEP_NAMES = List.of(
            "潮汐城中央广场", "潮汐城东北门", "项潮林", "苍岩隘口", "熔岩废墟",
            "平原村", "天空城", "雨林村", "海岸村", "火山村",
            "薰楠村", "薰曦村", "北洋村", "沙岸村", "绯樱村",
            "望山据点", "旭升岛", "北望村"
    );

    private static double hideZoom = DEFAULT_HIDE_ZOOM;
    private static final Set<String> keepNames = new LinkedHashSet<>();
    private static final List<String> keepKeywords = new ArrayList<>();
    private static boolean configLoaded;

    /** 记录被我们临时禁用的路径点及其原始状态（只会记录原本是启用的点） */
    private static final Map<Waypoint, Boolean> originalDisabled = new IdentityHashMap<>();
    private static WaypointSet currentSet;
    private static boolean hiding;
    private static boolean mapWasOpen;

    private static Class<?> cachedScreenClass;
    private static Method worldMapScaleMethod;
    private static Field worldMapDestScaleField;
    private static boolean scaleUnavailableLogged;

    private static Class<?> supportModsClass;
    private static Field supportModsField;
    private static Method requestRefreshMethod;

    public static void clientTick() {
        try {
            XaeroMinimapSession session = XaeroMinimapSession.getCurrentSession();
            if (session == null) return;
            WaypointsManager manager = session.getWaypointsManager();
            if (manager == null || manager.getCurrentWorld() == null) return;
            WaypointSet set = manager.getCurrentWorld().getCurrentSet();
            if (set == null) return;

            double scale = worldMapScale();
            boolean mapOpen = !Double.isNaN(scale);
            if (mapOpen != mapWasOpen) {
                mapWasOpen = mapOpen;
                // 每次重新打开世界地图时重读配置，改完配置不用重启游戏
                if (mapOpen) configLoaded = false;
            }
            loadConfig();

            if (currentSet != set) {
                // 换了世界/维度或重新登录：清空旧状态，并修复上次异常退出残留的隐藏标记
                currentSet = set;
                hiding = false;
                originalDisabled.clear();
                recoverFromCrashCache(set, manager);
            }

            if (mapOpen && scale <= hideZoom) {
                apply(set);
            } else if (hiding) {
                restore(set, manager);
            }
        } catch (Exception ignored) {
            // 路径点显示属于辅助功能，任何异常都不应打断客户端 tick
        }
    }

    /**
     * 当前世界地图的缩放（放大倍率，越小越远）；地图没打开时返回 NaN。
     * 优先读 {@code GuiMap#getUserScale()}，读不到则退回静态字段 {@code destScale}。
     */
    private static double worldMapScale() {
        Screen screen = Minecraft.getInstance().screen;
        if (screen == null) return Double.NaN;
        if (screen.getClass() != cachedScreenClass) {
            cachedScreenClass = screen.getClass();
            worldMapScaleMethod = findMethod(cachedScreenClass, "getUserScale");
            worldMapDestScaleField = findField(cachedScreenClass, "destScale");
            scaleUnavailableLogged = false;
        }
        if (worldMapScaleMethod != null) {
            try {
                return (double) worldMapScaleMethod.invoke(screen);
            } catch (Throwable ignored) {
            }
        }
        if (worldMapDestScaleField != null) {
            try {
                return worldMapDestScaleField.getDouble(null);
            } catch (Throwable ignored) {
            }
        }
        if (!scaleUnavailableLogged && cachedScreenClass.getName().startsWith("xaero.map.gui")) {
            scaleUnavailableLogged = true;
            LOGGER.info("[VMD-路径点] 当前界面 {} 无法读取缩放，世界地图缩远隐藏不会生效", cachedScreenClass.getName());
        }
        return Double.NaN;
    }

    private static Method findMethod(Class<?> clazz, String name) {
        for (Class<?> c = clazz; c != null; c = c.getSuperclass()) {
            try {
                return c.getMethod(name);
            } catch (NoSuchMethodException ignored) {
            }
        }
        return null;
    }

    private static Field findField(Class<?> clazz, String name) {
        for (Class<?> c = clazz; c != null; c = c.getSuperclass()) {
            try {
                Field field = c.getDeclaredField(name);
                field.setAccessible(true);
                return field;
            } catch (Throwable ignored) {
            }
        }
        return null;
    }

    private static void apply(WaypointSet set) {
        boolean changed = false;
        for (Waypoint waypoint : set.getList()) {
            if (isKept(waypoint.getName())) continue;
            Boolean original = originalDisabled.get(waypoint);
            if (original != null) {
                // 隐藏期间玩家又手动把它启用：按规则继续隐藏
                if (!waypoint.isDisabled()) {
                    waypoint.setDisabled(true);
                    changed = true;
                }
            } else if (!waypoint.isDisabled()) {
                waypoint.setDisabled(true);
                originalDisabled.put(waypoint, false);
                changed = true;
            }
        }
        if (!hiding) {
            hiding = true;
            changed = true;
            warnIfShowDisabledWaypoints();
        }
        if (changed) {
            writeCrashCache();
            requestWorldMapWaypointsRefresh();
            int kept = set.getList().size() - originalDisabled.size();
            LOGGER.info("[VMD-路径点] 世界地图缩远：隐藏 {} 个路径点（保留 {} 个）",
                    originalDisabled.size(), kept);
        }
    }

    private static void restore(WaypointSet set, WaypointsManager manager) {
        boolean changed = false;
        for (Map.Entry<Waypoint, Boolean> entry : originalDisabled.entrySet()) {
            if (entry.getKey().isDisabled() != entry.getValue()) {
                entry.getKey().setDisabled(entry.getValue());
                changed = true;
            }
        }
        int restored = originalDisabled.size();
        originalDisabled.clear();
        hiding = false;
        if (changed && !saveWaypoints(manager)) return;
        deleteCrashCache();
        requestWorldMapWaypointsRefresh();
        if (restored > 0) {
            LOGGER.info("[VMD-路径点] 世界地图缩远：恢复 {} 个路径点", restored);
        }
    }

    private static boolean isKept(String name) {
        if (keepNames.contains(name)) return true;
        for (String keyword : keepKeywords) {
            if (!keyword.isEmpty() && name.contains(keyword)) return true;
        }
        return false;
    }

    /**
     * 让 Xaero 世界地图重新把公共路径点转换成它自己的渲染对象（转换时才会读取 disabled）。
     * 反射调用，避免把 Xaero 世界地图变成硬依赖。
     */
    private static void requestWorldMapWaypointsRefresh() {
        try {
            if (supportModsClass == null) {
                supportModsClass = Class.forName("xaero.map.mods.SupportMods");
                supportModsField = supportModsClass.getField("xaeroMinimap");
            }
            Object supportMods = supportModsField.get(null);
            if (supportMods == null) return;
            if (requestRefreshMethod == null) {
                requestRefreshMethod = supportMods.getClass().getMethod("requestWaypointsRefresh");
            }
            requestRefreshMethod.invoke(supportMods);
        } catch (Throwable ignored) {
        }
    }

    /** 世界地图若开启了“显示已禁用路径点”，disabled 的点仍会被转换并绘制，这时隐藏不会生效 */
    private static void warnIfShowDisabledWaypoints() {
        try {
            Class<?> worldMapClass = Class.forName("xaero.map.WorldMap");
            Object settings = worldMapClass.getField("settings").get(null);
            if (settings == null) return;
            Field flag = settings.getClass().getField("showDisabledWaypoints");
            if (flag.getBoolean(settings)) {
                LOGGER.warn("[VMD-路径点] Xaero 世界地图开启了“显示已禁用路径点”，缩远隐藏不会生效，请在设置里关闭");
            }
        } catch (Throwable ignored) {
        }
    }

    /** 异常退出后：把上次隐藏过的点恢复为启用（这些点记录时原本都是启用的） */
    private static void recoverFromCrashCache(WaypointSet set, WaypointsManager manager) {
        if (!Files.exists(CRASH_CACHE_PATH)) return;
        Set<String> names = readCrashCache();
        boolean changed = false;
        for (Waypoint waypoint : set.getList()) {
            if (names.contains(waypoint.getName()) && waypoint.isDisabled()) {
                waypoint.setDisabled(false);
                changed = true;
            }
        }
        if (changed && !saveWaypoints(manager)) return;
        deleteCrashCache();
    }

    private static void loadConfig() {
        if (configLoaded) return;
        configLoaded = true;
        try {
            if (!Files.exists(CONFIG_PATH)) {
                hideZoom = DEFAULT_HIDE_ZOOM;
                keepNames.clear();
                keepNames.addAll(DEFAULT_KEEP_NAMES);
                keepKeywords.clear();
                writeDefaultConfig();
                return;
            }
            JsonObject json = JsonParser.parseString(Files.readString(CONFIG_PATH, StandardCharsets.UTF_8)).getAsJsonObject();
            if (json.has("hideZoom") && json.get("hideZoom").isJsonPrimitive()) {
                hideZoom = json.get("hideZoom").getAsDouble();
            }
            keepNames.clear();
            if (json.has("keep") && json.get("keep").isJsonArray()) {
                for (JsonElement element : json.getAsJsonArray("keep")) {
                    if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()) {
                        keepNames.add(element.getAsString());
                    }
                }
            }
            keepKeywords.clear();
            if (json.has("keepKeywords") && json.get("keepKeywords").isJsonArray()) {
                for (JsonElement element : json.getAsJsonArray("keepKeywords")) {
                    if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()) {
                        keepKeywords.add(element.getAsString());
                    }
                }
            }
        } catch (Exception ignored) {
            // 配置写坏时退回内置默认值，不影响游戏
            hideZoom = DEFAULT_HIDE_ZOOM;
            keepNames.clear();
            keepNames.addAll(DEFAULT_KEEP_NAMES);
            keepKeywords.clear();
        }
    }

    private static void writeDefaultConfig() {
        try {
            JsonObject json = new JsonObject();
            json.addProperty("hideZoom", DEFAULT_HIDE_ZOOM);
            JsonArray keep = new JsonArray();
            DEFAULT_KEEP_NAMES.forEach(keep::add);
            json.add("keep", keep);
            json.add("keepKeywords", new JsonArray());
            json.addProperty("_readme",
                    "世界地图缩到 hideZoom 及更远时，名称不在 keep（精确同名）且不包含 keepKeywords 任一关键字的路径点"
                            + "会被标成“已禁用”（地图上不绘制、路径点列表里保留），关闭地图或缩回来会自动恢复。"
                            + "修改保存后重新打开世界地图即可生效。");
            Files.createDirectories(CONFIG_PATH.getParent());
            Files.writeString(CONFIG_PATH, GSON.toJson(json), StandardCharsets.UTF_8);
        } catch (Exception ignored) {
        }
    }

    private static void writeCrashCache() {
        try {
            JsonObject json = new JsonObject();
            JsonArray names = new JsonArray();
            for (Waypoint waypoint : originalDisabled.keySet()) {
                names.add(waypoint.getName());
            }
            json.add("names", names);
            Files.createDirectories(CRASH_CACHE_PATH.getParent());
            Files.writeString(CRASH_CACHE_PATH, GSON.toJson(json), StandardCharsets.UTF_8);
        } catch (Exception ignored) {
        }
    }

    private static Set<String> readCrashCache() {
        Set<String> names = new LinkedHashSet<>();
        try {
            JsonObject json = JsonParser.parseString(Files.readString(CRASH_CACHE_PATH, StandardCharsets.UTF_8)).getAsJsonObject();
            if (json.has("names") && json.get("names").isJsonArray()) {
                for (JsonElement element : json.getAsJsonArray("names")) {
                    if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()) {
                        names.add(element.getAsString());
                    }
                }
            }
        } catch (Exception ignored) {
        }
        return names;
    }

    private static void deleteCrashCache() {
        try {
            Files.deleteIfExists(CRASH_CACHE_PATH);
        } catch (Exception ignored) {
        }
    }

    private static boolean saveWaypoints(WaypointsManager manager) {
        try {
            XaeroMinimapSession session = XaeroMinimapSession.getCurrentSession();
            if (session == null || session.getModMain() == null || manager.getCurrentWorld() == null) return false;
            session.getModMain().getSettings().saveWaypoints(manager.getCurrentWorld());
            return true;
        } catch (IOException ignored) {
            return false;
        }
    }
}
