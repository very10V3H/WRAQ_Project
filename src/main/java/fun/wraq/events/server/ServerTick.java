package fun.wraq.events.server;

import fun.wraq.common.fast.Tick;
import fun.wraq.events.core.BowEvent;
import fun.wraq.files.dataBases.DataBase;
import fun.wraq.process.func.DelayOperationWithAnimation;
import fun.wraq.process.func.damage.Dot;
import fun.wraq.process.func.damage.SputteringDamage;
import fun.wraq.process.system.element.Element;
import fun.wraq.process.system.market.MarketInfo;
import fun.wraq.process.system.profession.pet.allay.AllayPet;
import fun.wraq.process.system.randomevent.RandomEventsHandler;
import fun.wraq.process.system.reason.Reason;
import fun.wraq.process.system.vp.VpDataHandler;
import fun.wraq.series.instance.series.purple.PurpleIronCommon;
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
            int tickCount = Tick.get();
            if (tickCount % 100 == 0) {
                VpDataHandler.reviseDataMSGSend(event.getServer());
            }
            if (tickCount % 80 == 0) {
                PurpleIronCommon.handleServerTick();
            }
            if (tickCount % 20 == 1) {
                BowEvent.handleServerTick();
            }
            RandomEventsHandler.tick();
            Reason.serverTick();
            AllayPet.handleServerTick();
            SputteringDamage.handleServerTick();
            if (tickCount % 100 == 97) {
                Element.handleServerTick();
            }

            if (tickCount % 6000 == 3288) {
                ThreadPools.dataExecutor.execute(new Runnable() {
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
                });
            }
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
        Connection connection = DataBase.createNewDatabaseConnection();
        Statement statement = connection.createStatement();

        VpDataHandler.normalRead();
        DataBase.writeWorldInfo(statement);

        statement.close();
        connection.close();
    }
}
