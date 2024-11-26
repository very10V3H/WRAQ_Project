package fun.wraq.process.system.randomevent;

import fun.wraq.process.system.randomevent.impl.killmob.VillageAttack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class RandomEventsHandler {

    public static MinecraftServer server;

    public static List<KillMobEvent> killMobEvents = new ArrayList<>() {{
        add(new VillageAttack(Level.OVERWORLD, new Vec3(), List.of(

        ), server, "雨林村掠夺者 - 弩", "雨林村掠夺者 - 斧", List.of(

        ), List.of(

        )));

        add(new VillageAttack(Level.OVERWORLD, new Vec3(), List.of(

        ), server, "海岸村掠夺者 - 弩", "海岸村掠夺者 - 斧", List.of(

        ), List.of(

        )));

        add(new VillageAttack(Level.OVERWORLD, new Vec3(), List.of(

        ), server, "旭升岛海盗 - 弩", "旭升岛海盗 - 斧", List.of(

        ), List.of(

        )));
    }};

    public static void onKillMob(Player player, Mob mob) {
        killMobEvents.forEach(event -> event.onKillMob(player, mob));
    }
}
