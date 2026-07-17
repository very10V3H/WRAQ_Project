/** AI-Generated, 2026-05-10 */
package fun.wraq.common.util;

import fun.wraq.common.fast.PlayerHashMap;
import fun.wraq.common.fast.Tick;
import fun.wraq.events.mob.MobSpawn;
import net.mcreator.borninchaosv.init.BornInChaosV1ModEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class EntityQueryUtil {

    private static final Set<EntityType<?>> bornInChaosMobTypeSet = new HashSet<>();
    private static final PlayerHashMap<Mob> playerMainAttackTarget = new PlayerHashMap<>();

    // ---- Basic entity queries ----

    public static List<? extends Entity> getNearEntity(Entity center, Class<? extends Entity> type, double distance) {
        List<? extends Entity> list = center.level().getEntitiesOfClass(type, AABB.ofSize(center.position(), distance * 2, distance * 2, distance * 2));
        return list.stream().filter(e -> e.distanceTo(center) <= distance).toList();
    }

    public static List<? extends Entity> getNearEntity(Level level, Vec3 center, Class<? extends Entity> type, double distance) {
        List<? extends Entity> list = level.getEntitiesOfClass(type, AABB.ofSize(center, distance * 2, distance * 2, distance * 2));
        return list.stream().filter(e -> e.position().distanceTo(center) <= distance).toList();
    }

    public static List<Mob> getNearMob(Entity center, double distance) {
        return getNearEntity(center, Mob.class, distance).stream()
                .filter(entity -> entity instanceof Mob mob && isWraqMob(mob))
                .map(entity -> (Mob) entity)
                .toList();
    }

    public static List<Mob> getNearMob(Level level, Vec3 pos, double distance) {
        return getNearEntity(level, pos, Mob.class, distance).stream()
                .filter(entity -> entity instanceof Mob mob && isWraqMob(mob))
                .map(entity -> (Mob) entity)
                .toList();
    }

    public static Set<Player> getNearPlayer(Level level, Vec3 center, double radius) {
        return getNearEntity(level, center, Player.class, radius).stream()
                .filter(entity -> entity instanceof Player)
                .map(entity -> (Player) entity)
                .collect(Collectors.toSet());
    }

    public static Set<Player> getNearPlayer(Entity entity, double radius) {
        return getNearPlayer(entity.level(), entity.position(), radius);
    }

    public static Player getNearestPlayer(LivingEntity livingEntity, double radius) {
        return getNearPlayer(livingEntity.level(), livingEntity.position(), radius).stream().min(new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                return (int) (o1.distanceTo(livingEntity) - o2.distanceTo(livingEntity));
            }
        }).orElse(null);
    }

    public static Mob getNearestMob(Player player, double radius) {
        return getNearMob(player.level(), player.position(), radius).stream().min(new Comparator<Mob>() {
            @Override
            public int compare(Mob o1, Mob o2) {
                return (int) (o1.distanceTo(player) - o2.distanceTo(player));
            }
        }).orElse(null);
    }

    // ---- Ray-based queries ----

    public static Set<Mob> getPlayerRayMobList(Player player, double detectStep, double detectRange, double maxDistance) {
        Level level = player.level();
        Vec3 targetPos = player.pick(25, 0, false).getLocation();
        Vec3 startPos = player.pick(0.5, 0, false).getLocation();
        Vec3 posVec = targetPos.subtract(startPos).normalize();
        Set<Mob> mobs = new HashSet<>();
        for (double i = detectStep; i <= maxDistance; i += detectStep) {
            List<Mob> mobList1 = level.getEntitiesOfClass(Mob.class, AABB.ofSize(startPos.add(posVec.scale(i)),
                    detectRange, detectRange, detectRange)).stream().filter(EntityQueryUtil::isWraqMob).toList();
            mobs.addAll(mobList1);
        }
        return mobs;
    }

    public static Mob detectPlayerPickMob(Player player) {
        Level level = player.level();
        Vec3 TargetPos = player.pick(15, 0, false).getLocation();
        Vec3 StartPos = player.pick(0.5, 0, false).getLocation();
        Vec3 PosVec = TargetPos.subtract(StartPos).normalize();
        double Distance = TargetPos.distanceTo(StartPos);
        for (double i = 0; i < Distance; i += 0.5) {
            List<Mob> mobList1 = level.getEntitiesOfClass(Mob.class, AABB.ofSize(StartPos.add(PosVec.scale(i)), 0.5, 0.5, 0.5));
            for (Mob mob : mobList1) {
                return mob;
            }
        }
        return null;
    }

    public static Entity detectPlayerPickEntity(Player player, double distance, double range, Class<? extends Entity> clazz) {
        Level level = player.level();
        Vec3 TargetPos = player.pick(distance, 0, false).getLocation();
        Vec3 StartPos = player.pick(0.5, 0, false).getLocation();
        Vec3 PosVec = TargetPos.subtract(StartPos).normalize();
        double Distance = TargetPos.distanceTo(StartPos);
        for (double i = 0; i < Distance; i += 0.5) {
            List<? extends Entity> entities = level.getEntitiesOfClass(clazz, AABB.ofSize(StartPos.add(PosVec.scale(i)),
                    i > range ? range : 0.5, i > range ? range : 0.5, i > range ? range : 0.5));
            for (Entity entity : entities) {
                if (!entity.equals(player)) return entity;
            }
        }
        return null;
    }

    // ---- Vision/conical queries ----

    public static Set<Mob> getPlayerVisionConicalMobs(Player player, int maxDistance) {
        Set<Mob> mobSet = new HashSet<>();
        for (int i = 0; i < maxDistance; i++) {
            Vec3 pickPos = player.pick(i, 0, false).getLocation();
            int finalI = i;
            player.level().getEntitiesOfClass(Mob.class,
                            AABB.ofSize(pickPos, i * 2, i * 2, i * 2))
                    .stream().filter(mob -> mob.position().distanceTo(pickPos) < finalI)
                    .forEach(mobSet::add);
        }
        return mobSet;
    }

    // ---- Default target selection ----

    @Nullable
    public static Mob getDefaultTarget(Player player, double maxDistance) {
        Set<Mob> set = getPlayerRayMobList(player, 0.5, 1, maxDistance);
        if (!set.isEmpty()) {
            return set.stream().min(new Comparator<Mob>() {
                @Override
                public int compare(Mob o1, Mob o2) {
                    return (int) (o1.distanceTo(player) - o2.distanceTo(player));
                }
            }).orElse(null);
        }
        return null;
    }

    @Nullable
    public static Mob getDefaultTarget(Player player) {
        return getDefaultTarget(player, 32);
    }

    // ---- Player queries ----

    @Nullable
    public static ServerPlayer getPlayerByName(String name) {
        return Tick.server.getPlayerList().getPlayerByName(name);
    }

    public static List<ServerPlayer> getPlayers() {
        return Tick.server.getPlayerList().getPlayers();
    }

    // ---- Mob identification ----

    public static boolean isWraqMob(Mob mob) {
        if (mob == null) {
            return false;
        }
        boolean isAllay = mob instanceof Allay;
        boolean isBornInChaosMob = getBornInChaosMobType().contains(mob.getType());
        boolean isVanillaMob = mob instanceof Blaze || mob instanceof Shulker;
        return !isAllay && (mob.getDisplayName().getString().contains("Lv.")
                || mob.getDisplayName().getString().contains("木桩")
                || isBornInChaosMob || isVanillaMob);
    }

    public static Set<EntityType<?>> getBornInChaosMobType() {
        if (bornInChaosMobTypeSet.isEmpty()) {
            BornInChaosV1ModEntities.REGISTRY.getEntries().forEach(entityTypeRegistryObject -> {
                bornInChaosMobTypeSet.add(entityTypeRegistryObject.get());
            });
        }
        return bornInChaosMobTypeSet;
    }

    // ---- Player main attack target tracking ----

    public static void onPlayerMainAttack(Player player, Mob mob) {
        playerMainAttackTarget.put(player, mob);
    }

    @Nullable
    public static Mob getPlayerMainAttackTarget(Player player) {
        Mob mob = playerMainAttackTarget.getOrDefault(player, null);
        if (mob == null || !mob.isAlive()) {
            return null;
        }
        return mob;
    }
}
