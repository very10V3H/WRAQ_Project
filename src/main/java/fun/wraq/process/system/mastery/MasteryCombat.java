/** AI-Generated, 2026-10-25 */
package fun.wraq.process.system.mastery;

import fun.wraq.common.Compute;
import fun.wraq.common.fast.Tick;
import fun.wraq.common.util.HealUtil;
import fun.wraq.process.func.item.InventoryOperation;
import fun.wraq.common.util.struct.Shield;
import fun.wraq.common.registry.ModItems;
import fun.wraq.process.func.damage.Damage;
import fun.wraq.render.hud.Mana;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;

/**
 * 精通系统中依赖战斗上下文/时间的动态效果。
 *
 * <p>设计约定：</p>
 * <ul>
 *     <li>静态面板属性只写在 {@link MasteryStats}，这里只处理叠层、条件、触发和冷却；</li>
 *     <li>所有状态都在服务端维护，客户端不能直接写；</li>
 *     <li>入口方法按“攻击 / 技能 / 击杀 / 受击 / 每 tick”分组，新增节点时优先在对应分组追加；</li>
 *     <li>概率/阈值/持续时间使用具名常量，避免散落魔法数字。</li>
 * </ul>
 */
public final class MasteryCombat {

    private static final int FERVOR_DURATION = 40;       // 2s
    private static final int CONQUEROR_DURATION = 100;  // 5s
    private static final int LETHAL_TEMPO_DURATION = 60; // 3s
    private static final int KILL_STACK_DURATION = 200; // 10s
    private static final int CAST_STACK_DURATION = 100; // 5s
    private static final int NETHERFIRE_DURATION = 100; // 5s
    private static final int ENERGIZED_MAX = 20;
    private static final int AMBUSH_OUT_OF_COMBAT = 600; // 30s
    private static final int AMBUSH_BUFF_DURATION = 1200; // 60s
    private static final int PERIODIC_SHIELD_INTERVAL = 200; // 10s
    private static final int OUT_OF_COMBAT_BASE = 100; // 5s
    private static final int OUT_OF_COMBAT_TALENTED = 160; // 8s

    private static final Map<Player, State> STATES = new WeakHashMap<>();

    private MasteryCombat() {
    }

    private static final class State {
        // 精密
        private int fervorStacks;
        private long fervorUntil;
        private int conquerorStacks;
        private long conquerorUntil;
        private int lethalTempoStacks;
        private long lethalTempoUntil;
        private int normalHits;

        // 主宰
        private int killStacks;
        private long killUntil;
        private int energizedStacks;
        private int tenHitCount;
        private int thirdHitCount;
        private double pendingAoeRate;
        private long dashPenUntil;
        private long lastCombatTick;
        private long ambushBuffUntil;

        // 巫术
        private int castStacks;
        private long castUntil;
        private int netherfireStacks;
        private long netherfireUntil;
        private long periodicEmpowerReady;
        private boolean pendingEmpower;
        private boolean pendingEnergized;
        private long skillMoveUntil;
        private double skillMoveRate;
        private double manaSpent;
        private long manaBurstUntil;

        // 坚决
        private long nextPeriodicShieldTick;
        private long undyingReady;

        // 启迪
        private boolean ultimateReleased;
        private final Set<UUID> firstStrikeTargets = new HashSet<>();
        private double lastX = Double.NaN;
        private double lastZ = Double.NaN;
    }

    // ------------------------------------------------------------------
    // 普攻
    // ------------------------------------------------------------------

