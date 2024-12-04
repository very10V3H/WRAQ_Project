package fun.wraq.process.func;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Tick;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicReference;

public record StableAttributesModifier(String tag, double value, int stopTick) {

    public static Map<LivingEntity, List<StableAttributesModifier>> playerCooldownModifier = new WeakHashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerMovementSpeedModifier = new WeakHashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerMovementSpeedWithoutBattleModifier = new WeakHashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerHealthRecoverModifier = new WeakHashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerCritRateModifier = new WeakHashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerManaRecoverModifier = new WeakHashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerCritDamageModifier = new WeakHashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerDefencePenetrationModifier = new WeakHashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerDefencePenetration0Modifier = new WeakHashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerManaDamageModifier = new WeakHashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerAttackDamageModifier = new WeakHashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerCommonDamageEnhance = new WeakHashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerDefenceModifier = new WeakHashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerDefenceDecreaseModifier = new WeakHashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerPercentDefenceModifier = new WeakHashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerManaDefenceModifier = new WeakHashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerManaDefenceDecreaseModifier = new WeakHashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerPercentManaDefenceModifier = new WeakHashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerHealAmplifierModifier = new WeakHashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerHealAmplifierReductionModifier = new WeakHashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerMonsterControlDamageEffect = new WeakHashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerManaPenetration0Modifier = new WeakHashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerSlowdownEffectModifier = new WeakHashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerToughnessModifier = new WeakHashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerManaPenetrationModifier = new WeakHashMap<>();


    public static Map<LivingEntity, List<StableAttributesModifier>> mobDefenceModifier = new WeakHashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> mobPercentDefenceModifier = new WeakHashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> mobPercentManaDefenceModifier = new WeakHashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> mobHealAmplifierModifier = new WeakHashMap<>();


    public static List<StableAttributesModifier> getAttributeModifierList(LivingEntity entity, Map<LivingEntity, List<StableAttributesModifier>> modifierMap) {
        if (!modifierMap.containsKey(entity)) {
            modifierMap.put(entity, new ArrayList<>());
        }
        return modifierMap.get(entity);
    }

    public static void addAttributeModifier(LivingEntity entity, Map<LivingEntity, List<StableAttributesModifier>> modifierMap,
                                            StableAttributesModifier attributeModifier) {
        List<StableAttributesModifier> modifierList = getAttributeModifierList(entity, modifierMap);
        List<StableAttributesModifier> removeList = new ArrayList<>();
        modifierList.forEach(modifier -> {
            if (modifier.tag.equals(attributeModifier.tag)) {
                removeList.add(modifier);
            }
        });
        modifierList.removeAll(removeList);
        modifierList.add(attributeModifier);
    }

    public static void addM(LivingEntity entity, Map<LivingEntity, List<StableAttributesModifier>> modifierMap,
                            String tag, double value, int stopTick) {
        addAttributeModifier(entity, modifierMap, new StableAttributesModifier(tag, value, stopTick));
    }

    public static void addM(LivingEntity entity, Map<LivingEntity, List<StableAttributesModifier>> modifierMap,
                            String tag, double value, int stopTick, Item icon) {
        addAttributeModifier(entity, modifierMap, new StableAttributesModifier(tag, value, stopTick));
        if (entity instanceof Player player) {
            Compute.sendEffectLastTime(player, icon, stopTick - Tick.get());
        }
        if (entity instanceof Mob mob) {
            Compute.sendMobEffectHudToNearPlayer(mob, icon, tag, stopTick - Tick.get(), 0, false);
        }
    }

    public static void addM(LivingEntity entity, Map<LivingEntity, List<StableAttributesModifier>> modifierMap,
                            String tag, double value, int stopTick, String url) {
        addAttributeModifier(entity, modifierMap, new StableAttributesModifier(tag, value, stopTick));
        if (entity instanceof Player player) {
            Compute.sendEffectLastTime(player, url, stopTick - Tick.get(), 0, false);
        }
        if (entity instanceof Mob mob) {
            Compute.sendMobEffectHudToNearPlayer(mob, url, tag, stopTick - Tick.get(), 0, false);
        }
    }

    public static void removeAttributeModifierByTag(LivingEntity entity, Map<LivingEntity, List<StableAttributesModifier>> modifierMap, String tag) {
        List<StableAttributesModifier> modifierList = getAttributeModifierList(entity, modifierMap);
        List<StableAttributesModifier> removeList = new ArrayList<>();
        modifierList.forEach(modifier -> {
            if (modifier.tag.equals(tag)) {
                removeList.add(modifier);
            }
        });
        modifierList.removeAll(removeList);
    }

    public static double getModifierValue(LivingEntity entity, Map<LivingEntity, List<StableAttributesModifier>> modifierMap) {
        if (!modifierMap.containsKey(entity)) return 0;
        int tick = Tick.get();
        List<StableAttributesModifier> modifiers = modifierMap.get(entity);
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
