package com.very.wraq.common.attributeValues;

import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.world.entity.player.Player;

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
        vertigoTickMap.put(player.getName().getString(), player.getServer().getTickCount() + tick);
        Compute.debuffTimeSend(player, ModItems.MineSoul.get(), tick, 0);
    }

    public static boolean inVertigo(Player player) {
        String name = player.getName().getString();
        int tick = player.getServer().getTickCount();
        return vertigoTickMap.getOrDefault(name, 0) > tick;
    }

    public static Map<String, Integer> imprisonTickMap = new HashMap<>();

    public static void addImprisonEffect(Player player, int tick) {
        imprisonTickMap.put(player.getName().getString(), player.getServer().getTickCount() + tick);
        Compute.debuffTimeSend(player, ModItems.MineSoul.get(), tick, 0);
    }

    public static boolean inImprison(Player player) {
        String name = player.getName().getString();
        int tick = player.getServer().getTickCount();
        return inVertigo(player) || imprisonTickMap.getOrDefault(name, 0) > tick;
    }
}
