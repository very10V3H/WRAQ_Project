/** AI-Generated, 2026-10-25 */
package fun.wraq.process.system.mastery;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/**
 * 精通提供的、不依赖战斗上下文的被动属性。
 *
 * <p>百分比统一用小数表示：0.02 = 2%。动态叠层/条件效果在 {@link MasteryCombat} 中计算。</p>
 */
public final class MasteryStats {

    public static final MasteryStats EMPTY = new MasteryStats(Collections.emptyMap());
    private final Map<MasteryStat, Double> values;

    private MasteryStats(Map<MasteryStat, Double> values) {
        this.values = values;
    }

    public static MasteryStats empty() {
        return EMPTY;
    }

    public double value(MasteryStat stat) {
        return values.getOrDefault(stat, 0.0);
    }

    // 兼容精密界面/旧属性代码的便捷读取
    public double attackSpeed() { return value(MasteryStat.ATTACK_SPEED); }
    public double tenacity() { return value(MasteryStat.TENACITY); }
    public double moveSpeed() { return value(MasteryStat.MOVE_SPEED); }
    public double haste() { return value(MasteryStat.HASTE); }
    public double omnivamp() { return value(MasteryStat.OMNIVAMP); }
    public double armorPen() { return value(MasteryStat.ARMOR_PEN); }
    public double magicPen() { return value(MasteryStat.MAGIC_PEN); }
    public double damageDealt() { return value(MasteryStat.DAMAGE_DEALT); }
    public double damageTaken() { return value(MasteryStat.DAMAGE_TAKEN); }

    public static MasteryStats of(int[][] allocation) {
        Builder builder = new Builder();
        for (MasteryEffectId effect : MasteryEffectId.values()) {
            int level = allocation[effect.tree().ordinal()]
                    [MasteryAllocation.index(effect.row(), effect.node())];
            if (level <= 0) {
                continue;
            }
            addStatic(builder, effect, level);
        }
        return builder.build();
    }

