package com.very.wraq.series.instance.mixture;

import com.very.wraq.common.Compute;
import com.very.wraq.common.Tick;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.process.func.power.PowerLogic;
import com.very.wraq.projectiles.ActiveItem;
import com.very.wraq.projectiles.WraqPassiveEquip;
import com.very.wraq.projectiles.WraqSceptre;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.*;

public class WraqMixture extends WraqPassiveEquip implements ActiveItem {

    private final Component suffix;
    private final Style style;
    public final int eachArrowDecreaseCoolDownTick;
    public final int exManaArrowCount;
    private final double coolDownSecond;
    private final double lastSecond;
    public final double rate;
    public WraqMixture(Properties p_40524_, Style style, Component suffix, int exManaArrowCount, double rate,
                       int eachArrowDecreaseCoolDownTick, double coolDownSecond, double lastSecond, int levelRequire) {
        super(p_40524_);
        this.suffix = suffix;
        this.style = style;
        this.exManaArrowCount = exManaArrowCount;
        this.rate = rate;
        this.eachArrowDecreaseCoolDownTick = eachArrowDecreaseCoolDownTick;
        this.coolDownSecond = coolDownSecond;
        this.lastSecond = lastSecond;
        Utils.xpLevelManaDamage.put(this, 2d);
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
        ComponentUtils.descriptionActive(components, Component.literal("Essence之力").withStyle(CustomStyle.styleOfWorld));
        components.add(Component.literal(" 获得持续").withStyle(ChatFormatting.WHITE).
                append(Component.literal(String.format("%.2fs", lastSecond)).withStyle(style)).
                append(Component.literal("的").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("ω本源之力:").withStyle(CustomStyle.styleOfWorld)));
        components.add(Component.literal(" ω普通法球攻击").withStyle(CustomStyle.styleOfMana).
                append(Component.literal("将会减少所有").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("法术").withStyle(CustomStyle.styleOfMana)).
                append(Component.literal(String.format("%.2fs", eachArrowDecreaseCoolDownTick * 0.05)).withStyle(style)).
                append(Component.literal("冷却时间").withStyle(CustomStyle.styleOfIce)));
        if (exManaArrowCount > 0) {
            components.add(Component.literal(" ω普通法球攻击").withStyle(CustomStyle.styleOfMana).
                    append(Component.literal("将会额外释放").withStyle(ChatFormatting.WHITE)).
                    append(Component.literal("" + exManaArrowCount).withStyle(style)).
                    append(Component.literal("枚").withStyle(ChatFormatting.WHITE)).
                    append(Component.literal(String.format("%.0f%%", rate * 100)).withStyle(style)).
                    append(Component.literal("基础伤害的").withStyle(ChatFormatting.WHITE)).
                    append(Component.literal("法球").withStyle(CustomStyle.styleOfMana)));
        }
        ComponentUtils.coolDownTimeDescription(components, coolDownSecond);
        return components;
    }

    @Override
    public Component getSuffix() {
        return suffix;
    }

    @Override
    public Component getType() {
        return Component.literal("合剂").withStyle(CustomStyle.styleOfBrew);
    }

    public static WeakHashMap<Player, Integer> effectLastTickMap = new WeakHashMap<>();
    public static WeakHashMap<Player, WraqMixture> activeItem = new WeakHashMap<>();

    @Override
    public void active(Player player) {
        effectLastTickMap.put(player, (int) (Tick.get() + lastSecond * 20));
        activeItem.put(player, this);
        Compute.sendEffectLastTime(player, this, (int) (lastSecond * 20));
        MixtureItems.ITEMS.getEntries().forEach(item -> {
            Compute.playerItemCoolDown(player, item.get(), (int) (coolDownSecond * 20));
        });
    }

    public static void onLastTimeManaArrowHit(Player player) {
        if (effectLastTickMap.getOrDefault(player, 0) < Tick.get()) return;
        Map<Item, Integer> powerCoolDownTick = PowerLogic.playerPowerCoolDownRecord.getOrDefault(player, new HashMap<>());
        WraqMixture mixture = activeItem.get(player);
        powerCoolDownTick.forEach((power, coolDownTick) -> {
            double percent = player.getCooldowns().getCooldownPercent(power, 0);
            int leftTick = (int) (percent * coolDownTick);
            player.sendSystemMessage(Component.literal("leftTick = " + leftTick));
            leftTick = Math.max(0, leftTick - mixture.eachArrowDecreaseCoolDownTick);
            player.getCooldowns().addCooldown(power, leftTick);
            powerCoolDownTick.put(power, leftTick);
        });
    }

    public static WeakHashMap<Player, Integer> exShootTickMap = new WeakHashMap<>();

    public static void onShoot(Player player) {
        if (effectLastTickMap.getOrDefault(player, 0) < Tick.get()) return;
        exShootTickMap.put(player, activeItem.get(player).exManaArrowCount * 2);
    }

    public static void tick(Player player) {
        if (exShootTickMap.getOrDefault(player, -1) >= 0) {
            exShootTickMap.put(player, exShootTickMap.get(player) - 1);
            int exShootTick = exShootTickMap.get(player);
            if (exShootTick % 2 == 0 && player.getMainHandItem().getItem() instanceof WraqSceptre wraqSceptre) {
                wraqSceptre.shootManaArrow(player, activeItem.get(player).rate);
            }
        }
    }
}

