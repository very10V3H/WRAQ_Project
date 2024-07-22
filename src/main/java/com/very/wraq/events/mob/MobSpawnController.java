package com.very.wraq.events.mob;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class MobSpawnController {
    public List<Mob> mobList = new ArrayList<>();
    public List<Vec3> canSpawnPos;
    public final int oneZoneMaxMobNum;
    public final int boundaryUpX;
    public final int boundaryUpY;
    public final int boundaryUpZ;
    public final int boundaryDownX;
    public final int boundaryDownY;
    public final int boundaryDownZ;
    public final Level level;
    public final int mobPlayerRate;
    public final int averageLevel;
    public final int detectionRange;
    public final double summonOffset;
    public List<Boundary> multiBoundaryList = new ArrayList<>();

    public record Boundary(Vec3 upPos, Vec3 downPos) {
    }

    public MobSpawnController(List<Vec3> canSpawnPos, int oneZoneMaxMobNum,
                              int boundaryUpX, int boundaryUpY, int boundaryUpZ,
                              int boundaryDownX, int boundaryDownY, int boundaryDownZ,
                              double summonOffset, int detectionRange, Level level, int mobPlayerRate, int averageLevel) {
        this.canSpawnPos = canSpawnPos;
        this.oneZoneMaxMobNum = oneZoneMaxMobNum;
        this.boundaryUpX = boundaryUpX;
        this.boundaryUpY = boundaryUpY;
        this.boundaryUpZ = boundaryUpZ;
        this.boundaryDownX = boundaryDownX;
        this.boundaryDownY = boundaryDownY;
        this.boundaryDownZ = boundaryDownZ;
        this.summonOffset = summonOffset;
        this.detectionRange = detectionRange;
        this.level = level;
        this.mobPlayerRate = mobPlayerRate;
        this.averageLevel = averageLevel;

    }

    public MobSpawnController(List<Vec3> canSpawnPos, int oneZoneMaxMobNum,
                              int boundaryUpX, int boundaryUpZ, int boundaryDownX, int boundaryDownZ,
                              int detectionRange, Level level, int mobPlayerRate, int averageLevel) {
        this.canSpawnPos = canSpawnPos;
        this.oneZoneMaxMobNum = oneZoneMaxMobNum;
        this.boundaryUpX = boundaryUpX;
        this.boundaryUpY = Integer.MAX_VALUE;
        this.boundaryUpZ = boundaryUpZ;
        this.boundaryDownX = boundaryDownX;
        this.boundaryDownY = -Integer.MAX_VALUE;
        this.boundaryDownZ = boundaryDownZ;
        this.summonOffset = 1;
        this.detectionRange = detectionRange;
        this.level = level;
        this.mobPlayerRate = mobPlayerRate;
        this.averageLevel = averageLevel;
    }

    public MobSpawnController(List<Vec3> canSpawnPos, int oneZoneMaxMobNum,
                              int detectionRange, Level level, int mobPlayerRate, int averageLevel,
                              List<Boundary> multiBoundaryList) {
        this.canSpawnPos = canSpawnPos;
        this.oneZoneMaxMobNum = oneZoneMaxMobNum;
        this.boundaryUpX = Integer.MAX_VALUE;
        this.boundaryUpY = Integer.MAX_VALUE;
        this.boundaryUpZ = Integer.MAX_VALUE;
        this.boundaryDownX = -Integer.MAX_VALUE;
        this.boundaryDownY = -Integer.MAX_VALUE;
        this.boundaryDownZ = -Integer.MAX_VALUE;
        this.summonOffset = 1;
        this.detectionRange = detectionRange;
        this.level = level;
        this.mobPlayerRate = mobPlayerRate;
        this.averageLevel = averageLevel;
        this.multiBoundaryList = multiBoundaryList;
    }

    public <T> void detectAndSpawn() {
        mobList.removeIf(mob -> !mob.isAlive());

        // 若怪物越过边界 则将怪物随机传送至可重生地点
        mobList.forEach(mob -> {
            if (!multiBoundaryList.isEmpty()) {
                boolean mobIsInBoundary = false;
                for (Boundary boundary : multiBoundaryList) {
                    if (mob.getX() > boundary.downPos.x && mob.getY() > boundary.downPos.y
                            && mob.getZ() > boundary.downPos.z && mob.getX() < boundary.upPos.x
                            && mob.getY() < boundary.upPos.y && mob.getZ() < boundary.upPos.z) {
                        mobIsInBoundary = true;
                        break;
                    }
                }
                if (!mobIsInBoundary) {
                    mob.moveTo(canSpawnPos.get(new Random().nextInt(canSpawnPos.size())));
                }
            } else if (mob.getX() > boundaryUpX || mob.getX() < boundaryDownX
                    || mob.getY() > boundaryUpY || mob.getY() < boundaryDownY
                    || mob.getZ() > boundaryUpZ || mob.getZ() < boundaryDownZ) {
                mob.moveTo(canSpawnPos.get(new Random().nextInt(canSpawnPos.size())));
            }
        });

        AtomicBoolean thisZoneHasPlayer = new AtomicBoolean(false);
        canSpawnPos.forEach(pos -> {
            List<Player> playerList = level.getEntitiesOfClass(Player.class, AABB.ofSize(pos, detectionRange, detectionRange, detectionRange));
            for (Player player : playerList) {
                if (player.position().distanceTo(pos) < 4) {
                    thisZoneHasPlayer.set(true);
                    return;
                }
            }
            List<Mob> mobList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(pos, detectionRange, detectionRange, detectionRange));
            mobList.removeIf(mob -> !this.mobList.contains(mob));
            if (!playerList.isEmpty()) thisZoneHasPlayer.set(true);
            else return;
            // 若区域内怪物与玩家比较小且怪物数量不超过上限 则生成
            if (mobList.size() * 1.0 / playerList.size() < mobPlayerRate && this.mobList.size() < oneZoneMaxMobNum) {
                Mob mob = this.mobItemAndAttributeSet();
                Random r = new Random();
                Vec3 offset = Vec3.ZERO;
                if (summonOffset > 0) {
                    offset = new Vec3(r.nextDouble(summonOffset) - summonOffset / 2,
                            r.nextDouble(summonOffset) - summonOffset / 2, r.nextDouble(summonOffset) - summonOffset / 2);
                }
                mob.moveTo(pos.add(0.5, 0.5, 0.5).add(offset));
                this.mobList.add(mob);
                this.level.addFreshEntity(mob);
            }
        });

        // 若该区域全部检测范围内无玩家，则移除所有怪物
        if (!thisZoneHasPlayer.get()) {
            mobList.forEach(mob -> mob.remove(Entity.RemovalReason.KILLED));
        }
    }

    // 生成怪物
    public abstract Mob mobItemAndAttributeSet();

    // 怪物tick
    public abstract void tick();
}
