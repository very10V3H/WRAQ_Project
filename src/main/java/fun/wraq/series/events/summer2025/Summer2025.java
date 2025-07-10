package fun.wraq.series.events.summer2025;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Name;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.MySound;
import fun.wraq.events.mob.MobSpawn;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.system.cooking.CookingValue;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.events.SpecialEventItems;
import fun.wraq.series.events.spring2024.FireworkGun;
import net.minecraft.ChatFormatting;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.RandomUtils;
import vectorwing.farmersdelight.common.crafting.CookingPotRecipe;

import java.util.*;

public class Summer2025 {
    public static void onLogin(Player player) {
        if (isInActivityDate()) {
            sendMSG(player, Te.s("暑期活动进行中:"));
            player.sendSystemMessage(Te.s(" ".repeat(8), "·", CustomStyle.styleOfWater,
                    "双倍理智回复", CustomStyle.styleOfFlexible));
            player.sendSystemMessage(Te.s(" ".repeat(8), "·", CustomStyle.styleOfWater,
                    "+200%经验加成", ChatFormatting.LIGHT_PURPLE));
            player.sendSystemMessage(Te.s(" ".repeat(8), "·", CustomStyle.styleOfWater,
                    "+25%额外产出", CustomStyle.styleOfGold));
            player.sendSystemMessage(Te.s(" ".repeat(8), "·", CustomStyle.styleOfWater,
                    "高温补贴:", CustomStyle.styleOfPower, "每完成一个", "委托任务", CustomStyle.styleOfWorld,
                            "+2500VB", CustomStyle.styleOfGold));
            player.sendSystemMessage(Te.s(" ".repeat(8), "·", CustomStyle.styleOfWater,
                    "每日的14/16/20/21/22时的10分时刻，", "吃货僵尸", CustomStyle.styleOfPower,
                    "们就会聚集在", "旭升岛南部沙滩", CustomStyle.styleOfSunIsland, "。快去保护你的食物!"));
            player.getPersistentData().putInt(DAY_GET_SILVER_COIN_COUNT_KEY, 0);
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

    public static String mobName = "吃货僵尸";

    public static Map<String, Integer> playerEachRoundKillCount = new HashMap<>();

    public static Map<String, Integer> playerTotalKillCount = new HashMap<>();

    public static int winTimes = 0;

    public static boolean forceStartFlag = false;

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
        if (forceStartFlag || (lastTrigHour != currentHour
                && calendar.get(Calendar.MINUTE) == 10
                && (currentHour == 14 || currentHour == 16
                || currentHour == 20 || currentHour == 21 || currentHour == 22))) {
            forceStartFlag = false;
            currentRound = 0;
            lastTrigHour = currentHour;
            eventIsRunning = true;
            nextSpawnTick = Tick.get() + Tick.min(3);
            broad(Te.s("还有3分钟，吃货僵尸就要袭击", "旭升岛南部沙滩", CustomStyle.styleOfSunIsland, "了!"));
            getPlayers().forEach(serverPlayer -> {
                MySound.soundToPlayer(serverPlayer, SoundEvents.PLAYER_LEVELUP);
            });
        }
        if (eventIsRunning) {
            Set<String> zonePlayerName = new HashSet<>(getZonePlayerName());
            playerNames.addAll(zonePlayerName);
            // 播报剩余开始时间
            int leftTick = nextSpawnTick - Tick.get();
            if (leftTick > 0 && leftTick % 20 == 0) {
                if (leftTick < Tick.s(10) || leftTick % 100 == 0) {
                    broadToZonePlayers(Te.s("距离吃货僵尸来临，还有", leftTick / 20 + "s.", ChatFormatting.AQUA,
                            "当前波次:", currentRound, ChatFormatting.AQUA, "/", MAX_ROUND_NUM, CustomStyle.styleOfMoon));
                    getPlayers().forEach(serverPlayer -> {
                        MySound.soundToPlayer(serverPlayer, SoundEvents.EXPERIENCE_ORB_PICKUP);
                    });
                }
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
                broadToZonePlayers(Te.s("吃货僵尸们来了! 在3min内清理所有吃货僵尸!"));
                ++currentRound;
                summonMob(level, 100);
                lastSpawnTick = Tick.get();
                nextSpawnTick = -1;
            }
            // 中间处理
                // 超时
            if (currentRound > 0) {
                int leftRoundTick = lastSpawnTick + Tick.min(3) - Tick.get();
                if (leftRoundTick == Tick.s(60)) {
                    broadToZonePlayers(Te.s("还有60s!抓紧时间!"));
                    getPlayers().forEach(player -> MySound.soundToPlayer(player, SoundEvents.EXPERIENCE_ORB_PICKUP));
                } else if (leftRoundTick == Tick.s(30)) {
                    broadToZonePlayers(Te.s("还有30s!赶快!"));
                    getPlayers().forEach(player -> MySound.soundToPlayer(player, SoundEvents.EXPERIENCE_ORB_PICKUP));
                } else if (leftRoundTick == Tick.s(10)) {
                    broadToZonePlayers(Te.s("还有10s!要来不及了!"));
                    getPlayers().forEach(player -> MySound.soundToPlayer(player, SoundEvents.EXPERIENCE_ORB_PICKUP));
                }
            }
            if (currentRound > 0 && Tick.get() - lastSpawnTick > Tick.min(3)) {
                broadToZonePlayers(Te.s("吃货僵尸们成功登陆并吃掉了很多小零食!"));
                mobs.forEach(mob -> mob.remove(Entity.RemovalReason.KILLED));
                mobs.clear();
                playerTotalKillCount.clear();
                playerEachRoundKillCount.clear();
                nextSpawnTick = Tick.get() + Tick.s(5);
                lastSpawnTick = Tick.get();
            }
                // tick
            handleMobTick();
            // 结束处理
                // 每回合结束
            if (currentRound > 0 && Tick.get() > nextSpawnTick) {
                if (mobs.isEmpty() || mobs.stream().noneMatch(LivingEntity::isAlive)) {
                    rewardEachRound();
                    broadToZonePlayers(Te.s("我们成功击退了这波吃货僵尸!"));
                    getPlayers().forEach(serverPlayer -> {
                        MySound.soundToPlayer(serverPlayer, SoundEvents.PLAYER_LEVELUP);
                        FireworkGun.summonFireWork(serverPlayer.level(),
                                serverPlayer.getEyePosition().add(0, 2, 0));
                    });
                    ++winTimes;
                    playerEachRoundKillCount.clear();
                    if (currentRound >= MAX_ROUND_NUM) {
                        rewardFinal();
                        clear();
                        broad(Te.s("我们成功击退了吃货僵尸们!"));
                        getPlayers().forEach(serverPlayer -> {
                            MySound.soundToPlayer(serverPlayer, SoundEvents.PLAYER_LEVELUP);
                        });
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

    public static int MAX_ROUND_NUM = 10;

    public static int getMobXpLevelByRound() {
        if (currentRound < 2) {
            return 80;
        } else if (currentRound < 5) {
            return 150;
        } else if (currentRound < 7) {
            return 200;
        }
        return 225;
    }

    public static int getMobAttackDamageByRound() {
        if (currentRound < 2) {
            return 400;
        } else if (currentRound < 5) {
            return 1200;
        } else if (currentRound < 7) {
            return 2600;
        }
        return 3000;
    }

    public static int getMobDefenceByRound() {
        if (currentRound < 2) {
            return 50;
        } else if (currentRound < 5) {
            return 100;
        } else if (currentRound < 7) {
            return 180;
        }
        return 200;
    }

    public static int getMobMaxHealthByRound() {
        if (currentRound < 2) {
            return 9000;
        } else if (currentRound < 5) {
            return 320000;
        } else if (currentRound < 7) {
            return 2000000;
        }
        return 5000000;
    }

    public static void summonMob(Level level, int num) {
        List<CookingPotRecipe> recipes = CookingValue.getCookingPotRecipes();
        for (int i = 0; i < num; i++) {
            Zombie mob = new Zombie(EntityType.ZOMBIE, level);
            MobSpawn.setMobCustomName(mob, Te.s(mobName, CustomStyle.styleOfPower), getMobXpLevelByRound());
            MobSpawn.MobBaseAttributes.setMobBaseAttributes(mob, getMobAttackDamageByRound(),
                    getMobDefenceByRound(), getMobDefenceByRound(), getMobMaxHealthByRound(), 0.2);
            mob.moveTo(spawnPosList.get(i % spawnPosList.size())
                    .add(2.5 - RandomUtils.nextDouble(0, 5), 0, 2.5 - RandomUtils.nextDouble(0, 5)));
            level.addFreshEntity(mob);
            mob.setItemInHand(InteractionHand.OFF_HAND, new ItemStack(
                    recipes.get(RandomUtils.nextInt(0, recipes.size())).getResultItem(RegistryAccess.EMPTY).getItem()));
            mob.setItemSlot(EquipmentSlot.HEAD, Compute.getSimpleFoiledItemStack(Items.DIAMOND_HELMET));
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
            if (player.experienceLevel >= 80) {
                if (playerEachRoundKillCount.containsKey(Name.get(player))) {
                    InventoryOperation.giveItemStackWithMSG(player, SpecialEventItems.SNACK_GOLD_COIN.get());
                } else {
                    sendMSG(player, Te.s("需要80级才能获取活动奖励喔!"));
                }
            }
        });
    }

    public static void rewardFinal() {
        getPlayers().forEach(player -> {
            if (player.experienceLevel >= 80) {
                InventoryOperation.giveItemStackWithMSG(player, SpecialEventItems.SNACK_GOLD_COIN.get(), 2);
            } else {
                sendMSG(player, Te.s("需要80级才能获取活动奖励喔!"));
            }
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

    public static String DAY_GET_SILVER_COIN_COUNT_KEY = "DayGetSilverCoinCount";

    public static void onKill(Player player, Mob mob) {
        if (MobSpawn.getMobOriginName(mob).equals(mobName)) {
            playerEachRoundKillCount.compute(Name.get(player), (k, v) -> v == null ? 1 : v + 1);
            playerTotalKillCount.compute(Name.get(player), (k, v) -> v == null ? 1 : v + 1);
            if (RandomUtils.nextBoolean()) {
                if (Compute.getDataIntValue(player, DAY_GET_SILVER_COIN_COUNT_KEY) < 100) {
                    Compute.incrementDataIntValue(player, DAY_GET_SILVER_COIN_COUNT_KEY, 1);
                    InventoryOperation.giveItemStackWithMSG(player, SpecialEventItems.SNACK_SILVER_COIN.get());
                }
            }
            if (RandomUtils.nextDouble(0, 1) < 0.1) {
                InventoryOperation.giveItemStackWithMSG(player, new ItemStack(mob.getOffhandItem().getItem()));
                summonMob(player.level(), 5);
                FireworkGun.summonFireWork(mob.level(), mob.getEyePosition().add(0, 2, 0));
                sendMSG(player, Te.s("吃货僵尸叫来了它的同伴!"));
            }
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

    public static boolean canBeSpread(Mob mob) {
        return !MobSpawn.getMobOriginName(mob).equals(mobName);
    }
}
