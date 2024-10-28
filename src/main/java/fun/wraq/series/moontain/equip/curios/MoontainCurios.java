package fun.wraq.series.moontain.equip.curios;

import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.equip.impl.RandomCurios;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.series.castle.RandomCuriosAttributesUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;

public class MoontainCurios extends WraqCurios implements RandomCurios {
    public MoontainCurios(Properties properties) {
        super(properties);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        CompoundTag tag = stack.getOrCreateTagElement(Utils.MOD_ID);
        int enhanceRateTimes = tag.getInt(ENHANCE_RATE_TIMES_KEY);
        int enhanceFullRateTimes = tag.getInt(ENHANCE_FULL_RATE_TIMES_KEY);
        ComponentUtils.descriptionPassive(components, Te.s("筑造", hoverMainStyle()));
        components.add(Te.s(" 属性锻造次数: ", CustomStyle.styleOfMoontain, String.valueOf(enhanceRateTimes)));
        components.add(Te.s(" 上限锻造次数: ", CustomStyle.styleOfMoontain, String.valueOf(enhanceFullRateTimes)));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfMoontain;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfMoontain();
    }

    @Override
    public void setAttribute(ItemStack stack) {
        switch (RandomUtils.nextInt(0, 3)) {
            case 0 -> {
                RandomCuriosAttributesUtil.randomAttackAttributeProvide(stack, 3, 2, true);
                RandomCurios.setTypeDescriptionByTag(stack, Type.ATTACK);
                RandomCurios.setFullRateByTag(stack, 2);
            }
            case 1 -> {
                RandomCuriosAttributesUtil.randomDefenceAttributeProvide(stack, 3, 2, true);
                RandomCurios.setTypeDescriptionByTag(stack, Type.DEFENCE);
                RandomCurios.setFullRateByTag(stack, 2);
            }
            case 2 -> {
                RandomCuriosAttributesUtil.randomFunctionAttributeProvide(stack, 3, 2, true);
                RandomCurios.setTypeDescriptionByTag(stack, Type.FUNCTION);
                RandomCurios.setFullRateByTag(stack, 2);
            }
        }
    }

    // 将使用Tag来存储类型与百分比的数值

    @Override
    public double fullRate() {
        return 0;
    }

    @Override
    public Component getTypeDescription() {
        return null;
    }

    public static String ENHANCE_RATE_TIMES_KEY = "EnhanceRateTimesKey";

    public static void enhanceAttributesRate(ItemStack stack, double enhanceRate) {
        CompoundTag tag = stack.getOrCreateTagElement(Utils.MOD_ID);
        RandomCuriosAttributesUtil.attributeValueMap.keySet().forEach(key -> {
            if (stack.getOrCreateTagElement(Utils.MOD_ID).contains(key)) {
                double rate = tag.getDouble(key);
                double fullRate = RandomCurios.getFullRateByTag(stack);
                tag.putDouble(key, Math.min(fullRate, rate + enhanceRate));
            }
        });
        tag.putInt(ENHANCE_RATE_TIMES_KEY, tag.getInt(ENHANCE_RATE_TIMES_KEY) + 1);
    }

    public static String ENHANCE_FULL_RATE_TIMES_KEY = "EnhanceFullRateTimesKey";

    public static void enhanceAttributesFullRate(ItemStack stack, double enhanceRate) {
        double fullRate = RandomCurios.getFullRateByTag(stack);
        if (fullRate != 0) {
            RandomCurios.setFullRateByTag(stack, fullRate + enhanceRate);
            CompoundTag tag = stack.getOrCreateTagElement(Utils.MOD_ID);
            tag.putInt(ENHANCE_FULL_RATE_TIMES_KEY, tag.getInt(ENHANCE_FULL_RATE_TIMES_KEY) + 1);
        }
    }
}
