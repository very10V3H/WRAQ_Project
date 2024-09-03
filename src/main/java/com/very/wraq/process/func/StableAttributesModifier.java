package com.very.wraq.process.func;

import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public record StableAttributesModifier(String tag, double value, int stopTick) {

    public static Map<String, List<StableAttributesModifier>> playerCooldownModifier = new HashMap<>();
    public static Map<String, List<StableAttributesModifier>> playerMovementSpeedModifier = new HashMap<>();
    public static Map<String, List<StableAttributesModifier>> playerHealthRecoverModifier = new HashMap<>();
    public static Map<String, List<StableAttributesModifier>> playerCritRateModifier = new HashMap<>();
    public static Map<String, List<StableAttributesModifier>> playerManaRecoverModifier = new HashMap<>();
    public static Map<String, List<StableAttributesModifier>> playerCritDamageModifier = new HashMap<>();
    public static Map<String, List<StableAttributesModifier>> playerDefencePenetrationModifier = new HashMap<>();
    public static Map<String, List<StableAttributesModifier>> playerDefencePenetration0Modifier = new HashMap<>();
    public static Map<String, List<StableAttributesModifier>> playerManaDamageModifier = new HashMap<>();
    public static Map<String, List<StableAttributesModifier>> playerAttackDamageModifier = new HashMap<>();

    public static List<StableAttributesModifier> getAttributeModifierList(Player player, Map<String, List<StableAttributesModifier>> modifierMap) {
        String name = player.getName().getString();
        if (!modifierMap.containsKey(name)) {
            modifierMap.put(name, new ArrayList<>());
        }
        return modifierMap.get(name);
    }

    public static void addAttributeModifier(Player player, Map<String, List<StableAttributesModifier>> modifierMap, StableAttributesModifier attributeModifier) {
        List<StableAttributesModifier> modifierList = getAttributeModifierList(player, modifierMap);
        List<StableAttributesModifier> removeList = new ArrayList<>();
        modifierList.forEach(modifier -> {
            if (modifier.tag.equals(attributeModifier.tag)) {
                removeList.add(modifier);
            }
        });
        modifierList.removeAll(removeList);
        modifierList.add(attributeModifier);
    }

    public static void removeAttributeModifierByTag(Player player, Map<String, List<StableAttributesModifier>> modifierMap, String tag) {
        List<StableAttributesModifier> modifierList = getAttributeModifierList(player, modifierMap);
        List<StableAttributesModifier> removeList = new ArrayList<>();
        modifierList.forEach(modifier -> {
            if (modifier.tag.equals(tag)) {
                removeList.add(modifier);
            }
        });
        modifierList.removeAll(removeList);
    }

    public static double getModifierValue(Player player, Map<String, List<StableAttributesModifier>> modifierMap) {
        String name = player.getName().getString();
        if (!modifierMap.containsKey(name)) return 0;
        int tick = player.getServer().getTickCount();
        List<StableAttributesModifier> modifiers = modifierMap.get(name);
        List<StableAttributesModifier> removeList = new ArrayList<>();
        AtomicReference<Double> value = new AtomicReference<>((double) 0);
        modifiers.forEach(modifier -> {
            if (tick < modifier.stopTick) {
                value.set(value.get() + modifier.value());
            } else removeList.add(modifier);
        });
        modifiers.removeAll(removeList);
        return value.get();
    }

}
