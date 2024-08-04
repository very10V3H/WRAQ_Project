package com.very.wraq.process.system.endlessinstance;

import com.very.wraq.common.Compute;
import com.very.wraq.networking.ModNetworking;
import com.very.wraq.process.system.endlessinstance.network.EndlessInstanceKillCountS2CPacket;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public abstract class DailyEndlessInstance {
    public static int clientKillCount = 0;
    public static int clientLastTick = 0;

    private final Vec3 pos;
    private final int lastTick;
    private final Component name;
    private String challengingPlayerName;
    private int playerLevel;
    private int leftTick;
    private int killCount;
    private int maxMobNum;
    private List<Mob> mobList;

    public DailyEndlessInstance(final Component name, final Vec3 pos, final int lastTick, int leftTick, int killCount, int maxMobNum) {
        this.name = name;
        this.pos = pos;
        this.lastTick = lastTick;
        this.leftTick = leftTick;
        this.killCount = killCount;
        this.maxMobNum = maxMobNum;
        this.mobList = new ArrayList<>();
    }

    public void commonTick(Level level) {
        if (leftTick > 0) leftTick--;
        else if (isChallenging()) {
            ServerPlayer serverPlayer = Compute.getPlayerByName(level, challengingPlayerName);
            if (serverPlayer != null) {
                reward(serverPlayer);
                sendFormatBroad(level, Component.literal("").withStyle(ChatFormatting.WHITE).
                        append(serverPlayer.getDisplayName()).
                        append(Component.literal("完成了").withStyle(ChatFormatting.WHITE)).
                        append(Component.literal("无尽熵增 - ").withStyle(CustomStyle.styleOfWorld)).
                        append(name).
                        append(Component.literal("共击杀了").withStyle(ChatFormatting.WHITE)).
                        append(Component.literal(String.valueOf(killCount)).withStyle(ChatFormatting.RED)).
                        append(Component.literal("只怪物").withStyle(ChatFormatting.WHITE)));
            } else {
                sendFormatBroad(level, Component.literal("").withStyle(ChatFormatting.WHITE).
                        append(Component.literal(challengingPlayerName).withStyle(ChatFormatting.WHITE)).
                        append(Component.literal("完成了").withStyle(ChatFormatting.WHITE)).
                        append(Component.literal("无尽熵增 - ").withStyle(CustomStyle.styleOfWorld)).
                        append(name).
                        append(Component.literal("共击杀了").withStyle(ChatFormatting.WHITE)).
                        append(Component.literal(String.valueOf(killCount)).withStyle(ChatFormatting.RED)).
                        append(Component.literal("只怪物").withStyle(ChatFormatting.WHITE)));
            }
            stop();
        }

        if (isChallenging()) {
            mobList.removeIf(LivingEntity::isDeadOrDying);
            while(mobList.size() < maxMobNum) {
                Mob mob = summonMob(level);
                mob.moveTo(getPos());
                level.addFreshEntity(mob);
                mobList.add(mob);
            } // 击杀后立即刷新
        }
    }

    public void start(Player player) {
        challengingPlayerName = player.getName().getString();
        playerLevel = player.experienceLevel;
        leftTick = lastTick;
        mobList.forEach(mob -> mob.remove(Entity.RemovalReason.KILLED));
        mobList.clear();
        killCount = 0;
    }

    public void stop() {
        challengingPlayerName = null;
        playerLevel = 0;
        leftTick = -1;
        mobList.forEach(mob -> mob.remove(Entity.RemovalReason.KILLED));
        mobList.clear();
        killCount = 0;
    }

    public boolean active(Player player) {
        if (player.position().distanceTo(pos) > 10) {
            sendFormatMSG(player, Component.literal("离挑战点太远了。").withStyle(ChatFormatting.WHITE));
            return false;
        }
        if (isChallenging()) {
            sendFormatMSG(player, Component.literal("有玩家正在进行这项挑战。").withStyle(ChatFormatting.WHITE));
            return false;
        }
        sendFormatBroad(player.level(), Component.literal("").withStyle(ChatFormatting.WHITE).
                append(player.getDisplayName()).
                append(Component.literal("正在挑战: ").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("无尽熵增 - ").withStyle(CustomStyle.styleOfWorld)).
                append(name));
        sendFormatMSG(player, Component.literal("尽可能多低清理怪物！").withStyle(ChatFormatting.WHITE));
        start(player);
        return true;
    }

    public static boolean prohibitPlayerCauseDamage(Player player, Mob mob) {
        for (DailyEndlessInstance instance : DailyEndlessInstanceEvent.getEndlessInstanceList()) {
            if (instance.mobList.contains(mob) && !instance.getChallengingPlayerName().equals(player.getName().getString())) {
                return true;
            }
        }
        return false;
    }

    public boolean isChallenging() {
        return leftTick != -1;
    }

    protected void sendFormatMSG(Player player, Component component) {
        Compute.sendFormatMSG(player, Component.literal("无尽熵增").withStyle(CustomStyle.styleOfWorld), component);
    }

    protected void sendFormatBroad(Level level, Component component) {
        Compute.formatBroad(level, Component.literal("无尽熵增").withStyle(CustomStyle.styleOfWorld), component);
    }

    protected abstract Mob summonMob(Level level);

    protected abstract void reward(Player player);

    public static void onKillMob(Player player, Mob mob) {
        String name = player.getName().getString();
        for (DailyEndlessInstance instance : DailyEndlessInstanceEvent.getEndlessInstanceList()) {
            if (instance.getChallengingPlayerName().equals(name) && instance.mobList.contains(mob)) {
                instance.incrementKillCount();
                ModNetworking.sendToClient(new EndlessInstanceKillCountS2CPacket(instance.getKillCount()),
                        (ServerPlayer) player);
                Compute.soundToPlayer(player, SoundEvents.PLAYER_ATTACK_KNOCKBACK, mob.position());
            }
        }
    }

    public Vec3 getPos() {
        return pos;
    }

    public int getLastTick() {
        return lastTick;
    }

    public String getChallengingPlayerName() {
        return challengingPlayerName;
    }

    public int getPlayerLevel() {
        return playerLevel;
    }

    public int getLeftTick() {
        return leftTick;
    }

    public int getKillCount() {
        return killCount;
    }

    public void incrementKillCount() {
        killCount++;
    }

    public int getMaxMobNum() {
        return maxMobNum;
    }

    public List<Mob> getMobList() {
        return mobList;
    }
}
