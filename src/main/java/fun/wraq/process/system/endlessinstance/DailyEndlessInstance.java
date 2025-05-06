package fun.wraq.process.system.endlessinstance;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Te;
import fun.wraq.common.registry.MySound;
import fun.wraq.networking.ModNetworking;
import fun.wraq.process.system.endlessinstance.data.EndlessInstanceRecordData;
import fun.wraq.process.system.endlessinstance.network.EndlessInstanceKillCountS2CPacket;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class DailyEndlessInstance {
    public static int clientKillCount = 0;
    public static int clientLastTick = 0;

    private final Vec3 pos;
    private final int lastTick;
    public final Component name;
    private String challengingPlayerName;
    private int playerLevel;
    private int leftTick;
    private int killCount;
    private int maxMobNum;
    private List<Mob> mobList;
    private final int refreshDelayTick;

    public DailyEndlessInstance(final Component name, final Vec3 pos, final int lastTick, int maxMobNum, int refreshDelayTick) {
        this.name = name;
        this.pos = pos;
        this.lastTick = lastTick;
        this.maxMobNum = maxMobNum;
        this.mobList = new ArrayList<>();
        this.refreshDelayTick = refreshDelayTick;
    }

    public void commonTick(Level level) {
        if (leftTick > 0) leftTick--;
        else if (isChallenging()) {
            ServerPlayer serverPlayer = Compute.getPlayerByName(challengingPlayerName);
            if (serverPlayer != null) {
                reward(serverPlayer);
                sendFormatMSG(serverPlayer, Te.s("本次挑战共击杀了", killCount, ChatFormatting.RED, "只怪物"));
                EndlessInstanceRecordData.record(name.getString(), serverPlayer, killCount);
            }
            stop();
        }

        if (isChallenging() && leftTick != 0 && leftTick % refreshDelayTick == 0) {
            onFreshNotice();
            mobList.removeIf(LivingEntity::isDeadOrDying);
            while (mobList.size() < maxMobNum) {
                mobList.addAll(summonMob(level));
            } // 击杀后立即刷新
        }
    }

    public void onFreshNotice() {
    }

    public void start(Player player) {
        challengingPlayerName = player.getName().getString();
        playerLevel = player.experienceLevel;
        leftTick = lastTick;
        mobList.forEach(mob -> mob.remove(Entity.RemovalReason.KILLED));
        mobList.clear();
        killCount = 0;
        while (mobList.size() < maxMobNum) {
            mobList.addAll(summonMob(player.level()));
        } // 击杀后立即刷新
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
        if (isChallenging()) {
            return false;
        }
        if (!onRightClickTrig(player)) {
            return false;
        }
        sendFormatMSG(player, Component.literal("尽可能多地清理怪物！").withStyle(ChatFormatting.WHITE));
        start(player);
        return true;
    }

    public void spawnTitleArmorStand(Level level) {
        List<ArmorStand> armorStandList = level.getEntitiesOfClass(ArmorStand.class, AABB.ofSize(pos,
                8, 8, 8));
        armorStandList.forEach(armorStand -> armorStand.remove(Entity.RemovalReason.KILLED));
        if (!getNearPlayers(level).isEmpty() && !isChallenging()) {
            int size = getTrigConditionDescription().size();
            summonArmorStand(level, new Vec3(0, (size - 1) * 0.25, 0), Te.s("无尽熵增 - ", CustomStyle.styleOfWorld,
                    name));
            for (int i = 0 ; i < size ; i ++) {
                Component content = getTrigConditionDescription().get(i);
                summonArmorStand(level, new Vec3(0, (size - 2 - i) * 0.25, 0), content);
            }
        }
    }

    public List<Player> getNearPlayers(Level level) {
        double range = 16;
        List<Player> playerList = level.getEntitiesOfClass(Player.class,
                AABB.ofSize(pos, range * 2, range * 2, range * 2));
        playerList.removeIf(player -> player.position().distanceTo(pos) > range);
        Set<Player> players = new HashSet<>(playerList);
        if (!mobList.isEmpty()) {
            for (Mob mob : mobList) {
                players.addAll(level.getEntitiesOfClass(Player.class, AABB.ofSize(mob.position(),
                                range * 2, range * 2, range * 2))
                        .stream().filter(player -> player.distanceTo(mob) < range).toList());
            }
        }
        return players.stream().toList();
    }

    public void summonArmorStand(Level level, Vec3 offset, Component name) {
        Compute.summonArmorStand(level, pos.add(offset), name);
    }

    public static boolean prohibitPlayerCauseDamage(Player player, Mob mob) {
        for (DailyEndlessInstance instance : DailyEndlessInstanceEvent.getEndlessInstanceList()) {
            if (instance.isChallenging() && instance.mobList.contains(mob)
                    && instance.getChallengingPlayerName() != null
                    && !instance.getChallengingPlayerName().equals(player.getName().getString())) {
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

    protected abstract List<Mob> summonMob(Level level);
    protected abstract void reward(Player player);
    protected abstract boolean onRightClickTrig(Player player);
    protected abstract List<Component> getTrigConditionDescription();

    public static void onKillMob(Player player, Mob mob) {
        String name = player.getName().getString();
        for (DailyEndlessInstance instance : DailyEndlessInstanceEvent.getEndlessInstanceList()) {
            if (instance.getChallengingPlayerName() != null
                    && instance.getChallengingPlayerName().equals(name) && instance.mobList.contains(mob)) {
                instance.incrementKillCount();
                ModNetworking.sendToClient(new EndlessInstanceKillCountS2CPacket(instance.getKillCount()),
                        (ServerPlayer) player);
                MySound.soundToPlayer(player, SoundEvents.PLAYER_ATTACK_KNOCKBACK, mob.position());
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

    public int getPlayerXpLevel() {
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
