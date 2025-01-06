package fun.wraq.series.instance.series.lava.rune;

import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.newrunes.RuneItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class LifeRune extends WraqCurios implements RuneItem {

    public LifeRune(Properties properties) {
        super(properties);
        Utils.healthRecover.put(this, 5d);
        Utils.maxHealth.put(this, 1600d);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getComprehensiveTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Component.literal("复苏之风").withStyle(hoverMainStyle()));
        components.add(Component.literal(" 获得").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.healthRecover("已损失生命值1%")));
        ComponentUtils.descriptionPassive(components, Component.literal("狂野生长").withStyle(hoverMainStyle()));
        components.add(Component.literal(" 受到来自").withStyle(ChatFormatting.WHITE).
                append(Component.literal("怪物").withStyle(ChatFormatting.RED)).
                append(Component.literal("的伤害且该伤害会使你的").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.health("")).
                append(Component.literal("低于20%").withStyle(ChatFormatting.GREEN)).
                append(Component.literal("时，免疫该伤害").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 并在触发后，为你提供持续5s的").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.healthRecover("10%最大生命值")));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfLife;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfAlchemy();
    }
}