    /**
     * 普攻命中时调用，返回这一下单体普攻的伤害倍率。
     */
    public static double onNormalAttack(Player player, Mob target) {
        if (player.level().isClientSide) {
            return 1;
        }
        State state = state(player);
        long now = Tick.get();
        cleanup(state, now);
        state.lastCombatTick = now;

        double rate = 0;

        // 精密：狂热 / 征服者 / 致命节奏 / 强攻
        int fervorLevel = level(player, MasteryEffectId.FERVOR);
        if (fervorLevel > 0) {
            state.fervorStacks = Math.min(5 + fervorLevel, state.fervorStacks + 1);
            state.fervorUntil = now + FERVOR_DURATION;
        }

        int conquerorLevel = keystoneLevel(player, MasteryEffectId.CONQUEROR);
        if (conquerorLevel > 0) {
            state.conquerorStacks = Math.min(30 + 3 * conquerorLevel, state.conquerorStacks + 1);
            state.conquerorUntil = now + CONQUEROR_DURATION;
        }

        int lethalLevel = keystoneLevel(player, MasteryEffectId.LETHAL_TEMPO);
        if (lethalLevel > 0) {
            state.lethalTempoStacks = Math.min(20 + 2 * lethalLevel, state.lethalTempoStacks + 1);
            state.lethalTempoUntil = now + LETHAL_TEMPO_DURATION;
        }

        state.normalHits++;
        int pressLevel = keystoneLevel(player, MasteryEffectId.PRESS_THE_ATTACK);
        if (pressLevel > 0 && state.normalHits % 3 == 0) {
            rate += 0.30 + 0.01 * pressLevel;
        }

        // 主宰：盈能
        int energizedLevel = level(player, MasteryEffectId.D_ENERGIZED);
        if (energizedLevel > 0) {
            if (state.energizedStacks >= ENERGIZED_MAX) {
                state.energizedStacks = 0;
                rate += 0.20 + 0.02 * energizedLevel;
            }
            state.energizedStacks = Math.min(ENERGIZED_MAX, state.energizedStacks + 1);
        }

        // 主宰：10 次攻击后的下一次强化
        int tenLevel = keystoneLevel(player, MasteryEffectId.D_TEN_HITS);
        if (tenLevel > 0) {
            if (state.tenHitCount >= 10) {
                state.tenHitCount = 0;
                rate += 0.50 + 0.05 * tenLevel;
                state.pendingAoeRate = Math.max(state.pendingAoeRate, 0.50 + 0.05 * tenLevel);
            } else {
                state.tenHitCount++;
            }
        }

        // 主宰：每第三次攻击
        int thirdLevel = keystoneLevel(player, MasteryEffectId.D_THIRD_HIT);
        if (thirdLevel > 0) {
            state.thirdHitCount++;
            if (state.thirdHitCount % 3 == 0) {
                rate += 0.10 + 0.01 * thirdLevel;
                state.pendingAoeRate = Math.max(state.pendingAoeRate, 0.10 + 0.01 * thirdLevel);
            }
        }
        return 1 + rate;
    }

    /**
     * 主宰基石的范围普攻：在 AttackEvent 结算主目标后调用。
     * 只对 5 格内、非主目标的最多 8 名敌人造成相同倍率的普通攻击伤害。
     */
    public static void onNormalAttackAoE(Player player, Mob primary, double damage) {
        if (player.level().isClientSide || damage <= 0) {
            return;
        }
        State state = state(player);
        double rate = state.pendingAoeRate;
        if (rate <= 0) {
            return;
        }
        state.pendingAoeRate = 0;
        List<Mob> nearby = player.level().getEntitiesOfClass(Mob.class,
                player.getBoundingBox().inflate(5),
                mob -> mob != primary && mob.isAlive() && Compute.isWraqMob(mob));
        for (int i = 0; i < Math.min(8, nearby.size()); i++) {
            Damage.causeDirectDamageToMob(player, nearby.get(i), damage * rate);
        }
    }

    /** 兼容精密旧入口；新代码请使用带 skillType/cooldown 的版本。 */
    public static void onSkillCast(Player player) {
        onSkillRelease(player, -1, 0, 0);
    }

    /**
     * 释放前调用，返回本次技能是否无法力消耗。
     * 由 {@code SkillV2.onPlayerTryToRelease} 在扣蓝前接入。
     */
    public static boolean beginSkillRelease(Player player, int skillType) {
        if (player.level().isClientSide) {
            return false;
        }
        State state = state(player);
        long now = Tick.get();
        cleanup(state, now);
        boolean free = false;

        int empowerLevel = keystoneLevel(player, MasteryEffectId.S_PERIODIC_EMPOWER);
        if (empowerLevel > 0 && now >= state.periodicEmpowerReady) {
            state.pendingEmpower = true;
            free = true;
        }

        int energizedLevel = level(player, MasteryEffectId.D_ENERGIZED);
        if (energizedLevel > 0 && state.energizedStacks >= ENERGIZED_MAX) {
            state.energizedStacks = 0;
            state.pendingEnergized = true;
        }
        return free;
    }

