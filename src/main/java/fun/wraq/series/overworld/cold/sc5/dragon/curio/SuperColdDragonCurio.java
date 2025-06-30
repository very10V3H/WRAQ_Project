package fun.wraq.series.overworld.cold.sc5.dragon.curio;

import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.equip.impl.RandomCurios;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.series.castle.RandomCuriosAttributesUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuperColdDragonCurio extends WraqCurios implements RandomCurios {

    public SuperColdDragonCurio(Properties properties) {
        super(properties);
    }

    @Override
    public Component getTypeDescription() {
        return null;
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        return List.of();
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfIce;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfSuperCold();
    }

    public static Map<String, Double> attributeValueMap = new HashMap<>() {{
        put(StringUtils.RandomCuriosAttribute.percentAttackDamage, 0.14);
        put(StringUtils.RandomCuriosAttribute.attackDamageEnhance, 0.14);
        put(StringUtils.RandomCuriosAttribute.critDamage, 0.14);
        put(StringUtils.RandomCuriosAttribute.critRate, 0.1);
        put(StringUtils.RandomCuriosAttribute.defencePenetration0, 100d);

        put(StringUtils.RandomCuriosAttribute.percentManaDamageEnhance, 0.14);
        put(StringUtils.RandomCuriosAttribute.manaDamageEnhance, 0.14);
        put(StringUtils.RandomCuriosAttribute.manaPenetration0, 100d);
        put(StringUtils.RandomCuriosAttribute.maxMana, 800d);
        put(StringUtils.RandomCuriosAttribute.percentManaRecoverEnhance, 0.1);

        put(StringUtils.RandomCuriosAttribute.defence, 800d);
        put(StringUtils.RandomCuriosAttribute.manaDefence, 400d);
        put(StringUtils.RandomCuriosAttribute.percentHealthRecover, 0.05);
        put(StringUtils.RandomCuriosAttribute.healEffectUp, 0.1);
        put(StringUtils.RandomCuriosAttribute.percentMaxHealthEnhance, 0.2);

        put(StringUtils.RandomCuriosAttribute.attackSpeedEnhance, 0.2);
        put(StringUtils.RandomCuriosAttribute.coolDown, 0.35);
        put(StringUtils.RandomCuriosAttribute.commonMovementSpeed, 0.35);
        put(StringUtils.RandomCuriosAttribute.swiftnessUp, 5d);
    }};

    public static Map<String, String> attributeTypeMap = new HashMap<>() {{
        put(StringUtils.RandomCuriosAttribute.percentAttackDamage, Type.ATTACK);
        put(StringUtils.RandomCuriosAttribute.attackDamageEnhance, Type.ATTACK);
        put(StringUtils.RandomCuriosAttribute.critDamage, Type.ATTACK);
        put(StringUtils.RandomCuriosAttribute.critRate, Type.ATTACK);
        put(StringUtils.RandomCuriosAttribute.defencePenetration0, Type.ATTACK);

        put(StringUtils.RandomCuriosAttribute.percentManaDamageEnhance, Type.ATTACK);
        put(StringUtils.RandomCuriosAttribute.manaDamageEnhance, Type.ATTACK);
        put(StringUtils.RandomCuriosAttribute.manaPenetration0, Type.ATTACK);
        put(StringUtils.RandomCuriosAttribute.maxMana, Type.ATTACK);
        put(StringUtils.RandomCuriosAttribute.percentManaRecoverEnhance, Type.ATTACK);

        put(StringUtils.RandomCuriosAttribute.defence, Type.DEFENCE);
        put(StringUtils.RandomCuriosAttribute.manaDefence, Type.DEFENCE);
        put(StringUtils.RandomCuriosAttribute.percentHealthRecover, Type.DEFENCE);
        put(StringUtils.RandomCuriosAttribute.healEffectUp, Type.DEFENCE);
        put(StringUtils.RandomCuriosAttribute.percentMaxHealthEnhance, Type.DEFENCE);

        put(StringUtils.RandomCuriosAttribute.attackSpeedEnhance, Type.FUNCTION);
        put(StringUtils.RandomCuriosAttribute.coolDown, Type.FUNCTION);
        put(StringUtils.RandomCuriosAttribute.commonMovementSpeed, Type.FUNCTION);
        put(StringUtils.RandomCuriosAttribute.swiftnessUp, Type.FUNCTION);
    }};

    public static List<String> attributeList = new ArrayList<>() {{
        this.addAll(attributeTypeMap.keySet());
    }};

    @Override
    public void setAttribute(ItemStack stack) {
        String attribute = attributeList.get(RandomUtils.nextInt(0, attributeList.size()));
        RandomCuriosAttributesUtil.provideSingleStableValueAttribute(stack,
                attribute, attributeValueMap.get(attribute));
        RandomCurios.setTypeDescriptionByTag(stack, attributeTypeMap.get(attribute));
    }

    @Override
    public double fullRate() {
        return 0;
    }
}
