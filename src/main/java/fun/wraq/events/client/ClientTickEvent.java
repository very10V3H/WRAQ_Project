package fun.wraq.events.client;

import fun.wraq.common.Compute;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.process.func.DelayOperationWithAnimation;
import fun.wraq.process.system.lottery.NewLotteries;
import fun.wraq.process.system.wayPoints.MyWayPoint;
import fun.wraq.process.system.wayPoints.WaypointZoomHider;
import fun.wraq.render.gui.WraqScreen;
import fun.wraq.render.gui.villagerTrade.TradeList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.text.ParseException;
import java.util.Calendar;

@Mod.EventBusSubscriber(Dist.CLIENT)
@OnlyIn(Dist.CLIENT)
public class ClientTickEvent {
    @SubscribeEvent
    public static void ClientTick(TickEvent.ClientTickEvent event) throws ParseException {
        if (event.phase == TickEvent.Phase.START) {
            // 世界地图是暂停界面（单人），PlayerTick 不会执行，所以路径点相关逻辑都挂在这里：
            // 1) 加入世界/切换维度后把 MyWayPoint 的路径点同步到 Xaero；
            // 2) 世界地图缩远时隐藏非白名单路径点。
            MyWayPoint.trySyncWaypoints();
            WaypointZoomHider.clientTick();
            DelayOperationWithAnimation.clientTick(event);
            if (ClientUtils.serverTime != null) {
                NewLotteries.setCurrentLotteryLoots(Compute
                                .StringToCalendar(ClientUtils.serverTime)
                                .get(Calendar.DAY_OF_YEAR));
                TradeList.setTradeContent();
            }
            WraqScreen.infoLifeCycleTicks = Math.max(0, WraqScreen.infoLifeCycleTicks - 1);
        }
    }
}
