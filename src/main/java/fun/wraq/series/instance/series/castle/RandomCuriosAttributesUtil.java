package fun.wraq.series.instance.series.castle;

import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class RandomCuriosAttributesUtil {

    public static Map<String, Double> attributeValueMap = new HashMap<>() {{
        put(StringUtils.RandomCuriosAttribute.attackDamage, 200d);
        put(StringUtils.RandomCuriosAttribute.manaDamage, 400d);
        put(StringUtils.RandomCuriosAttribute.maxHealth, 800d);
        put(StringUtils.RandomCuriosAttribute.defence, 36d);
        put(StringUtils.RandomCuriosAttribute.manaDefence, 48d);
        put(StringUtils.RandomCuriosAttribute.defencePenetration0, 20d);
        put(StringUtils.RandomCuriosAttribute.manaPenetration0, 20d);
        put(StringUtils.RandomCuriosAttribute.coolDown, 0.15);
        put(StringUtils.RandomCuriosAttribute.manaRecover, 15d);
        put(StringUtils.RandomCuriosAttribute.maxMana, 50d);
        put(StringUtils.RandomCuriosAttribute.swiftnessUp, 0.8);
        put(StringUtils.RandomCuriosAttribute.critDamage, 0.06);
        put(StringUtils.RandomCuriosAttribute.expUp, 0.3);
        put(StringUtils.RandomCuriosAttribute.critRate, 0.04);
        put(StringUtils.RandomCuriosAttribute.healthSteal, 0.05);
        put(StringUtils.RandomCuriosAttribute.defencePenetration, 0.05);
        put(StringUtils.RandomCuriosAttribute.movementSpeed, 0.25);
        put(StringUtils.RandomCuriosAttribute.commonMovementSpeed, 0.1);
        put(StringUtils.RandomCuriosAttribute.healthRecover, 50d);
        put(StringUtils.RandomCuriosAttribute.percentHealthRecover, 0.05d);
        put(StringUtils.RandomCuriosAttribute.healEffectUp, 0.1);
        put(StringUtils.RandomCuriosAttribute.manaPenetration, 0.05);
        put(StringUtils.RandomCuriosAttribute.manaHealthSteal, 0.05);

        // 以下是特殊类型的属性，为确保其不会在寻常全属性随机饰品中出现，需要在特殊属性表中添加说明
        put(StringUtils.RandomCuriosAttribute.finalDamageEnhance, 0.05);
        put(StringUtils.RandomCuriosAttribute.percentAttackDamage, 0.05);
        put(StringUtils.RandomCuriosAttribute.percentManaDamageEnhance, 0.05);
        put(StringUtils.RandomCuriosAttribute.percentDefenceEnhance, 0.05);
        put(StringUtils.RandomCuriosAttribute.percentManaDefenceEnhance, 0.05);
    }};

    public static List<String> specialAttributes = List.of(
            StringUtils.RandomCuriosAttribute.finalDamageEnhance,
            StringUtils.RandomCuriosAttribute.percentAttackDamage,
            StringUtils.RandomCuriosAttribute.percentManaDamageEnhance,
            StringUtils.RandomCuriosAttribute.percentDefenceEnhance,
            StringUtils.RandomCuriosAttribute.percentManaDefenceEnhance
    );

    public static void randomAttributeProvide(ItemStack itemStack, int attributeNum, double rate, boolean distinct) {
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        List<String> attributeList = new ArrayList<>(attributeValueMap.keySet());
        attributeList.removeAll(specialAttributes);
        provideRandomAttributeFromList(attributeNum, rate, distinct, data, attributeList);
    }

    public static void randomAttributeProvide(ItemStack itemStack, int attributeNum, double rate) {
        randomAttributeProvide(itemStack, attributeNum, rate, false);
    }

    public static void randomAttackAttributeProvide(ItemStack itemStack, int attributeNum, double rate, boolean distinct) {
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        List<String> attributeList = List.of(
                StringUtils.RandomCuriosAttribute.attackDamage,
                StringUtils.RandomCuriosAttribute.manaDamage,
                StringUtils.RandomCuriosAttribute.defencePenetration0,
                StringUtils.RandomCuriosAttribute.manaPenetration0,
                StringUtils.RandomCuriosAttribute.critRate,
                StringUtils.RandomCuriosAttribute.critDamage,
                StringUtils.RandomCuriosAttribute.healthSteal,
                StringUtils.RandomCuriosAttribute.manaHealthSteal,
                StringUtils.RandomCuriosAttribute.defencePenetration,
                StringUtils.RandomCuriosAttribute.manaPenetration
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
                    StringUtils.RandomCuriosAttribute.maxHealth,
                    StringUtils.RandomCuriosAttribute.defence,
                    StringUtils.RandomCuriosAttribute.manaDefence,
                    StringUtils.RandomCuriosAttribute.healthRecover,
                    StringUtils.RandomCuriosAttribute.healEffectUp
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
                    StringUtils.RandomCuriosAttribute.coolDown,
                    StringUtils.RandomCuriosAttribute.manaRecover,
                    StringUtils.RandomCuriosAttribute.maxMana,
                    StringUtils.RandomCuriosAttribute.swiftnessUp,
                    StringUtils.RandomCuriosAttribute.expUp,
                    StringUtils.RandomCuriosAttribute.movementSpeed,
                    StringUtils.RandomCuriosAttribute.commonMovementSpeed
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
