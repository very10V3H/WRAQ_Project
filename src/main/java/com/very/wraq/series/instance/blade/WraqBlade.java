package com.very.wraq.series.instance.blade;

import com.very.wraq.common.Compute;
import com.very.wraq.common.DelayOperationWithAnimation;
import com.very.wraq.common.Tick;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.core.AttackEvent;
import com.very.wraq.projectiles.ActiveItem;
import com.very.wraq.projectiles.WraqPassiveEquip;
import com.very.wraq.projectiles.WraqSword;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class WraqBlade extends WraqPassiveEquip implements ActiveItem {

    private final Component suffix;
    private final Style style;
    private final double rate;
    public WraqBlade(Properties p_40524_, Style style, Component suffix, double rate, int levelRequire) {
        super(p_40524_);
        this.suffix = suffix;
        this.style = style;
        this.rate = rate;
        Utils.xpLevelAttackDamage.put(this, 1d);
        Utils.levelRequire.put(this, levelRequire);
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
        components.add(Component.literal(" 居合必定暴击").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        ComponentUtils.coolDownTimeDescription(components, 9);
        return components;
    }

    @Override
    public Component getSuffix() {
        return suffix;
    }

    @Override
    public Component getType() {
        return Component.literal("名刀").withStyle(CustomStyle.styleOfSakura);
    }

    @Override
    public void active(Player player) {
        if (player.experienceLevel < Utils.levelRequire.get(this) || !(player.getMainHandItem().getItem() instanceof WraqSword)) return;
        boolean success = DelayOperationWithAnimation.addToQueue(new DelayOperationWithAnimation(
                DelayOperationWithAnimation.Animation.samurai, Tick.get() + 10, player) {
            @Override
            public void trig() {
                if (player.getMainHandItem().getItem() instanceof WraqSword) {
                    AttackEvent.getPlayerNormalAttackRangeMobList(player).forEach(mob -> {
                        AttackEvent.attackToMonster(mob, player, rate, true);
                    });
                }
            }
        });
        if (success) {
            BladeItems.ITEMS.getEntries().stream().map(RegistryObject::get).forEach(item -> {
                Compute.playerItemCoolDown(player, item, 9);
            });
        }
    }
}

