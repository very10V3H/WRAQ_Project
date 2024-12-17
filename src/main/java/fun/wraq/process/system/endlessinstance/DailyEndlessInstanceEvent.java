package fun.wraq.process.system.endlessinstance;

import fun.wraq.common.fast.Tick;
import fun.wraq.process.system.endlessinstance.instance.EasternTower;
import fun.wraq.process.system.endlessinstance.instance.ManaPlainTemple;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class DailyEndlessInstanceEvent {
    public static List<fun.wraq.process.system.endlessinstance.DailyEndlessInstance> endlessInstanceList = new ArrayList<>();

    public static void initEndlessInstanceList() {
        endlessInstanceList.add(EasternTower.getInstance());
        endlessInstanceList.add(ManaPlainTemple.getInstance());
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
            if (Tick.get() % 10 == 1) {
                getEndlessInstanceList().forEach(instance -> {
                    instance.spawnTitleArmorStand(level);
                });
            }
        }
    }

    @SubscribeEvent
    public static void rightClick(PlayerInteractEvent event) {
        if (event.getSide().isServer() && event.getHand().equals(InteractionHand.MAIN_HAND)) {
            onRightClick(event.getEntity());
        }
    }

    public static void onRightClick(Player player) {
        getEndlessInstanceList()
                .stream().filter(instance -> instance.getPos().distanceTo(player.position()) < 10)
                .findAny().ifPresent(instance -> {
                    instance.active(player);
                });
    }

    public static void onServerStop() {
        getEndlessInstanceList().forEach(instance -> {
            instance.getMobList().forEach(mob -> {
                mob.remove(Entity.RemovalReason.KILLED);
            });
        });
    }
}
