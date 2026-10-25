/**
 * AI-Generated, 2026-10-25
 */
package fun.wraq.common.util.struct;

import net.minecraft.world.entity.Mob;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 怪物护盾。
 *
 * <p>与玩家护盾（{@link Shield}）对应，但作用于 {@link Mob}：</p>
 * <ul>
 *     <li>{@link #provideShield(Mob, int, double)} 给怪物挂一层有持续时间、有数值的护盾（可叠多层）；</li>
 *     <li>受伤时由 {@link #absorbDamage(Mob, double)} 优先扣除护盾，扣完才扣血；</li>
 *     <li>持续时间在 {@code MobShieldHandler} 的 LivingTickEvent 里递减，到期自动移除并同步 0；</li>
 *     <li>护盾值通过 {@link Shield#broadcastShield} 同步给周围玩家，用于头顶血条上的白色护盾段。</li>
 * </ul>
 *
 * <p>使用示例：</p>
 * <pre>{@code
 * // 怪物出生 / 进入某阶段时，给 10 秒、相当于 50% 最大生命值的护盾
 * MobShield.providePercentShield(mob, Tick.s(10), 0.5);
 * }</pre>
 */
public final class MobShield {

    private MobShield() {
    }

    /** 怪物 UUID → 护盾层列表（按挂载顺序消耗）。 */
    private static final Map<UUID, List<Shield>> MOB_SHIELD_QUEUE = new HashMap<>();

    /**
     * 给怪物挂一层护盾。
     *
     * @param mob          目标怪物
     * @param durationTick 持续时间（tick）
     * @param value        护盾值（必须 &gt; 0）
     */
    public static void provideShield(Mob mob, int durationTick, double value) {
        if (mob == null || mob.level().isClientSide || value <= 0) {
            return;
        }
        List<Shield> queue = MOB_SHIELD_QUEUE.computeIfAbsent(mob.getUUID(), k -> new ArrayList<>());
        queue.add(new Shield(Math.max(1, durationTick), value));
        computeMobShield(mob);
    }

    /**
     * 给怪物挂一层按最大生命值比例的护盾。
     *
     * @param mob          目标怪物
     * @param durationTick 持续时间（tick）
     * @param percent      护盾值占最大生命值的比例，例如 0.5 表示 50%
     */
    public static void providePercentShield(Mob mob, int durationTick, double percent) {
        if (mob == null) {
            return;
        }
        provideShield(mob, durationTick, mob.getMaxHealth() * percent);
    }

    /**
     * 重新统计护盾值：移除到期/耗尽的层，并把当前总量同步给周围玩家。
     *
     * @return 当前护盾总量
     */
    public static double computeMobShield(Mob mob) {
        List<Shield> queue = MOB_SHIELD_QUEUE.get(mob.getUUID());
        if (queue == null) {
            return 0;
        }
        double total = 0;
        Iterator<Shield> iterator = queue.iterator();
        while (iterator.hasNext()) {
            Shield shield = iterator.next();
            if (shield.getTick() > 0 && shield.getValue() > 0) {
                total += shield.getValue();
            }
            if (shield.getTick() <= 0 || shield.getValue() <= 0) {
                iterator.remove();
            }
        }
        if (queue.isEmpty()) {
            MOB_SHIELD_QUEUE.remove(mob.getUUID());
        }
        Shield.broadcastShield(mob, total);
        return total;
    }

    /**
     * 护盾吸收伤害。
     *
     * @param mob    目标怪物
     * @param damage 伤害值
     * @return 护盾吸收后剩余的伤害（护盾足够时为 0）
     */
    public static double absorbDamage(Mob mob, double damage) {
        if (damage <= 0) {
            return damage;
        }
        List<Shield> queue = MOB_SHIELD_QUEUE.get(mob.getUUID());
        if (queue == null || queue.isEmpty()) {
            return damage;
        }
        double remaining = damage;
        for (Shield shield : queue) {
            if (shield.getTick() <= 0 || shield.getValue() <= 0) {
                continue;
            }
            if (shield.getValue() > remaining) {
                shield.setValue(shield.getValue() - remaining);
                remaining = 0;
                break;
            }
            remaining -= shield.getValue();
            shield.setTick(0);
            shield.setValue(0);
        }
        computeMobShield(mob);
        return Math.max(0, remaining);
    }

    /**
     * 每 tick 递减护盾持续时间（由 {@code MobShieldHandler} 调用）。
     */
    public static void tickMobShield(Mob mob) {
        List<Shield> queue = MOB_SHIELD_QUEUE.get(mob.getUUID());
        if (queue == null) {
            return;
        }
        for (Shield shield : queue) {
            if (shield.getTick() > 0) {
                shield.setTick(shield.getTick() - 1);
            }
        }
        computeMobShield(mob);
    }

    /** 该怪物身上是否还挂着护盾层。 */
    public static boolean hasShield(Mob mob) {
        return MOB_SHIELD_QUEUE.containsKey(mob.getUUID());
    }

    /** 当前护盾总量（不触发同步）。 */
    public static double getShield(Mob mob) {
        List<Shield> queue = MOB_SHIELD_QUEUE.get(mob.getUUID());
        if (queue == null) {
            return 0;
        }
        double total = 0;
        for (Shield shield : queue) {
            if (shield.getTick() > 0 && shield.getValue() > 0) {
                total += shield.getValue();
            }
        }
        return total;
    }

    /** 清空护盾（死亡 / 离场时调用），并通知周围玩家护盾归零。 */
    public static void clearMobShield(Mob mob) {
        if (MOB_SHIELD_QUEUE.remove(mob.getUUID()) != null) {
            Shield.broadcastShield(mob, 0);
        }
    }
}
