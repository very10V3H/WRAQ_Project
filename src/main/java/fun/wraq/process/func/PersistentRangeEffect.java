package fun.wraq.process.func;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Tick;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class PersistentRangeEffect {

    public Level level;
    public Vec3 center;
    public double radius;
    public LivingEntity owner;
    public PersistentRangeEffectOperation operation;
    public int startTick;
    public int trigTick;
    public int stopTick;
    public PersistentRangeEffect(Level level, Vec3 center, double radius, LivingEntity livingEntity,
                                 PersistentRangeEffectOperation operation, int startTick, int trigTick, int stopTick) {
        this.level = level;
        this.center = center;
        this.radius = radius;
        this.owner = livingEntity;
        this.operation = operation;
        this.startTick = startTick;
        this.trigTick = trigTick;
        this.stopTick = stopTick;
    }

    public List<Mob> getRangeMob() {
        return Compute.getNearMob(level, center, radius);
    }

    public Set<Player> getRangePlayer() {
        return Compute.getNearPlayer(level, center, radius);
    }

    public static List<PersistentRangeEffect> effectList = new CopyOnWriteArrayList<>();

    public static void addEffect(LivingEntity livingEntity, Vec3 targetPos, double radius,
                                 PersistentRangeEffectOperation operation, int trigTick, int lastTick) {
        effectList.add(new PersistentRangeEffect(livingEntity.level(), targetPos, radius, livingEntity,
                operation, Tick.get(), trigTick, Tick.get() + lastTick));
    }

    public static void addEffect(Level level, Vec3 targetPos, double radius,
                                 PersistentRangeEffectOperation operation, int startTick, int trigTick, int lastTick) {
        effectList.add(new PersistentRangeEffect(level, targetPos, radius, null,
                operation, startTick, trigTick, Tick.get() + lastTick));
    }

    public static void levelTick(Level level) {
        int tick = Tick.get();
        effectList.removeIf(effect -> tick > effect.stopTick);
        effectList.stream().filter(effect -> effect.level.equals(level)
                        && tick % effect.trigTick == 0
                        && tick >= effect.startTick)
                .forEach(effect -> {
                    effect.operation.operation(effect);
                });
    }
}
