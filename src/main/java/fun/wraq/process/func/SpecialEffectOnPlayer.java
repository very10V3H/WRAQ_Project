package fun.wraq.process.func;

import com.mojang.datafixers.util.Pair;
import fun.wraq.common.Compute;
import fun.wraq.common.registry.ModItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;

public class SpecialEffectOnPlayer {
    public static Map<String, Double> slowdownEffectMap = new HashMap<>();
    public static Map<String, Integer> slowdownStartTimeMap = new HashMap<>();
    public static Map<String, Integer> slowdownEndTimeMap = new HashMap<>();

    public static void slowdownEffectProvide(Player player, int lastTick, double fullEffect) {
        int tick = player.getServer().getTickCount();
        String name = player.getName().getString();
        slowdownEffectMap.put(name, fullEffect);
        slowdownStartTimeMap.put(name, tick);
        slowdownEndTimeMap.put(name, tick + lastTick);
        Compute.debuffTimeSend(player, ModItems.SlimeBall.get().getDefaultInstance(), lastTick, 0);
    }

    public static double slowdownEffectValue(Player player) {
        int tick = player.getServer().getTickCount();
        String name = player.getName().getString();
        if (slowdownEffectMap.containsKey(name) && slowdownEndTimeMap.containsKey(name)
                && slowdownStartTimeMap.containsKey(name) && slowdownEndTimeMap.get(name) >= tick) {
            int fullRange = slowdownEndTimeMap.get(name) - slowdownStartTimeMap.get(name);
            int currentRange = slowdownEndTimeMap.get(name) - tick;
            return 1 - slowdownEffectMap.get(name) * ((double) currentRange / fullRange);
        }
        return 1;
    }

    public static Map<String, Integer> vertigoTickMap = new HashMap<>();

    public static void addVertigoEffect(Player player, int tick) {
        String name = player.getName().getString();
        vertigoTickMap.put(name, player.getServer().getTickCount() + tick);
        Compute.debuffTimeSend(player, ModItems.MineSoul.get(), tick, 0);
        imprisonPosMap.put(name, player.position());
        imprisonRotMap.put(name, Pair.of(player.getXRot(), player.getYRot()));
    }

    public static boolean inVertigo(Player player) {
        String name = player.getName().getString();
        int tick = player.getServer().getTickCount();
        return vertigoTickMap.getOrDefault(name, 0) > tick;
    }

    public static Map<String, Integer> imprisonTickMap = new HashMap<>();
    public static Map<String, Vec3> imprisonPosMap = new HashMap<>();
    public static Map<String, Pair<Float, Float>> imprisonRotMap = new HashMap<>();

    public static void addImprisonEffect(Player player, int tick) {
        String name = player.getName().getString();
        imprisonTickMap.put(name, player.getServer().getTickCount() + tick);
        Compute.debuffTimeSend(player, ModItems.MineSoul.get(), tick, 0);
        imprisonPosMap.put(name, player.position());
        imprisonRotMap.put(name, Pair.of(player.getXRot(), player.getYRot()));
    }

    public static boolean inImprison(Player player) {
        String name = player.getName().getString();
        int tick = player.getServer().getTickCount();
        return inVertigo(player) || imprisonTickMap.getOrDefault(name, 0) > tick;
    }
}
