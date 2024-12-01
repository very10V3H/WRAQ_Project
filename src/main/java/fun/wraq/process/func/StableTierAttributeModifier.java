package fun.wraq.process.func;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Tick;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.*;

public class StableTierAttributeModifier {

    public String tag;
    public double eachTierValue;
    public int stopTick;
    public int tier;
    public int maxTier;
    public StableTierAttributeModifier(String tag, double eachTierValue, int stopTick, int tier, int maxTier) {
        this.tag = tag;
        this.eachTierValue = eachTierValue;
        this.stopTick = stopTick;
        this.tier = tier;
        this.maxTier = maxTier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    public static Map<LivingEntity, List<StableTierAttributeModifier>> defence = new WeakHashMap<>();
    public static Map<LivingEntity, List<StableTierAttributeModifier>> manaDefence = new WeakHashMap<>();
    public static Map<LivingEntity, List<StableTierAttributeModifier>> percentDefence = new WeakHashMap<>();
    public static Map<LivingEntity, List<StableTierAttributeModifier>> percentManaDefence = new WeakHashMap<>();
    public static Map<LivingEntity, List<StableTierAttributeModifier>> onlyDisplay = new WeakHashMap<>();
    public static Map<LivingEntity, List<StableTierAttributeModifier>> baseArrowDamageEnhanceRate = new WeakHashMap<>();
    public static Map<LivingEntity, List<StableTierAttributeModifier>> playerCommonDamageEnhance = new WeakHashMap<>();
    public static Map<LivingEntity, List<StableTierAttributeModifier>> playerWithstandDamageReduce = new WeakHashMap<>();

    public static List<StableTierAttributeModifier> getAttributeModifierList(LivingEntity entity, Map<LivingEntity, List<StableTierAttributeModifier>> modifierMap) {
        if (!modifierMap.containsKey(entity)) {
            modifierMap.put(entity, new ArrayList<>());
        }
        return modifierMap.get(entity);
    }

    public static void setAttributeModifierTier(Player player,
                                                Map<LivingEntity, List<StableTierAttributeModifier>> modifierMap,
                                                String tag, int tier) {
        getAttributeModifierList(player, modifierMap)
                .stream().filter(modifier -> modifier.tag.equals(tag))
                .forEach(modifier -> {
                    modifier.setTier(tier);
        });
        getAttributeModifierList(player, modifierMap).removeIf(modifier -> modifier.tier == 0);
    }

    public static void relativeSetAttributeModifierTier(Player player,
                                                Map<LivingEntity, List<StableTierAttributeModifier>> modifierMap,
                                                String tag, int relativeTier, String url) {
        getAttributeModifierList(player, modifierMap)
                .stream().filter(modifier -> modifier.tag.equals(tag))
                .forEach(modifier -> {
                    modifier.setTier(modifier.tier + relativeTier);
                    if (!url.isEmpty()) {
                        Compute.sendEffectLastTime(player, url, modifier.stopTick - Tick.get(),
                                modifier.tier + relativeTier, false);
                    }
                });
        getAttributeModifierList(player, modifierMap).removeIf(modifier -> modifier.tier == 0);
    }

    public static void addAttributeModifier(LivingEntity entity, Map<LivingEntity, List<StableTierAttributeModifier>> modifierMap,
                                            StableTierAttributeModifier modifier, String url) {
        List<StableTierAttributeModifier> modifierList = getAttributeModifierList(entity, modifierMap);
        List<StableTierAttributeModifier> removeList = new ArrayList<>();
        int highestTier = 0;
        for (StableTierAttributeModifier stableTierAttributeModifier : modifierList) {
            if (stableTierAttributeModifier.tag.equals(modifier.tag)) {
                removeList.add(stableTierAttributeModifier);
                if (stableTierAttributeModifier.tier > highestTier
                        && stableTierAttributeModifier.stopTick > Tick.get()) {
                    highestTier = stableTierAttributeModifier.tier;
                }
            }
        }
        modifierList.removeAll(removeList);
        int finalTier = Math.min(highestTier + 1, modifier.maxTier);
        modifierList.add(new StableTierAttributeModifier(modifier.tag, modifier.eachTierValue,
                modifier.stopTick, finalTier, modifier.maxTier));
        if (!url.isEmpty()) {
            if (entity instanceof Mob mob) {
                Compute.sendMobEffectHudToNearPlayer(mob, url, modifier.tag, modifier.stopTick - Tick.get(), finalTier, false);
            }
            if (entity instanceof Player player) {
                Compute.sendEffectLastTime(player, url, modifier.stopTick - Tick.get(), finalTier, false);
            }
        }
    }

    public static int getAttributeModifierTier(LivingEntity entity,
                                               Map<LivingEntity, List<StableTierAttributeModifier>> modifierMap,
                                               String tag) {
        return getAttributeModifierList(entity, modifierMap)
                .stream().filter(modifier -> modifier.tag.equals(tag))
                .findAny()
                .map(tierAttributeModifier -> tierAttributeModifier.tier)
                .orElse(0);
    }

    public static void removeAttributeModifier(LivingEntity entity, Map<LivingEntity, List<StableTierAttributeModifier>> modifierMap,
                                               String tag, Item icon) {
        getAttributeModifierList(entity, modifierMap).removeIf(modifier -> modifier.tag.equals(tag));
        if (entity instanceof Mob mob) {
            Compute.removeMobEffectHudToNearPlayer(mob, icon, tag);
        }
    }

    public static void addM(LivingEntity entity, Map<LivingEntity, List<StableTierAttributeModifier>> modifierMap,
                            String tag, double eachTierValue, int stopTick, int maxTier, Item icon) {
        addAttributeModifier(entity, modifierMap, new StableTierAttributeModifier(tag, eachTierValue, stopTick, 1, maxTier), "item/" + icon);
    }

    public static void addM(LivingEntity entity, Map<LivingEntity, List<StableTierAttributeModifier>> modifierMap,
                            String tag, double eachTierValue, int stopTick, int maxTier, String url) {
        addAttributeModifier(entity, modifierMap, new StableTierAttributeModifier(tag, eachTierValue, stopTick, 1, maxTier), url);
    }

    public static double getModifierValue(LivingEntity entity, Map<LivingEntity, List<StableTierAttributeModifier>> modifierMap) {
        if (!modifierMap.containsKey(entity)) return 0;
        List<StableTierAttributeModifier> modifiers = modifierMap.get(entity);
        List<StableTierAttributeModifier> removeList = new ArrayList<>();
        double value = 0;
        for (StableTierAttributeModifier modifier : modifiers) {
            if (Tick.get() < modifier.stopTick) {
                value += modifier.eachTierValue * modifier.tier;
            } else removeList.add(modifier);
        }
        modifiers.removeAll(removeList);
        return value;
    }
}
