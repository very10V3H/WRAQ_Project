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
        put(StringUtils.CuriosAttribute.defence, 36d);
        put(StringUtils.CuriosAttribute.manaDefence, 48d);
        put(StringUtils.CuriosAttribute.defencePenetration0, 20d);
        put(StringUtils.CuriosAttribute.manaPenetration0, 20d);
        put(StringUtils.CuriosAttribute.coolDown, 0.15);
        put(StringUtils.CuriosAttribute.manaRecover, 15d);
        put(StringUtils.CuriosAttribute.maxMana, 50d);
        put(StringUtils.CuriosAttribute.swiftnessUp, 0.8);
        put(StringUtils.CuriosAttribute.critDamage, 0.06);
        put(StringUtils.CuriosAttribute.expUp, 0.3);
        put(StringUtils.CuriosAttribute.critRate, 0.04);
        put(StringUtils.CuriosAttribute.healthSteal, 0.05);
        put(StringUtils.CuriosAttribute.defencePenetration, 0.05);
        put(StringUtils.CuriosAttribute.movementSpeed, 0.25);
        put(StringUtils.CuriosAttribute.commonMovementSpeed, 0.1);
        put(StringUtils.CuriosAttribute.healthRecover, 50d);
        put(StringUtils.CuriosAttribute.percentHealthRecover, 0.05d);
        put(StringUtils.CuriosAttribute.healEffectUp, 0.1);
        put(StringUtils.CuriosAttribute.manaPenetration, 0.05);
        put(StringUtils.CuriosAttribute.manaHealthSteal, 0.05);
        put(StringUtils.CuriosAttribute.finalDamageEnhance, 0.05);
    }};

    public static void randomAttributeProvide(ItemStack itemStack, int attributeNum, double rate, boolean distinct) {
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        List<String> attributeList = new ArrayList<>(attributeValueMap.keySet());
        attributeList.remove(StringUtils.CuriosAttribute.finalDamageEnhance);
        provideRandomAttributeFromList(attributeNum, rate, distinct, data, attributeList);
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
        provideRandomAttributeFromList(attributeNum, rate, distinct, data, attributeList);
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
        provideRandomAttributeFromList(attributeNum, rate, distinct, data, attributeList);
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
        provideRandomAttributeFromList(attributeNum, rate, distinct, data, attributeList);
    }

    public static void provideRandomAttributeFromList(int attributeNum, double rate, boolean distinct,
                                                      CompoundTag data, List<String> attributeList) {
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

    public static void provideRandomAttributeFromList(int attributeNum, double rate, boolean distinct,
                                                      ItemStack itemStack, List<String> attributeList) {
        provideRandomAttributeFromList(attributeNum, rate, distinct,
                itemStack.getOrCreateTagElement(Utils.MOD_ID), attributeList);
    }

    public static void randomFunctionAttributeProvide(ItemStack itemStack, int attributeNum, double rate) {
        randomFunctionAttributeProvide(itemStack, attributeNum, rate, false);
    }

    public static void provideSingleAttribute(ItemStack itemStack, String attributeName,
                                              double rate, double origin, double bound) {
        Random random = new Random();
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        data.putDouble(attributeName, random.nextDouble(origin, bound) * rate);
    }

}
