package fun.wraq.process.func;

import fun.wraq.common.fast.Tick;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public record PersistentRangeEffect(Level level, Vec3 center, double radius, Player player,
                                    PersistentRangeEffectOperation operation, int trigTick, int stopTick) {

    public static List<PersistentRangeEffect> effectList = new ArrayList<>();

    public static void addEffect(Player player, Vec3 targetPos, double radius,
                                 PersistentRangeEffectOperation operation, int trigTick, int lastTick) {
        effectList.add(new PersistentRangeEffect(player.level(), targetPos, radius, player,
                operation, trigTick, Tick.get() + lastTick));
    }

    public static void levelTick(Level level) {
        int tick = Tick.get();
        effectList.removeIf(effect -> tick > effect.stopTick());
        effectList.stream().filter(effect -> effect.level.equals(level) && tick % effect.trigTick() == 0)
                .forEach(effect -> {
                    effect.operation.operation(effect);
                });
    }
}
