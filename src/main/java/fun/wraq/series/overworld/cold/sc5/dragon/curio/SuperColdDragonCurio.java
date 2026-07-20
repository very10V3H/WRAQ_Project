package fun.wraq.series.overworld.cold.sc5.dragon.curio;

import fun.wraq.blocks.entity.Decomposable;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.equip.impl.RandomCurios;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.StringUtils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.crystal.CrystalItems;
import fun.wraq.series.instance.series.castle.RandomCuriosAttributesUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuperColdDragonCurio extends WraqCurios implements RandomCurios, Decomposable {

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
        put(StringUtils.RandomAttributes.percentAttackDamage, 0.14);
        put(StringUtils.RandomAttributes.attackDamageEnhance, 0.14);
        put(StringUtils.RandomAttributes.critDamage, 0.14);
        put(StringUtils.RandomAttributes.critRate, 0.1);
        put(StringUtils.RandomAttributes.defencePenetration0, 100d);

        put(StringUtils.RandomAttributes.percentManaDamageEnhance, 0.14);
        put(StringUtils.RandomAttributes.manaDamageEnhance, 0.14);
        put(StringUtils.RandomAttributes.manaPenetration0, 100d);
        put(StringUtils.RandomAttributes.maxMana, 800d);
        put(StringUtils.RandomAttributes.percentManaRecoverEnhance, 0.04);

        put(StringUtils.RandomAttributes.defence, 800d);
        put(StringUtils.RandomAttributes.manaDefence, 400d);
        put(StringUtils.RandomAttributes.percentHealthRecover, 0.05);
        put(StringUtils.RandomAttributes.healEffectUp, 0.1);
        put(StringUtils.RandomAttributes.percentMaxHealthEnhance, 0.2);

        put(StringUtils.RandomAttributes.attackSpeedEnhance, 0.2);
        put(StringUtils.RandomAttributes.coolDown, 0.35);
        put(StringUtils.RandomAttributes.commonMovementSpeed, 0.35);
        put(StringUtils.RandomAttributes.swiftnessUp, 5d);
    }};

    public static Map<String, String> attributeTypeMap = new HashMap<>() {{
        put(StringUtils.RandomAttributes.percentAttackDamage, Type.ATTACK);
        put(StringUtils.RandomAttributes.attackDamageEnhance, Type.ATTACK);
        put(StringUtils.RandomAttributes.critDamage, Type.ATTACK);
        put(StringUtils.RandomAttributes.critRate, Type.ATTACK);
        put(StringUtils.RandomAttributes.defencePenetration0, Type.ATTACK);

        put(StringUtils.RandomAttributes.percentManaDamageEnhance, Type.ATTACK);
        put(StringUtils.RandomAttributes.manaDamageEnhance, Type.ATTACK);
        put(StringUtils.RandomAttributes.manaPenetration0, Type.ATTACK);
        put(StringUtils.RandomAttributes.maxMana, Type.ATTACK);
        /*put(StringUtils.RandomCuriosAttribute.percentManaRecoverEnhance, Type.ATTACK);*/

        put(StringUtils.RandomAttributes.defence, Type.DEFENCE);
        put(StringUtils.RandomAttributes.manaDefence, Type.DEFENCE);
        put(StringUtils.RandomAttributes.percentHealthRecover, Type.DEFENCE);
        put(StringUtils.RandomAttributes.healEffectUp, Type.DEFENCE);
        put(StringUtils.RandomAttributes.percentMaxHealthEnhance, Type.DEFENCE);

        put(StringUtils.RandomAttributes.attackSpeedEnhance, Type.FUNCTION);
        put(StringUtils.RandomAttributes.coolDown, Type.FUNCTION);
        put(StringUtils.RandomAttributes.commonMovementSpeed, Type.FUNCTION);
        put(StringUtils.RandomAttributes.swiftnessUp, Type.FUNCTION);
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

    @Override
    public ItemStack getProduct() {
        return new ItemStack(CrystalItems.BLUE_CRYSTAL_B.get());
    }
}
