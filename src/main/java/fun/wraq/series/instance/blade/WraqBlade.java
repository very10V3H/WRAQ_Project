package fun.wraq.series.instance.blade;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqPassiveEquip;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.gui.illustrate.Display;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WraqBlade extends WraqPassiveEquip implements ActiveItem {

    private final Component suffix;
    private final Style style;
    private final double rate;
    public WraqBlade(Properties p_40524_, Style style, Component suffix, double rate, int levelRequire) {
        super(p_40524_);
        this.suffix = suffix;
        this.style = style;
        this.rate = rate;
        Utils.xpLevelAttackDamage.put(this, 2d);
        Utils.levelRequire.put(this, levelRequire);
        Display.souvenirsList.add(this);
    }

    @Override
    public Style getMainStyle() {
        return style;
    }

    @Override
    public List<Component> getAdditionDescriptions() {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        ComponentUtils.descriptionActive(components, Component.literal("居合").withStyle(CustomStyle.styleOfSakura));
        components.add(Component.literal(" 向攻击范围进行一次").withStyle(ChatFormatting.WHITE).
                append(Component.literal("居合斩").withStyle(CustomStyle.styleOfSakura)));
        components.add(Component.literal(" 对范围内的所有敌人造成").withStyle(ChatFormatting.WHITE).
                append(Component.literal(String.format("%.0f%%", rate * 100)).withStyle(style)).
                append(Component.literal("基础数值的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("普通近战攻击").withStyle(CustomStyle.styleOfPower)));
        components.add(Te.s(" 普通攻击", CustomStyle.styleOfPower, "每命中一名敌人，将减少该物品", "0.25s剩余冷却时间", CustomStyle.styleOfIce));
        components.add(Component.literal(" 居合必定暴击").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        ComponentUtils.coolDownTimeDescription(components, 6);
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfSouvenirs();
    }

    @Override
    public Component getType() {
        return Component.literal("名刀").withStyle(CustomStyle.styleOfSakura);
    }

    @Override
    public void active(Player player) {

    }

    @Override
    public double manaCost(Player player) {
        return 0;
    }

    public static Map<Player, Map<Item, Integer>> itemBladeCooldownRecord = new HashMap<>();

    public static void onAttackHitEachTarget(Player player) {
        if (itemBladeCooldownRecord.containsKey(player)) {
            Compute.decreaseCoolDownLeftTick(player, itemBladeCooldownRecord.get(player), 5);
        }
    }
}

