package fun.wraq.process.func;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Tick;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicReference;

public record StableAttributesModifier(String tag, double value, int stopTick) {

    public static Map<Player, List<StableAttributesModifier>> playerCooldownModifier = new WeakHashMap<>();
    public static Map<Player, List<StableAttributesModifier>> playerMovementSpeedModifier = new WeakHashMap<>();
    public static Map<Player, List<StableAttributesModifier>> playerHealthRecoverModifier = new WeakHashMap<>();
    public static Map<Player, List<StableAttributesModifier>> playerCritRateModifier = new WeakHashMap<>();
    public static Map<Player, List<StableAttributesModifier>> playerManaRecoverModifier = new WeakHashMap<>();
    public static Map<Player, List<StableAttributesModifier>> playerCritDamageModifier = new WeakHashMap<>();
    public static Map<Player, List<StableAttributesModifier>> playerDefencePenetrationModifier = new WeakHashMap<>();
    public static Map<Player, List<StableAttributesModifier>> playerDefencePenetration0Modifier = new WeakHashMap<>();
    public static Map<Player, List<StableAttributesModifier>> playerManaDamageModifier = new WeakHashMap<>();
    public static Map<Player, List<StableAttributesModifier>> playerAttackDamageModifier = new WeakHashMap<>();
    public static Map<Player, List<StableAttributesModifier>> playerCommonDamageEnhance = new WeakHashMap<>();

    public static List<StableAttributesModifier> getAttributeModifierList(Player player, Map<Player, List<StableAttributesModifier>> modifierMap) {
        if (!modifierMap.containsKey(player)) {
            modifierMap.put(player, new ArrayList<>());
        }
        return modifierMap.get(player);
    }

    public static void addAttributeModifier(Player player, Map<Player, List<StableAttributesModifier>> modifierMap,
                                            StableAttributesModifier attributeModifier) {
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

    public static void addM(Player player, Map<Player, List<StableAttributesModifier>> modifierMap,
                            String tag, double value, int stopTick) {
        addAttributeModifier(player, modifierMap, new StableAttributesModifier(tag, value, stopTick));
    }

    public static void addM(Player player, Map<Player, List<StableAttributesModifier>> modifierMap,
                            String tag, double value, int stopTick, Item icon) {
        addAttributeModifier(player, modifierMap, new StableAttributesModifier(tag, value, stopTick));
        Compute.sendEffectLastTime(player, icon, stopTick - Tick.get());
    }

    public static void removeAttributeModifierByTag(Player player, Map<Player, List<StableAttributesModifier>> modifierMap, String tag) {
        List<StableAttributesModifier> modifierList = getAttributeModifierList(player, modifierMap);
        List<StableAttributesModifier> removeList = new ArrayList<>();
        modifierList.forEach(modifier -> {
            if (modifier.tag.equals(tag)) {
                removeList.add(modifier);
            }
        });
        modifierList.removeAll(removeList);
    }

    public static double getModifierValue(Player player, Map<Player, List<StableAttributesModifier>> modifierMap) {
        if (!modifierMap.containsKey(player)) return 0;
        int tick = player.getServer().getTickCount();
        List<StableAttributesModifier> modifiers = modifierMap.get(player);
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
