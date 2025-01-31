package fun.wraq.process.system.entrustment.mob;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.Utils;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.events.mob.MobSpawnController;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.func.guide.Guide;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.func.plan.PlanPlayer;
import fun.wraq.process.func.rank.RankData;
import fun.wraq.process.system.profession.smith.SmithPlayerData;
import fun.wraq.process.system.tower.Tower;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.events.SpecialEventItems;
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
import net.minecraft.world.item.ItemStack;

import java.sql.SQLException;
import java.text.ParseException;
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
        return Compute.getPlayerSpecificKeyCompoundTagData(player, PLAYER_DATA_KEY);
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

    public static final String DAILY_FINISHED_TIMES_KEY = "DailyFinishedTimes";

    public static int getDailyFinishedTimes(Player player) {
        return getPlayerData(player).getInt(DAILY_FINISHED_TIMES_KEY);
    }

    public static void setDailyFinishedTimes(Player player, int dailyFinishedTime) {
        getPlayerData(player).putInt(DAILY_FINISHED_TIMES_KEY, dailyFinishedTime);
    }

    public static void incrementDailyFinishedTimes(Player player, int increment) throws SQLException {
        setDailyFinishedTimes(player, getDailyFinishedTimes(player) + increment);
        sendMSG(player, Te.s("今日已完成", getDailyFinishedTimes(player), "次",
                "委托任务", CustomStyle.styleOfWorld));
        sendMSG(player,
                Te.s(Te.c(Te.s("「查看每日进度奖励」", ChatFormatting.LIGHT_PURPLE),
                "/vmd entrustment queryDaily", Te.s("点击以查看每日进度奖励", ChatFormatting.LIGHT_PURPLE)),
                Te.c(Te.s("   「查看每周进度奖励」", ChatFormatting.LIGHT_PURPLE),
                "/vmd entrustment queryWeekly", Te.s("点击以查看每周进度奖励", ChatFormatting.LIGHT_PURPLE))));
        sendAcceptOrBondStoreMSG(player);
        int currentFinishedTime = getDailyFinishedTimes(player);
        if (currentFinishedTime == 5) {
            InventoryOperation.giveItemStackWithMSG(player, new ItemStack(ModItems.TP_TICKET.get(), 2));
            Tower.givePlayerStar(player, 5, "委托任务每日进度奖励");
            formatMSG(Te.s(player.getDisplayName(), "今日完成了", "5", ChatFormatting.GREEN, "次",
                    "委托任务", CustomStyle.styleOfWorld));
        }
        if (currentFinishedTime == 10) {
            InventoryOperation.giveItemStackWithMSG(player, new ItemStack(ModItems.TP_TICKET.get(), 3));
            InventoryOperation.giveItemStackWithMSG(player, new ItemStack(ModItems.BOND.get()));
            InventoryOperation.giveItemStackWithMSG(player, new ItemStack(ModItems.GOLDEN_BEANS.get(), 2));
            formatMSG(Te.s(player.getDisplayName(), "今日完成了", "10", ChatFormatting.YELLOW, "次",
                    "委托任务", CustomStyle.styleOfWorld));
        }
        if (currentFinishedTime == 15) {
            InventoryOperation.giveItemStackWithMSG(player, new ItemStack(ModItems.TP_TICKET.get(), 3));
            Tower.givePlayerStar(player, 5, "委托任务每日进度奖励");
            formatMSG(Te.s(player.getDisplayName(), "今日完成了", "15", ChatFormatting.LIGHT_PURPLE, "次",
                    "委托任务", CustomStyle.styleOfWorld));
        }
        if (currentFinishedTime == 20) {
            InventoryOperation.giveItemStackWithMSG(player, new ItemStack(ModItems.TP_TICKET.get(), 3));
            InventoryOperation.giveItemStackWithMSG(player, new ItemStack(ModItems.JUNIOR_SUPPLY.get()));
            formatMSG(Te.s(player.getDisplayName(), "今日完成了", "20", ChatFormatting.LIGHT_PURPLE, "次",
                    "委托任务", CustomStyle.styleOfWorld));
        }
        incrementWeeklyFinishedTimes(player, increment);
    }

    private static void sendAcceptOrBondStoreMSG(Player player) {
        player.sendSystemMessage(Te.s(" ".repeat(4),
                Te.s("「接取委托」").withStyle((s) -> {
            return s.withColor(ChatFormatting.GREEN).withClickEvent(
                            new ClickEvent(ClickEvent.Action.RUN_COMMAND,
                                    "/vmd entrustment accept"))
                    .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                            Te.s("点击以", "接取", ChatFormatting.AQUA, "一个新的委托任务")));
        }), " ".repeat(4),
                        Te.s("「打开委托商店」").withStyle((s) -> {
            return s.withColor(CustomStyle.styleOfWorld.getColor()).withClickEvent(
                            new ClickEvent(ClickEvent.Action.RUN_COMMAND,
                                    "/vmd entrustment openStore"))
                    .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                            Te.s("点击以打开债委托商店界面")));
        })));
    }

    public static void queryDailyTimes(Player player) {
        Compute.sendBlankLine(player, 1);
        int currentFinishedTime = getDailyFinishedTimes(player);
        sendMSG(player, Te.s("委托任务每日进度奖励", CustomStyle.styleOfWorld));
        sendMSG(player, Te.s("今天已完成", getDailyFinishedTimes(player) + "次", CustomStyle.styleOfWorld));
        player.sendSystemMessage(Te.s(" ", getStatus(currentFinishedTime, 5), "5 ", ChatFormatting.GREEN,
                " -> ", ModItems.TP_TICKET, " * 2", " + ", ModItems.WORLD_SOUL_5, " * 5"));
        player.sendSystemMessage(Te.s(" ", getStatus(currentFinishedTime, 10), "10", ChatFormatting.YELLOW,
                " -> ", ModItems.TP_TICKET, " * 3", " + ", ModItems.BOND, " * 1", " + ", ModItems.GOLDEN_BEANS, " * 2"));
        player.sendSystemMessage(Te.s(" ", getStatus(currentFinishedTime, 15), "15", ChatFormatting.LIGHT_PURPLE,
                " -> ", ModItems.TP_TICKET, " * 3", " + ", ModItems.WORLD_SOUL_5, " * 5"));
        player.sendSystemMessage(Te.s(" ", getStatus(currentFinishedTime, 20), "20", ChatFormatting.RED,
                " -> ", ModItems.TP_TICKET, " * 3", " + ", ModItems.JUNIOR_SUPPLY, " * 1"));
        sendMSG(player,
                Te.s(Te.c(Te.s("「查看每日进度奖励」", ChatFormatting.LIGHT_PURPLE),
                                "/vmd entrustment queryDaily", Te.s("点击以查看每日进度奖励", ChatFormatting.LIGHT_PURPLE)),
                        Te.c(Te.s("   「查看每周进度奖励」", ChatFormatting.LIGHT_PURPLE),
                                "/vmd entrustment queryWeekly", Te.s("点击以查看每周进度奖励", ChatFormatting.LIGHT_PURPLE))));
        Compute.sendBlankLine(player, 2);
    }

    public static Component getStatus(int current, int require) {
        return current >= require ?
                Te.s("[", CustomStyle.styleOfStone, "+", ChatFormatting.GREEN, "]", CustomStyle.styleOfStone)
                : Te.s("[-]", CustomStyle.styleOfStone);
    }

    public static final String WEEKLY_FINISHED_TIMES_KEY = "WeeklyFinishedTimes";

    public static int getWeeklyFinishedTimes(Player player) {
        return Compute.getSpecificKeyDataIntValue(player, PLAYER_DATA_KEY, WEEKLY_FINISHED_TIMES_KEY);
    }

    public static void incrementWeeklyFinishedTimes(Player player, int increment) throws SQLException {
        Compute.incrementSpecificKeyDataIntValue(player, PLAYER_DATA_KEY, WEEKLY_FINISHED_TIMES_KEY, increment);
        int currentFinishedTime = Compute.getSpecificKeyDataIntValue(player, PLAYER_DATA_KEY, WEEKLY_FINISHED_TIMES_KEY);
        if (currentFinishedTime == 20) {
            InventoryOperation.giveItemStackWithMSG(player, new ItemStack(ModItems.SPECIAL_BOND.get()));
            Tower.givePlayerStar(player, 10, "委托任务每周进度奖励");
            InventoryOperation.giveItemStackWithMSG(player, new ItemStack(ModItems.supplyBoxTier0.get()));
            formatMSG(Te.s(player.getDisplayName(), "本周完成了", "20", ChatFormatting.GREEN, "次",
                    "委托任务", CustomStyle.styleOfWorld));
        }
        if (currentFinishedTime == 40) {
            InventoryOperation.giveItemStackWithMSG(player, new ItemStack(ModItems.SPECIAL_BOND.get()));
            Tower.givePlayerStar(player, 20, "委托任务每周进度奖励");
            InventoryOperation.giveItemStackWithMSG(player, new ItemStack(ModItems.ORE_SUPPLY.get()));
            formatMSG(Te.s(player.getDisplayName(), "本周完成了", "40", ChatFormatting.AQUA, "次",
                    "委托任务", CustomStyle.styleOfWorld));
        }
        if (currentFinishedTime == 60) {
            InventoryOperation.giveItemStackWithMSG(player, new ItemStack(ModItems.SPECIAL_BOND.get()));
            Tower.givePlayerStar(player, 30, "委托任务每周进度奖励");
            InventoryOperation.giveItemStackWithMSG(player, new ItemStack(ModItems.JUNIOR_SUPPLY.get()));
            formatMSG(Te.s(player.getDisplayName(), "本周完成了", "60", ChatFormatting.YELLOW, "次",
                    "委托任务", CustomStyle.styleOfWorld));
        }
        if (currentFinishedTime == 80) {
            InventoryOperation.giveItemStackWithMSG(player, new ItemStack(ModItems.SPECIAL_BOND.get()));
            Tower.givePlayerStar(player, 40, "委托任务每周进度奖励");
            InventoryOperation.giveItemStackWithMSG(player, new ItemStack(ModItems.SENIOR_POTION_SUPPLY.get()));
            formatMSG(Te.s(player.getDisplayName(), "本周完成了", "80", ChatFormatting.LIGHT_PURPLE, "次",
                    "委托任务", CustomStyle.styleOfWorld));
        }
        if (currentFinishedTime == 100) {
            InventoryOperation.giveItemStackWithMSG(player, new ItemStack(ModItems.SPECIAL_BOND.get()));
            Tower.givePlayerStar(player, 50, "委托任务每周进度奖励");
            InventoryOperation.giveItemStackWithMSG(player, new ItemStack(ModItems.SENIOR_SUPPLY.get()));
            formatMSG(Te.s(player.getDisplayName(), "本周完成了", "100", ChatFormatting.RED, "次",
                    "委托任务", CustomStyle.styleOfWorld));
        }
    }

    public static void queryWeeklyTimes(Player player) {
        Compute.sendBlankLine(player, 1);
        sendMSG(player, Te.s("委托任务每周进度奖励", CustomStyle.styleOfWorld));
        sendMSG(player, Te.s("本周已完成", getWeeklyFinishedTimes(player) + "次", CustomStyle.styleOfWorld));
        int currentFinishedTime = getWeeklyFinishedTimes(player);
        player.sendSystemMessage(Te.s(" ", getStatus(currentFinishedTime, 20),
                "20 ", ChatFormatting.GREEN,
                " -> ", ModItems.SPECIAL_BOND, " * 1",
                " + ", ModItems.WORLD_SOUL_5, " * 10",
                " + ", ModItems.supplyBoxTier0, " * 1"));
        player.sendSystemMessage(Te.s(" ", getStatus(currentFinishedTime, 40),
                "40 ", ChatFormatting.AQUA,
                " -> ", ModItems.SPECIAL_BOND, " * 1",
                " + ", ModItems.WORLD_SOUL_5, " * 20",
                " + ", ModItems.ORE_SUPPLY, " * 1"));
        player.sendSystemMessage(Te.s(" ", getStatus(currentFinishedTime, 60),
                "60 ", ChatFormatting.YELLOW,
                " -> ", ModItems.SPECIAL_BOND, " * 1",
                " + ", ModItems.WORLD_SOUL_5, " * 30",
                " + ", ModItems.JUNIOR_SUPPLY, " * 1"));
        player.sendSystemMessage(Te.s(" ", getStatus(currentFinishedTime, 80),
                "80 ", ChatFormatting.LIGHT_PURPLE,
                " -> ", ModItems.SPECIAL_BOND, " * 1",
                " + ", ModItems.WORLD_SOUL_5, " * 40",
                " + ", ModItems.SENIOR_POTION_SUPPLY, " * 1"));
        player.sendSystemMessage(Te.s(" ", getStatus(currentFinishedTime, 100),
                "100", ChatFormatting.RED,
                " -> ", ModItems.SPECIAL_BOND, " * 1",
                " + ", ModItems.WORLD_SOUL_5, " * 50",
                " + ", ModItems.SENIOR_SUPPLY, " * 1"));
        sendMSG(player,
                Te.s(Te.c(Te.s("「查看每日进度奖励」", ChatFormatting.LIGHT_PURPLE),
                                "/vmd entrustment queryDaily", Te.s("点击以查看每日进度奖励", ChatFormatting.LIGHT_PURPLE)),
                        Te.c(Te.s("   「查看每周进度奖励」", ChatFormatting.LIGHT_PURPLE),
                                "/vmd entrustment queryWeekly", Te.s("点击以查看每周进度奖励", ChatFormatting.LIGHT_PURPLE))));
        Compute.sendBlankLine(player, 1);
    }

    public static void setWeeklyFinishedTimes(Player player, int times) {
        Compute.setSpecificKeyDataIntValue(player, PLAYER_DATA_KEY, WEEKLY_FINISHED_TIMES_KEY, times);
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

    public static final String AVERAGE_FINISHED_TICK_KEY = "AverageFinishedTick";

    public static int getAverageFinishedTick(Player player) {
        return Compute.getSpecificKeyDataIntValue(player, PLAYER_DATA_KEY, AVERAGE_FINISHED_TICK_KEY);
    }

    public static void onFinished(Player player, int costTick) {
        int totalFinishedTimes = getTotalFinishedTimes(player);
        if (totalFinishedTimes == 1) {
            Compute.setSpecificKeyDataIntValue(player, PLAYER_DATA_KEY, AVERAGE_FINISHED_TICK_KEY, totalFinishedTimes);
            return;
        }
        int oldAverageTick = Compute.getSpecificKeyDataIntValue(player, PLAYER_DATA_KEY, AVERAGE_FINISHED_TICK_KEY);
        int newAverageTick = (int) (oldAverageTick * 1.0 * (totalFinishedTimes - 1) / totalFinishedTimes
                + costTick * 1.0 / totalFinishedTimes);
        Compute.setSpecificKeyDataIntValue(player, PLAYER_DATA_KEY, AVERAGE_FINISHED_TICK_KEY, newAverageTick);
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
            sendMSG(player, Te.s("还需要等待",
                    getDeltaTimeFormatString(playerNextAllowAcceptTickMap.getOrDefault(name, 0),
                            Tick.get()), CustomStyle.styleOfWorld, "才能接取委托任务"));
            return;
        }
        List<MobSpawnController> controllers = MobSpawn.getAllControllers()
                .stream().filter(controller -> {
                    return controller.averageLevel <= player.experienceLevel - 8;
                }).toList();
        Random random = new Random();
        MobSpawnController controller = controllers.get(random.nextInt(controllers.size()));
        MobKillEntrustment entrustment =
                new MobKillEntrustment(controller.mobName, random.nextInt(80, 160), Tick.get());
        // 原先 96 192 因春节活动调整为 80 160
        playerCurrentEntrustmentMap.put(name, entrustment);
        for (int i = 0 ; i < 5 ; i ++) {
            player.sendSystemMessage(Component.literal(""));
        }
        sendMSG(player, Te.s("已接取委托: ", "击杀", ChatFormatting.RED,
                entrustment.targetCount + "只", controller.mobName));
        for (int i = 0 ; i < 4 ; i ++) {
            player.sendSystemMessage(Component.literal(""));
        }
        sendPacketToClient(player);
        MySound.soundToPlayer(player, SoundEvents.VILLAGER_TRADE);
        Guide.sendGuideDisplayStatusToClient(player, true);
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
            playerNextAllowAcceptTickMap.put(name, Tick.get() + Tick.min(5));
            sendMSG(player, Te.s("已取消委托:", "需要在", "5min", ChatFormatting.AQUA, "后才能再次接取委托任务"));
            sendPacketToClient(player);
            MySound.soundToPlayer(player, SoundEvents.VILLAGER_NO);
        }
    }

    public record TimeAndTier(int minutes, Component component, double rate) {
    }

    public static final String TIER_FINISHED_TIMES_DATA_KEY = "EachTierFinishedTimes";
    public static CompoundTag getEachTierFinishedTimesData(Player player) {
        CompoundTag data = getPlayerData(player);
        if (!data.contains(TIER_FINISHED_TIMES_DATA_KEY)) {
            data.put(TIER_FINISHED_TIMES_DATA_KEY, new CompoundTag());
        }
        return data.getCompound(TIER_FINISHED_TIMES_DATA_KEY);
    }

    public static void incrementEachTierFinishedTimes(Player player, String tier) {
        CompoundTag data = getEachTierFinishedTimesData(player);
        data.putInt(tier, data.getInt(tier) + 1);
    }

    public static int getEachTierFinishedTimes(Player player, String tier) {
        return getEachTierFinishedTimesData(player).getInt(tier);
    }

    public static void queryEachTierFinishedTimes(Player player) {
        sendMSG(player, Te.s("完成情况明细如下:"));
        timeAndTiers.forEach(timeAndTier -> {
            player.sendSystemMessage(Te.s(timeAndTier.component, " ",
                    getEachTierFinishedTimes(player, timeAndTier.component.getString()) + "次", CustomStyle.styleOfWorld));
        });
        player.sendSystemMessage(Te.s(" 平均用时:", getDeltaTimeFormatString(getAverageFinishedTick(player), 0)));
    }

    public static final List<TimeAndTier> timeAndTiers = List.of(
            new TimeAndTier(4, Te.s("A+", ChatFormatting.RED), 2),
            new TimeAndTier(5, Te.s("A", ChatFormatting.GOLD), 1.8),
            new TimeAndTier(8, Te.s("B+", ChatFormatting.LIGHT_PURPLE), 1.5),
            new TimeAndTier(10, Te.s("B", ChatFormatting.AQUA), 1.25),
            new TimeAndTier(12, Te.s("C", ChatFormatting.GRAY), 1)
    );

    public static final List<TimeAndTier> planTimeAndTiers = List.of(
            new TimeAndTier(5, Te.s("A+", ChatFormatting.RED), 2),
            new TimeAndTier(6, Te.s("A", ChatFormatting.GOLD), 1.8)
    );

    public static void playerTryToSubmit(Player player) throws SQLException, ParseException {
        String name = player.getName().getString();
        if (playerCurrentEntrustmentMap.containsKey(name)) {
            MobKillEntrustment entrustment = playerCurrentEntrustmentMap.get(name);
            if (playerKillProcessMap.getOrDefault(name, 0) >= entrustment.targetCount) {
                // reward 与 信息

                int costTick = Tick.get() - entrustment.startServerTick;
                double rate = 0.8;
                TimeAndTier timeAndTier = null;
                if (PlanPlayer.getPlayerTier(player) > 0) {
                    for (TimeAndTier timeAndTier1 : planTimeAndTiers) {
                        if (costTick <= Tick.min(timeAndTier1.minutes)) {
                            rate = timeAndTier1.rate;
                            timeAndTier = timeAndTier1;
                            break;
                        }
                    }
                    player.sendSystemMessage(Component.literal(""));
                    if (timeAndTier != null) {
                        sendMSG(player, Te.s("在",
                                getDeltaTimeFormatString(Tick.get(), entrustment.startServerTick),
                                "内完成了", "委托任务", CustomStyle.styleOfWorld, "，获得了", timeAndTier.component, "评级"));
                        broad(Te.s(player, "在 ",
                                getDeltaTimeFormatString(Tick.get(), entrustment.startServerTick), CustomStyle.styleOfWorld,
                                " 内完成了", "委托任务", CustomStyle.styleOfWorld, "，获得了", timeAndTier.component, "评级"));
                        incrementEachTierFinishedTimes(player, timeAndTier.component.getString());
                    } else {
                        sendMSG(player, Te.s("在",
                                getDeltaTimeFormatString(Tick.get(), entrustment.startServerTick),
                                "内完成了", "委托任务", CustomStyle.styleOfWorld, "，获得了", "B+", ChatFormatting.LIGHT_PURPLE, "评级"));
                        incrementEachTierFinishedTimes(player, "B+");
                    }
                    setExpiredLeftMin(player, 10);
                    setPlayerReputation(player, Math.min(getPlayerReputation(player) + 1, 15));
                    InventoryOperation.giveItemStackWithMSG(player, ModItems.GOLDEN_BEANS.get());
                    SmithPlayerData.onPlayerSubmitEntrustment(player);
                } else {
                    for (TimeAndTier timeAndTier1 : timeAndTiers) {
                        if (costTick <= Tick.min(timeAndTier1.minutes)) {
                            rate = timeAndTier1.rate;
                            timeAndTier = timeAndTier1;
                            break;
                        }
                    }
                    if (timeAndTier != null) {
                        sendMSG(player, Te.s("在",
                                getDeltaTimeFormatString(Tick.get(), entrustment.startServerTick),
                                "内完成了", "委托任务", CustomStyle.styleOfWorld, "，获得了", timeAndTier.component, "评级"));
                        if (timeAndTier.minutes <= 8) {
                            broad(Te.s(player, "在 ",
                                    getDeltaTimeFormatString(Tick.get(), entrustment.startServerTick), CustomStyle.styleOfWorld,
                                    " 内完成了", "委托任务", CustomStyle.styleOfWorld, "，获得了", timeAndTier.component, "评级"));
                            setExpiredLeftMin(player, 10);
                            setPlayerReputation(player, Math.min(getPlayerReputation(player) + 1, 15));
                            InventoryOperation.giveItemStackWithMSG(player, ModItems.GOLDEN_BEANS.get());
                            SmithPlayerData.onPlayerSubmitEntrustment(player);
                        }
                        incrementEachTierFinishedTimes(player, timeAndTier.component.getString());
                    } else {
                        sendMSG(player, Te.s("在",
                                getDeltaTimeFormatString(Tick.get(), entrustment.startServerTick),
                                "内完成了", "委托任务", CustomStyle.styleOfWorld));
                        incrementEachTierFinishedTimes(player, "D");
                    }
                }

                double entrustmentReputationReward = rate * ((double) player.experienceLevel / 20)
                        * (1 + getExRateOfReputation(getPlayerReputation(player)) * 6) + 8;
                Compute.giveReputation(player, entrustmentReputationReward,
                        Te.s("委托任务", CustomStyle.styleOfWorld));
                double rankExReputationRewardRate = RankData.getExReputationMissionRewardRate(player);
                if (rankExReputationRewardRate > 0) {
                    Compute.giveReputation(player, entrustmentReputationReward * rankExReputationRewardRate,
                            Te.s("职级奖励", CustomStyle.styleOfWorld));
                }
                Compute.giveReputation(player, entrustmentReputationReward * 0.5,
                        Te.s("新春活动", CustomStyle.styleOfSpring));
                InventoryOperation.giveItemStackWithMSG(player, SpecialEventItems.MONEY.get(), 2);

                Compute.givePercentExpToPlayer(player,
                        0.05 * (1 + rate) * (1 + getExRateOfReputation(getPlayerReputation(player))),
                        2, player.experienceLevel);

                incrementDailyFinishedTimes(player, 1);
                incrementTotalFinishedTimes(player, 1);
                onFinished(player, costTick);
                playerCurrentEntrustmentMap.remove(name);
                playerKillProcessMap.remove(name);
                sendPacketToClient(player);
                MySound.soundToPlayer(player, SoundEvents.VILLAGER_CELEBRATE);
                MySound.soundToPlayer(player, SoundEvents.PLAYER_LEVELUP);

                Guide.sendGuideDisplayStatusToClient(player, true);
            }
        }
    }

    public static void sendMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("委托任务", CustomStyle.styleOfWorld), content);
    }

    public static void formatMSG(Component content) {
        Compute.formatBroad(Te.s("委托任务", CustomStyle.styleOfWorld), content);
    }

    public static void broad(Component content) {
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
                            getDailyFinishedTimes(player), getWeeklyFinishedTimes(player),
                            getTotalFinishedTimes(player), getAverageFinishedTick(player),
                    playerNextAllowAcceptTickMap.getOrDefault(name, 0)), (ServerPlayer) player);
        } else {
            ModNetworking.sendToClient(new MobKillEntrustmentInfoS2CPacket(Te.s(""), 0, 0, 0,
                            getExpiredLeftMin(player), getPlayerReputation(player),
                            getDailyFinishedTimes(player), getWeeklyFinishedTimes(player),
                            getTotalFinishedTimes(player), getAverageFinishedTick(player),
                    playerNextAllowAcceptTickMap.getOrDefault(name, 0)), (ServerPlayer) player);
        }
    }

    public static final String VILLAGER_NAME = "联合研院秘书 - 贝尔";

    public static void onPlayerInteractWithVillager(Player player) throws SQLException, ParseException {
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
            sendMSG(player, Te.s("还需要等待",
                    getDeltaTimeFormatString(playerNextAllowAcceptTickMap.getOrDefault(name, 0),
                            Tick.get()), CustomStyle.styleOfWorld, "才能接取委托任务"));
            MySound.soundToPlayer(player, SoundEvents.VILLAGER_NO);
            for (int i = 0 ; i < 4 ; i ++) {
                player.sendSystemMessage(Component.literal(""));
            }
            sendMSG(player, Te.s("「打开委托商店」").withStyle((s) -> {
                return s.withColor(CustomStyle.styleOfWorld.getColor()).withClickEvent(
                                new ClickEvent(ClickEvent.Action.RUN_COMMAND,
                                        "/vmd entrustment openStore"))
                        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                Te.s("点击以打开债委托商店界面")));
            }));
            for (int i = 0 ; i < 4 ; i ++) {
                player.sendSystemMessage(Component.literal(""));
            }
            return;
        }
        sendMSG(player, Te.s("近期如何？ ", player.getName().getString()));
        for (int i = 0 ; i < 3 ; i ++) {
            player.sendSystemMessage(Component.literal(""));
        }
        // 接取委托命令与债券商店
        sendAcceptOrBondStoreMSG(player);
        for (int i = 0 ; i < 4 ; i ++) {
            player.sendSystemMessage(Component.literal(""));
        }
        MySound.soundToNearPlayer(player, SoundEvents.VILLAGER_AMBIENT);
    }

    public static void openStore(Player player) {
        Compute.openTradeScreenByVillagerName(player, VILLAGER_NAME);
        MySound.soundToPlayer(player, SoundEvents.VILLAGER_TRADE);
    }

    public static String getDeltaTimeFormatString(int tick1, int tick2) {
        return (tick1 - tick2) / 1200 + "min" + Tick.toSeconds((tick1 - tick2) % 1200) + "s";
    }
}
