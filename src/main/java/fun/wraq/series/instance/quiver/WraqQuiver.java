package fun.wraq.series.instance.quiver;

import fun.wraq.common.Compute;
import fun.wraq.common.equip.WraqBow;
import fun.wraq.common.equip.WraqPassiveEquip;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;

public class WraqQuiver extends WraqPassiveEquip implements ActiveItem {

    private final Component suffix;
    private final Style style;
    private final double rate;
    private final int shootTimes;
    public WraqQuiver(Properties p_40524_, Style style, Component suffix, double rate, int shootTimes
            , double swift, int levelRequire) {
        super(p_40524_);
        this.suffix = suffix;
        this.style = style;
        this.rate = rate;
        this.shootTimes = shootTimes;
        Utils.swiftnessUp.put(this, swift);
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
        ComponentUtils.descriptionActive(components, Component.literal("速射").withStyle(CustomStyle.styleOfFlexible));
        components.add(Component.literal(" 从箭袋中快速抽出").withStyle(ChatFormatting.WHITE).
                append(Component.literal("" + shootTimes).withStyle(CustomStyle.styleOfFlexible)).
                append(Component.literal("支箭射出").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 每支箭矢拥有").withStyle(ChatFormatting.WHITE).
                append(Component.literal(String.format("%.0f%%", rate * 100)).withStyle(style)).
                append(Component.literal("基础伤害").withStyle(ChatFormatting.WHITE)));
        components.add(Component.literal(" 额外箭矢视为").withStyle(ChatFormatting.WHITE).
                append(Component.literal("普通箭矢攻击").withStyle(CustomStyle.styleOfFlexible)));
        ComponentUtils.coolDownTimeDescription(components, 9);
        return components;
    }

    @Override
    public Component getSuffix() {
        return suffix;
    }

    @Override
    public Component getType() {
        return Component.literal("箭袋").withStyle(CustomStyle.styleOfFlexible);
    }

    @Override
    public void active(Player player) {
        if (player.experienceLevel < Utils.levelRequire.getOrDefault(this, 0)) return;
        batchAddExShoot(player, rate, shootTimes);
        getAllQuiver().forEach(item -> {
            Compute.playerItemCoolDown(player, item, 9);
        });
    }

    @Override
    public double manaCost(Player player) {
        return 0;
    }

    private List<Item> getAllQuiver() {
        return QuiverItems.ITEMS.getEntries().stream().map(RegistryObject::get).toList();
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
                    if (player.getMainHandItem().getItem() instanceof WraqBow wraqBow) {
                        wraqBow.shoot((ServerPlayer) player, rate, false);
                    }
                }
            }
        }));
    }
}
