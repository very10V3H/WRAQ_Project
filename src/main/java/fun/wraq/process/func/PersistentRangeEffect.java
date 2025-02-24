package fun.wraq.process.func;

import fun.wraq.common.fast.Tick;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public record PersistentRangeEffect(Level level, Vec3 center, double radius, LivingEntity livingEntity,
                                    PersistentRangeEffectOperation operation, int startTick, int trigTick, int stopTick) {

    public static List<PersistentRangeEffect> effectList = new ArrayList<>();

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
        effectList.removeIf(effect -> tick > effect.stopTick());
        effectList.stream().filter(effect -> effect.level.equals(level)
                        && tick % effect.trigTick() == 0
                        && tick >= effect.startTick())
                .forEach(effect -> {
                    effect.operation.operation(effect);
                });
    }
}
