package fun.wraq.process.system.randomevent;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.process.func.StableAttributesModifier;
import fun.wraq.process.system.randomevent.impl.dig.DigBlockEvent;
import fun.wraq.process.system.randomevent.impl.killmob.KillMobEvent;
import fun.wraq.process.system.randomevent.impl.killmob.SlimeKingEvent;
import fun.wraq.process.system.randomevent.impl.killmob.multi.CaveSpiderMultiMobEvent;
import fun.wraq.process.system.randomevent.impl.killmob.multi.VillageAttack;
import fun.wraq.process.system.randomevent.impl.urgent.UrgentEvent;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class RandomEventsHandler {

    public static MinecraftServer server;

    public static List<RandomEvent> randomEvents = new ArrayList<>();

    public static void initRandomEvents() {
        randomEvents.addAll(getKillMobEvents());
        randomEvents.addAll(getUrgentEvents());
        randomEvents.addAll(getDigBlockEvents());
    }

    public static void removeRandomEvents() {
        randomEvents.clear();
        killMobEvents.clear();
        urgentEvents.clear();
        digBlockEvents.clear();
    }

    public static List<RandomEvent> getRandomEvents() {
        if (randomEvents.isEmpty()) {
            initRandomEvents();
        }
        return randomEvents;
    }

    public static void onServerStop() {
        for (RandomEvent randomEvent : getRandomEvents()) {
            randomEvent.reset();
        }
        RandomEventsHandler.removeRandomEvents();
    }

    public static List<KillMobEvent> killMobEvents = new ArrayList<>();

    public static void initKillMobEvents() {
        killMobEvents.add(new VillageAttack(Level.OVERWORLD, new Vec3(1144, 79, 40), List.of(
                Te.s("雨林村", CustomStyle.styleOfForest, "似乎马上就要有", "掠夺者", CustomStyle.styleOfStone,
                        "入侵了")
        ), List.of(
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
                new Vec3(1119, 82, 40),
                new Vec3(1131, 80, 45),
                new Vec3(1141, 79, 46),
                new Vec3(1144, 79, 40),
                new Vec3(1137, 80, 35)
        ), RandomEvent.getDefaultRewardList()));

        killMobEvents.add(new VillageAttack(Level.OVERWORLD, new Vec3(846, 63, -423), List.of(
                Te.s("海岸村", CustomStyle.styleOfWater, "似乎马上就要有", "掠夺者", CustomStyle.styleOfStone,
                        "入侵了")
        ), List.of(
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
                new Vec3(846, 63, -423),
                new Vec3(849, 64, -429),
                new Vec3(854, 63, -423),
                new Vec3(853, 63, -414),
                new Vec3(865, 64, -429),
                new Vec3(874, 62, -422)
        ), RandomEvent.getDefaultRewardList()));

        killMobEvents.add(new VillageAttack(Level.OVERWORLD, new Vec3(1814, 67, 400), List.of(
                Te.s("旭升岛", CustomStyle.styleOfSunIsland, "似乎马上就要有", "海盗", CustomStyle.styleOfSea,
                        "入侵了")
        ), List.of(
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
                new Vec3(1814, 67, 400),
                new Vec3(1798, 66, 388),
                new Vec3(1816, 67, 390),
                new Vec3(1808, 67, 392),
                new Vec3(1801, 66, 398),
                new Vec3(1809, 67, 399)
        ), RandomEvent.getDefaultRewardList()));

        killMobEvents.add(new CaveSpiderMultiMobEvent(Level.OVERWORLD, new Vec3(1280, 82, 208), List.of(
                Te.s("纽维庙", CustomStyle.styleOfForest, "似乎有些", "异动", CustomStyle.styleOfLife)
        ), List.of(
                Te.s("大量的", "洞穴蜘蛛", CustomStyle.styleOfLife, "出现在了", "纽维庙", CustomStyle.styleOfLife,
                        "附近"),
                Te.s("击杀它们可以获取大量的奖励!")
        ), List.of(
                Te.s("纽维庙", CustomStyle.styleOfLife, "里的泛滥的",
                        "洞穴蜘蛛", CustomStyle.styleOfLife, "被清理干净了!")
        ), List.of(
                Te.s("纽维庙", CustomStyle.styleOfLife, "里的", "洞穴蜘蛛", CustomStyle.styleOfLife, "泛滥成灾"),
                Te.s("不知道今晚睡觉会不会跑去找你呢？")
        ), server, List.of(
                new Vec3(1280, 82, 208)
        ), RandomEvent.getDefaultRewardList(), null));

        killMobEvents.add(new SlimeKingEvent(Level.OVERWORLD, new Vec3(1055, 63, -648), List.of(
                Te.s("莱姆king", CustomStyle.styleOfLife, "说:",
                        "在座的各位除了我都是LJ", CustomStyle.styleOfFlexible)
        ), List.of(
                Te.s("莱姆king", CustomStyle.styleOfLife, "出现了!")
        ), List.of(
                Te.s("莱姆king", CustomStyle.styleOfLife, "被击碎了!", "它并不甘心，马上就要二度降临!")
        ), List.of(
                Te.s("莱姆king", CustomStyle.styleOfLife, "踩碎了你们的尊严，并吐着粘液溜走了"),
                Te.s("这被羞辱的感觉可不好受呀")
        ), server, RandomEvent.getDefaultRewardList(), null));
    }

    public static List<KillMobEvent> getKillMobEvents() {
        if (killMobEvents.isEmpty()) {
            initKillMobEvents();
        }
        return killMobEvents;
    }

    public static List<UrgentEvent> urgentEvents = new ArrayList<>();

    public static void initUrgentEvents() {
        urgentEvents.add(new UrgentEvent(Level.OVERWORLD, new Vec3(1460, 74, -900), List.of(
                Te.s("有物资被投放至", "炼雨湖心", CustomStyle.styleOfWater, "附近了，赶快去领取吧!")
        ), List.of(
                Te.s("炼雨湖心", CustomStyle.styleOfWater, "的物资已被领取完毕")
        ), server, RandomEvent.getDefaultRewardList(), null));

        urgentEvents.add(new UrgentEvent(Level.OVERWORLD, new Vec3(2132, 304, -228), List.of(
                Te.s("有物资被投放至", "朔山", CustomStyle.styleOfMoon1, "附近了，赶快去领取吧!")
        ), List.of(
                Te.s("朔山", CustomStyle.styleOfMoon1, "的物资已被领取完毕")
        ), server, RandomEvent.getDefaultRewardList(), null));

        urgentEvents.add(new UrgentEvent(Level.OVERWORLD, new Vec3(1390, 81, -262), List.of(
                Te.s("有物资被投放至", "唤魔庙", CustomStyle.styleOfMana, "附近了，赶快去领取吧!")
        ), List.of(
                Te.s("唤魔庙", CustomStyle.styleOfMana, "的物资已被领取完毕")
        ), server, RandomEvent.getDefaultRewardList(), null));

        urgentEvents.add(new UrgentEvent(Level.OVERWORLD, new Vec3(754, 181, -86), List.of(
                Te.s("有物资被投放至", "德朗斯蒂克高原", CustomStyle.styleOfPlain, "附近了，赶快去领取吧!")
        ), List.of(
                Te.s("德朗斯蒂克高原", CustomStyle.styleOfPlain, "的物资已被领取完毕")
        ), server, RandomEvent.getDefaultRewardList(), null));
    }

    public static List<UrgentEvent> getUrgentEvents() {
        if (urgentEvents.isEmpty()) {
            initUrgentEvents();
        }
        return urgentEvents;
    }

    public static List<DigBlockEvent> digBlockEvents = new ArrayList<>();

    public static void initDigBlockEvents() {
        digBlockEvents.add(new DigBlockEvent(Level.OVERWORLD, new Vec3(1534, 81, 305), List.of(
                Te.s("炼魔平原", CustomStyle.styleOfMana, "附近地面", "析出", CustomStyle.styleOfMana,
                        "了一大片", "魔源晶石", CustomStyle.styleOfMana),
                Te.s("速速前往清理它们吧！")
        ), List.of(
                Te.s("你们成功清理了", "炼魔平原", CustomStyle.styleOfMana, "的", "析出魔晶", CustomStyle.styleOfMana),
                Te.s("天空城", CustomStyle.styleOfSky, "给予了你们丰厚的奖赏!")
        ), List.of(
                Te.s("炼魔平原", CustomStyle.styleOfMana, "的", "析出魔晶", CustomStyle.styleOfMana, "再次溶解进了地下")
        ), server, List.of(
                Blocks.AMETHYST_BLOCK
        ), RandomEvent.getDefaultRewardList(), null));

/*        digBlockEvents.add(new DigBlockEvent(Level.OVERWORLD, new Vec3(1074, 77, -1260), List.of(
                Te.s("薰曦村", CustomStyle.styleOfJacaranda, "东南侧的",
                        "薰衣草", CustomStyle.styleOfJacaranda, "长得太多了，居民们正在为之苦恼"),
                Te.s("热心的你还不快去帮他们清理吗？")
        ), List.of(
                Te.s("你们成功清理了", "薰曦村", CustomStyle.styleOfJacaranda, "东南侧泛滥的",
                        "薰衣草", CustomStyle.styleOfJacaranda),
                Te.s("居民们给予了你们丰厚的奖赏!")
        ), List.of(
                Te.s("薰曦村", CustomStyle.styleOfJacaranda, "东南侧的",
                        "薰衣草", CustomStyle.styleOfJacaranda, "泛滥成灾了")
        ), server, List.of(
                BOPBlocks.TALL_LAVENDER.get()
        ), RandomEvent.getDefaultRewardList(), null));*/
    }

    public static List<DigBlockEvent> getDigBlockEvents() {
        if (digBlockEvents.isEmpty()) {
            initDigBlockEvents();
        }
        return digBlockEvents;
    }

    public static RandomEvent nextTimeEvent;

    public static int status = -1;

    public static int lastRandomElementTrigMinute = -1;

    public static void tick() {
        getRandomEvents()
                .stream().filter(event -> event.isCarryingOut)
                .forEach(RandomEvent::handleTick);
        Calendar calendar = Calendar.getInstance();
        int minute = calendar.get(Calendar.MINUTE);
        if ((minute == 59 || minute == 29) && status != 0) {
            ready();
            status = 0;
        }
        if ((minute == 0 || minute == 30) && status != 1 && nextTimeEvent != null) {
            begin();
            status = 1;
        }
        if ((minute == 15 || minute == 45) && lastRandomElementTrigMinute != minute) {
            lastRandomElementTrigMinute = minute;
            randomElement();
        }
    }

    public static void randomElement() {
        int randomNum = RandomUtils.nextInt(0, 7);
        List<ServerPlayer> players = Tick.server.getPlayerList().getPlayers();
        switch (randomNum) {
            case 0 -> {
                players.forEach(player -> {
                    StableAttributesModifier.addM(player, StableAttributesModifier.playerPercentHealthRecoverModifier,
                            "RandomElementEvent", 0.02, Tick.get() + Tick.min(15),
                            ModItems.LIFE_ELEMENT.get());
                    sendElementMSG(player, Te.s("生机迸发，万物复苏", CustomStyle.styleOfLife,
                            " - 获得持续15min的", ComponentUtils.AttributeDescription.healthRecover("2%")));
                });
            }
            case 1 -> {
                players.forEach(player -> {
                    StableAttributesModifier.addM(player, StableAttributesModifier.playerCooldownModifier,
                            "RandomElementEvent", 0.3, Tick.get() + Tick.min(15),
                            ModItems.WATER_ELEMENT.get());
                    sendElementMSG(player, Te.s("碧水匀涌，青碧无暇", CustomStyle.styleOfWater,
                            " - 获得持续15min的", ComponentUtils.AttributeDescription.coolDown("30")));
                });
            }
            case 2 -> {
                players.forEach(player -> {
                    StableAttributesModifier.addM(player, StableAttributesModifier.playerPercentAttackDamageModifier,
                            "RandomElementEvent", 0.1, Tick.get() + Tick.min(15),
                            ModItems.FIRE_ELEMENT.get());
                    StableAttributesModifier.addM(player, StableAttributesModifier.playerPercentManaDamageModifier,
                            "RandomElementEvent", 0.1, Tick.get() + Tick.min(15));
                    sendElementMSG(player, Te.s("炽热烈焰，燃地冲顶", CustomStyle.styleOfFire,
                            " - 获得持续15min的", ComponentUtils.AttributeDescription.attackDamage("10%"),
                            "与", ComponentUtils.AttributeDescription.manaDamage("10%"), "增幅。"));
                });
            }
            case 3 -> {
                players.forEach(player -> {
                    StableAttributesModifier.addM(player, StableAttributesModifier.playerPercentDefenceModifier,
                            "RandomElementEvent", 0.2, Tick.get() + Tick.min(15),
                            ModItems.STONE_ELEMENT.get());
                    StableAttributesModifier.addM(player, StableAttributesModifier.playerPercentManaDefenceModifier,
                            "RandomElementEvent", 0.2, Tick.get() + Tick.min(15));
                    sendElementMSG(player, Te.s("层岩叠嶂，震天撼地", CustomStyle.styleOfStone,
                            " - 获得持续15min的", ComponentUtils.AttributeDescription.defence("20%"),
                            "与", ComponentUtils.AttributeDescription.manaDefence("20%"), "增幅。"));
                });
            }
            case 4 -> {
                players.forEach(player -> {
                    StableAttributesModifier.addM(player, StableAttributesModifier.playerCritRateModifier,
                            "RandomElementEvent", 0.2, Tick.get() + Tick.min(15),
                            ModItems.ICE_ELEMENT.get());
                    sendElementMSG(player, Te.s("凌冽冰气，摧肤刺骨", CustomStyle.styleOfIce,
                            " - 获得持续15min的", ComponentUtils.AttributeDescription.critRate("20%")));
                });
            }
            case 5 -> {
                players.forEach(player -> {
                    StableAttributesModifier.addM(player, StableAttributesModifier.playerCritDamageModifier,
                            "RandomElementEvent", 0.4, Tick.get() + Tick.min(15),
                            ModItems.LIGHTNING_ELEMENT.get());
                    sendElementMSG(player, Te.s("暴怒狂雷，响彻大地", CustomStyle.styleOfLightingIsland,
                            " - 获得持续15min的", ComponentUtils.AttributeDescription.critDamage("40%")));
                });
            }
            case 6 -> {
                players.forEach(player -> {
                    StableAttributesModifier.addM(player, StableAttributesModifier.playerMovementSpeedModifier,
                            "RandomElementEvent", 0.3, Tick.get() + Tick.min(15),
                            ModItems.WIND_ELEMENT.get());
                    sendElementMSG(player, Te.s("微风吹拂，驭气漂浮", CustomStyle.styleOfKaze,
                            " - 获得持续15min的", ComponentUtils.AttributeDescription.movementSpeed("30%")));
                });
            }
        }
    }

    public static void sendElementMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("元素事件", ChatFormatting.LIGHT_PURPLE), content);
    }

    public static void ready() {
        Random random = new Random();
        nextTimeEvent = getRandomEvents().get(random.nextInt(getRandomEvents().size() - 1));
        nextTimeEvent.setWorldSoul5Reward(random.nextDouble() < 0.5);
        if (!nextTimeEvent.readyAnnouncement.isEmpty()) {
            nextTimeEvent.readyAnnouncement.forEach(component -> {
                nextTimeEvent.broad(component);
            });
            if (nextTimeEvent.hasWorldSoul5Reward) {
                Component component = ModItems.WORLD_SOUL_5.get().getDefaultInstance().getDisplayName();
                nextTimeEvent.broad(Te.s("这是一个带有", component, "奖励的", "随机事件!", CustomStyle.styleOfLife,
                        "参与即可获得", component));
            }
        }
    }

    public static void forcedFinish() {
        nextTimeEvent.setForcedFinish();
    }

    public static void begin() {
        nextTimeEvent.begin();
        if (nextTimeEvent.readyAnnouncement.isEmpty()) {
            if (nextTimeEvent.hasWorldSoul5Reward) {
                Component component = ModItems.WORLD_SOUL_5.get().getDefaultInstance().getDisplayName();
                nextTimeEvent.broad(Te.s("这是一个带有", component, "奖励的", "随机事件!", CustomStyle.styleOfLife,
                        "参与即可获得", component));
            }
        }
    }

    public static void onKillMob(Player player, Mob mob) {
        getKillMobEvents().stream().filter(event -> event.isCarryingOut).forEach(event -> event.onKillMob(player, mob));
    }

    public static void onBreakBlock(Player player, BlockPos blockPos) {
        if (getDigBlockEvents().stream().anyMatch(event -> event.blockPosSet.contains(blockPos))) {
            getDigBlockEvents().stream().filter(event -> event.isCarryingOut)
                    .forEach(event -> event.onBreakBlock(player, blockPos));
        }
    }
}
