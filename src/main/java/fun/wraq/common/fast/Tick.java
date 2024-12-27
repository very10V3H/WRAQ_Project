package fun.wraq.common.fast;

import net.minecraft.server.MinecraftServer;

public class Tick {
    public static MinecraftServer server;
    public static int get() {
        if (server != null) return server.getTickCount();
        return 0;
    }

    public static int s(int s) {
        return 20 * s;
    }

    public static int min(int min) {
        return 1200 * min;
    }

    public static int toSeconds(int tick) {
        return tick / 20;
    }

    public static int toMinutes(int tick) {
        return tick / 1200;
    }
}