    /**
     * 所有“只影响面板属性”的精通节点在这里集中登记。
     * 条件伤害、叠层、护盾、位移等动态效果不要写到这里。
     */
    private static void addStatic(Builder b, MasteryEffectId effect, int level) {
        switch (effect) {
            // ---------------- 精密 ----------------
            case ALACRITY -> b.add(MasteryStat.ATTACK_SPEED, level * 0.02);
            case TENACITY -> b.add(MasteryStat.TENACITY, level * 0.02);
            case CELERITY -> b.add(MasteryStat.MOVE_SPEED, level * 0.01);
            case DOUBLE_EDGED -> {
                b.add(MasteryStat.DAMAGE_DEALT, level * 0.01);
                b.add(MasteryStat.DAMAGE_TAKEN, level * 0.01);
            }
            case TRANSCENDENCE -> b.add(MasteryStat.HASTE, level * 0.01);
            case BLOODLINE -> b.add(MasteryStat.OMNIVAMP, level * 0.0001);
            case DOUBLE_PEN -> {
                b.add(MasteryStat.ARMOR_PEN, level * 0.01);
                b.add(MasteryStat.MAGIC_PEN, level * 0.01);
            }

            // ---------------- 主宰 ----------------
            case D_FIXED_PEN -> {
                b.add(MasteryStat.FLAT_ARMOR_PEN, level);
                b.add(MasteryStat.FLAT_MAGIC_PEN, level);
            }
            case D_CRIT_RATE -> b.add(MasteryStat.CRIT_RATE, level * 0.01);
            case D_CRIT_DAMAGE -> b.add(MasteryStat.CRIT_DAMAGE, level * 0.01);
            case D_FINAL_DAMAGE -> {
                b.add(MasteryStat.FINAL_DAMAGE, level * 0.01);
                b.add(MasteryStat.FINAL_DAMAGE_TAKEN, level * 0.01);
            }
            case D_PERCENT_PEN -> {
                b.add(MasteryStat.ARMOR_PEN, level * 0.01);
                b.add(MasteryStat.MAGIC_PEN, level * 0.01);
            }
            case D_PERCENT_ATK -> {
                b.add(MasteryStat.PERCENT_ATTACK, level * 0.01);
                b.add(MasteryStat.PERCENT_MANA, level * 0.01);
            }
            case D_OMNIVAMP -> b.add(MasteryStat.OMNIVAMP, level * 0.0001);

            // ---------------- 巫术 ----------------
            case S_SKILL1 -> b.add(MasteryStat.SKILL_DAMAGE_1, level * 0.01);
            case S_SKILL2 -> b.add(MasteryStat.SKILL_DAMAGE_2, level * 0.01);
            case S_SKILL3 -> b.add(MasteryStat.SKILL_DAMAGE_3, level * 0.01);
            case S_CD1 -> b.add(MasteryStat.SKILL_COOLDOWN_1, level * 0.01);
            case S_CD2 -> b.add(MasteryStat.SKILL_COOLDOWN_2, level * 0.01);
            case S_CD3 -> b.add(MasteryStat.SKILL_COOLDOWN_3, level * 0.01);
            case S_MANA_COST_DAMAGE -> {
                b.add(MasteryStat.MANA_COST_INCREASE, level * 0.10);
                b.add(MasteryStat.SKILL_DAMAGE, level * 0.05);
            }
            case S_MAX_MANA -> b.add(MasteryStat.MAX_MANA, level * 30);
            case S_MANA_REGEN -> b.add(MasteryStat.MANA_REGEN, level * 2);
            case S_HASTE -> b.add(MasteryStat.HASTE, level * 0.015);

            // ---------------- 坚决 ----------------
            case R_DEFENSES -> {
                b.add(MasteryStat.ARMOR, level * 2);
                b.add(MasteryStat.MAGIC_RESIST, level * 2);
            }
            case R_HEALTH_REGEN -> b.add(MasteryStat.HEALTH_REGEN, level);
            case R_HEALTH -> {
                b.add(MasteryStat.HEALTH, level * 50);
                b.add(MasteryStat.PERCENT_HEALTH, level * 0.005);
            }
            case R_DAMAGE_REDUCTION -> b.add(MasteryStat.DAMAGE_REDUCTION, level * 0.02);
            case R_THORNS -> b.add(MasteryStat.THORNS, 0.10 + level * 0.02);
            case R_PERCENT_HEALTH -> b.add(MasteryStat.PERCENT_HEALTH, level * 0.01);
            case R_PERCENT_DEFENSES -> b.add(MasteryStat.PERCENT_DEFENSES, level * 0.01);
            case R_PERCENT_REGEN -> b.add(MasteryStat.PERCENT_HEALTH_REGEN, level * 0.001);
            case R_SHIELD_POWER -> b.add(MasteryStat.SHIELD_POWER, level * 0.02);
            case R_DODGE -> b.add(MasteryStat.DODGE, level * 0.01);
            case R_HEAL_POWER -> b.add(MasteryStat.HEAL_POWER, level * 0.01);

            // ---------------- 启迪 ----------------
            case I_XP -> b.add(MasteryStat.XP_BONUS, level * 0.02);
            case I_OUT_OF_COMBAT_MOVE -> b.add(MasteryStat.OUT_OF_COMBAT_MOVE, level * 0.02);
            case I_EXTRA_PRODUCE, I_EXTRA_PRODUCE_2 -> b.add(MasteryStat.EXTRA_PRODUCE, level * 0.01);
            case I_CONSUMABLE_REFUND -> b.add(MasteryStat.CONSUMABLE_REFUND, level * 0.02);
            case I_WATER_AFFINITY -> b.add(MasteryStat.WATER_MOVE, level * 0.10);
            case I_MINING_SPEED -> b.add(MasteryStat.MINING_SPEED, level * 0.02);
            case I_ELEMENT_STRENGTH -> b.add(MasteryStat.ELEMENT_STRENGTH, level * 0.01);
            case I_OUT_OF_COMBAT_TIME -> b.add(MasteryStat.OUT_OF_COMBAT_REDUCTION, level * 0.4);
            case I_KILL_GOLD -> b.add(MasteryStat.KILL_GOLD_CHANCE, 0.025 + level * 0.005);
            case I_EQUIP_PASSIVE -> b.add(MasteryStat.EQUIP_PASSIVE, level * 0.01);
            case I_EQUIP_HASTE -> b.add(MasteryStat.EQUIP_HASTE, level * 0.02);
            case I_EQUIP_STATS -> b.add(MasteryStat.EQUIP_STATS, level * 0.01);
            case I_TRAVELER -> b.add(MasteryStat.TRAVELER_MOVE, level * 0.03);
            case I_FIRST_STRIKE -> b.add(MasteryStat.FIRST_STRIKE, 0.05 + level * 0.005);
            case I_SLOW_IMMUNE -> b.add(MasteryStat.SLOW_RESIST, level * 0.03);

            default -> {
                // 动态/基石效果由 MasteryCombat 处理
            }
        }
    }

    public static final class Builder {
        private final EnumMap<MasteryStat, Double> values = new EnumMap<>(MasteryStat.class);

        public Builder add(MasteryStat stat, double value) {
            if (value != 0) {
                values.merge(stat, value, Double::sum);
            }
            return this;
        }

        public MasteryStats build() {
            return values.isEmpty()
                    ? EMPTY : new MasteryStats(Collections.unmodifiableMap(new EnumMap<>(values)));
        }
    }
}
