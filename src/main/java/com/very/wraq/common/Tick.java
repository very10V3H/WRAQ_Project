package com.very.wraq.common;

import net.minecraft.server.MinecraftServer;

public class Tick {
    public static MinecraftServer server;
    public static int get() {
        if (server != null) return server.getTickCount();
        return 0;
    }
}
