package fun.wraq.events.client;

import fun.wraq.common.Compute;
import fun.wraq.common.util.ClientUtils;
import fun.wraq.process.func.DelayOperationWithAnimation;
import fun.wraq.process.system.lottery.NewLotteries;
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
            DelayOperationWithAnimation.clientTick(event);
            if (ClientUtils.serverTime != null) {
                NewLotteries.setCurrentLotteryLoots(Compute
                                .StringToCalendar(ClientUtils.serverTime)
                                .get(Calendar.DAY_OF_YEAR));
            }
        }
    }
}
