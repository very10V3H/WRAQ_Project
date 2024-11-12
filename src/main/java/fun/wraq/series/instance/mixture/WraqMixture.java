package fun.wraq.series.instance.mixture;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqPassiveEquip;
import fun.wraq.common.equip.WraqSceptre;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.impl.onhit.OnHitEffectPassiveEquip;
import fun.wraq.common.impl.onshoot.OnShootManaArrowPassiveEquip;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.process.func.power.PowerLogic;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.*;

public class WraqMixture extends WraqPassiveEquip implements ActiveItem, OnShootManaArrowPassiveEquip, OnHitEffectPassiveEquip {

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
        if (player.experienceLevel < Utils.levelRequire.get(this)) return;
        effectLastTickMap.put(player, (int) (Tick.get() + lastSecond * 20));
        activeItem.put(player, this);
        Compute.sendEffectLastTime(player, this, (int) (lastSecond * 20));
        MixtureItems.ITEMS.getEntries().forEach(item -> {
            Compute.playerItemCoolDown(player, item.get(), coolDownSecond);
        });
    }

    @Override
    public double manaCost(Player player) {
        return 0;
    }

    @Override
    public void onShoot(Player player) {
        if (effectLastTickMap.getOrDefault(player, 0) < Tick.get()) return;
        batchAddExShoot(player, activeItem.get(player).rate, activeItem.get(player).exManaArrowCount);
    }

    @Override
    public void onHit(Player player, Mob mob) {
        if (effectLastTickMap.getOrDefault(player, 0) < Tick.get()) return;
        Map<Item, Integer> powerCoolDownTick = PowerLogic.playerPowerCoolDownRecord.getOrDefault(player, new HashMap<>());
        WraqMixture mixture = activeItem.get(player);
        Compute.decreaseCoolDownLeftTick(player, powerCoolDownTick, mixture.eachArrowDecreaseCoolDownTick);
    }

    public static WeakHashMap<Player, Queue<Double>> exShootRateQueueMap = new WeakHashMap<>();

    public static void addExShoot(Player player, double rate) {
        exShootRateQueueMap.entrySet().removeIf(entry -> !entry.getKey().isAlive());
        if (!exShootRateQueueMap.containsKey(player)) exShootRateQueueMap.put(player, new ArrayDeque<>());
        Queue<Double> queue = exShootRateQueueMap.get(player);
        queue.add(rate);
    }

    public static void batchAddExShoot(Player player, double rate, int count) {
        for (int i = 0; i < count; i++) {
            addExShoot(player, rate);
        }
    }

    public static void tick() {
        exShootRateQueueMap.forEach(((player, queue) -> {
            if (!queue.isEmpty()) {
                if (Tick.get() % 2 == 0) {
                    double rate = queue.remove();
                    if (player.getMainHandItem().getItem() instanceof WraqSceptre wraqSceptre) {
                        wraqSceptre.shootManaArrow(player, rate, false);
                    }
                }
            }
        }));
    }
}

