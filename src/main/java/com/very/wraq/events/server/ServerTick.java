package com.very.wraq.events.server;

import com.very.wraq.events.mob.MobSpawn;
import com.very.wraq.files.dataBases.DataBase;
import com.very.wraq.process.func.DelayOperationWithAnimation;
import com.very.wraq.process.func.damage.Dot;
import com.very.wraq.process.func.plan.PlanPlayer;
import com.very.wraq.process.system.lottery.NewLotteries;
import com.very.wraq.process.system.market.MarketInfo;
import com.very.wraq.process.system.tower.Tower;
import com.very.wraq.process.system.vp.VpDataHandler;
import com.very.wraq.series.instance.mixture.WraqMixture;
import com.very.wraq.series.instance.quiver.WraqQuiver;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber
public class ServerTick {
    @SubscribeEvent
    public static void ServerTickEvent(TickEvent.ServerTickEvent event) throws SQLException {
        LoginInEvent.newMSGSend(event);
        Dot.tick(event);
        if (event.side.isServer() && event.phase == TickEvent.Phase.START) {
            DelayOperationWithAnimation.serverTick(event);
            WraqQuiver.tick();
            WraqMixture.tick();
            int tickCount = event.getServer().getTickCount();
            if (tickCount % 6000 == 3288) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MarketInfo.marketItemInfoWrite(event.getServer().overworld());
                        MarketInfo.marketProfitInfoWrite(event.getServer().overworld());
                        try {
                            dataIO();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }).start();
            }

            if (tickCount % 100 == 0) VpDataHandler.reviseDataMSGSend(event.getServer());
        }
    }

    public static <T> void clearMap(Map<Integer, T> map, Level overworld, Level nether, Level end) {
        List<Integer> removeList = new ArrayList<>();
        map.keySet().forEach(id -> {
            boolean flag = true;
            if (overworld.getEntity(id) != null && overworld.getEntity(id).isAlive()) flag = false;
            if (nether.getEntity(id) != null && nether.getEntity(id).isAlive()) flag = false;
            if (end.getEntity(id) != null && end.getEntity(id).isAlive()) flag = false;
            if (flag) removeList.add(id);
        });
        removeList.forEach(map::remove);
    }

    public static void dataIO() throws SQLException {
        Connection connection = DataBase.getDatabaseConnection();
        Statement statement = connection.createStatement();

        Tower.writeToDataBase(statement);
        Tower.writeStarCountToDataBase(statement);
        NewLotteries.writeToDataBase(statement);
        MobSpawn.writeToSQL(statement);
        PlanPlayer.write();
        VpDataHandler.normalRead();
        DataBase.writeWorldInfo(statement);

        statement.close();
        connection.close();
    }
}
