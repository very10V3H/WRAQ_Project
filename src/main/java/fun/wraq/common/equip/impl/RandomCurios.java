package fun.wraq.common.equip.impl;

import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface RandomCurios {
    void setAttribute(ItemStack stack);

    double fullRate();

    class Type {
        public static final String ATTACK = "ATTACK";
        public static final String DEFENCE = "DEFENCE";
        public static final String FUNCTION = "FUNCTION";
        public static final String COMPREHENSIVE = "COMPREHENSIVE";
        public static final String ALL = "ALL";
    }

    String typeTagKey = "RandomCuriosTypeTagKey";

    @Nullable
    static Component getTypeDescriptionByTag(ItemStack stack) {
        if (stack.getTagElement(Utils.MOD_ID) != null && stack.getTagElement(Utils.MOD_ID).contains(typeTagKey)) {
            switch (stack.getTagElement(Utils.MOD_ID).getString(typeTagKey)) {
                case Type.ATTACK -> {
                    return ComponentUtils.getAttackTypeDescriptionOfCurios();
                }
                case Type.DEFENCE -> {
                    return ComponentUtils.getDefenceTypeDescriptionOfCurios();
                }
                case Type.FUNCTION -> {
                    return ComponentUtils.getFuncTypeDescriptionOfCurios();
                }
                case Type.COMPREHENSIVE -> {
                    return ComponentUtils.getComprehensiveTypeDescriptionOfCurios();
                }
                case Type.ALL -> {
                    return ComponentUtils.getAllTypeDescriptionOfCurios();
                }
            }
        }
        return null;
    }

    static void setTypeDescriptionByTag(ItemStack stack, String type) {
        stack.getOrCreateTagElement(Utils.MOD_ID).putString(typeTagKey, type);
    }

    String fullRateTagKey = "RandomCuriosFullRateTagKey";
    static double getFullRateByTag(ItemStack stack) {
        if (stack.getTagElement(Utils.MOD_ID) != null && stack.getTagElement(Utils.MOD_ID).contains(fullRateTagKey)) {
            return stack.getTagElement(Utils.MOD_ID).getDouble(fullRateTagKey);
        }
        return 0;
    }

    static void setFullRateByTag(ItemStack stack, double rate) {
        stack.getOrCreateTagElement(Utils.MOD_ID).putDouble(fullRateTagKey, rate);
    }
}
