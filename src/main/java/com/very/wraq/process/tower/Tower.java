package com.very.wraq.process.tower;

import com.mojang.logging.LogUtils;
import com.very.wraq.files.dataBases.DataBase;
import com.very.wraq.netWorking.ModNetworking;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
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

    public static final String[] numToRoma = {"I"};
    public static final int[] levelRequire = {100};

    /*public static final String[] numToRoma = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};*/
    /*public static final int[] levelRequire = {100, 120, 140, 160, 180, 200};*/

    public static String rewardGetRecordKey = "towerRewardGetRecord";
    public static String rewardGetRecordValue = "0".repeat(80);

    public Tower(Vec3 playerTpPos, List<Vec3> mobSummonPos) {
        this.playerTpPos = playerTpPos;
        this.mobSummonPos = mobSummonPos;

        this.isChallenging = false;
        this.mobList = new ArrayList<>();
        this.mobSummonTimes = -1;
        this.currentPlayer = null;
        this.summonDelay = -1;
        this.startTime = -1;
    }

    public static List<Tower> instanceList = new ArrayList<Tower>(){{
        add(new Tower(new Vec3(371, -62.5, 948.5),
                List.of(new Vec3(371, -62, 964), new Vec3(396, -62, 989),
                        new Vec3(371, -62, 1014), new Vec3(346, -62, 989))));
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

    private void summonMob(Level level) {
        for (Vec3 mobSummonPo : mobSummonPos) {
            Zombie zombie = new Zombie(EntityType.ZOMBIE, level);
            Compute.SetMobCustomName(zombie, ModItems.MobArmorTower1FloorHelmet.get(),Component.literal("本源1层测试怪物").withStyle(ChatFormatting.AQUA));
            zombie.setItemSlot(EquipmentSlot.HEAD , ModItems.MobArmorTower1FloorHelmet.get().getDefaultInstance());
            zombie.setItemSlot(EquipmentSlot.MAINHAND , Items.DIAMOND_SWORD.getDefaultInstance());
            zombie.getAttribute(Attributes.MAX_HEALTH).setBaseValue(1500000);
            zombie.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5D);
            zombie.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(1000);
            zombie.setHealth(zombie.getMaxHealth());
            zombie.moveTo(mobSummonPo);
            mobList.add(zombie);
            level.addFreshEntity(zombie);
        }
    }

    public static void tick(TickEvent.LevelTickEvent event) throws SQLException, IOException {
        if (event.side.isServer() && event.phase.equals(TickEvent.Phase.START) && event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD))) {
            Level level = event.level;
            int tickCount = level.getServer().getTickCount();
            for (Tower tower : instanceList) {
                if (tower.summonDelay == tickCount && tower.mobSummonTimes > 0) {
                    tower.summonMob(level);
                    --tower.mobSummonTimes;
                }
                if (tower.isChallenging && tower.allMobIsClear() && tower.mobSummonTimes > 0 && tower.summonDelay < tickCount) {
                    tower.summonDelay = tickCount + 20;
                }
                if (tower.isChallenging && tower.allMobIsClear() && tower.mobSummonTimes == 0) {
                    tower.reward();
                    tower.reset();
                }
            }
        }
    }

    private void reset() {
        Compute.RespawnPlayer(this.currentPlayer);
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
        if (playerStatus.containsKey(player.getName().getString())) return playerStatus.get(player.getName().getString());
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

    private void reward() throws IOException, SQLException {
        int stage = 4 - Math.min(3, (this.currentPlayer.getServer().getTickCount() - this.startTime) / 3600);
        int index = instanceList.indexOf(this);
        int minus = stage - getPlayerStatus(currentPlayer, index);
        if (minus > 0) {
            Compute.ItemStackGive(this.currentPlayer, new ItemStack(ModItems.LabourDayLottery.get(), minus));
            StringBuilder stringBuilder = new StringBuilder(getPlayerStatus(this.currentPlayer));
            stringBuilder.setCharAt(index, String.valueOf(stage).charAt(0));
            putPlayerStatus(this.currentPlayer, stringBuilder.toString());
            ModNetworking.sendToClient(new TowerStatusS2CPacket(stringBuilder.toString()), (ServerPlayer) this.currentPlayer);
            Compute.FormatBroad(this.currentPlayer.level(), Component.literal("本源回廊").withStyle(CustomStyle.styleOfWorld),
                    Component.literal("").withStyle(ChatFormatting.WHITE).
                            append(this.currentPlayer.getDisplayName()).
                            append(Component.literal(" 完成了 ").withStyle(ChatFormatting.WHITE)).
                            append(Component.literal("本源回廊 - " + Tower.numToRoma[instanceList.indexOf(this)]).withStyle(CustomStyle.styleOfWorld)).
                            append(Component.literal(" 的 ").withStyle(ChatFormatting.WHITE)).
                            append(Component.literal(stage + "★").withStyle(CustomStyle.styleOfWorld)).
                            append(Component.literal("挑战").withStyle(ChatFormatting.RED)));
        }
    }

    public static void playerTryToChallenging(ServerPlayer serverPlayer, int index) throws SQLException {
        Tower tower = instanceList.get(index);
        if (getPlayerStatus(serverPlayer, index) == 4) {
            Compute.FormatMSGSend(serverPlayer, Component.literal("本源回廊").withStyle(CustomStyle.styleOfWorld),
                    Component.literal("您已完成本期挑战").withStyle(ChatFormatting.WHITE));
            return;
        }
        if (tower.isChallenging) {
            Compute.FormatMSGSend(serverPlayer, Component.literal("本源回廊").withStyle(CustomStyle.styleOfWorld),
                    Component.literal("该回廊已有玩家在挑战").withStyle(ChatFormatting.WHITE));
            return;
        }
        if (serverPlayer.experienceLevel < Tower.levelRequire[index]) {
            Compute.FormatMSGSend(serverPlayer, Component.literal("本源回廊").withStyle(CustomStyle.styleOfWorld),
                    Component.literal("等级未达到需求").withStyle(ChatFormatting.WHITE));
            return;
        }
        serverPlayer.teleportTo(serverPlayer.getServer().getLevel(Level.OVERWORLD), tower.playerTpPos.x, tower.playerTpPos.y, tower.playerTpPos.z, 0, 0);
        tower.isChallenging = true;
        tower.mobSummonTimes = 3;
        tower.currentPlayer = serverPlayer;
        tower.summonDelay = serverPlayer.getServer().getTickCount() + 20;
        tower.startTime = serverPlayer.getServer().getTickCount();
    }

    public static String clientTowerStatus;

    public static final Map<String, String> playerStatus = new HashMap<>();

    public static void writeToDataBase() throws SQLException {
        LogUtils.getLogger().info("writing tower data to MySQL...");
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
        connection.close();
        LogUtils.getLogger().info("written tower data to MySQL successfully");
    }

    public static void resetPlayerData(Player player) {
        playerStatus.put(player.getName().getString(), rewardGetRecordValue);
    }
}
