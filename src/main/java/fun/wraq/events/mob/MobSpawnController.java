package fun.wraq.events.mob;

import fun.wraq.common.Compute;
import fun.wraq.common.attribute.MobAttributes;
import fun.wraq.common.util.items.ItemAndRate;
import fun.wraq.process.system.element.Element;
import fun.wraq.series.events.dragonboat.DragonBoatFes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.IPlantable;

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
    public int preventRefreshDistance = 8;
    public boolean spawnFlag = false;
    public Vec3 averagePos;

    public record Boundary(Vec3 upPos, Vec3 downPos) {
    }

    public MobSpawnController(Component mobName, List<Vec3> canSpawnPos, int oneZoneMaxMobNum,
                              int boundaryUpX, int boundaryUpY, int boundaryUpZ,
                              int boundaryDownX, int boundaryDownY, int boundaryDownZ,
                              double summonOffset, int detectionRange, Level level,
                              int mobPlayerRate, int averageLevel, int preventRefreshDistance) {
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
        this.preventRefreshDistance = preventRefreshDistance;

        if (!canSpawnPos.isEmpty()) {
            double averageX = 0;
            double averageY = 0;
            double averageZ = 0;
            for (Vec3 canSpawnPo : canSpawnPos) {
                averageX += canSpawnPo.x;
                averageY += canSpawnPo.y;
                averageZ += canSpawnPo.z;
            }
            averageX /= canSpawnPos.size();
            averageY /= canSpawnPos.size();
            averageZ /= canSpawnPos.size();
            averagePos = new Vec3(averageX, averageY, averageZ);
        }
    }

    public MobSpawnController(Component mobName, List<Vec3> canSpawnPos, int oneZoneMaxMobNum,
                              int boundaryUpX, int boundaryUpY, int boundaryUpZ,
                              int boundaryDownX, int boundaryDownY, int boundaryDownZ,
                              double summonOffset, int detectionRange, Level level, int mobPlayerRate, int averageLevel) {
        this(mobName, canSpawnPos, oneZoneMaxMobNum,
                boundaryUpX, boundaryUpY, boundaryUpZ,
                boundaryDownX, boundaryDownY, boundaryDownZ,
                summonOffset, detectionRange, level,
                mobPlayerRate, averageLevel, 8);
    }

    public MobSpawnController(Component mobName, List<Vec3> canSpawnPos, int oneZoneMaxMobNum,
                              int boundaryUpX, int boundaryUpZ, int boundaryDownX, int boundaryDownZ,
                              int detectionRange, Level level, int mobPlayerRate, int averageLevel,
                              int preventRefreshDistance) {
        this(mobName, canSpawnPos, oneZoneMaxMobNum, boundaryUpX, Integer.MAX_VALUE, boundaryUpZ,
                boundaryDownX, -Integer.MAX_VALUE, boundaryDownZ, 2, detectionRange,
                level, mobPlayerRate, averageLevel, preventRefreshDistance);
    }

    public MobSpawnController(Component mobName, List<Vec3> canSpawnPos,
                              int boundaryUpX, int boundaryUpZ, int boundaryDownX, int boundaryDownZ,
                              int detectionRange, Level level, int mobPlayerRate, int averageLevel) {
        this(mobName, canSpawnPos, canSpawnPos.size() * 3, boundaryUpX, Integer.MAX_VALUE, boundaryUpZ,
                boundaryDownX, -Integer.MAX_VALUE, boundaryDownZ, 2, detectionRange,
                level, mobPlayerRate, averageLevel);
    }

    public MobSpawnController(Component mobName, List<Vec3> canSpawnPos,
                              int boundaryUpX, int boundaryUpZ,
                              int boundaryDownX, int boundaryDownZ,
                              Level level, int averageLevel) {
        this(mobName, canSpawnPos, canSpawnPos.size() * 3, boundaryUpX, Integer.MAX_VALUE, boundaryUpZ,
                boundaryDownX, -Integer.MAX_VALUE, boundaryDownZ, 2, 16,
                level, 1, averageLevel);
    }

    public MobSpawnController(Component mobName, List<Vec3> canSpawnPos,
                              int boundaryUpX, int boundaryUpY, int boundaryUpZ,
                              int boundaryDownX, int boundaryDownY, int boundaryDownZ,
                              Level level, int averageLevel) {
        this(mobName, canSpawnPos, canSpawnPos.size() * 3, boundaryUpX, boundaryUpY, boundaryUpZ,
                boundaryDownX, boundaryDownY, boundaryDownZ, 2, 16,
                level, 1, averageLevel);
    }

    public MobSpawnController(Component mobName, List<Vec3> canSpawnPos,
                              int boundaryUpX, int boundaryUpY, int boundaryUpZ,
                              int boundaryDownX, int boundaryDownY, int boundaryDownZ,
                              int preventRefreshDistance, Level level, int averageLevel) {
        this(mobName, canSpawnPos, canSpawnPos.size() * 3, boundaryUpX, boundaryUpY, boundaryUpZ,
                boundaryDownX, boundaryDownY, boundaryDownZ, 2, 16,
                level, 1, averageLevel);
        this.preventRefreshDistance = preventRefreshDistance;
    }

    public MobSpawnController(Component mobName, List<Vec3> canSpawnPos,
                              int detectionRange, Level level, int mobPlayerRate, int averageLevel,
                              List<Boundary> multiBoundaryList) {
        this(mobName, canSpawnPos, canSpawnPos.size() * 3,
                Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE,
                -Integer.MAX_VALUE, -Integer.MAX_VALUE, -Integer.MAX_VALUE, 2, detectionRange,
                level, mobPlayerRate, averageLevel);
        this.multiBoundaryList = multiBoundaryList;
    }

    public void handleTick() {
        mobList.removeIf(mob -> !mob.isAlive());
        mobList.forEach(mob -> {
            if (mob != null && mob.isAlive()) {
                if (!(mob instanceof Animal)) {
                    Player player = Compute.getNearestPlayer(mob, 32);
                    if (player != null && !player.isCreative() && !player.isSpectator()) {
                        mob.setTarget(player);
                    }
                }
                if (getElement() != null) {
                    Element.provideElement(mob, getElement().type(), getElement().value());
                }
                eachMobTick(mob);
            }
        });
        if (spawnFlag) {
            if (this.level.getServer().getPlayerList().getPlayers().stream()
                    .anyMatch(player -> canSpawnPos.stream().anyMatch(pos -> player.position().distanceTo(pos) < 48))) {
                spawnFlag = false;
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

                // 怪物数量未超上限，则开始全部生成
                if (averagePos != null && level.getEntitiesOfClass(Mob.class,
                        AABB.ofSize(averagePos, 96, 96, 96)).size()
                        > oneZoneMaxMobNum * canSpawnPos.size() * 2) {
                    return;
                }
                if (this.mobList.size() < oneZoneMaxMobNum) {
                    // 对每个刷新点附近进行探测，若满足生成条件，则生成
                    canSpawnPos.forEach(pos -> {
                        // 该点探测附近玩家列表
                        List<Player> playerList = level.getEntitiesOfClass(Player.class, AABB.ofSize(pos,
                                        detectionRange * 2, detectionRange * 2, detectionRange * 2))
                                .stream().filter(player -> player.position().distanceTo(pos) < detectionRange)
                                .toList();
                        // 玩家距离此刷新点距离小于指定格则不生成怪物
                        if (playerList.stream()
                                .anyMatch(player -> player.position().distanceTo(pos) < preventRefreshDistance)) {
                            return;
                        }
                        int summonTimes = 3;
                        if (canSpawnPos.size() == 1) {
                            summonTimes = 3 + playerList.size();
                        }
                        for (int i = 0; i < summonTimes; i++) {
                            Mob mob = this.mobItemAndAttributeSet();
                            DragonBoatFes.handleOnMobSpawn(mob);
                            Random r = new Random();
                            Vec3 offset = Vec3.ZERO;
                            if (summonOffset > 0) {
                                offset = new Vec3(
                                        r.nextDouble(summonOffset) - summonOffset / 2,
                                        r.nextDouble(summonOffset) - summonOffset / 2,
                                        r.nextDouble(summonOffset) - summonOffset / 2
                                );
                            }
                            Vec3 targetPos = pos.add(0.5, 0.5, 0.5).add(offset);
                            Block block = level.getBlockState(
                                    new BlockPos((int) targetPos.x, (int) targetPos.y, (int) targetPos.z)).getBlock();
                            if (block instanceof IPlantable
                                    || block instanceof SnowLayerBlock
                                    || block.equals(Blocks.AIR)) {
                                mob.moveTo(targetPos);
                            } else {
                                mob.moveTo(pos.add(0.5, 0.5, 0.5));
                            }
                            this.mobList.add(mob);
                            this.level.addFreshEntity(mob);

                            LivingEntity mounts = getMounts();
                            if (mounts != null) {
                                mounts.moveTo(mob.position());
                                level.addFreshEntity(mounts);
                                mob.startRiding(mounts);
                                MobSpawn.mountsMap.put(mounts, mob);
                            }
                        }
                    });
                }
            }
            else {
                mobList.forEach(mob -> {
                    mob.remove(Entity.RemovalReason.KILLED);
                });
                mobList.clear();
            }
        }
    }

    public LivingEntity getMounts() {
        return null;
    }

    // 生成怪物
    public abstract Mob mobItemAndAttributeSet();

    // 每只怪物的tick
    public void eachMobTick(Mob mob) {

    }

    public abstract List<ItemAndRate> getDropList();

    public abstract String getKillCountDataKey();

    public abstract MobAttributes getMobAttributes();

    public Element.Unit getElement() {
        return null;
    }

    public List<Component> getIntroduction() {
        return List.of();
    }
}
