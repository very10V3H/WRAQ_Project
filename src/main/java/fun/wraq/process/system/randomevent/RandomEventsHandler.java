package fun.wraq.process.system.randomevent;

import fun.wraq.common.fast.Te;
import fun.wraq.process.system.randomevent.impl.killmob.KillMobEvent;
import fun.wraq.process.system.randomevent.impl.killmob.multi.CaveSpiderMultiMobEvent;
import fun.wraq.process.system.randomevent.impl.killmob.multi.SlimeKingEvent;
import fun.wraq.process.system.randomevent.impl.killmob.village.VillageAttack;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class RandomEventsHandler {

    public static MinecraftServer server;

    public static List<KillMobEvent> killMobEvents = new ArrayList<>();

    public static void initKillMobEvents() {
        killMobEvents.add(new VillageAttack(Level.OVERWORLD, new Vec3(1144, 79, 40), List.of(
                Te.s("雨林村", CustomStyle.styleOfForest, "东侧突然出现了大量的",
                        "掠夺者", CustomStyle.styleOfStone),
                Te.s("赶快去保护村庄吧!")
        ), server, "雨林村掠夺者 - 弩", "雨林村掠夺者 - 斧", List.of(

        ), List.of(
                new Vec3(1144, 79, 40)
        )));

        killMobEvents.add(new VillageAttack(Level.OVERWORLD, new Vec3(846, 63, -423), List.of(
                Te.s("海岸村", CustomStyle.styleOfWater, "西侧突然出现了大量的",
                        "掠夺者", CustomStyle.styleOfStone),
                Te.s("赶快去保护村庄吧!")
        ), server, "海岸村掠夺者 - 弩", "海岸村掠夺者 - 斧", List.of(

        ), List.of(
                new Vec3(846, 63, -423)
        )));

        killMobEvents.add(new VillageAttack(Level.OVERWORLD, new Vec3(1814, 67, 400), List.of(
                Te.s("旭升岛", CustomStyle.styleOfSunIsland, "东南侧海岸突然出现了大量的",
                        "海盗", CustomStyle.styleOfSea),
                Te.s("赶快去保护村庄吧!")
        ), server, "旭升岛海盗 - 弩", "旭升岛海盗 - 斧", List.of(

        ), List.of(
                new Vec3(1814, 67, 400)
        )));

        killMobEvents.add(new CaveSpiderMultiMobEvent(Level.OVERWORLD, new Vec3(1280, 82, 208), List.of(

        ), server, List.of(
                new Vec3(1280, 82, 208)
        )));

        killMobEvents.add(new SlimeKingEvent(Level.OVERWORLD, new Vec3(1055, 63, -648), List.of(

        ), server));
    }

    public static List<KillMobEvent> getKillMobEvents() {
        if (killMobEvents.isEmpty()) {
            initKillMobEvents();
        }
        return killMobEvents;
    }

    public static void tick() {
        getKillMobEvents().forEach(RandomEvent::handleTick);
    }

    public static void onKillMob(Player player, Mob mob) {
        getKillMobEvents().forEach(event -> event.onKillMob(player, mob));
    }
}