    /**
     * 技能释放成功后调用。
     *
     * @param cooldownTicks 实际剩余冷却，用于“疾风咒”
     * @param manaCost      实际消耗法力，用于“法力爆发”
     */
    public static void onSkillRelease(Player player, int skillType, int cooldownTicks, double manaCost) {
        if (player.level().isClientSide) {
            return;
        }
        State state = state(player);
        long now = Tick.get();
        cleanup(state, now);
        boolean outOfCombat = now - state.lastCombatTick >= AMBUSH_OUT_OF_COMBAT;
        state.lastCombatTick = now;

        onSkillCastInternal(player, state, now);

        // 巫术：释放技能叠层
        int castLevel = level(player, MasteryEffectId.S_CAST_STACKS);
        if (castLevel > 0) {
            state.castStacks = Math.min(10 + 2 * castLevel, state.castStacks + 1);
            state.castUntil = now + CAST_STACK_DURATION;
        }

        // 巫术：冥火
        int netherfireLevel = keystoneLevel(player, MasteryEffectId.S_NETHERFIRE);
        if (netherfireLevel > 0) {
            state.netherfireStacks = Math.min(9999, state.netherfireStacks + 1);
            state.netherfireUntil = now + NETHERFIRE_DURATION;
        }

        // 主宰：施法获得盈能层数
        int energizedLevel = level(player, MasteryEffectId.D_ENERGIZED);
        if (energizedLevel > 0) {
            state.energizedStacks = Math.min(ENERGIZED_MAX, state.energizedStacks + 1);
        }

        // 巫术：疾风咒
        int moveLevel = level(player, MasteryEffectId.S_CD_MOVE);        if (moveLevel > 0 && cooldownTicks > 0) {
            state.skillMoveUntil = now + 100;
            state.skillMoveRate = (cooldownTicks / 20.0) * (0.01 + 0.002 * moveLevel);
        }

        // 巫术：法力爆发
        int burstLevel = keystoneLevel(player, MasteryEffectId.S_MANA_BURST);
        if (burstLevel > 0 && manaCost > 0) {
            state.manaSpent += manaCost;
            if (state.manaSpent >= fun.wraq.common.attribute.PlayerAttributes.maxMana(player)) {
                state.manaSpent = 0;
                state.manaBurstUntil = now + (10 + burstLevel) * 20;
            }
        }

        // 主宰：暗影突袭
        int ambushLevel = keystoneLevel(player, MasteryEffectId.D_AMBUSH);
        if (ambushLevel > 0 && skillType == 4 && outOfCombat) {
            state.ambushBuffUntil = now + AMBUSH_BUFF_DURATION;
        }

        // 启迪：旅行者（释放终极后失效）
        if (skillType == 4) {
            state.ultimateReleased = true;
        }

        if (state.pendingEmpower) {
            state.pendingEmpower = false;
            state.periodicEmpowerReady = now + 100;
        }
        state.pendingEnergized = false;
    }

    private static void onSkillCastInternal(Player player, State state, long now) {
        int level = keystoneLevel(player, MasteryEffectId.CONQUEROR);
        if (level <= 0) {
            return;
        }
        state.conquerorStacks = Math.min(30 + 3 * level, state.conquerorStacks + 2);
        state.conquerorUntil = now + CONQUEROR_DURATION;
    }

