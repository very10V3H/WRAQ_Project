package fun.wraq.process.system.entrustment.MobEntrustmentInfo;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.func.rank.RankData;
import fun.wraq.process.system.tower.Tower;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MobKillEntrustment {
    public Component mobName;
    public int targetCount;
    public int startServerTick;

    public MobKillEntrustment(Component mobName, int targetCount, int startServerTick) {
        this.mobName = mobName;
        this.targetCount = targetCount;
        this.startServerTick = startServerTick;
    }

    public static Map<String, MobKillEntrustment> playerCurrentEntrustmentMap = new HashMap<>();
    public static Map<String, Integer> playerNextAllowAcceptTickMap = new HashMap<>();
    public static Map<String, Integer> playerKillProcessMap = new HashMap<>();

    public static final String PLAYER_DATA_KEY = "MobKillEntrustmentData";

    public static CompoundTag getPlayerData(Player player) {
        CompoundTag tag = player.getPersistentData();
        if (!tag.contains(PLAYER_DATA_KEY)) {
            tag.put(PLAYER_DATA_KEY, new CompoundTag());
        }
        return tag.getCompound(PLAYER_DATA_KEY);
    }

    public static final String REPUTATION_DATA_KEY = "Reputation";
    public static int getPlayerReputation(Player player) {
        return getPlayerData(player).getInt(REPUTATION_DATA_KEY);
    }
    public static void setPlayerReputation(Player player, int reputation) {
        getPlayerData(player).putInt(REPUTATION_DATA_KEY, reputation);
    }
    public static void incrementPlayerReputation(Player player, int increment) {
        setPlayerReputation(player, getPlayerReputation(player) + increment);
        if (increment > 0) {
            sendMSG(player, Te.s("+" + increment, ChatFormatting.GREEN, "悬赏声望", CustomStyle.styleOfWorld));
        } else {
            sendMSG(player, Te.s("-" + Math.abs(increment), ChatFormatting.RED, "悬赏声望", CustomStyle.styleOfWorld));
        }
    }
    public static double getExRateOfReputation(int reputation) {
        if (reputation < 5) {
            return 0;
        } else if (reputation < 10) {
            return (reputation - 5) * 0.1;
        } else {
            return 0.5;
        }
    }

    public static final String EXPIRED_LEFT_MIN_KEY = "ExpiredLeftMin";
    public static int getExpiredLeftMin(Player player) {
        return getPlayerData(player).getInt(EXPIRED_LEFT_MIN_KEY);
    }
    public static void setExpiredLeftMin(Player player, int expiredLeftMin) {
        getPlayerData(player).putInt(EXPIRED_LEFT_MIN_KEY, expiredLeftMin);
    }
    public static void incrementExpiredLeftMin(Player player, int increment) {
        setExpiredLeftMin(player, getExpiredLeftMin(player) + increment);
    }

    public static final String DAILY_FINISHED_TIME_KEY = "DailyFinishedTime";
    public static int getDailyFinishedTime(Player player) {
        return getPlayerData(player).getInt(DAILY_FINISHED_TIME_KEY);
    }
    public static void setDailyFinishedTime(Player player, int dailyFinishedTime) {
        getPlayerData(player).putInt(DAILY_FINISHED_TIME_KEY, dailyFinishedTime);
    }
    public static void incrementDailyFinishedTime(Player player, int increment) throws SQLException {
        setDailyFinishedTime(player, getDailyFinishedTime(player) + increment);
        sendMSG(player, Te.s("今日已完成", getDailyFinishedTime(player), "次",
                "委托任务", CustomStyle.styleOfWorld));
        int currentFinishedTime = getDailyFinishedTime(player);
        if (currentFinishedTime < 10) {
            sendMSG(player, Te.s("今日再完成", 10 - currentFinishedTime, "次", "委托任务", CustomStyle.styleOfWorld,
                    "即可获得", ModItems.worldSoul5, "* 10", " + ", ModItems.JUNIOR_SUPPLY));
        } else if (currentFinishedTime < 20) {
            sendMSG(player, Te.s("今日再完成", 20 - currentFinishedTime, "次", "委托任务", CustomStyle.styleOfWorld,
                    "即可获得", ModItems.BOND));
        } else if (currentFinishedTime < 50) {
            sendMSG(player, Te.s("今日再完成", 50 - currentFinishedTime, "次", "委托任务", CustomStyle.styleOfWorld,
                    "即可获得", ModItems.SPECIAL_BOND));
        }
        if (currentFinishedTime == 10) {
            Tower.givePlayerStar(player, 10, "委托任务");
            InventoryOperation.itemStackGiveWithMSG(player, ModItems.JUNIOR_SUPPLY.get());
            formatMSG(Te.s(player.getDisplayName(), "今日完成了", "10", ChatFormatting.GREEN, "次",
                    "委托任务", CustomStyle.styleOfWorld));
        }
        if (currentFinishedTime == 20) {
            InventoryOperation.itemStackGiveWithMSG(player, ModItems.BOND.get());
            formatMSG(Te.s(player.getDisplayName(), "今日完成了", "20", ChatFormatting.YELLOW, "次",
                    "委托任务", CustomStyle.styleOfWorld));
        }
        if (currentFinishedTime == 50) {
            InventoryOperation.itemStackGiveWithMSG(player, ModItems.SPECIAL_BOND.get());
            formatMSG(Te.s(player.getDisplayName(), "今日完成了", "50", ChatFormatting.RED, "次",
                    "委托任务", CustomStyle.styleOfWorld));
        }
    }

    public static final String TOTAL_FINISHED_TIMES = "TotalFinishedTimes";
    public static int getTotalFinishedTimes(Player player) {
        return getPlayerData(player).getInt(TOTAL_FINISHED_TIMES);
    }
    public static void setTotalFinishedTimes(Player player, int totalFinishedTimes) {
        getPlayerData(player).putInt(TOTAL_FINISHED_TIMES, totalFinishedTimes);
    }
    public static void incrementTotalFinishedTimes(Player player, int increment) {
        setTotalFinishedTimes(player, getTotalFinishedTimes(player) + increment);
    }

    public static void handleTick(Player player) {
        if (player.tickCount % Tick.min(1) == 0) {
            if (getExpiredLeftMin(player) > 0) {
                incrementExpiredLeftMin(player, -1);
                sendPacketToClient(player);
            } else {
                if (getPlayerReputation(player) > 0) {
                    incrementPlayerReputation(player, -1);
                    sendPacketToClient(player);
                }
            }
        }
    }

    public static void playerTryToAccept(Player player) {
        String name = player.getName().getString();
        if (Compute.getNearEntity(player, Villager.class, 8).isEmpty()) {
            sendMSG(player, Te.s("似乎需要找到贝尔才能接取", "委托任务", CustomStyle.styleOfWorld));
            return;
        }
        if (player.experienceLevel < 75) {
            sendMSG(player, Te.s("委托任务", CustomStyle.styleOfWorld, "需要达到",
                    Utils.getLevelDescription(75), "才能接取"));
            MySound.soundToPlayer(player, SoundEvents.VILLAGER_NO);
            return;
        }
        if (playerCurrentEntrustmentMap.containsKey(name)) {
            sendMSG(player, Te.s("已经有一个正在进行的委托了"));
            return;
        }
        if (playerNextAllowAcceptTickMap.getOrDefault(name, 0) > Tick.get()) {
            sendMSG(player, Te.s("还需要等待", MobKillEntrustmentHud.getDeltaTimeFormatString(Tick.get(),
                    playerNextAllowAcceptTickMap.getOrDefault(name, 0)), "才能接取委托任务"));
            return;
        }
        List<MobSpawnController> controllers = MobSpawn.getAllControllers()
                .stream().filter(controller -> {
                    return controller.averageLevel <= player.experienceLevel - 8;
                }).toList();
        Random random = new Random();
        MobSpawnController controller = controllers.get(random.nextInt(controllers.size()));
        MobKillEntrustment entrustment =
                new MobKillEntrustment(controller.mobName,
                        random.nextInt(player.experienceLevel / 2, player.experienceLevel), Tick.get());
        playerCurrentEntrustmentMap.put(name, entrustment);
        sendMSG(player, Te.s("已接取委托: ", "击杀", ChatFormatting.RED, entrustment.targetCount + "只", controller.mobName));
        sendPacketToClient(player);
        MySound.soundToPlayer(player, SoundEvents.VILLAGER_TRADE);
    }

    public static void playerTryToCancel(Player player) {
        String name = player.getName().getString();
        if (Compute.getNearEntity(player, Villager.class, 8).isEmpty()) {
            sendMSG(player, Te.s("似乎需要找到贝尔才能取消", "委托任务", CustomStyle.styleOfWorld));
            return;
        }
        if (playerCurrentEntrustmentMap.containsKey(name)) {
            playerCurrentEntrustmentMap.remove(name);
            playerKillProcessMap.remove(name);
            playerNextAllowAcceptTickMap.put(name, Tick.get() + Tick.min(10));
            sendMSG(player, Te.s("已取消委托:", "需要在", "10min", ChatFormatting.AQUA, "后才能再次接取委托任务"));
            sendPacketToClient(player);
            MySound.soundToPlayer(player, SoundEvents.VILLAGER_NO);
        }
    }

    public record TimeAndTier(int minutes, Component component, double rate) {
    }

    public static final List<TimeAndTier> timeAndTiers = List.of(
            new TimeAndTier(4, Te.s("A+", ChatFormatting.RED), 2),
            new TimeAndTier(5, Te.s("A", ChatFormatting.GOLD), 1.8),
            new TimeAndTier(6, Te.s("B+", ChatFormatting.LIGHT_PURPLE), 1.5),
            new TimeAndTier(8, Te.s("B", ChatFormatting.AQUA), 1.25),
            new TimeAndTier(10, Te.s("C", ChatFormatting.GRAY), 1)
    );

    public static void playerTryToSubmit(Player player) throws SQLException {
        String name = player.getName().getString();
        if (playerCurrentEntrustmentMap.containsKey(name)) {
            MobKillEntrustment entrustment = playerCurrentEntrustmentMap.get(name);
            if (playerKillProcessMap.getOrDefault(name, 0) >= entrustment.targetCount) {
                // reward 与 信息

                int usedTick = Tick.get() - entrustment.startServerTick;
                double rate = 0.8;
                TimeAndTier timeAndTier = null;
                for (TimeAndTier timeAndTier1 : timeAndTiers) {
                    if (usedTick <= Tick.min(timeAndTier1.minutes)) {
                        rate = timeAndTier1.rate;
                        timeAndTier = timeAndTier1;
                        break;
                    }
                }
                if (timeAndTier != null) {
                    sendMSG(player, Te.s("在",
                            MobKillEntrustmentHud.getDeltaTimeFormatString(Tick.get(), entrustment.startServerTick),
                            "内完成了", "委托任务", CustomStyle.styleOfWorld, "，获得了", timeAndTier.component, "评级"));
                    if (timeAndTier.minutes <= 15) {
                        setExpiredLeftMin(player, 10);
                        setPlayerReputation(player, Math.min(getPlayerReputation(player) + 1, 15));
                    }
                } else {
                    sendMSG(player, Te.s("在",
                            MobKillEntrustmentHud.getDeltaTimeFormatString(Tick.get(), entrustment.startServerTick),
                            "内完成了", "委托任务", CustomStyle.styleOfWorld));
                }
                Compute.playerReputationAddOrCost(player,
                        (int) Math.ceil((rate * ((double) player.experienceLevel / 20)
                                * (1 + getExRateOfReputation(getPlayerReputation(player)) * 6) + 8)
                                * RankData.reputationMissionRewardRate(player)));
                if (RankData.reputationMissionRewardRate(player) > 1) {
                    RankData.sendFormatMSG(player, Te.s("你的", "职级", ChatFormatting.AQUA, "为你额外提供了 ",
                            (int) Math.ceil((rate * ((double) player.experienceLevel / 20)
                                    * (1 + getExRateOfReputation(getPlayerReputation(player)) * 6) + 8)
                                    * (RankData.reputationMissionRewardRate(player) - 1)) + "声望值",
                            ChatFormatting.YELLOW));
                }
                Compute.givePercentExpToPlayer(player, 0.05 * (1 + rate) * (1 + getExRateOfReputation(getPlayerReputation(player))),
                        0, player.experienceLevel);

                incrementDailyFinishedTime(player, 1);
                incrementTotalFinishedTimes(player, 1);
                playerCurrentEntrustmentMap.remove(name);
                playerKillProcessMap.remove(name);
                sendPacketToClient(player);
                MySound.soundToPlayer(player, SoundEvents.VILLAGER_CELEBRATE);
                MySound.soundToPlayer(player, SoundEvents.PLAYER_LEVELUP);
            }
        }
    }

    public static void sendMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("委托任务", CustomStyle.styleOfWorld), content);
    }

    public static void formatMSG(Component content) {
        Compute.formatBroad(Te.s("委托任务", CustomStyle.styleOfWorld), content);
    }

    public static void onKill(Player player, Mob mob) {
        String name = player.getName().getString();
        if (playerCurrentEntrustmentMap.containsKey(name)) {
            MobKillEntrustment entrustment = playerCurrentEntrustmentMap.get(name);
            if (entrustment.mobName.getString().equals(MobSpawn.getMobOriginName(mob))) {
                playerKillProcessMap.compute(name, (k, v) -> v == null ? 1 : v + 1);
                sendPacketToClient(player);
            }
        }
    }

    public static void sendPacketToClient(Player player) {
        String name = player.getName().getString();
        if (playerCurrentEntrustmentMap.containsKey(name)) {
            MobKillEntrustment entrustment = playerCurrentEntrustmentMap.get(name);
            ModNetworking.sendToClient(new MobKillEntrustmentInfoS2CPacket(entrustment.mobName, entrustment.targetCount,
                            playerKillProcessMap.getOrDefault(name, 0), entrustment.startServerTick,
                            getExpiredLeftMin(player), getPlayerReputation(player),
                            getDailyFinishedTime(player), getTotalFinishedTimes(player)),
                    (ServerPlayer) player);
        } else {
            ModNetworking.sendToClient(new MobKillEntrustmentInfoS2CPacket(Te.s(""), 0, 0, 0,
                            getExpiredLeftMin(player), getPlayerReputation(player),
                            getDailyFinishedTime(player), getTotalFinishedTimes(player)),
                    (ServerPlayer) player);
        }
    }

    public static final String VILLAGER_NAME = "联合研院秘书 - 贝尔";

    public static void onPlayerInteractWithVillager(Player player) throws SQLException {
        String name = player.getName().getString();
        if (playerCurrentEntrustmentMap.containsKey(name)) {
            MobKillEntrustment entrustment = playerCurrentEntrustmentMap.get(name);
            if (playerKillProcessMap.getOrDefault(name, 0) >= entrustment.targetCount) {
                playerTryToSubmit(player);
            } else {
                sendMSG(player, Te.s("已经有一个正在进行的委托了，是否取消？"));
                // 取消委托命令
                sendMSG(player, Te.s("「点击此处取消委托」").withStyle((s) -> {
                    return s.withColor(ChatFormatting.RED).withClickEvent(
                                    new ClickEvent(ClickEvent.Action.RUN_COMMAND,
                                            "/vmd entrustment cancel"))
                            .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                    Te.s("点击以", "取消", ChatFormatting.RED, "正在进行的委托")));
                }));
                MySound.soundToPlayer(player, SoundEvents.VILLAGER_AMBIENT);
            }
            return;
        }
        if (playerNextAllowAcceptTickMap.getOrDefault(name, 0) > Tick.get()) {
            sendMSG(player, Te.s("还需要等待", MobKillEntrustmentHud.getDeltaTimeFormatString(Tick.get(),
                    playerNextAllowAcceptTickMap.getOrDefault(name, 0)), "才能接取委托任务"));
            MySound.soundToPlayer(player, SoundEvents.VILLAGER_NO);
            return;
        }
        sendMSG(player, Te.s("接取委托？"));
        // 接取委托命令
        sendMSG(player, Te.s("「点击此处接取委托」").withStyle((s) -> {
            return s.withColor(ChatFormatting.GREEN).withClickEvent(
                            new ClickEvent(ClickEvent.Action.RUN_COMMAND,
                                    "/vmd entrustment accept"))
                    .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                            Te.s("点击以", "接取", ChatFormatting.AQUA, "一个新的委托任务")));
        }));
        MySound.soundToNearPlayer(player, SoundEvents.VILLAGER_AMBIENT);
    }
}
