package fun.wraq.series.events.summer2025;

import com.Polarice3.Goety.common.entities.ModEntityType;
import com.Polarice3.Goety.common.entities.hostile.illagers.Envioker;
import fun.wraq.common.Compute;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.events.SpecialEventItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.RandomUtils;

import java.util.*;

public class Summer2025 {
    public static void onLogin(Player player) {
        if (isInActivityDate()) {
            sendMSG(player, Te.s("暑期活动预热中:"));
            player.sendSystemMessage(Te.s(" ".repeat(8), "·", CustomStyle.styleOfWater,
                    "双倍理智回复", CustomStyle.styleOfFlexible));
            player.sendSystemMessage(Te.s(" ".repeat(8), "·", CustomStyle.styleOfWater,
                    "+200%经验加成", ChatFormatting.LIGHT_PURPLE));
            player.sendSystemMessage(Te.s(" ".repeat(8), "·", CustomStyle.styleOfWater,
                    "+25%额外产出", CustomStyle.styleOfGold));
            player.sendSystemMessage(Te.s(" ".repeat(8), "·", CustomStyle.styleOfWater,
                    "高温补贴:", CustomStyle.styleOfPower, "每完成一个", "委托任务", CustomStyle.styleOfWorld,
                            "+2500VB", CustomStyle.styleOfGold));
        }
    }

