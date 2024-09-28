package fun.wraq.series.instance.blade;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.registry.MySound;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.core.AttackEvent;
import fun.wraq.process.func.DelayOperationWithAnimation;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.equip.WraqPassiveEquip;
import fun.wraq.common.equip.WraqSword;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.blade.BladeItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
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
                    MySound.SoundToAll(player, SoundEvents.PLAYER_ATTACK_KNOCKBACK);
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

