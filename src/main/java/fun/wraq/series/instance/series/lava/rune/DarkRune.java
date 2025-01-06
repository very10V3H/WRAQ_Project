package fun.wraq.series.instance.series.lava.rune;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.newrunes.NewRuneItems;
import fun.wraq.series.newrunes.RuneItem;
import fun.wraq.series.newrunes.chapter6.MoonNewRune;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DarkRune extends WraqCurios implements RuneItem {

    public DarkRune(Properties properties) {
        super(properties);
        Utils.coolDownDecrease.put(this, 0.08);
        Utils.manaDamage.put(this, 200d);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getComprehensiveTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Component.literal("明镜止水").withStyle(CustomStyle.styleOfMoon));
        components.add(Component.literal(" 快捷栏4 ~ 9").withStyle(CustomStyle.styleOfMoon).
                append(Component.literal("每存在1件可以").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("主动释放但未在冷却时间的").withStyle(ChatFormatting.AQUA)).
                append(Component.literal("物品").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 为你提供").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.getCommonDamageEnhance("6%")));
        ComponentUtils.descriptionPassive(components, Component.literal("物法兼修").withStyle(hoverMainStyle()));
        components.add(Component.literal(" 手持").withStyle(ChatFormatting.WHITE).
                append(Component.literal("剑").withStyle(CustomStyle.styleOfPower)).
                append(Component.literal("/").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("弓").withStyle(CustomStyle.styleOfFlexible)).
                append(Component.literal("会提供等同于").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.manaDamage("20%")).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.attackDamage("")));
        components.add(Component.literal(" 手持").withStyle(ChatFormatting.WHITE).
                append(Component.literal("法杖").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal("会提供等同于").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.attackDamage("40%")).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.manaDamage("")));
        return components;
    }

    @Override
    public void tick(Player player) {
        int count = MoonNewRune.getPassiveCount(player);
        if (count > 0) {
            Compute.sendEffectLastTime(player, NewRuneItems.moonNewRune.get(), count, true);
        } else Compute.removeEffectLastTime(player, NewRuneItems.moonNewRune.get());
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfCastle;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfAlchemy();
    }
}