    /** 技能伤害加成（不含暴击结算本身，只返回额外倍率）。 */
    public static double skillDamage(Player player, int skillType) {
        if (player.level().isClientSide) {
            return 0;
        }
        State state = state(player);
        long now = Tick.get();
        cleanup(state, now);

        double rate = MasteryBonusCache.stats(player).value(MasteryStat.SKILL_DAMAGE);
        rate += switch (skillType) {
            case 1 -> MasteryBonusCache.stats(player).value(MasteryStat.SKILL_DAMAGE_1);
            case 2 -> MasteryBonusCache.stats(player).value(MasteryStat.SKILL_DAMAGE_2);
            case 3 -> MasteryBonusCache.stats(player).value(MasteryStat.SKILL_DAMAGE_3);
            default -> 0;
        };

        // 巫术：暴击转化
        int convertLevel = level(player, MasteryEffectId.S_CRIT_CONVERT);
        if (convertLevel > 0) {
            double critRate = fun.wraq.common.attribute.PlayerAttributes.critRate(player);
            double critDamage = fun.wraq.common.attribute.PlayerAttributes.critDamage(player);
            rate += critRate * (1 + critDamage) * 0.10 * convertLevel;
        }

        // 巫术：冥火
        int netherfireLevel = keystoneLevel(player, MasteryEffectId.S_NETHERFIRE);
        if (netherfireLevel > 0) {
            rate += state.netherfireStacks * (0.01 + 0.001 * netherfireLevel);
        }

        if (state.pendingEmpower) {
            int empowerLevel = keystoneLevel(player, MasteryEffectId.S_PERIODIC_EMPOWER);
            rate += 2.0 + 0.10 * empowerLevel;
        }
        if (state.pendingEnergized) {
            int energizedLevel = level(player, MasteryEffectId.D_ENERGIZED);
            rate += 0.20 + 0.02 * energizedLevel;
        }

        // 主宰：暗影突袭的首次终极
        if (skillType == 4) {
            int ambushLevel = keystoneLevel(player, MasteryEffectId.D_AMBUSH);
            if (ambushLevel > 0 && now - state.lastCombatTick >= AMBUSH_OUT_OF_COMBAT) {
                rate += 2.0 + 0.20 * ambushLevel;
            }
        }
        return rate;
    }

    public static int modifySkillCooldown(Player player, int skillType, int baseTicks) {
        double rate = switch (skillType) {
            case 1 -> MasteryBonusCache.stats(player).value(MasteryStat.SKILL_COOLDOWN_1);
            case 2 -> MasteryBonusCache.stats(player).value(MasteryStat.SKILL_COOLDOWN_2);
            case 3 -> MasteryBonusCache.stats(player).value(MasteryStat.SKILL_COOLDOWN_3);
            default -> 0;
        };
        return Math.max(0, (int) Math.round(baseTicks * (1 - rate)));
    }

    public static int modifySkillManaCost(Player player, int skillType, int baseCost) {
        double increase = MasteryBonusCache.stats(player).value(MasteryStat.MANA_COST_INCREASE);
        return Math.max(0, (int) Math.round(baseCost * (1 + increase)));
    }

    // ------------------------------------------------------------------
    // 伤害/减伤/条件
    // ------------------------------------------------------------------

    /** 最终伤害的动态部分（静态部分在 MasteryStats.FINAL_DAMAGE）。 */
    public static double finalDamageRate(Player player, Mob target) {
        if (player.level().isClientSide) {
            return 0;
        }
        State state = state(player);
        long now = Tick.get();
        cleanup(state, now);

        double rate = 0;

        // 主宰：击杀叠层
        int killLevel = keystoneLevel(player, MasteryEffectId.D_KILL_STACKS);
        if (killLevel > 0) {
            rate += state.killStacks * 0.005;
        }

        // 主宰：周围敌人数量
        int manyLevel = level(player, MasteryEffectId.D_SURROUND_MANY);
        int oneLevel = level(player, MasteryEffectId.D_SURROUND_ONE);
        if (manyLevel > 0 || oneLevel > 0) {
            AABB box = player.getBoundingBox().inflate(8);
            List<Mob> nearby = player.level().getEntitiesOfClass(Mob.class, box,
                    mob -> mob.isAlive() && Compute.isWraqMob(mob));
            int count = nearby.size();
            if (manyLevel > 0) {
                rate += Math.min(10, count) / 10.0 * (0.10 + 0.01 * manyLevel);
            }
            if (oneLevel > 0 && count == 1) {
                rate += 0.10 + 0.01 * oneLevel;
            }
        }

        // 主宰：对受控目标
        int controlledLevel = level(player, MasteryEffectId.D_CONTROLLED);
        if (controlledLevel > 0 && isControlled(target)) {
            rate += 0.10 + 0.01 * controlledLevel;
        }
        return rate;
    }

