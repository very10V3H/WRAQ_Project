package fun.wraq.series.instance.series.ice;

import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.element.Element;
import fun.wraq.common.equip.impl.RandomCurios;
import fun.wraq.common.impl.display.UsageOrGetWayDescriptionItem;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.series.castle.RandomCuriosAttributesUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class IceBelt extends WraqCurios implements RandomCurios, UsageOrGetWayDescriptionItem {

    public IceBelt(Properties properties) {
        super(properties);
        Element.IceElementValue.put(this, 0.5);
        Utils.levelRequire.put(this, 135);
    }

    @Override
    public List<Component> getWayDescription() {
        List<Component> components = new ArrayList<>();
        components.add(Component.literal("通过击杀").withStyle(ChatFormatting.WHITE).
                append(Component.literal("冰霜骑士").withStyle(CustomStyle.styleOfIce)).
                append(Component.literal("概率掉落").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("基础属性为随机六词条").withStyle(ChatFormatting.WHITE).
                append(Component.literal("综合型").withStyle(CustomStyle.styleOfIce)).
                append(Component.literal("属性").withStyle(ChatFormatting.WHITE)));
        return components;
    }

    @Override
    public Component getTypeDescription() {
        return Component.literal("").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.getComprehensiveTypeDescriptionOfCurios()).
                append(Component.literal(" v = 6 * " + BigDecimal.valueOf(fullRate()).stripTrailingZeros()).withStyle(CustomStyle.styleOfIce));
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
        return ComponentUtils.getSuffixOfIce();
    }

    @Override
    public void setAttribute(ItemStack stack) {
        RandomCuriosAttributesUtil.randomAttackAttributeProvide(stack, 2, fullRate());
        RandomCuriosAttributesUtil.randomDefenceAttributeProvide(stack, 2, fullRate());
        RandomCuriosAttributesUtil.randomFunctionAttributeProvide(stack, 2, fullRate());
    }

    @Override
    public double fullRate() {
        return 0.7;
    }
}
