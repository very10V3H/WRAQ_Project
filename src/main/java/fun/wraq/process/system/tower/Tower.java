package fun.wraq.process.system.tower;

import com.mojang.logging.LogUtils;
import fun.wraq.Items.MainStory_1.BackSpawn;
import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import fun.wraq.common.util.Utils;
import fun.wraq.events.core.InventoryCheck;
import fun.wraq.files.dataBases.DataBase;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.process.func.plan.PlanPlayer;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tower {
    private Vec3 playerTpPos;
    private List<Vec3> mobSummonPos;

    private boolean isChallenging;
    private List<Mob> mobList;
    private int mobSummonTimes;
    private Player currentPlayer;
    private int summonDelay;
    private int startTime;

    private final Vec3 upperBound;
    private final Vec3 downBound;

    public static final String[] numToRoma = {"I", "II", "III", "IV", "V", "VI"};
    /*, "VII", "VIII", "IX", "X"*/
    public static final int[] levelRequire = {100, 120, 140, 160, 180, 200};

    public static String rewardGetRecordKey = "towerRewardGetRecord";
    public static String rewardGetRecordValue = "0".repeat(80);

    public Tower(Vec3 playerTpPos, List<Vec3> mobSummonPos, Vec3 upperBound, Vec3 downBound) {
        this.playerTpPos = playerTpPos;
        this.mobSummonPos = mobSummonPos;

        this.isChallenging = false;
        this.mobList = new ArrayList<>();
        this.mobSummonTimes = -1;
        this.currentPlayer = null;
        this.summonDelay = -1;
        this.startTime = -1;
        this.upperBound = upperBound;
        this.downBound = downBound;
    }

    public static List<Tower> instanceList = new ArrayList<>() {{

        // update to 2.0 new world
        int xOffset = 585;
        int zOffset = -1018;

        add(new Tower(new Vec3(371 + xOffset, -62.5, 948.5 + zOffset),
                List.of(new Vec3(371 + xOffset, -62, 964 + zOffset), new Vec3(396 + xOffset, -62, 989 + zOffset),
                        new Vec3(371 + xOffset, -62, 1014 + zOffset), new Vec3(346 + xOffset, -62, 989 + zOffset)),
                new Vec3(419 + xOffset, -44, 1037 + zOffset), new Vec3(322 + xOffset, -64, 940 + zOffset)));
        add(new Tower(new Vec3(371 + xOffset, -44.5, 948.5 + zOffset),
                List.of(new Vec3(371 + xOffset, -44, 964 + zOffset), new Vec3(396 + xOffset, -44, 989 + zOffset),
                        new Vec3(371 + xOffset, -44, 1014 + zOffset), new Vec3(346 + xOffset, -44, 989 + zOffset)),
                new Vec3(419 + xOffset, -44 + 18, 1037 + zOffset), new Vec3(322 + xOffset, -64 + 18, 940 + zOffset)));
        add(new Tower(new Vec3(371 + xOffset, -26.5, 948.5 + zOffset),
                List.of(new Vec3(371 + xOffset, -26, 964 + zOffset), new Vec3(396 + xOffset, -26, 989 + zOffset),
                        new Vec3(371 + xOffset, -26, 1014 + zOffset), new Vec3(346 + xOffset, -26, 989 + zOffset)),
                new Vec3(419 + xOffset, -44 + 36, 1037 + zOffset), new Vec3(322 + xOffset, -64 + 36, 940 + zOffset)));

        add(new Tower(new Vec3(371 + xOffset, -8.5, 948.5 + zOffset),
                List.of(new Vec3(371 + xOffset, -8, 964 + zOffset), new Vec3(396 + xOffset, -8, 989 + zOffset),
                        new Vec3(371 + xOffset, -8, 1014 + zOffset), new Vec3(346 + xOffset, -8, 989 + zOffset)),
                new Vec3(419 + xOffset, -44 + 54, 1037 + zOffset), new Vec3(322 + xOffset, -64 + 54, 940 + zOffset)));
        add(new Tower(new Vec3(371 + xOffset, 10.5, 948.5 + zOffset),
                List.of(new Vec3(371 + xOffset, 10, 964 + zOffset), new Vec3(396 + xOffset, 10, 989 + zOffset),
                        new Vec3(371 + xOffset, 10, 1014 + zOffset), new Vec3(346 + xOffset, 10, 989 + zOffset)),
                new Vec3(419 + xOffset, -44 + 72, 1037 + zOffset), new Vec3(322 + xOffset, -64 + 72, 940 + zOffset)));
        add(new Tower(new Vec3(371 + xOffset, 28.5, 948.5 + zOffset),
                List.of(new Vec3(371 + xOffset, 28, 964 + zOffset), new Vec3(396 + xOffset, 28, 989 + zOffset),
                        new Vec3(371 + xOffset, 28, 1014 + zOffset), new Vec3(346 + xOffset, 28, 989 + zOffset)),
                new Vec3(419 + xOffset, -44 + 90, 1037 + zOffset), new Vec3(322 + xOffset, -64 + 90, 940 + zOffset)));
    }};

    public static void playerInChallengingDeadOrLogout(Player player) {
        for (Tower tower : instanceList) {
            if (tower.currentPlayer == player) {
                tower.reset();
            }
        }
    }

    private boolean allMobIsClear() {
        for (Mob mob : mobList) {
            if (mob.isAlive()) return false;
        }
        return true;
    }

    public static void tick(TickEvent.LevelTickEvent event) throws SQLException, IOException, ParseException {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START) && event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD))) {
            Level level = event.level;
            int tickCount = level.getServer().getTickCount();
            for (Tower tower : instanceList) {
                if (!tower.isChallenging) continue;
                int serialNum = instanceList.indexOf(tower);

                if (tower.currentPlayer.position().x > tower.upperBound.x
                        || tower.currentPlayer.position().y > tower.upperBound.y
                        || tower.currentPlayer.position().z > tower.upperBound.z
                        || tower.currentPlayer.position().x < tower.downBound.x
                        || tower.currentPlayer.position().y < tower.downBound.y
                        || tower.currentPlayer.position().z < tower.downBound.z) {
                    towerTypeFormatBroad(level, Component.literal("").withStyle(ChatFormatting.WHITE).
                            append(tower.currentPlayer.getDisplayName()).
                            append(Component.literal("在挑战 ").withStyle(ChatFormatting.WHITE)).
                            append(Component.literal("本源回廊 - " + Tower.numToRoma[instanceList.indexOf(tower)]).withStyle(CustomStyle.styleOfWorld)).
                            append(Component.literal(" 中离开挑战区域，挑战失败").withStyle(ChatFormatting.WHITE)));
                    towerTypeFormatMSG(tower.currentPlayer, Component.literal("离开挑战区域").withStyle(ChatFormatting.WHITE));
                    tower.reset();
                }

                if (!tower.allMobIsClear()) {
                    if (tickCount % 20 == 0 && serialNum == 0) TowerMob.tickControl1Floor(tower.mobList);
                    if (serialNum == 5) TowerMob.tickControl6Floor(tower.mobList);
                    String[] elementType = {Element.life, Element.water, Element.fire, Element.ice, Element.stone, Element.lightning};
                    tower.mobList.forEach(mob -> {
                        Element.ElementProvider(mob, elementType[instanceList.indexOf(tower)], 5);
                    });
                }

                // 生成多波怪物
                if (tower.summonDelay == tickCount && tower.mobSummonTimes > 0) {
                    TowerMob.summonMob(serialNum, level, tower.mobList, tower.mobSummonPos);
                    --tower.mobSummonTimes;
                }

                // 生成延迟
                if (tower.allMobIsClear() && tower.mobSummonTimes > 0 && tower.summonDelay < tickCount) {
                    tower.summonDelay = tickCount + 20;
                }

                // 清理完毕
                if (tower.allMobIsClear() && tower.mobSummonTimes == 0) {
                    tower.reward();
                    tower.reset();
                }

                // 超时
                if (tickCount - tower.startTime > 4800) {
                    towerTypeFormatBroad(level, Component.literal("").withStyle(ChatFormatting.WHITE).
                            append(tower.currentPlayer.getDisplayName()).
                            append(Component.literal("在挑战 ").withStyle(ChatFormatting.WHITE)).
                            append(Component.literal("本源回廊 - " + Tower.numToRoma[instanceList.indexOf(tower)]).withStyle(CustomStyle.styleOfWorld)).
                            append(Component.literal(" 中超时，挑战失败").withStyle(ChatFormatting.WHITE)));
                    towerTypeFormatMSG(tower.currentPlayer, Component.literal("挑战超时").withStyle(ChatFormatting.WHITE));
                    tower.reset();
                }
            }
        }
    }

    private void reset() {
        if (currentPlayer != null) Compute.respawnPlayer(this.currentPlayer);
        isChallenging = false;
        mobSummonTimes = -1;
        currentPlayer = null;
        for (Mob mob : this.mobList) mob.remove(Entity.RemovalReason.KILLED);

        this.mobList = new ArrayList<>();
        this.summonDelay = -1;
    }

    public static int getPlayerStatus(Player player, int index) throws SQLException {
        return Integer.parseInt(getPlayerStatus(player).substring(index, index + 1));
    }

    public static String getPlayerStatus(Player player) throws SQLException {
        if (playerStatus.containsKey(player.getName().getString()))
            return playerStatus.get(player.getName().getString());
        else {
            String status = DataBase.get(player, rewardGetRecordKey);
            if (status == null) {
                DataBase.put(player, rewardGetRecordKey, rewardGetRecordValue);
                playerStatus.put(player.getName().getString(), rewardGetRecordValue);
                return rewardGetRecordValue;
            }
            playerStatus.put(player.getName().getString(), status);
            return status;
        }
    }

    public static void putPlayerStatus(Player player, String status) {
        playerStatus.put(player.getName().getString(), status);
    }

    private void reward() throws IOException, SQLException, ParseException {
        // 30s为一阶段
        int stage = 4 - Math.min(3, (this.currentPlayer.getServer().getTickCount() - this.startTime) / 1200);
        int index = instanceList.indexOf(this);
        int minus = stage - getPlayerStatus(currentPlayer, index);
        if (minus > 0) {
            givePlayerStar(this.currentPlayer, minus * (PlanPlayer.getPlayerTier(this.currentPlayer) > 0 ? 2 : 1), "本源塔挑战");
            if (PlanPlayer.getPlayerTier(this.currentPlayer) > 0) {
                Compute.sendFormatMSG(this.currentPlayer, Component.literal("本源").withStyle(CustomStyle.styleOfWorld),
                        Component.literal("计划为你额外提供了 ").withStyle(ChatFormatting.WHITE).
                                append(Component.literal(minus + "*").withStyle(CustomStyle.styleOfWorld)).
                                append(ModItems.worldSoul5.get().getDefaultInstance().getDisplayName()));
            }
            StringBuilder stringBuilder = new StringBuilder(getPlayerStatus(this.currentPlayer));
            stringBuilder.setCharAt(index, String.valueOf(stage).charAt(0));
            putPlayerStatus(this.currentPlayer, stringBuilder.toString());
            ModNetworking.sendToClient(new TowerStatusS2CPacket(stringBuilder.toString()), (ServerPlayer) this.currentPlayer);
        }
        Compute.formatBroad(this.currentPlayer.level(), Component.literal("本源回廊").withStyle(CustomStyle.styleOfWorld),
                Component.literal("").withStyle(ChatFormatting.WHITE).
                        append(this.currentPlayer.getDisplayName()).
                        append(Component.literal(" 完成了 ").withStyle(ChatFormatting.WHITE)).
                        append(Component.literal("本源回廊 - " + Tower.numToRoma[instanceList.indexOf(this)]).withStyle(CustomStyle.styleOfWorld)).
                        append(Component.literal(" 的 ").withStyle(ChatFormatting.WHITE)).
                        append(Component.literal(stage + "★").withStyle(CustomStyle.styleOfWorld)).
                        append(Component.literal("挑战").withStyle(ChatFormatting.RED)));
        TowerTimeRecord.refreshRecord(instanceList.indexOf(this) + 1, this.currentPlayer, this.currentPlayer.getServer().getTickCount() - this.startTime);
    }

    public static void playerTryToChallenging(ServerPlayer serverPlayer, int index) throws SQLException {
        if (serverPlayer.position().distanceTo(BackSpawn.spawnPos) >= 512) {
            towerTypeFormatMSG(serverPlayer, Component.literal("你需要在天空城附近才能进入本源回廊").withStyle(ChatFormatting.WHITE));
            return;
        }
        Tower tower = instanceList.get(index);
        if (playerIsChallengingTower(serverPlayer) != -1) {
            towerTypeFormatMSG(serverPlayer, Component.literal("请先完成已有挑战").withStyle(ChatFormatting.WHITE));
            return;
        }
        if (getPlayerStatus(serverPlayer, index) == 4) {
            towerTypeFormatMSG(serverPlayer, Component.literal("您已完成本期挑战").withStyle(ChatFormatting.WHITE));
            return;
        }
        if (tower.isChallenging) {
            towerTypeFormatMSG(serverPlayer, Component.literal("该回廊已有玩家在挑战").withStyle(ChatFormatting.WHITE));
            return;
        }
        if (serverPlayer.experienceLevel < Tower.levelRequire[index]) {
            towerTypeFormatMSG(serverPlayer, Component.literal("等级未达到需求").withStyle(ChatFormatting.WHITE));
            return;
        }
        serverPlayer.teleportTo(serverPlayer.getServer().getLevel(Level.OVERWORLD), tower.playerTpPos.x, tower.playerTpPos.y, tower.playerTpPos.z, 0, 0);
        tower.isChallenging = true;
        tower.mobSummonTimes = 2;
        tower.currentPlayer = serverPlayer;
        tower.summonDelay = serverPlayer.getServer().getTickCount() + 100;
        tower.startTime = serverPlayer.getServer().getTickCount();

        towerTypeFormatBroad(serverPlayer.level(), Component.literal("").withStyle(ChatFormatting.WHITE).
                append(serverPlayer.getDisplayName()).
                append(Component.literal("正在挑战 ").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("本源回廊 - " + Tower.numToRoma[index]).withStyle(CustomStyle.styleOfWorld)));

        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                new ClientboundSetTitleTextPacket(Component.literal("本源回廊 - " + Tower.numToRoma[index]).withStyle(CustomStyle.styleOfWorld));
        serverPlayer.connection.send(clientboundSetTitleTextPacket);
    }

    public static String clientTowerStatus;

    public static final Map<String, String> playerStatus = new HashMap<>();

    public static void writeToDataBase() throws SQLException {
        Connection connection = DataBase.getDatabaseConnection();
        Statement statement = connection.createStatement();
        playerStatus.forEach((name, s) -> {
            try {
                DataBase.put(statement, name, rewardGetRecordKey, s);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        statement.close();

    }

    public static void writeToDataBase(Statement statement) {
        playerStatus.forEach((name, s) -> {
            try {
                DataBase.put(statement, name, rewardGetRecordKey, s);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void synchronizedWrite(Statement statement) throws SQLException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                writeToDataBase(statement);
            }
        }).start();
    }

    public static void resetData(Player player) {
        towerTypeFormatMSG(player, Component.literal("本源回廊的挑战进度已被重置").withStyle(CustomStyle.styleOfWorld));
        playerStatus.put(player.getName().getString(), rewardGetRecordValue);
    }

    public static void towerTypeFormatMSG(Player player, Component component) {
        Compute.sendFormatMSG(player, Component.literal("本源回廊").withStyle(CustomStyle.styleOfWorld), component);
    }

    public static void towerTypeFormatBroad(Level level, Component component) {
        Compute.formatBroad(level, Component.literal("本源回廊").withStyle(CustomStyle.styleOfWorld), component);
    }

    public static int playerIsChallengingTower(Player player) {
        for (Tower tower : instanceList) {
            if (tower.isChallenging && tower.currentPlayer.equals(player)) return instanceList.indexOf(tower);
        }
        return -1;
    }

    public static int mobIsTowerMob(Mob mob) {
        for (Tower tower : instanceList) {
            if (tower.isChallenging && tower.mobList.contains(mob)) return instanceList.indexOf(tower);
        }
        return -1;
    }

    public List<Mob> getMobList() {
        return this.mobList;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public int getStartTime() {
        return this.startTime;
    }

    public static Map<String, Integer> playerStarGetCounts = new HashMap<>();

    public static String starGetCounts = "starGetCounts";

    public static void givePlayerStar(Player player, int count, String type) throws SQLException {
        String playerName = player.getName().getString();

        // 往数据库查找
        if (!playerStarGetCounts.containsKey(playerName)) {
            Connection connection = DataBase.getDatabaseConnection();
            Statement statement = connection.createStatement();
            String countsString = DataBase.get(statement, player, starGetCounts);
            if (countsString != null) {
                playerStarGetCounts.put(playerName, Integer.parseInt(countsString));
            } else playerStarGetCounts.put(playerName, 0);
            statement.close();
    
        }

        // 给予
        ItemStack starStack = new ItemStack(ModItems.worldSoul5.get(), count);
        InventoryCheck.addOwnerTagToItemStack(player, starStack);
        InventoryOperation.itemStackGive(player, starStack);
        LogUtils.getLogger().info("{} {} by {}", playerName, Utils.LogTypes.worldSoul5, type);

        // 增加
        playerStarGetCounts.put(playerName, playerStarGetCounts.get(playerName) + count);
    }

    public static void writeStarCountToDataBase() throws SQLException {
        Connection connection = DataBase.getDatabaseConnection();
        Statement statement = connection.createStatement();
        playerStarGetCounts.forEach((name, integer) -> {
            try {
                DataBase.put(statement, name, starGetCounts, String.valueOf(integer));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        statement.close();

    }

    public static void writeStarCountToDataBase(Statement statement) throws SQLException {
        playerStarGetCounts.forEach((name, integer) -> {
            try {
                DataBase.put(statement, name, starGetCounts, String.valueOf(integer));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void synchronizedWriteStarCounts(Statement statement) throws SQLException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    writeStarCountToDataBase(statement);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