    /** 不依赖目标的条件普通伤害加成。 */
    public static double commonDamageSelf(Player player) {
        if (player.level().isClientSide) {
            return 0;
        }
        State state = state(player);
        long now = Tick.get();
        cleanup(state, now);
        double rate = 0;

        // 精密
        if (level(player, MasteryEffectId.FERVOR) > 0) {
            rate += state.fervorStacks * 0.01;
        }
        if (keystoneLevel(player, MasteryEffectId.CONQUEROR) > 0) {
            rate += state.conquerorStacks * 0.003;
        }
        int lastStandLevel = level(player, MasteryEffectId.LAST_STAND);
        if (lastStandLevel > 0 && player.getHealth() / player.getMaxHealth() < 0.4) {
            rate += 0.08 + 0.008 * lastStandLevel;
        }

        // 坚决：高生命增伤
        int highLowLevel = level(player, MasteryEffectId.R_HIGH_LOW);
        if (highLowLevel > 0 && player.getHealth() / player.getMaxHealth() > 0.5) {
            rate += 0.02 * highLowLevel;
        }

        // 坚决：损失生命增伤
        int missingLevel = keystoneLevel(player, MasteryEffectId.R_MISSING_HEALTH);
        if (missingLevel > 0) {
            double lost = 1 - player.getHealth() / player.getMaxHealth();
            double steps = Math.min(50, lost / 0.02);
            rate += steps * (0.01 + 0.001 * missingLevel);
        }

        // 启迪：水下亲和
        int waterLevel = level(player, MasteryEffectId.I_WATER_AFFINITY);
        if (waterLevel > 0 && player.isInWater()) {
            rate += 0.02 * waterLevel;
        }
        return rate;
    }

    /** 依赖目标生命比例的条件普通伤害加成（精密）。 */
    public static double commonDamageTarget(Player player, Mob target) {
        if (player.level().isClientSide) {
            return 0;
        }
        double selfPercent = player.getHealth() / player.getMaxHealth();
        double targetPercent = target.getHealth() / target.getMaxHealth();
        double rate = 0;

        int bullyLevel = level(player, MasteryEffectId.BULLY);
        if (bullyLevel > 0 && selfPercent > targetPercent) {
            int steps = Math.min(5, (int) ((selfPercent - targetPercent) / 0.10));
            rate += steps * (0.01 + 0.002 * bullyLevel);
        }

        int coupLevel = level(player, MasteryEffectId.COUP_DE_GRACE);
        if (coupLevel > 0 && targetPercent < 0.40) {
            rate += 0.05 + 0.005 * coupLevel;
        }

        int cutDownLevel = level(player, MasteryEffectId.CUT_DOWN);
        if (cutDownLevel > 0 && targetPercent > selfPercent + 0.40) {
            rate += 0.10 + 0.01 * cutDownLevel;
        }
        return rate;
    }

    public static double damageTaken(Player player) {
        return MasteryBonusCache.stats(player).value(MasteryStat.FINAL_DAMAGE_TAKEN);
    }

    public static double dodgeRate(Player player) {
        return MasteryBonusCache.stats(player).value(MasteryStat.DODGE);
    }

    public static double slowResist(Player player) {
        return Math.min(0.9, MasteryBonusCache.stats(player).value(MasteryStat.SLOW_RESIST));
    }

    // ------------------------------------------------------------------
    // 属性动态部分
    // ------------------------------------------------------------------

    public static double attackSpeed(Player player) {
        if (player.level().isClientSide) {
            return 0;
        }
        State state = state(player);
        cleanup(state, Tick.get());
        return keystoneLevel(player, MasteryEffectId.LETHAL_TEMPO) > 0
                ? state.lethalTempoStacks * 0.005 : 0;
    }

