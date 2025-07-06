package fun.wraq.series.overworld.chapter2.sky.Crest;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.equip.impl.RepeatableCurios;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.system.element.Element;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SkyCrest extends WraqCurios implements RepeatableCurios {

    public static List<Item> crestList = new ArrayList<>();

    public SkyCrest(Properties properties, int tier) {
        super(properties, 16);
        Utils.critDamage.put(this, new double[]{0.01, 0.02, 0.03, 0.04, 0.06}[tier]);
        Utils.manaPenetration0.put(this, new double[]{1, 2, 3, 4, 8}[tier]);
        Element.windElementValue.put(this, new double[]{0.05, 0.12, 0.2, 0.32, 0.5}[tier]);
        crestList.add(this);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getAttackTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Component.literal("天空的加护").withStyle(CustomStyle.styleOfSky));
        components.add(Component.literal("1.").
                append(ComponentUtils.AttributeDescription.health("")).
                append(Component.literal("高于80%时，至多提升")).
                append(ComponentUtils.AttributeDescription.attackDamage("100%")).
                append(Component.literal("与").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.movementSpeed("40%")));
        components.add(Component.literal("2.").
                append(ComponentUtils.AttributeDescription.health("")).
                append(Component.literal("介于40%与80%之间时，至多提升")).
                append(ComponentUtils.AttributeDescription.attackDamage("40%")));
        components.add(Component.literal("3.").
                append(ComponentUtils.AttributeDescription.health("")).
                append(Component.literal("低于40%时，每十秒至多获得")).
                append(ComponentUtils.AttributeDescription.attackDamage("10%")).
                append(Component.literal("护盾。")).
                append(Component.literal("持续10s")).withStyle(ChatFormatting.WHITE));
        components.add(Component.literal(" "));
        components.add(Te.s("基于套装数量的数值:(1_20%,2_50%,3_140%,4_200%").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfSky;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfChapterII();
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