    // 活动在9.1日自动关闭（无需重启）
    public static boolean isInActivityDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR) == 2025
                && calendar.get(Calendar.MONTH) >= Calendar.JULY && calendar.get(Calendar.MONTH) < Calendar.SEPTEMBER;
    }

    public static double getExReasonRecoverRate() {
        return isInActivityDate() ? 1 : 0;
    }

    public static double getExExpUp() {
        return isInActivityDate() ? 2 : 0;
    }

    public static double getExHarvestRate() {
        return isInActivityDate() ? 0.25 : 0;
    }

    public static void onFinishEntrustment(Player player) {
        if (!isInActivityDate()) {
            return;
        }
        Compute.VBIncomeAndMSGSend(player, 2500);
        sendMSG(player, Te.s("高温补贴已发放!"));
    }

    public static void sendMSG(Player player, Component content) {
        Compute.sendFormatMSG(player, Te.s("暑期活动", CustomStyle.styleOfPower), content);
    }

    public static void broad(Component content) {
        Compute.formatBroad(Te.s("暑期活动", CustomStyle.styleOfPower), content);
    }

    public static int lastTrigHour = -1;

    public static boolean eventIsRunning = false;

    public static int currentRound = -1;

    public static Set<Mob> mobs = new HashSet<>();

    public static int nextSpawnTick = -1;

    public static Set<String> playerNames = new HashSet<>();

    public static int lastSpawnTick = -1;

    public static String mobName = "一只小馋猫";

    public static Map<String, Integer> playerEachRoundKillCount = new HashMap<>();

    public static Map<String, Integer> playerTotalKillCount = new HashMap<>();

    public static int winTimes = 0;

    public static void clear() {
        eventIsRunning = false;
        currentRound = 1;
        nextSpawnTick = -1;
        playerNames.clear();
        playerEachRoundKillCount.clear();
        playerTotalKillCount.clear();
        mobs.forEach(mob -> mob.remove(Entity.RemovalReason.KILLED));
        mobs.clear();
        winTimes = 0;
    }

    public static void handleOverworldLevelTick(Level level) {
        if (Tick.get() < 100) {
            return;
        }
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        if (lastTrigHour != currentHour
                && (true)) {
            currentRound = 0;
            lastTrigHour = currentHour;
            eventIsRunning = true;
            nextSpawnTick = Tick.get() + Tick.s(5);
            broad(Te.s("还有1min活动就要开始了!"));
        }
        if (eventIsRunning) {
            Set<String> zonePlayerName = new HashSet<>(getZonePlayerName());
            playerNames.addAll(zonePlayerName);
            // 播报剩余开始时间
            int leftTick = nextSpawnTick - Tick.get();
            if (leftTick > 0 && leftTick % 20 == 0) {
                broadToZonePlayers(Te.s("还有", leftTick / 20 + "s", ChatFormatting.AQUA, "生成."));
            }
            // 获取区域内玩家，如果区域无玩家，则强行结束活动
            if (currentRound > 0) {
                if (zonePlayerName.isEmpty()) {
                    clear();
                    return;
                }
            }
            // 生成
            if (nextSpawnTick != -1 && Tick.get() > nextSpawnTick && currentRound <= 20) {
                ++currentRound;
                summonMob(level);
                lastSpawnTick = Tick.get();
                nextSpawnTick = -1;
            }
            // 中间处理
                // 超时
            if (Tick.get() - lastSpawnTick > Tick.min(3)) {
                broadToZonePlayers(Te.s("本轮挑战失败."));
                mobs.forEach(mob -> mob.remove(Entity.RemovalReason.KILLED));
                mobs.clear();
                playerTotalKillCount.clear();
                playerEachRoundKillCount.clear();
                nextSpawnTick = Tick.get() + Tick.s(5);
            }
                // tick
            handleMobTick();
            // 结束处理
                // 每回合结束
            if (currentRound > 0 && Tick.get() > nextSpawnTick) {
                if (mobs.isEmpty() || mobs.stream().noneMatch(LivingEntity::isAlive)) {
                    rewardEachRound();
                    ++winTimes;
                    playerEachRoundKillCount.clear();
                    if (currentRound >= 2) {
                        rewardFinal();
                        clear();
                        broad(Te.s("活动结束!"));
                    } else {
                        nextSpawnTick = Tick.get() + Tick.s(5);
                    }
                }
            }
        }
    }

    public static List<Vec3> spawnPosList = List.of(
            new Vec3(1763, 65, 458),
            new Vec3(1771, 65, 446),
            new Vec3(1788, 66, 436),
            new Vec3(1790, 65, 423),
            new Vec3(1803, 66, 412),
            new Vec3(1818, 67, 404),
            new Vec3(1827, 68, 393),
            new Vec3(1836, 70, 377)
    );

    public static int getMobXpLevelByRound() {
        if (currentRound < 5) {
            return 80;
        } else if (currentRound < 10) {
            return 150;
        } else if (currentRound < 15) {
            return 200;
        }
        return 225;
    }

    public static int getMobAttackDamageByRound() {
        if (currentRound < 5) {
            return 400;
        } else if (currentRound < 10) {
            return 1200;
        } else if (currentRound < 15) {
            return 2600;
        }
        return 3000;
    }

    public static int getMobDefenceByRound() {
        if (currentRound < 5) {
            return 50;
        } else if (currentRound < 10) {
            return 100;
        } else if (currentRound < 15) {
            return 180;
        }
        return 200;
    }

    public static int getMobMaxHealthByRound() {
        if (currentRound < 5) {
            return 9000;
        } else if (currentRound < 10) {
            return 320000;
        } else if (currentRound < 15) {
            return 2000000;
        }
        return 5000000;
    }

    public static void summonMob(Level level) {
        for (int i = 0; i < 100; i++) {
            Envioker mob = new Envioker(ModEntityType.ENVIOKER.get(), level);
            MobSpawn.setMobCustomName(mob, Te.s(mobName, CustomStyle.styleOfPower), getMobXpLevelByRound());
            MobSpawn.MobBaseAttributes.setMobBaseAttributes(mob, getMobAttackDamageByRound(),
                    getMobDefenceByRound(), getMobDefenceByRound(), getMobMaxHealthByRound(), 0.2);
            mob.moveTo(spawnPosList.get(i % spawnPosList.size())
                    .add(2.5 - RandomUtils.nextDouble(0, 5), 0, 2.5 - RandomUtils.nextDouble(0, 5)));
            level.addFreshEntity(mob);
            mobs.add(mob);
        }
    }

    public static void handleMobTick() {
        mobs.forEach(mob -> {
            getPlayers().stream().min(new Comparator<ServerPlayer>() {
                @Override
                public int compare(ServerPlayer o1, ServerPlayer o2) {
                    return (int) (o1.distanceTo(mob) - o2.distanceTo(mob));
                }
            }).ifPresent(mob::setTarget);
        });
    }

    public static void rewardEachRound() {
        getPlayers().forEach(player -> {
            if (playerEachRoundKillCount.containsKey(Name.get(player))) {
                InventoryOperation.giveItemStackWithMSG(player, SpecialEventItems.SNACK_GOLD_COIN.get());
            }
        });
    }

    public static void rewardFinal() {
        getPlayers().forEach(player -> {
            InventoryOperation.giveItemStackWithMSG(player, SpecialEventItems.SNACK_GOLD_COIN.get(), 5);
        });
    }

    public static Set<ServerPlayer> getPlayers() {
        Set<ServerPlayer> set = new HashSet<>();
        playerNames.forEach(name -> {
            ServerPlayer serverPlayer = Tick.server.getPlayerList().getPlayerByName(name);
            if (serverPlayer != null) {
                set.add(serverPlayer);
            }
        });
        return set;
    }

    public static void broadToZonePlayers(Component content) {
        getPlayers().forEach(serverPlayer -> {
            sendMSG(serverPlayer, content);
        });
    }

    public static double getMobWithstandModifiedDamage(Player player, Mob mob, double damage) {
        if (MobSpawn.getMobOriginName(mob).equals(mobName)) {
            playerNames.add(Name.get(player));
            return Math.min(mob.getMaxHealth() * 0.01, damage);
        }
        return damage;
    }

    public static void onKill(Player player, Mob mob) {
        if (MobSpawn.getMobOriginName(mob).equals(mobName)) {
            playerEachRoundKillCount.compute(Name.get(player), (k, v) -> v == null ? 1 : v + 1);
            playerTotalKillCount.compute(Name.get(player), (k, v) -> v == null ? 1 : v + 1);
        }
    }

    public static Set<String> getZonePlayerName() {
        Set<String> set = new HashSet<>();
        Tick.server.overworld().getEntitiesOfClass(Player.class,
                        AABB.ofSize(new Vec3(1803, 65, 406), 150, 100, 150))
                .forEach(player -> {
                    set.add(Name.get(player));
                });
        return set;
    }

    public static void onServerStop() {
        mobs.forEach(mob -> mob.remove(Entity.RemovalReason.KILLED));
        mobs.clear();
    }
}
