package com.very.wraq.events.core;

import com.very.wraq.events.mob.MobSpawn;
import com.very.wraq.files.dataBases.DataBase;
import com.very.wraq.process.func.damage.Dot;
import com.very.wraq.process.system.element.Element;
import com.very.wraq.process.system.element.equipAndCurios.waterElement.WaterElementSword;
import com.very.wraq.process.series.lottery.NewLotteries;
import com.very.wraq.process.func.plan.PlanPlayer;
import com.very.wraq.process.system.tower.Tower;
import com.very.wraq.process.system.vp.VpDataHandler;
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
            int tickCount = event.getServer().getTickCount();
            if (tickCount % 20 == 0) {
                Level overworld = event.getServer().getLevel(Level.OVERWORLD);
                Level nether = event.getServer().getLevel(Level.NETHER);
                Level end = event.getServer().getLevel(Level.END);
                List<Map<Integer, Integer>> mapList = new ArrayList<>() {{
                    add(Element.lifeElementParticle);
                    add(Element.waterElementParticle);
                    add(Element.fireElementParticle);
                    add(Element.stoneElementParticle);
                    add(Element.windElementParticle);
                    add(Element.iceElementParticle);
                    add(Element.lightningElementParticle);

                    add(Element.lifeAndIceTickMap);
                    add(Element.lifeAndLightningTimesMap);
                    add(Element.lifeAndWindTimesMap);
                    add(Element.waterAndStoneTimesMap);
                    add(Element.waterAndIceTimesMap);
                    add(Element.waterAndLightningTimesMap);
                    add(Element.fireAndWindTimesMap);
                    add(Element.stoneAndIceTimesMap);
                    add(Element.stoneAndLightningTimesMap);
                    add(Element.stoneAndWindTimesMap);
                    add(Element.iceAndLightningTimesMap);
                    add(Element.iceAndWindTimesMap);
                    add(WaterElementSword.mobDefenceDecreaseTickMap);
                }};
                List<Map<Integer, Double>> mapList1 = new ArrayList<>() {{
                    add(Element.lifeAndIceEffectMap);
                    add(Element.lifeAndLightningEffectMap);
                    add(Element.lifeAndWindEffectMap);
                    add(Element.waterAndStoneEffectMap);
                    add(Element.waterAndIceEffectMap);
                    add(Element.waterAndLightningEffectMap);
                    add(Element.fireAndWindEffectMap);
                    add(Element.stoneAndIceEffectMap);
                    add(Element.stoneAndLightningEffectMap);
                    add(Element.stoneAndWindEffectMap);
                    add(Element.iceAndLightningEffectMap);
                    add(Element.iceAndWindEffectMap);
                }};
                mapList.forEach(map -> {
                    clearMap(map, overworld, nether, end);
                });
                mapList1.forEach(map -> {
                    clearMap(map, overworld, nether, end);
                });
                clearMap(Element.entityElementUnit, overworld, nether, end);
                clearMap(BowEvent.causeDamageList, overworld, nether, end);

            }
            if (tickCount % 6000 == 3288) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
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
        DataBase.putAllMarketItemInfo(statement);
        DataBase.putAllMarketPlayerInfo(statement);
        MobSpawn.writeToSQL(statement);
        PlanPlayer.write();
        /*PlanPlayer.readTier();*/
        VpDataHandler.normalRead();

        statement.close();
        connection.close();
    }
}
