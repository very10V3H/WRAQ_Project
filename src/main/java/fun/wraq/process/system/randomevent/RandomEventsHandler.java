package fun.wraq.process.system.randomevent;

import fun.wraq.process.system.randomevent.impl.killmob.KillMobEvent;
import fun.wraq.process.system.randomevent.impl.killmob.village.VillageAttack;
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
        killMobEvents.add(new VillageAttack(Level.OVERWORLD, new Vec3(0, 0, 0), List.of(

        ), server, "雨林村掠夺者 - 弩", "雨林村掠夺者 - 斧", List.of(

        ), List.of(

        )));

        killMobEvents.add(new VillageAttack(Level.OVERWORLD, new Vec3(0, 0, 0), List.of(

        ), server, "海岸村掠夺者 - 弩", "海岸村掠夺者 - 斧", List.of(

        ), List.of(

        )));

        killMobEvents.add(new VillageAttack(Level.OVERWORLD, new Vec3(0, 0, 0), List.of(

        ), server, "旭升岛海盗 - 弩", "旭升岛海盗 - 斧", List.of(

        ), List.of(

        )));
    }

    public static List<KillMobEvent> getKillMobEvents() {
        if (killMobEvents.isEmpty()) {
            initKillMobEvents();
        }
        return killMobEvents;
    }

    public static void onKillMob(Player player, Mob mob) {
        getKillMobEvents().forEach(event -> event.onKillMob(player, mob));
    }
}
