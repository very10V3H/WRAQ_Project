package com.very.wraq.events.core;

import com.very.wraq.process.tower.Tower;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.sql.SQLException;

@Mod.EventBusSubscriber
public class ServerTick {
    @SubscribeEvent
    public static void ServerTickEvent(TickEvent.ServerTickEvent event) throws SQLException {
        if (event.side.isServer() && event.phase == TickEvent.Phase.START) {
            int tickCount = event.getServer().getTickCount();
            if (tickCount % 36000 == 20000) {
                Tower.writeToDataBase();
            }
        }
    }
}
