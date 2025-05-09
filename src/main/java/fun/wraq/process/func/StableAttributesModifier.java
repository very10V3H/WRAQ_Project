package fun.wraq.process.func;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Tick;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public record StableAttributesModifier(String tag, double value, int stopTick) {

    public static Map<LivingEntity, List<StableAttributesModifier>> playerCooldownModifier = new HashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerMovementSpeedModifier = new HashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerMovementSpeedWithoutBattleModifier = new HashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerHealthRecoverModifier = new HashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerPercentHealthRecoverModifier = new HashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerCritRateModifier = new HashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerManaRecoverModifier = new HashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerCritDamageModifier = new HashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerDefencePenetrationModifier = new HashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerDefencePenetration0Modifier = new HashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerManaDamageModifier = new HashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerPercentManaDamageModifier = new HashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerAttackDamageModifier = new HashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerPercentAttackDamageModifier = new HashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerCommonDamageEnhance = new HashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerDefenceModifier = new HashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerDefenceDecreaseModifier = new HashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerPercentDefenceModifier = new HashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerManaDefenceModifier = new HashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerManaDefenceDecreaseModifier = new HashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerPercentManaDefenceModifier = new HashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerHealAmplifierModifier = new HashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerHealAmplifierReductionModifier = new HashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerMonsterControlDamageEffect = new HashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerManaPenetration0Modifier = new HashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerSlowdownEffectModifier = new HashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerToughnessModifier = new HashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerManaPenetrationModifier = new HashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerSwiftnessModifier = new HashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerExHarvestModifier = new HashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> playerExAttackSpeed = new HashMap<>();

    public static Map<LivingEntity, List<StableAttributesModifier>> mobDefenceModifier = new HashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> mobPercentDefenceModifier = new HashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> mobPercentManaDefenceModifier = new HashMap<>();
    public static Map<LivingEntity, List<StableAttributesModifier>> mobHealAmplifierModifier = new HashMap<>();

    public static List<StableAttributesModifier> getAttributeModifierList(LivingEntity entity, Map<LivingEntity, List<StableAttributesModifier>> modifierMap) {
        if (!modifierMap.containsKey(entity)) {
            modifierMap.put(entity, new ArrayList<>());
        }
        return modifierMap.get(entity);
    }

    public static void addAttributeModifier(LivingEntity entity, Map<LivingEntity, List<StableAttributesModifier>> modifierMap,
                                            StableAttributesModifier attributeModifier) {
        modifierMap.entrySet().removeIf(entry -> entry.getKey() == null || entry.getKey().isDeadOrDying());
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
