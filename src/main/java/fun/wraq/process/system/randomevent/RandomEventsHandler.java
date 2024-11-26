package fun.wraq.process.system.randomevent;

import fun.wraq.common.fast.Te;
import fun.wraq.process.system.randomevent.impl.killmob.KillMobEvent;
import fun.wraq.process.system.randomevent.impl.killmob.SlimeKingEvent;
import fun.wraq.process.system.randomevent.impl.killmob.multi.CaveSpiderMultiMobEvent;
import fun.wraq.process.system.randomevent.impl.killmob.multi.VillageAttack;
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
        ), List.of(
                Te.s("大家成功保卫住了", "雨林村", CustomStyle.styleOfForest),
                Te.s("雨林村", CustomStyle.styleOfForest, "的民众们向你们表示崇高敬意，并给予了你们一些物资")
        ), List.of(
                Te.s("雨林村", CustomStyle.styleOfForest, "被", "掠夺者", CustomStyle.styleOfStone, "抢走了超多物资"),
                Te.s("民众对", "联合研院与天空城", CustomStyle.styleOfWorld, "的援助感到十分失望")
        ), server, "雨林村掠夺者弩手", "雨林村掠夺者斧卫", List.of(

        ), List.of(
                new Vec3(1144, 79, 40)
        )));

        killMobEvents.add(new VillageAttack(Level.OVERWORLD, new Vec3(846, 63, -423), List.of(
                Te.s("海岸村", CustomStyle.styleOfWater, "西侧突然出现了大量的",
                        "掠夺者", CustomStyle.styleOfStone),
                Te.s("赶快去保护村庄吧!")
        ), List.of(
                Te.s("大家成功保卫住了", "海岸村", CustomStyle.styleOfWater),
                Te.s("海岸村", CustomStyle.styleOfWater, "的民众们向你们表示崇高敬意，并给予了你们一些物资")
        ), List.of(
                Te.s("海岸村", CustomStyle.styleOfWater, "被", "掠夺者", CustomStyle.styleOfStone, "抢走了超多物资"),
                Te.s("民众对", "联合研院与天空城", CustomStyle.styleOfWorld, "的援助感到十分失望")
        ), server, "海岸村掠夺者弩手", "海岸村掠夺者斧卫", List.of(

        ), List.of(
                new Vec3(846, 63, -423)
        )));

        killMobEvents.add(new VillageAttack(Level.OVERWORLD, new Vec3(1814, 67, 400), List.of(
                Te.s("旭升岛", CustomStyle.styleOfSunIsland, "东南侧海岸突然出现了大量的",
                        "海盗", CustomStyle.styleOfSea),
                Te.s("赶快去保护村庄吧!")
        ), List.of(
                Te.s("大家成功保卫住了", "旭升岛", CustomStyle.styleOfSunIsland),
                Te.s("旭升岛", CustomStyle.styleOfSunIsland, "的民众们向你们表示崇高敬意，并给予了你们一些物资")
        ), List.of(
                Te.s("旭升岛", CustomStyle.styleOfSunIsland, "被", "掠夺者", CustomStyle.styleOfStone, "抢走了超多物资"),
                Te.s("民众对", "联合研院与天空城", CustomStyle.styleOfWorld, "的援助感到十分失望")
        ), server, "旭升岛海盗弩手", "旭升岛海盗斧卫", List.of(

        ), List.of(
                new Vec3(1814, 67, 400)
        )));

        killMobEvents.add(new CaveSpiderMultiMobEvent(Level.OVERWORLD, new Vec3(1280, 82, 208), List.of(
                Te.s("大量的", "洞穴蜘蛛", CustomStyle.styleOfLife, "出现在了", "纽维庙", CustomStyle.styleOfLife,
                        "附近"),
                Te.s("击杀它们可以获取大量的奖励!")
        ), List.of(
                Te.s("纽维庙", CustomStyle.styleOfLife, "里的", "洞穴蜘蛛", CustomStyle.styleOfLife, "")
        ), server, List.of(
                new Vec3(1280, 82, 208)
        )));

        killMobEvents.add(new SlimeKingEvent(Level.OVERWORLD, new Vec3(1055, 63, -648), List.of(
                Te.s("莱姆king", CustomStyle.styleOfLife, "出现了!")
        ), List.of(
                Te.s("莱姆king", CustomStyle.styleOfLife, "被击碎了!", "它并不甘心，马上就要二度降临!")
        ), List.of(
                Te.s("莱姆king", CustomStyle.styleOfLife, "踩碎了你们的尊严，并吐着粘液溜走了"),
                Te.s("这被羞辱的感觉可不好受呀")
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
