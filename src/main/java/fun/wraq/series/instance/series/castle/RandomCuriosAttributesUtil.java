package fun.wraq.series.instance.series.castle;

import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class RandomCuriosAttributesUtil {

    public static Map<String, Double> attributeValueMap = new HashMap<>() {{
        put(StringUtils.CuriosAttribute.attackDamage, 200d);
        put(StringUtils.CuriosAttribute.manaDamage, 400d);
        put(StringUtils.CuriosAttribute.maxHealth, 800d);
        put(StringUtils.CuriosAttribute.defence, 8d);
        put(StringUtils.CuriosAttribute.manaDefence, 8d);
        put(StringUtils.CuriosAttribute.defencePenetration0, 4d);
        put(StringUtils.CuriosAttribute.manaPenetration0, 4d);
        put(StringUtils.CuriosAttribute.coolDown, 0.15);
        put(StringUtils.CuriosAttribute.manaRecover, 15d);
        put(StringUtils.CuriosAttribute.maxMana, 50d);
        put(StringUtils.CuriosAttribute.swiftnessUp, 1.5);
        put(StringUtils.CuriosAttribute.critDamage, 0.2);
        put(StringUtils.CuriosAttribute.expUp, 0.5);
        put(StringUtils.CuriosAttribute.critRate, 0.1);
        put(StringUtils.CuriosAttribute.healthSteal, 0.1);
        put(StringUtils.CuriosAttribute.defencePenetration, 0.15);
        put(StringUtils.CuriosAttribute.movementSpeed, 0.5);
        put(StringUtils.CuriosAttribute.commonMovementSpeed, 0.25);
        put(StringUtils.CuriosAttribute.healthRecover, 50d);
        put(StringUtils.CuriosAttribute.percentHealthRecover, 0.05d);
        put(StringUtils.CuriosAttribute.healEffectUp, 0.5);
        put(StringUtils.CuriosAttribute.manaPenetration, 0.15);
        put(StringUtils.CuriosAttribute.manaHealthSteal, 0.1);
    }};

    public static void randomAttributeProvide(ItemStack itemStack, int attributeNum, double rate, boolean distinct) {
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        List<String> attributeList = new ArrayList<>(attributeValueMap.keySet());
        Set<Integer> set = new HashSet<>();
        Random random = new Random();
        for (int i = 0; i < attributeNum; i++) {
            int randomIndex = random.nextInt(attributeList.size());
            if (distinct) {
                if (attributeNum > attributeList.size()) {
                    return;
                }
                while (set.contains(randomIndex)) {
                    randomIndex = random.nextInt(attributeList.size());
                }
                set.add(randomIndex);
            }
            String attribute = attributeList.get(randomIndex);
            data.putDouble(attribute, data.getDouble(attribute) + random.nextDouble(0.25, 1) * rate);
        }
    }

    public static void randomAttributeProvide(ItemStack itemStack, int attributeNum, double rate) {
        randomAttributeProvide(itemStack, attributeNum, rate, false);
    }

    public static void randomAttackAttributeProvide(ItemStack itemStack, int attributeNum, double rate, boolean distinct) {
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        List<String> attributeList = List.of(
                StringUtils.CuriosAttribute.attackDamage,
                StringUtils.CuriosAttribute.manaDamage,
                StringUtils.CuriosAttribute.defencePenetration0,
                StringUtils.CuriosAttribute.manaPenetration0,
                StringUtils.CuriosAttribute.critRate,
                StringUtils.CuriosAttribute.critDamage,
                StringUtils.CuriosAttribute.healthSteal,
                StringUtils.CuriosAttribute.manaHealthSteal,
                StringUtils.CuriosAttribute.defencePenetration,
                StringUtils.CuriosAttribute.manaPenetration
        );
        Random random = new Random();
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < attributeNum; i++) {
            int randomIndex = random.nextInt(attributeList.size());
            if (distinct) {
                if (attributeNum > attributeList.size()) {
                    return;
                }
                while (set.contains(randomIndex)) {
                    randomIndex = random.nextInt(attributeList.size());
                }
                set.add(randomIndex);
            }
            String attribute = attributeList.get(randomIndex);
            data.putDouble(attribute, data.getDouble(attribute) + random.nextDouble(0.25, 1) * rate);
        }
    }

    public static void randomAttackAttributeProvide(ItemStack itemStack, int attributeNum, double rate) {
        randomAttackAttributeProvide(itemStack, attributeNum, rate, false);
    }

    public static void randomDefenceAttributeProvide(ItemStack itemStack, int attributeNum, double rate, boolean distinct) {
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        List<String> attributeList = new ArrayList<>() {{
            String[] strings = {
                    StringUtils.CuriosAttribute.maxHealth,
                    StringUtils.CuriosAttribute.defence,
                    StringUtils.CuriosAttribute.manaDefence,
                    StringUtils.CuriosAttribute.healthRecover,
                    StringUtils.CuriosAttribute.healEffectUp
            };
            addAll(List.of(strings));
        }};
        Random random = new Random();
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < attributeNum; i++) {
            int randomIndex = random.nextInt(attributeList.size());
            if (distinct) {
                if (attributeNum > attributeList.size()) {
                    return;
                }
                while (set.contains(randomIndex)) {
                    randomIndex = random.nextInt(attributeList.size());
                }
                set.add(randomIndex);
            }
            String attribute = attributeList.get(randomIndex);
            data.putDouble(attribute, data.getDouble(attribute) + random.nextDouble(0.25, 1) * rate);
        }
    }

    public static void randomDefenceAttributeProvide(ItemStack itemStack, int attributeNum, double rate) {
        randomDefenceAttributeProvide(itemStack, attributeNum, rate, false);
    }

    public static void randomFunctionAttributeProvide(ItemStack itemStack, int attributeNum, double rate, boolean distinct) {
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        List<String> attributeList = new ArrayList<>() {{
            String[] strings = {
                    StringUtils.CuriosAttribute.coolDown,
                    StringUtils.CuriosAttribute.manaRecover,
                    StringUtils.CuriosAttribute.maxMana,
                    StringUtils.CuriosAttribute.swiftnessUp,
                    StringUtils.CuriosAttribute.expUp,
                    StringUtils.CuriosAttribute.movementSpeed,
                    StringUtils.CuriosAttribute.commonMovementSpeed
            };
            addAll(List.of(strings));
        }};
        Random random = new Random();
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < attributeNum; i++) {
            int randomIndex = random.nextInt(attributeList.size());
            if (distinct) {
                if (attributeNum > attributeList.size()) {
                    return;
                }
                while (set.contains(randomIndex)) {
                    randomIndex = random.nextInt(attributeList.size());
                }
                set.add(randomIndex);
            }
            String attribute = attributeList.get(randomIndex);
            data.putDouble(attribute, data.getDouble(attribute) + random.nextDouble(0.25, 1) * rate);
        }
    }

    public static void randomFunctionAttributeProvide(ItemStack itemStack, int attributeNum, double rate) {
        randomFunctionAttributeProvide(itemStack, attributeNum, rate, false);
    }
}
