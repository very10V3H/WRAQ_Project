package fun.wraq.series.instance.series.castle;

import fun.wraq.common.util.StringUtils;
import fun.wraq.common.util.Utils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class RandomCuriosAttributesUtil {

    public static Map<String, Double> attributeValueMap = new HashMap<>() {{
        put(StringUtils.RandomAttributes.attackDamage, 200d);
        put(StringUtils.RandomAttributes.manaDamage, 400d);
        put(StringUtils.RandomAttributes.maxHealth, 800d);
        put(StringUtils.RandomAttributes.defence, 36d);
        put(StringUtils.RandomAttributes.manaDefence, 48d);
        put(StringUtils.RandomAttributes.defencePenetration0, 20d);
        put(StringUtils.RandomAttributes.manaPenetration0, 20d);
        put(StringUtils.RandomAttributes.coolDown, 0.15);
        put(StringUtils.RandomAttributes.manaRecover, 15d);
        put(StringUtils.RandomAttributes.maxMana, 50d);
        put(StringUtils.RandomAttributes.swiftnessUp, 0.8);
        put(StringUtils.RandomAttributes.critDamage, 0.06);
        put(StringUtils.RandomAttributes.expUp, 0.3);
        put(StringUtils.RandomAttributes.critRate, 0.04);
        put(StringUtils.RandomAttributes.healthSteal, 0.05);
        put(StringUtils.RandomAttributes.defencePenetration, 0.05);
        put(StringUtils.RandomAttributes.movementSpeed, 0.25);
        put(StringUtils.RandomAttributes.commonMovementSpeed, 0.1);
        put(StringUtils.RandomAttributes.healthRecover, 50d);
        put(StringUtils.RandomAttributes.percentHealthRecover, 0.008d);
        put(StringUtils.RandomAttributes.healEffectUp, 0.1);
        put(StringUtils.RandomAttributes.manaPenetration, 0.05);
        put(StringUtils.RandomAttributes.manaHealthSteal, 0.05);

        // 以下是特殊类型的属性，为确保其不会在寻常全属性随机饰品中出现，需要在特殊属性表中添加说明
        put(StringUtils.RandomAttributes.finalDamageEnhance, 0.05);
        put(StringUtils.RandomAttributes.percentAttackDamage, 0.05);
        put(StringUtils.RandomAttributes.percentManaDamageEnhance, 0.05);
        put(StringUtils.RandomAttributes.percentDefenceEnhance, 0.05);
        put(StringUtils.RandomAttributes.percentManaDefenceEnhance, 0.05);
        put(StringUtils.RandomAttributes.attackSpeedEnhance, 0.025);
        /*put(StringUtils.RandomCuriosAttribute.percentManaRecoverEnhance, 0.02);*/
        put(StringUtils.RandomAttributes.attackDamageEnhance, 0.02);
        put(StringUtils.RandomAttributes.manaDamageEnhance, 0.02);
        put(StringUtils.RandomAttributes.percentMaxHealthEnhance, 0.02);
    }};

    public static List<String> specialAttributes = List.of(
            StringUtils.RandomAttributes.finalDamageEnhance,
            StringUtils.RandomAttributes.percentAttackDamage,
            StringUtils.RandomAttributes.percentManaDamageEnhance,
            StringUtils.RandomAttributes.percentDefenceEnhance,
            StringUtils.RandomAttributes.percentManaDefenceEnhance,
            StringUtils.RandomAttributes.attackSpeedEnhance,
            /*StringUtils.RandomCuriosAttribute.percentManaRecoverEnhance,*/
            StringUtils.RandomAttributes.attackDamageEnhance,
            StringUtils.RandomAttributes.manaDamageEnhance,
            StringUtils.RandomAttributes.percentMaxHealthEnhance
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
                StringUtils.RandomAttributes.attackDamage,
                StringUtils.RandomAttributes.manaDamage,
                StringUtils.RandomAttributes.defencePenetration0,
                StringUtils.RandomAttributes.manaPenetration0,
                StringUtils.RandomAttributes.critRate,
                StringUtils.RandomAttributes.critDamage,
                StringUtils.RandomAttributes.healthSteal,
                StringUtils.RandomAttributes.manaHealthSteal,
                StringUtils.RandomAttributes.defencePenetration,
                StringUtils.RandomAttributes.manaPenetration
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
                    StringUtils.RandomAttributes.maxHealth,
                    StringUtils.RandomAttributes.defence,
                    StringUtils.RandomAttributes.manaDefence,
                    StringUtils.RandomAttributes.healthRecover,
                    StringUtils.RandomAttributes.healEffectUp
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
                    StringUtils.RandomAttributes.coolDown,
                    StringUtils.RandomAttributes.manaRecover,
                    StringUtils.RandomAttributes.maxMana,
                    StringUtils.RandomAttributes.swiftnessUp,
                    StringUtils.RandomAttributes.expUp,
                    StringUtils.RandomAttributes.movementSpeed,
                    StringUtils.RandomAttributes.commonMovementSpeed
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

    public static void provideSingleStableValueAttribute(ItemStack itemStack, String attributeName, double value) {
        CompoundTag data = itemStack.getOrCreateTagElement(Utils.MOD_ID);
        data.putDouble(attributeName, value / attributeValueMap.get(attributeName));
    }
}
