package com.very.wraq.common.Utils.Struct;

import com.very.wraq.common.Utils.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Instance {
    private Component InstanceName;
    private Component Position;
    private int LevelRequire;
    private int[] PsCost;
    private int LeastPeopleNum;
    private List<Mob> mobList;
    private boolean IsChallenge;
    private PlayerTeam CurrentChallengePlayerTeam;
    private int Tick;
    private int Difficulty;
    private Vec3 TeleportPosition;
    private ResourceKey<Level> Level;
    private int DeadTimes;

    public Instance(Component instanceName,
                    Component position,
                    int levelRequire,
                    int[] psCost,
                    int leastPeopleNum,
                    List<Mob> mobList,
                    boolean isChallenge,
                    PlayerTeam currentChallengePlayerTeam,
                    int tick,
                    int difficulty,
                    Vec3 teleportPosition,
                    ResourceKey<net.minecraft.world.level.Level> level,
                    int deadTimes) {
        this.InstanceName = instanceName;
        this.Position = position;
        this.LevelRequire = levelRequire;
        this.PsCost = psCost;
        this.LeastPeopleNum = leastPeopleNum;
        this.mobList = mobList;
        this.IsChallenge = isChallenge;
        this.CurrentChallengePlayerTeam = currentChallengePlayerTeam;
        this.Tick = tick;
        this.Difficulty = difficulty;
        this.TeleportPosition = teleportPosition;
        this.Level = level;
        this.DeadTimes = deadTimes;
    }

    public void reset() {
        if (this.mobList != null) {
            this.mobList.forEach(mob -> mob.remove(Entity.RemovalReason.KILLED));
        }
        this.mobList = null;
        this.IsChallenge = false;
        Utils.ChallengingPlayerTeam.remove(this.CurrentChallengePlayerTeam);
        this.CurrentChallengePlayerTeam = null;
        this.Tick = 0;
        this.DeadTimes = 0;
    }

    public int getTick() {
        return Tick;
    }

    public List<Mob> getMobList() {
        return mobList;
    }

    public boolean isChallenge() {
        return IsChallenge;
    }

    public Component getInstanceName() {
        return InstanceName;
    }

    public Component getPosition() {
        return Position;
    }

    public int getLeastPeopleNum() {
        return LeastPeopleNum;
    }

    public int getLevelRequire() {
        return LevelRequire;
    }

    public int[] getPsCost() {
        return PsCost;
    }

    public PlayerTeam getCurrentChallengePlayerTeam() {
        return this.CurrentChallengePlayerTeam;
    }

    public void setChallenge(boolean challenge) {
        IsChallenge = challenge;
    }

    public void setInstanceName(Component instanceName) {
        InstanceName = instanceName;
    }

    public void setCurrentChallengePlayerTeam(PlayerTeam currentChallengePlayer) {
        this.CurrentChallengePlayerTeam = currentChallengePlayer;
    }

    public void setLeastPeopleNum(int leastPeopleNum) {
        LeastPeopleNum = leastPeopleNum;
    }

    public void setLevelRequire(int levelRequire) {
        LevelRequire = levelRequire;
    }

    public void setMobList(List<Mob> mobList) {
        this.mobList = mobList;
    }

    public void setPosition(Component position) {
        Position = position;
    }

    public void setPsCost(int[] psCost) {
        PsCost = psCost;
    }

    public void setTick(int tick) {
        Tick = tick;
    }

    public void addTick() {
        this.Tick++;
    }

    public void setLevel(ResourceKey<net.minecraft.world.level.Level> level) {
        Level = level;
    }

    public int getDifficulty() {
        return Difficulty;
    }

    public ResourceKey<net.minecraft.world.level.Level> getLevel() {
        return Level;
    }

    public Vec3 getTeleportPosition() {
        return TeleportPosition;
    }

    public void setDifficulty(int difficulty) {
        Difficulty = difficulty;
    }

    public void setTeleportPosition(Vec3 teleportPosition) {
        TeleportPosition = teleportPosition;
    }

    public boolean allMobIsDead() {
        AtomicBoolean Flag = new AtomicBoolean(true);
        this.getMobList().forEach(mob -> {
            if (mob.isAlive()) Flag.set(false);
        });
        return Flag.get();
    }

    public int getDeadTimes() {
        return DeadTimes;
    }

    public void setDeadTimes(int deadTimes) {
        DeadTimes = deadTimes;
    }

    public void addDeadTimes() {
        DeadTimes++;
    }

}