    /** 征服者满层提供的 0.1% 全能吸血。 */
    public static double fullConquerorOmnivamp(Player player) {
        if (player.level().isClientSide) {
            return 0;
        }
        int level = keystoneLevel(player, MasteryEffectId.CONQUEROR);
        if (level <= 0) {
            return 0;
        }
        State state = state(player);
        cleanup(state, Tick.get());
        return state.conquerorStacks >= 30 + 3 * level ? 0.001 : 0;
    }

    public static double percentAttack(Player player) {
        if (player.level().isClientSide) {
            return 0;
        }
        State state = state(player);
        long now = Tick.get();
        cleanup(state, now);
        double rate = 0;
        int castLevel = level(player, MasteryEffectId.S_CAST_STACKS);
        if (castLevel > 0) {
            rate += state.castStacks * 0.005;
        }
        int ambushLevel = keystoneLevel(player, MasteryEffectId.D_AMBUSH);
        if (ambushLevel > 0 && now < state.ambushBuffUntil) {
            rate += 0.10 + 0.01 * ambushLevel;
        }
        return rate;
    }

    public static double percentMana(Player player) {
        if (player.level().isClientSide) {
            return 0;
        }
        State state = state(player);
        long now = Tick.get();
        cleanup(state, now);
        double rate = 0;
        int castLevel = level(player, MasteryEffectId.S_CAST_STACKS);
        if (castLevel > 0) {
            rate += state.castStacks * 0.005;
        }
        int ambushLevel = keystoneLevel(player, MasteryEffectId.D_AMBUSH);
        if (ambushLevel > 0 && now < state.ambushBuffUntil) {
            rate += 0.10 + 0.01 * ambushLevel;
        }
        int burstLevel = keystoneLevel(player, MasteryEffectId.S_MANA_BURST);
        if (burstLevel > 0 && now < state.manaBurstUntil) {
            rate += 0.10 + 0.01 * burstLevel;
        }
        return rate;
    }

    public static double magicPenPercent(Player player) {
        if (player.level().isClientSide) {
            return 0;
        }
        State state = state(player);
        long now = Tick.get();
        cleanup(state, now);
        int burstLevel = keystoneLevel(player, MasteryEffectId.S_MANA_BURST);
        return burstLevel > 0 && now < state.manaBurstUntil
                ? 0.10 + 0.01 * burstLevel : 0;
    }

    public static double percentDefenses(Player player) {
        if (player.level().isClientSide) {
            return 0;
        }
        double rate = MasteryBonusCache.stats(player).value(MasteryStat.PERCENT_DEFENSES);
        int ccLevel = level(player, MasteryEffectId.R_CC_DEFENSES);
        if (ccLevel > 0 && isCrowdControlled(player)) {
            rate += 0.03 * ccLevel;
        }
        return rate;
    }

    public static double flatArmorPen(Player player) {
        double value = MasteryBonusCache.stats(player).value(MasteryStat.FLAT_ARMOR_PEN);
        State state = state(player);
        long now = Tick.get();
        cleanup(state, now);
        if (level(player, MasteryEffectId.D_DASH) > 0 && now < state.dashPenUntil) {
            value += 5 + 2 * level(player, MasteryEffectId.D_DASH);
        }
        return value;
    }

    public static double flatMagicPen(Player player) {
        double value = MasteryBonusCache.stats(player).value(MasteryStat.FLAT_MAGIC_PEN);
        State state = state(player);
        long now = Tick.get();
        cleanup(state, now);
        if (level(player, MasteryEffectId.D_DASH) > 0 && now < state.dashPenUntil) {
            value += 5 + 2 * level(player, MasteryEffectId.D_DASH);
        }
        return value;
    }

