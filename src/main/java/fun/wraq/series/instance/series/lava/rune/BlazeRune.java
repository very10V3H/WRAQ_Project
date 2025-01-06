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

public class BlazeRune extends WraqCurios implements RuneItem {

    public BlazeRune(Properties properties) {
        super(properties);
        Utils.critDamage.put(this, 0.2);
        Utils.attackDamage.put(this, 30d);
        Utils.manaDamage.put(this, 60d);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getComprehensiveTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = hoverMainStyle();
        ComponentUtils.descriptionPassive(components, Component.literal("熔岩强击").withStyle(hoverMainStyle()));
        components.add(Component.literal(" 每第三次 ").withStyle(ChatFormatting.WHITE).
                append(Component.literal("近战攻击").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("/").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("法球攻击").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("/").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("箭矢攻击").withStyle(CustomStyle.styleOfFlexible)).
                append(Component.literal(" 会拥有").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("双倍基础伤害").withStyle(hoverMainStyle())));
        ComponentUtils.descriptionPassive(components, Component.literal("熔岩爆裂").withStyle(style));
        components.add(Component.literal(" 击杀一名敌人时，会在其位置造成一次").withStyle(ChatFormatting.WHITE).
                append(Component.literal("小范围爆炸").withStyle(style)));
        components.add(Component.literal(" 爆炸").withStyle(style).
                append(Component.literal("将直接对范围内的目标造成所击杀敌人的").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.maxHealth("8%")).
                append(Component.literal("物理伤害").withStyle(CustomStyle.styleOfPower)));
        components.add(Component.literal(" 并且附带").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.attackDamageValue("100%")).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("真实伤害").withStyle(CustomStyle.styleOfSea)));
        components.add(Component.literal(" 物理伤害仅会受护甲影响，不受任何伤害提升").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfPower;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfAlchemy();
    }
}
