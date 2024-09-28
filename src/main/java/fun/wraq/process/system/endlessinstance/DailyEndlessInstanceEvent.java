package fun.wraq.process.system.endlessinstance;

import fun.wraq.process.system.endlessinstance.DailyEndlessInstance;
import fun.wraq.process.system.endlessinstance.instance.EasternTower;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class DailyEndlessInstanceEvent {
    public static List<fun.wraq.process.system.endlessinstance.DailyEndlessInstance> endlessInstanceList = new ArrayList<>();

    public static void initEndlessInstanceList() {
        endlessInstanceList.add(EasternTower.getInstance());
    }

    public static List<DailyEndlessInstance> getEndlessInstanceList() {
        if (endlessInstanceList.isEmpty()) initEndlessInstanceList();
        return endlessInstanceList;
    }

    @SubscribeEvent
    public static void levelTick(TickEvent.LevelTickEvent event) {
        if (event.side.isServer() && event.level.dimension().equals(Level.OVERWORLD)
                && event.phase.equals(TickEvent.Phase.START)) {
            Level level = event.level;
            getEndlessInstanceList().forEach(instance -> {
                instance.commonTick(level);
            });
        }
    }
}
