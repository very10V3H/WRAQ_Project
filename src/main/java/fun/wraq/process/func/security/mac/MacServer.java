package fun.wraq.process.func.security.mac;

import fun.wraq.common.fast.Te;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class MacServer {

    public static Map<String, String> MAC_MAP = new HashMap<>();

    public static void checkMac(ServerPlayer serverPlayer, String mac) {
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
    }

    public static void onLogOut(ServerPlayer serverPlayer) {
        MAC_MAP.remove(serverPlayer.getName().getString());
    }
}
