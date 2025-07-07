package fun.wraq.process.func.security.mac;

import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class MacServer {

    public static Map<String, String> MAC_MAP = new HashMap<>();
    public static Map<String, Set<String>> MAC_TO_PLAYER_NAMES_MAP = new HashMap<>();
    public static Set<String> WHITE_LIST = new HashSet<>();

    public static void checkMac(ServerPlayer serverPlayer, String mac) {
        if (WHITE_LIST.contains(Name.get(serverPlayer))) {
            return;
        }
        if (MAC_MAP.containsValue(mac)) {
            AtomicReference<String> name = new AtomicReference<>();
            MAC_MAP.forEach((k, v) -> {
                if (v.equals(mac)) {
                    name.set(k);
                }
            });
            serverPlayer.connection.disconnect(Te.s("同一台硬件设备只允许登录一个账号。已登录的账号：", name.get()));
        } else {
            MAC_MAP.put(serverPlayer.getName().getString(), mac);
        }

        if (!MAC_TO_PLAYER_NAMES_MAP.containsKey(mac)) {
            MAC_TO_PLAYER_NAMES_MAP.put(mac, new HashSet<>());
        }
        if (MAC_TO_PLAYER_NAMES_MAP.get(mac).size() >= 3) {
            MutableComponent component = Te.s("");
            MAC_TO_PLAYER_NAMES_MAP.get(mac).forEach(name -> {
                component.append(Te.s(" ", name));
            });
            serverPlayer.connection.disconnect(Te.s("这台硬件设备今天登陆过过多账号了! 曾登录的账号: ", component));
        } else {
            MAC_TO_PLAYER_NAMES_MAP.get(mac).add(mac);
        }
    }

    public static void onLogOut(ServerPlayer serverPlayer) {
        MAC_MAP.remove(serverPlayer.getName().getString());
    }
}