    public static double moveSpeed(Player player) {
        if (player.level().isClientSide) {
            return 0;
        }
        State state = state(player);
        long now = Tick.get();
        cleanup(state, now);
        double rate = 0;
        int moveLevel = level(player, MasteryEffectId.S_CD_MOVE);
        if (moveLevel > 0 && now < state.skillMoveUntil) {
            rate += state.skillMoveRate;
        }
        int ambushLevel = keystoneLevel(player, MasteryEffectId.D_AMBUSH);
        if (ambushLevel > 0 && now < state.ambushBuffUntil) {
            rate += 0.10 + 0.01 * ambushLevel;
        }
        int travelerLevel = keystoneLevel(player, MasteryEffectId.I_TRAVELER);
        if (travelerLevel > 0 && !state.ultimateReleased) {
            rate += MasteryBonusCache.stats(player).value(MasteryStat.TRAVELER_MOVE);
        }
        return rate;
    }

    public static double outOfCombatMove(Player player) {
        return MasteryBonusCache.stats(player).value(MasteryStat.OUT_OF_COMBAT_MOVE);
    }

    // ------------------------------------------------------------------
    // 击杀 / 受击 / 死亡
    // ------------------------------------------------------------------

    public static void onKill(Player player) {
        if (player.level().isClientSide) {
            return;
        }
        State state = state(player);
        long now = Tick.get();
        cleanup(state, now);

        int triumphLevel = level(player, MasteryEffectId.TRIUMPH);
        if (triumphLevel > 0) {
            double heal = 10 * triumphLevel + player.getMaxHealth() * 0.0001 * triumphLevel;
            Compute.playerHeal(player, heal);
        }

        int killLevel = keystoneLevel(player, MasteryEffectId.D_KILL_STACKS);
        if (killLevel > 0) {
            state.killStacks = Math.min(10 + killLevel, state.killStacks + 1);
            state.killUntil = now + KILL_STACK_DURATION;
        }

        int goldLevel = level(player, MasteryEffectId.I_KILL_GOLD);
        if (goldLevel > 0 && new java.util.Random().nextDouble() < 0.025 + 0.005 * goldLevel) {
            InventoryOperation.giveItemStack(player,
                    new ItemStack(ModItems.COPPER_COIN.get()));
        }
    }

    /** 受到怪物伤害后调用：反伤等。 */
    public static void onPlayerHurt(Player player, Mob attacker, double damage) {
        if (player.level().isClientSide || damage <= 0) {
            return;
        }
        State state = state(player);
        long now = Tick.get();
        cleanup(state, now);
        state.lastCombatTick = now;

        int thornsLevel = level(player, MasteryEffectId.R_THORNS);
        if (thornsLevel > 0) {
            Damage.causeAutoAdaptionRateDamageToMob(player, attacker,
                    0.10 + 0.02 * thornsLevel, false);
        }
    }

    /**
     * 致命伤害拦截。返回 true 表示本次伤害应被取消。
     */
    public static boolean tryUndying(Player player, double damage) {
        if (player.level().isClientSide) {
            return false;
        }
        int level = keystoneLevel(player, MasteryEffectId.R_UNDYING);
        if (level <= 0) {
            return false;
        }
        State state = state(player);
        long now = Tick.get();
        if (now < state.undyingReady || damage < player.getHealth()) {
            return false;
        }
        state.undyingReady = now + (200 - 10 * level) * 20L;
        double maxHealth = player.getMaxHealth();
        player.setHealth((float) Math.max(1, maxHealth * 0.5));
        double maxMana = Mana.getPlayerMaxManaNum(player);
        Mana.addOrCostPlayerMana(player, maxMana * 0.5 - Mana.getPlayerCurrentManaNum(player));
        return true;
    }

    /** 击杀首只目标时的一次性增伤；由 Damage 在最终伤害前调用。 */
    public static double firstStrikeMultiplier(Player player, Mob target) {
        int level = level(player, MasteryEffectId.I_FIRST_STRIKE);
        if (level <= 0) {
            return 1;
        }
        State state = state(player);
        if (state.firstStrikeTargets.add(target.getUUID())) {
            return 1 + 0.05 + 0.005 * level;
        }
        return 1;
    }

    /** 位移技能释放时调用（供后续接入具体位移技能）。 */
    public static void onDash(Player player) {
        if (player.level().isClientSide) {
            return;
        }
        int level = level(player, MasteryEffectId.D_DASH);
        if (level <= 0) {
            return;
        }
        state(player).dashPenUntil = Tick.get() + 60;
    }

