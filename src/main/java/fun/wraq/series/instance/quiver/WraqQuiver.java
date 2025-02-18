package fun.wraq.series.instance.quiver;

import fun.wraq.common.equip.WraqBow;
import fun.wraq.common.equip.WraqPassiveEquip;
import fun.wraq.common.equip.impl.ActiveItem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.core.bow.MyArrow;
import fun.wraq.process.func.DelayOperationWithAnimation;
import fun.wraq.process.func.particle.ParticleProvider;
import fun.wraq.render.gui.illustrate.Display;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
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
        Display.souvenirsList.add(this);
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
        ComponentUtils.descriptionActive(components, Te.s("齐射", CustomStyle.styleOfFlexible));
        components.add(Te.s(" 按下", "Shift", ChatFormatting.AQUA, "使用，获得持续3s的", "齐射效果", CustomStyle.styleOfFlexible));
        components.add(Te.s(" 在", "齐射效果", CustomStyle.styleOfFlexible, "的持续时间内:"));
        components.add(Te.s(" 进行", "普通箭矢攻击", CustomStyle.styleOfFlexible, "时，将会额外射出"));
        components.add(Te.s(" 2支箭矢", CustomStyle.styleOfFlexible, "，额外箭矢将会自动锁定", "准星附近敌人"));
        ComponentUtils.coolDownTimeDescription(components, 9);
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfSouvenirs();
    }

    @Override
    public Component getType() {
        return Component.literal("箭袋").withStyle(CustomStyle.styleOfFlexible);
    }

    @Override
    public void active(Player player) {

    }

    @Override
    public double manaCost(Player player) {
        return 0;
    }

    private List<Item> getAllQuiver() {
        return QuiverItems.ITEMS.getEntries().stream().map(RegistryObject::get).toList();
    }

    public static Map<Player, Queue<Double>> exShootRateQueueMap = new HashMap<>();

    public static void addExShoot(Player player, double rate) {
        exShootRateQueueMap.entrySet().removeIf(entry -> entry.getKey() == null || !entry.getKey().isAlive());
        if (!exShootRateQueueMap.containsKey(player)) exShootRateQueueMap.put(player, new ArrayDeque<>());
        Queue<Double> queue = exShootRateQueueMap.get(player);
        queue.add(rate);
    }

    public static void batchAddExShoot(Player player, double rate, int count) {
        for (int i = 0; i < count; i++) {
            addExShoot(player, rate);
        }
    }

    public static void handleServerPlayerTick(Player player) {
        if (exShootRateQueueMap.containsKey(player)) {
            Queue<Double> queue = exShootRateQueueMap.get(player);
            if (!queue.isEmpty()) {
                if (Tick.get() % 2 == 0) {
                    double rate = queue.remove();
                    if (player.getMainHandItem().getItem() instanceof WraqBow wraqBow) {
                        wraqBow.shoot((ServerPlayer) player, rate, false);
                        DelayOperationWithAnimation.addToQueue(new DelayOperationWithAnimation(
                                DelayOperationWithAnimation.Animation.bursts, Tick.get() + 5, player
                        ) {
                            @Override
                            public void trig() {

                            }
                        });
                    }
                }
            }
        }
    }

    private static Map<Player, Integer> passiveExpiredTickMap = new WeakHashMap<>();
    public static void shootQuiverExArrow(Player player) {
        if (passiveExpiredTickMap.getOrDefault(player, 0) > Tick.get()) {
            shootExArrow(player);
        }
    }

    private static void shootExArrow(Player player) {
        List<Mob> targetList = new ArrayList<>();
        Map<Mob, Double> distance = new HashMap<>();
        for (int i = 0 ; i < 30 ; i ++) {
            Vec3 pickPos = player.pick(i, 0, false).getLocation();
            int finalI = i;
            player.level().getEntitiesOfClass(Mob.class,
                            AABB.ofSize(pickPos, i * 2, i * 2, i * 2))
                    .stream().filter(mob -> mob.position().distanceTo(pickPos) < finalI)
                    .forEach(mob -> {
                        if (distance.containsKey(mob)) {
                            if (distance.get(mob) > mob.position().distanceTo(pickPos)) {
                                distance.put(mob, mob.position().distanceTo(pickPos));
                            }
                        } else {
                            distance.put(mob, mob.position().distanceTo(pickPos));
                        }
                        if (!targetList.contains(mob)) targetList.add(mob);
                    });
        }
        targetList.sort(new Comparator<Mob>() {
            @Override
            public int compare(Mob o1, Mob o2) {
                if (distance.get(o1) < distance.get(o2)) {
                    return -1;
                }
                else {
                    if (distance.get(o1) > distance.get(o2)) {
                        return 1;
                    }
                    return 0;
                }
            }
        });
        if (targetList.size() > 1) {
            targetList.subList(1, Math.min(3, targetList.size())).forEach(mob -> {
                MyArrow myArrow = new MyArrow(EntityType.ARROW, player.level(), player, true, 0.25);
                myArrow.setDeltaMovement(mob.position().add(0, 1, 0)
                        .subtract(player.position().add(0, 1.5, 0)).normalize().scale(4.5));
                myArrow.moveTo(player.pick(0.5, 0, false).getLocation());
                myArrow.setCritArrow(true);
                myArrow.setNoGravity(true);
                ProjectileUtil.rotateTowardsMovement(myArrow, 1);
                player.level().addFreshEntity(myArrow);
                ParticleProvider.createLineParticle(player.level(), (int) mob.distanceTo(player),
                        player.pick(0.5, 0, false).getLocation(),
                        mob.position().add(0, 1, 0), ParticleTypes.CRIT);
            });
        }
    }
}
