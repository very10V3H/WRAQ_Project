package fun.wraq.events.mob.instance.item;

import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.equip.impl.RandomCurios;
import fun.wraq.common.impl.display.UsageOrGetWayDescriptionItem;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.series.castle.CastleCurios;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PlainNecklace extends WraqCurios implements RandomCurios, UsageOrGetWayDescriptionItem {

    public PlainNecklace(Properties properties) {
        super(properties);
        Utils.levelRequire.put(this, 50);
    }

    @Override
    public Component getTypeDescription() {
        return Component.literal("").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.getFuncTypeDescriptionOfCurios()).
                append(Component.literal(" v = 3 * " + BigDecimal.valueOf(rate()).stripTrailingZeros())
                        .withStyle(CustomStyle.styleOfWorld));
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        return List.of();
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfPlain;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfChapterI();
    }

    @Override
    public List<Component> getWayDescription() {
        List<Component> components = new ArrayList<>();
        components.add(Component.literal("击杀").withStyle(ChatFormatting.WHITE).
                append(Component.literal("普莱尼").withStyle(CustomStyle.styleOfPlain)).
                append(Component.literal("概率掉落").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal("基础属性为随机三词条").withStyle(ChatFormatting.WHITE).
                append(Component.literal("功能型").withStyle(CustomStyle.styleOfWorld)).
                append(Component.literal("属性").withStyle(ChatFormatting.WHITE)));
        return components;
    }

    @Override
    public void setAttribute(ItemStack stack) {
        CastleCurios.randomFunctionAttributeProvide(stack, 3, rate());
    }

    @Override
    public double rate() {
        return 0.4;
    }
}
