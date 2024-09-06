package com.very.wraq.process.func;

import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public record ChangedAttributesModifier(String tag, double value, int startTick, int stopTick, boolean attenuation) {

    public static Map<String, List<ChangedAttributesModifier>> movementSpeedUp = new HashMap<>();
    public static Map<String, List<ChangedAttributesModifier>> exAttackDamage = new HashMap<>();
    public static Map<String, List<ChangedAttributesModifier>> exManaDamage = new HashMap<>();

    public static List<ChangedAttributesModifier> getAttributeModifierList(Player player, Map<String, List<ChangedAttributesModifier>> modifierMap) {
        String name = player.getName().getString();
        if (!modifierMap.containsKey(name)) {
            modifierMap.put(name, new ArrayList<>());
        }
        return modifierMap.get(name);
    }

    public static void addAttributeModifier(Player player, Map<String, List<ChangedAttributesModifier>> modifierMap, String tag, double value, int lastTick, boolean attenuation) {
        int tick = player.getServer().getTickCount();
        addAttributeModifier(player, modifierMap, new ChangedAttributesModifier(tag, value, tick, tick + lastTick, attenuation));
    }

    public static void addAttributeModifier(Player player, Map<String, List<ChangedAttributesModifier>> modifierMap, ChangedAttributesModifier attributeModifier) {
        List<ChangedAttributesModifier> modifierList = getAttributeModifierList(player, modifierMap);
        List<ChangedAttributesModifier> removeList = new ArrayList<>();
        modifierList.forEach(modifier -> {
            if (modifier.tag.equals(attributeModifier.tag)) {
                removeList.add(modifier);
            }
        });
        modifierList.removeAll(removeList);
        modifierList.add(attributeModifier);
    }

    public static void removeAttributeModifierByTag(Player player, Map<String, List<ChangedAttributesModifier>> modifierMap, String tag) {
        List<ChangedAttributesModifier> modifierList = getAttributeModifierList(player, modifierMap);
        List<ChangedAttributesModifier> removeList = new ArrayList<>();
        modifierList.forEach(modifier -> {
            if (modifier.tag.equals(tag)) {
                removeList.add(modifier);
            }
        });
        modifierList.removeAll(removeList);
    }

    public static double getModifierValue(Player player, Map<String, List<ChangedAttributesModifier>> modifierMap) {
        String name = player.getName().getString();
        if (!modifierMap.containsKey(name)) return 0;
        int tick = player.getServer().getTickCount();
        List<ChangedAttributesModifier> modifiers = modifierMap.get(name);
        List<ChangedAttributesModifier> removeList = new ArrayList<>();
        AtomicReference<Double> value = new AtomicReference<>((double) 0);
        modifiers.forEach(modifier -> {
            if (tick < modifier.stopTick) {
                double rate = (double) (modifier.stopTick - tick) / (modifier.stopTick - modifier.startTick);
                if (!modifier.attenuation) rate = 1 - rate;
                value.set(value.get() + modifier.value() * rate);
            } else removeList.add(modifier);
        });
        modifiers.removeAll(removeList);
        return value.get();
    }
}
