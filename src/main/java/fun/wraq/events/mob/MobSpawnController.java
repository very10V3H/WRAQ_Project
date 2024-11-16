package fun.wraq.events.mob;

import fun.wraq.common.util.ItemAndRate;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class MobSpawnController {
    public final Component mobName;
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
    public int preventRefreshDistance = 4;

    public record Boundary(Vec3 upPos, Vec3 downPos) {}

    public MobSpawnController(Component mobName, List<Vec3> canSpawnPos, int oneZoneMaxMobNum,
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
        this.mobName = mobName;
    }

    public MobSpawnController(Component mobName, List<Vec3> canSpawnPos, int oneZoneMaxMobNum,
                              int boundaryUpX, int boundaryUpZ, int boundaryDownX, int boundaryDownZ,
                              int detectionRange, Level level, int mobPlayerRate, int averageLevel) {
        this(mobName, canSpawnPos, oneZoneMaxMobNum, boundaryUpX, Integer.MAX_VALUE, boundaryUpZ,
                boundaryDownX, -Integer.MAX_VALUE, boundaryDownZ, 1, detectionRange,
                level, mobPlayerRate, averageLevel);
    }

    public MobSpawnController(Component mobName, List<Vec3> canSpawnPos,
                              int boundaryUpX, int boundaryUpZ,
                              int boundaryDownX, int boundaryDownZ,
                              Level level, int averageLevel) {
        this(mobName, canSpawnPos, canSpawnPos.size() * 4, boundaryUpX, Integer.MAX_VALUE, boundaryUpZ,
                boundaryDownX, -Integer.MAX_VALUE, boundaryDownZ, 1, 16,
                level, 1, averageLevel);
    }

    public MobSpawnController(Component mobName, List<Vec3> canSpawnPos,
                              int boundaryUpX, int boundaryUpY, int boundaryUpZ,
                              int boundaryDownX, int boundaryDownY, int boundaryDownZ,
                              Level level, int averageLevel) {
        this(mobName, canSpawnPos, canSpawnPos.size() * 4, boundaryUpX, boundaryUpY, boundaryUpZ,
                boundaryDownX, boundaryDownY, boundaryDownZ, 1, 16,
                level, 1, averageLevel);
    }

    public MobSpawnController(Component mobName, List<Vec3> canSpawnPos,
                              int boundaryUpX, int boundaryUpY, int boundaryUpZ,
                              int boundaryDownX, int boundaryDownY, int boundaryDownZ,
                              int preventRefreshDistance, Level level, int averageLevel) {
        this(mobName, canSpawnPos, canSpawnPos.size() * 4, boundaryUpX, boundaryUpY, boundaryUpZ,
                boundaryDownX, boundaryDownY, boundaryDownZ, 1, 16,
                level, 1, averageLevel);
        this.preventRefreshDistance = preventRefreshDistance;
    }

    public MobSpawnController(Component mobName, List<Vec3> canSpawnPos, int oneZoneMaxMobNum,
                              int detectionRange, Level level, int mobPlayerRate, int averageLevel,
                              List<Boundary> multiBoundaryList) {
        this(mobName, canSpawnPos, oneZoneMaxMobNum, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE,
                -Integer.MAX_VALUE, -Integer.MAX_VALUE, -Integer.MAX_VALUE, 1, detectionRange,
                level, mobPlayerRate, averageLevel);
        this.multiBoundaryList = multiBoundaryList;
    }

    public void detectAndSpawn() {
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

        if (this.level.getServer().getPlayerList().getPlayers().stream()
                .anyMatch(player -> canSpawnPos.stream().anyMatch(pos -> player.position().distanceTo(pos) < 60))) {
            // 对每个刷新点附近进行探测，若满足生成条件，则生成
            canSpawnPos.forEach(pos -> {
                // 该点探测附近玩家列表
                List<Player> playerList = level.getEntitiesOfClass(Player.class, AABB.ofSize(pos,
                                detectionRange * 2, detectionRange * 2, detectionRange * 2))
                        .stream().filter(player -> player.position().distanceTo(pos) < detectionRange)
                        .toList();

                // 该点探测附近怪物列表
                List<Mob> mobList = this.mobList
                        .stream().filter(mob -> mob.position().distanceTo(pos) < detectionRange)
                        .toList();

                // 玩家距离此刷新点距离小于4格则不生成怪物
                if (playerList.stream().anyMatch(player -> player.position().distanceTo(pos) < preventRefreshDistance)) return;

                // 若该点附近怪物与玩家比较小且怪物数量不超过上限 则生成
                if (mobList.size() * 1.0 / Math.max(1, playerList.size()) < mobPlayerRate
                        && this.mobList.size() < oneZoneMaxMobNum) {
                    if (level.getServer().getPlayerList().getPlayers().stream()
                            .anyMatch(player -> player.position().distanceTo(pos) < 60)) {
                        int summonTimes = 1;
                        if (canSpawnPos.size() == 1) {
                            summonTimes = 2 + playerList.size();
                        }
                        for (int i = 0 ; i < summonTimes ; i ++) {
                            Mob mob = this.mobItemAndAttributeSet();
                            Random r = new Random();
                            Vec3 offset = Vec3.ZERO;
                            if (summonOffset > 0) {
                                offset = new Vec3(
                                        r.nextDouble(summonOffset) - summonOffset / 2,
                                        r.nextDouble(summonOffset) - summonOffset / 2,
                                        r.nextDouble(summonOffset) - summonOffset / 2
                                );
                            }
                            mob.moveTo(pos.add(0.5, 0.5, 0.5).add(offset));
                            this.mobList.add(mob);
                            this.level.addFreshEntity(mob);
                        }
                    }
                }
            });
        }

        // 移除距离玩家过远的怪物
        mobList.forEach(mob -> {
            if (mob.getServer().getPlayerList().getPlayers()
                    .stream().allMatch(player -> player.distanceTo(mob) > 60)) {
                mob.remove(Entity.RemovalReason.KILLED);
            }
        });
    }

    // 生成怪物
    public abstract Mob mobItemAndAttributeSet();

    // 怪物tick
    public abstract void tick();

    public abstract List<ItemAndRate> getDropList();
}