    // ------------------------------------------------------------------
    // tick
    // ------------------------------------------------------------------

    public static void handlePlayerTick(Player player) {
        if (player.level().isClientSide) {
            return;
        }
        State state = state(player);
        long now = Tick.get();
        cleanup(state, now);

        // 移动积攒盈能
        int energizedLevel = level(player, MasteryEffectId.D_ENERGIZED);
        if (energizedLevel > 0) {
            if (Double.isNaN(state.lastX)) {
                state.lastX = player.getX();
                state.lastZ = player.getZ();
            } else if (player.distanceToSqr(state.lastX, player.getY(), state.lastZ) > 1.0) {
                state.energizedStacks = Math.min(ENERGIZED_MAX, state.energizedStacks + 1);
                state.lastX = player.getX();
                state.lastZ = player.getZ();
            }
        }

        if (player.tickCount % 20 != 0) {
            return;
        }

        // 坚决：周期护盾
        int shieldLevel = keystoneLevel(player, MasteryEffectId.R_PERIODIC_SHIELD);
        if (shieldLevel > 0 && now >= state.nextPeriodicShieldTick) {
            state.nextPeriodicShieldTick = now + PERIODIC_SHIELD_INTERVAL;
            Shield.providePlayerShield(player, PERIODIC_SHIELD_INTERVAL,
                    player.getMaxHealth() * (0.10 + 0.01 * shieldLevel));
        }

        // 坚决：低生命回复
        int highLowLevel = level(player, MasteryEffectId.R_HIGH_LOW);
        if (highLowLevel > 0 && player.getHealth() / player.getMaxHealth() < 0.5) {
            HealUtil.playerHeal(player, player.getMaxHealth() * 0.004 * highLowLevel);
        }

        // 坚决：损失生命回复
        int missingLevel = keystoneLevel(player, MasteryEffectId.R_MISSING_HEALTH);
        if (missingLevel > 0) {
            double lost = player.getMaxHealth() - player.getHealth();
            if (lost > 0) {
                HealUtil.playerHeal(player, lost * (0.005 + 0.0005 * missingLevel));
            }
        }

        // 巫术：脱战 10 秒后清空法力爆发累计
        if (now - state.lastCombatTick >= 200) {
            state.manaSpent = 0;
        }
    }

    // ------------------------------------------------------------------
    // 内部
    // ------------------------------------------------------------------

    private static boolean isControlled(Mob mob) {
        return mob.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)
                || mob.hasEffect(MobEffects.DIG_SLOWDOWN)
                || mob.hasEffect(MobEffects.WEAKNESS);
    }

    private static boolean isCrowdControlled(Player player) {
        return player.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)
                || player.hasEffect(MobEffects.DIG_SLOWDOWN)
                || player.hasEffect(MobEffects.WEAKNESS);
    }

    public static int outOfCombatWindowTicks(Player player) {
        double reduction = MasteryBonusCache.stats(player).value(MasteryStat.OUT_OF_COMBAT_REDUCTION);
        if (reduction <= 0) {
            return OUT_OF_COMBAT_BASE;
        }
        return Math.max(20, (int) Math.round((8 - reduction) * 20));
    }

    private static State state(Player player) {
        return STATES.computeIfAbsent(player, p -> new State());
    }

    private static void cleanup(State state, long now) {
        if (now > state.fervorUntil) state.fervorStacks = 0;
        if (now > state.conquerorUntil) state.conquerorStacks = 0;
        if (now > state.lethalTempoUntil) state.lethalTempoStacks = 0;
        if (now > state.killUntil) state.killStacks = 0;
        if (now > state.castUntil) state.castStacks = 0;
        if (now > state.netherfireUntil) state.netherfireStacks = 0;
    }

    private static int level(Player player, MasteryEffectId effect) {
        return MasteryBonusCache.get(player).points(effect.tree(), effect.row(), effect.node());
    }

    private static int keystoneLevel(Player player, MasteryEffectId effect) {
        int points = level(player, effect);
        return points == MasteryAllocation.rowCap(MasteryAllocation.ROW_COUNT - 1) ? points : 0;
    }
}
