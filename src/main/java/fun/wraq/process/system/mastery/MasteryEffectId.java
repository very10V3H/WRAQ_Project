/** AI-Generated, 2026-10-25 */
package fun.wraq.process.system.mastery;

import fun.wraq.common.util.ComponentUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.List;

/**
 * 五条路径的全部精通节点定义。
 *
 * <p>这里只描述节点位置、名称与展示文本；静态面板属性在 {@link MasteryStats}，
 * 战斗中的叠层/条件/触发效果在 {@link MasteryCombat}。新增节点时只需：
 * 1. 在本枚举加常量；2. 在 MasteryKeystones（若是基石）登记；3. 在对应属性/战斗入口接入。</p>
 */
public enum MasteryEffectId {

    // ------------------------------------------------------------------
    // 精密
    // ------------------------------------------------------------------
    ALACRITY(MasteryTree.PRECISION, 0, 0, "欢欣"),
    TENACITY(MasteryTree.PRECISION, 0, 1, "坚韧"),
    CELERITY(MasteryTree.PRECISION, 0, 2, "疾行"),

    DOUBLE_EDGED(MasteryTree.PRECISION, 1, 0, "双刃剑"),
    TRIUMPH(MasteryTree.PRECISION, 1, 1, "凯旋"),

    TRANSCENDENCE(MasteryTree.PRECISION, 2, 0, "超然"),
    BLOODLINE(MasteryTree.PRECISION, 2, 1, "血统"),
    DOUBLE_PEN(MasteryTree.PRECISION, 2, 2, "双穿"),

    FERVOR(MasteryTree.PRECISION, 3, 0, "狂热"),
    BULLY(MasteryTree.PRECISION, 3, 1, "恃强凌弱"),

    COUP_DE_GRACE(MasteryTree.PRECISION, 4, 0, "致命一击"),
    LAST_STAND(MasteryTree.PRECISION, 4, 1, "坚毅不倒"),
    CUT_DOWN(MasteryTree.PRECISION, 4, 2, "巨人杀手"),

    CONQUEROR(MasteryTree.PRECISION, 5, 0, "征服者"),
    PRESS_THE_ATTACK(MasteryTree.PRECISION, 5, 1, "强攻"),
    LETHAL_TEMPO(MasteryTree.PRECISION, 5, 2, "致命节奏"),

    // ------------------------------------------------------------------
    // 主宰（名称暂定）
    // ------------------------------------------------------------------
    D_FIXED_PEN(MasteryTree.DOMINATION, 0, 0, "破甲"),
    D_CRIT_RATE(MasteryTree.DOMINATION, 0, 1, "锐意"),
    D_CRIT_DAMAGE(MasteryTree.DOMINATION, 0, 2, "致命"),

    D_FINAL_DAMAGE(MasteryTree.DOMINATION, 1, 0, "伤害至上"),
    D_KILL_STACKS(MasteryTree.DOMINATION, 1, 1, "猎杀收藏"),

    D_OMNIVAMP(MasteryTree.DOMINATION, 2, 0, "嗜血"),
    D_PERCENT_PEN(MasteryTree.DOMINATION, 2, 1, "穿透"),
    D_PERCENT_ATK(MasteryTree.DOMINATION, 2, 2, "力量增幅"),

    D_DASH(MasteryTree.DOMINATION, 3, 0, "灵动"),
    D_ENERGIZED(MasteryTree.DOMINATION, 3, 1, "盈能"),

    D_SURROUND_MANY(MasteryTree.DOMINATION, 4, 0, "众矢之的"),
    D_SURROUND_ONE(MasteryTree.DOMINATION, 4, 1, "孤军深入"),
    D_CONTROLLED(MasteryTree.DOMINATION, 4, 2, "压制"),

    D_AMBUSH(MasteryTree.DOMINATION, 5, 0, "暗影突袭"),
    D_TEN_HITS(MasteryTree.DOMINATION, 5, 1, "连环打击"),
    D_THIRD_HIT(MasteryTree.DOMINATION, 5, 2, "三重奏"),

    // ------------------------------------------------------------------
    // 巫术
    // ------------------------------------------------------------------
    S_SKILL1(MasteryTree.SORCERY, 0, 0, "奥术精通·壹"),
    S_SKILL2(MasteryTree.SORCERY, 0, 1, "奥术精通·贰"),
    S_SKILL3(MasteryTree.SORCERY, 0, 2, "奥术精通·叁"),

    S_CD_MOVE(MasteryTree.SORCERY, 1, 0, "疾风咒"),
    S_CAST_STACKS(MasteryTree.SORCERY, 1, 1, "咒力汇聚"),

    S_CD1(MasteryTree.SORCERY, 2, 0, "节能·壹"),
    S_CD2(MasteryTree.SORCERY, 2, 1, "节能·贰"),
    S_CD3(MasteryTree.SORCERY, 2, 2, "节能·叁"),

    S_MANA_COST_DAMAGE(MasteryTree.SORCERY, 3, 0, "魔力燃烧"),
    S_CRIT_CONVERT(MasteryTree.SORCERY, 3, 1, "暴击转化"),

    S_MAX_MANA(MasteryTree.SORCERY, 4, 0, "法力涌泉"),
    S_MANA_REGEN(MasteryTree.SORCERY, 4, 1, "冥想"),
    S_HASTE(MasteryTree.SORCERY, 4, 2, "急速咏唱"),

    S_NETHERFIRE(MasteryTree.SORCERY, 5, 0, "冥火"),
    S_PERIODIC_EMPOWER(MasteryTree.SORCERY, 5, 1, "奥术涌动"),
    S_MANA_BURST(MasteryTree.SORCERY, 5, 2, "法力爆发"),

    // ------------------------------------------------------------------
    // 坚决
    // ------------------------------------------------------------------
    R_DEFENSES(MasteryTree.RESOLVE, 0, 0, "坚甲"),
    R_HEALTH_REGEN(MasteryTree.RESOLVE, 0, 1, "复苏"),
    R_HEALTH(MasteryTree.RESOLVE, 0, 2, "强健"),

    R_DAMAGE_REDUCTION(MasteryTree.RESOLVE, 1, 0, "铁壁"),
    R_THORNS(MasteryTree.RESOLVE, 1, 1, "荆棘"),

    R_PERCENT_HEALTH(MasteryTree.RESOLVE, 2, 0, "生命洪流"),
    R_PERCENT_DEFENSES(MasteryTree.RESOLVE, 2, 1, "双抗强化"),
    R_PERCENT_REGEN(MasteryTree.RESOLVE, 2, 2, "再生"),

    R_HIGH_LOW(MasteryTree.RESOLVE, 3, 0, "不屈"),
    R_CC_DEFENSES(MasteryTree.RESOLVE, 3, 1, "坚定"),

    R_SHIELD_POWER(MasteryTree.RESOLVE, 4, 0, "护盾强化"),
    R_DODGE(MasteryTree.RESOLVE, 4, 1, "闪避"),
    R_HEAL_POWER(MasteryTree.RESOLVE, 4, 2, "治疗强化"),

    R_PERIODIC_SHIELD(MasteryTree.RESOLVE, 5, 0, "守护者"),
    R_MISSING_HEALTH(MasteryTree.RESOLVE, 5, 1, "背水一战"),
    R_UNDYING(MasteryTree.RESOLVE, 5, 2, "不死图腾"),

    // ------------------------------------------------------------------
    // 启迪
    // ------------------------------------------------------------------
    I_XP(MasteryTree.INSPIRATION, 0, 0, "博学"),
    I_OUT_OF_COMBAT_MOVE(MasteryTree.INSPIRATION, 0, 1, "轻装"),
    I_EXTRA_PRODUCE(MasteryTree.INSPIRATION, 0, 2, "丰饶"),

    I_CONSUMABLE_REFUND(MasteryTree.INSPIRATION, 1, 0, "节俭"),
    I_WATER_AFFINITY(MasteryTree.INSPIRATION, 1, 1, "水下亲和"),

    I_MINING_SPEED(MasteryTree.INSPIRATION, 2, 0, "矿工"),
    I_ELEMENT_STRENGTH(MasteryTree.INSPIRATION, 2, 1, "元素共鸣"),
    I_OUT_OF_COMBAT_TIME(MasteryTree.INSPIRATION, 2, 2, "脱战"),

    I_EXTRA_PRODUCE_2(MasteryTree.INSPIRATION, 3, 0, "丰饶·进阶"),
    I_KILL_GOLD(MasteryTree.INSPIRATION, 3, 1, "淘金"),

    I_EQUIP_PASSIVE(MasteryTree.INSPIRATION, 4, 0, "装备共鸣"),
    I_EQUIP_HASTE(MasteryTree.INSPIRATION, 4, 1, "装备急速"),
    I_EQUIP_STATS(MasteryTree.INSPIRATION, 4, 2, "装备强化"),

    I_TRAVELER(MasteryTree.INSPIRATION, 5, 0, "旅行者"),
    I_FIRST_STRIKE(MasteryTree.INSPIRATION, 5, 1, "先手"),
    I_SLOW_IMMUNE(MasteryTree.INSPIRATION, 5, 2, "自由");

    private final MasteryTree tree;
    private final int row;
    private final int node;
    private final String displayName;

    MasteryEffectId(MasteryTree tree, int row, int node, String displayName) {
        this.tree = tree;
        this.row = row;
        this.node = node;
        this.displayName = displayName;
    }

    public MasteryTree tree() {
        return tree;
    }

    public int row() {
        return row;
    }

    public int node() {
        return node;
    }

    public String displayName() {
        return displayName;
    }

    public List<Component> description(int level) {
        return switch (this) {
            // 精密
            case ALACRITY -> one(ComponentUtils.AttributeDescription.getAttackSpeed(
                    "+" + pct(level * 0.02) + "（每级 +2%）"));
            case TENACITY -> one(ComponentUtils.AttributeDescription.toughness(
                    "+" + pct(level * 0.02) + "（每级 +2%）"));
            case CELERITY -> one(ComponentUtils.AttributeDescription.movementSpeed(
                    "+" + pct(level * 0.01) + "（每级 +1%）"));
            case DOUBLE_EDGED -> list(
                    ComponentUtils.AttributeDescription.commonDamage(
                            "+" + pct(level * 0.01) + "（每级 +1%）"),
                    ComponentUtils.AttributeDescription.damageTaken(
                            "+" + pct(level * 0.01) + "（每级 +1%）"));
            case TRIUMPH -> one(ComponentUtils.AttributeDescription.killHeal(
                    "击杀回复 " + (10 * level) + " + " + pct(level * 0.0001) + " 最大生命"));
            case TRANSCENDENCE -> one(ComponentUtils.AttributeDescription.coolDown(
                    "+" + level + " 点（每级 +1）"));
            case BLOODLINE -> one(ComponentUtils.AttributeDescription.skillHealthSteal(
                    "+" + pct(level * 0.0001) + "（每级 +0.01%）"));
            case DOUBLE_PEN -> list(
                    ComponentUtils.AttributeDescription.defencePenetration(
                            "+" + pct(level * 0.01) + "（每级 +1%）"),
                    ComponentUtils.AttributeDescription.manaPenetration(
                            "+" + pct(level * 0.01) + "（每级 +1%）"));
            case FERVOR -> list(
                    ComponentUtils.AttributeDescription.commonDamage(
                            "+" + pct((5 + level) * 0.01) + "（满层）"),
                    condition("普攻命中叠层，每层 +1%，持续 2 秒"));
            case BULLY -> list(
                    ComponentUtils.AttributeDescription.commonDamage(
                            "+" + pct((0.01 + 0.002 * level) * 5) + "（50% 差值满层）"),
                    condition("自身生命比例每比目标高 10%，多 +"
                            + pct(0.01 + 0.002 * level)));
            case COUP_DE_GRACE -> one(ComponentUtils.AttributeDescription.commonDamage(
                    "+" + pct(0.05 + 0.005 * level) + "（目标生命 <40%）"));
            case LAST_STAND -> one(ComponentUtils.AttributeDescription.commonDamage(
                    "+" + pct(0.08 + 0.008 * level) + "（自身生命 <40%）"));
            case CUT_DOWN -> one(ComponentUtils.AttributeDescription.commonDamage(
                    "+" + pct(0.10 + 0.01 * level) + "（目标生命比例高 40%+）"));
            case CONQUEROR -> list(
                    ComponentUtils.AttributeDescription.commonDamage(
                            "+" + pct((30 + 3 * level) * 0.003) + "（满层）"),
                    ComponentUtils.AttributeDescription.skillHealthSteal("+0.10%（满层）"),
                    condition("技能释放 +2 层，普攻 +1 层，持续 5 秒"));
            case PRESS_THE_ATTACK -> one(ComponentUtils.AttributeDescription.commonDamage(
                    "+" + pct(0.30 + 0.01 * level) + "（每 3 次普攻的单体那一下）"));
            case LETHAL_TEMPO -> list(
                    ComponentUtils.AttributeDescription.getAttackSpeed(
                            "+" + pct((20 + 2 * level) * 0.005) + "（满层）"),
                    condition("普攻命中 +1 层，每层 +0.5%，持续 3 秒"));

            // 主宰
            case D_FIXED_PEN -> list(
                    ComponentUtils.AttributeDescription.defencePenetration("固定 +" + level),
                    ComponentUtils.AttributeDescription.manaPenetration("固定 +" + level));
            case D_CRIT_RATE -> one(ComponentUtils.AttributeDescription.critRate(
                    "+" + pct(level * 0.01) + "（每级 +1%）"));
            case D_CRIT_DAMAGE -> one(ComponentUtils.AttributeDescription.critDamage(
                    "+" + pct(level * 0.01) + "（每级 +1%）"));
            case D_FINAL_DAMAGE -> list(
                    ComponentUtils.AttributeDescription.finalDamage(
                            "+" + pct(level * 0.01) + "（每级 +1%）"),
                    ComponentUtils.AttributeDescription.damageTaken(
                            "+" + pct(level * 0.01) + "（每级 +1%）"));
            case D_KILL_STACKS -> list(
                    ComponentUtils.AttributeDescription.finalDamage(
                            "每层 +0.5%，最多 " + (10 + level) + " 层（满层 +"
                                    + pct((10 + level) * 0.005) + "）"),
                    condition("击杀目标获得层数，持续 10 秒"));
            case D_OMNIVAMP -> one(ComponentUtils.AttributeDescription.skillHealthSteal(
                    "+" + pct(level * 0.0001) + "（每级 +0.01%）"));
            case D_PERCENT_PEN -> list(
                    ComponentUtils.AttributeDescription.defencePenetration(
                            "+" + pct(level * 0.01) + "（每级 +1%）"),
                    ComponentUtils.AttributeDescription.manaPenetration(
                            "+" + pct(level * 0.01) + "（每级 +1%）"));
            case D_PERCENT_ATK -> list(
                    ComponentUtils.AttributeDescription.attackDamage(
                            "+" + pct(level * 0.01) + "（每级 +1%）"),
                    ComponentUtils.AttributeDescription.manaDamage(
                            "+" + pct(level * 0.01) + "（每级 +1%）"));
            case D_DASH -> list(
                    line("位移技能冷却 -" + pct(0.10 + 0.02 * level), ChatFormatting.AQUA),
                    ComponentUtils.AttributeDescription.defencePenetration(
                            "位移后固定 +" + (5 + 2 * level)),
                    condition("位移后 3 秒内获得固定穿透"));
            case D_ENERGIZED -> list(
                    ComponentUtils.AttributeDescription.finalDamage(
                            "下一次攻击/技能 +" + pct(0.20 + 0.02 * level)),
                    condition("移动、攻击、施法积攒层数，满层后消耗"));
            case D_SURROUND_MANY -> list(
                    ComponentUtils.AttributeDescription.finalDamage(
                            "最多 +" + pct(0.10 + 0.01 * level)),
                    condition("周围敌人越多越高，10 名敌人时达到上限"));
            case D_SURROUND_ONE -> list(
                    ComponentUtils.AttributeDescription.finalDamage(
                            "+" + pct(0.10 + 0.01 * level)),
                    condition("周围仅 1 名敌人时生效"));
            case D_CONTROLLED -> list(
                    ComponentUtils.AttributeDescription.finalDamage(
                            "+" + pct(0.10 + 0.01 * level)),
                    condition("对减速、控制中的目标生效"));
            case D_AMBUSH -> list(
                    ComponentUtils.AttributeDescription.skillDamage(
                            "首次终极 +" + pct(2.0 + 0.2 * level) + "（总倍率 "
                                    + pct(3.0 + 0.2 * level) + "）"),
                    line("随后 60 秒：攻击力/法术强度/移速 +"
                            + pct(0.10 + 0.01 * level), ChatFormatting.GREEN),
                    condition("脱离战斗 30 秒后首次释放终极触发"));
            case D_TEN_HITS -> list(
                    ComponentUtils.AttributeDescription.commonDamage(
                            "第 10 次攻击后 +" + pct(0.50 + 0.05 * level)
                                    + " 范围普攻"),
                    condition("半径 5 格，命中主目标附近敌人"));
            case D_THIRD_HIT -> list(
                    ComponentUtils.AttributeDescription.commonDamage(
                            "每 3 次普攻 +" + pct(0.10 + 0.01 * level)
                                    + " 范围普攻"),
                    condition("命中目标及其周围敌人"));

            // 巫术
            case S_SKILL1 -> one(ComponentUtils.AttributeDescription.skillDamage(
                    "技能1 +" + pct(level * 0.01) + "（每级 +1%）"));
            case S_SKILL2 -> one(ComponentUtils.AttributeDescription.skillDamage(
                    "技能2 +" + pct(level * 0.01) + "（每级 +1%）"));
            case S_SKILL3 -> one(ComponentUtils.AttributeDescription.skillDamage(
                    "技能3 +" + pct(level * 0.01) + "（每级 +1%）"));
            case S_CD_MOVE -> list(
                    ComponentUtils.AttributeDescription.movementSpeed(
                            "每 1 秒冷却 +" + pct(0.01 + 0.002 * level) + "，持续 5 秒"),
                    condition("释放技能后按该技能冷却时间计算"));
            case S_CAST_STACKS -> list(
                    ComponentUtils.AttributeDescription.attackDamage(
                            "每层 +0.5% 攻击力"),
                    ComponentUtils.AttributeDescription.manaDamage(
                            "每层 +0.5% 法术强度"),
                    condition("释放技能 +1 层，最多 " + (10 + 2 * level)
                            + " 层，持续 5 秒"));
            case S_CD1 -> one(line("技能1 冷却 -" + pct(level * 0.01)
                    + "（每级 +1%）", ChatFormatting.AQUA));
            case S_CD2 -> one(line("技能2 冷却 -" + pct(level * 0.01)
                    + "（每级 +1%）", ChatFormatting.AQUA));
            case S_CD3 -> one(line("技能3 冷却 -" + pct(level * 0.01)
                    + "（每级 +1%）", ChatFormatting.AQUA));
            case S_MANA_COST_DAMAGE -> list(
                    line("技能法力消耗 +" + pct(level * 0.10)
                            + "（每级 +10%）", ChatFormatting.AQUA),
                    ComponentUtils.AttributeDescription.skillDamage(
                            "+" + pct(level * 0.05) + "（每级 +5%）"));
            case S_CRIT_CONVERT -> list(
                    ComponentUtils.AttributeDescription.skillDamage(
                            "暴击几率 × (1 + 暴击伤害) × " + pct(level * 0.10)),
                    condition("将暴击属性转化为技能伤害"));
            case S_MAX_MANA -> one(ComponentUtils.AttributeDescription.maxMana(
                    "+" + (level * 30) + "（每级 +30）"));
            case S_MANA_REGEN -> one(ComponentUtils.AttributeDescription.manaRecover(
                    "+" + (level * 2) + "（每级 +2）"));
            case S_HASTE -> one(ComponentUtils.AttributeDescription.coolDown(
                    "+" + pct(level * 0.015) + "（每级 +1.5 点）"));
            case S_NETHERFIRE -> list(
                    ComponentUtils.AttributeDescription.skillDamage(
                            "每层 +" + pct(0.01 + 0.001 * level) + " 魔法伤害"),
                    condition("技能命中叠层，持续 5 秒，可无限叠加"));
            case S_PERIODIC_EMPOWER -> list(
                    ComponentUtils.AttributeDescription.skillDamage(
                            "下一次技能 +" + pct(2.0 + 0.10 * level)),
                    condition("同时无法力消耗，冷却 5 秒"));
            case S_MANA_BURST -> list(
                    ComponentUtils.AttributeDescription.manaDamage(
                            "+" + pct(0.10 + 0.01 * level)),
                    ComponentUtils.AttributeDescription.manaPenetration(
                            "+" + pct(0.10 + 0.01 * level)),
                    condition("累计消耗 100% 最大法力，持续 "
                            + (10 + level) + " 秒；脱战 10 秒后清空累计"));

            // 坚决
            case R_DEFENSES -> list(
                    ComponentUtils.AttributeDescription.defence("+" + (level * 2)),
                    ComponentUtils.AttributeDescription.manaDefence("+" + (level * 2)));
            case R_HEALTH_REGEN -> one(ComponentUtils.AttributeDescription.healthRecover(
                    "+" + level + "（每级 +1）"));
            case R_HEALTH -> one(ComponentUtils.AttributeDescription.maxHealth(
                    "+" + (level * 50) + " + " + pct(level * 0.005)));
            case R_DAMAGE_REDUCTION -> one(line("伤害减免 +"
                    + pct(level * 0.02) + "（每级 +2%）", ChatFormatting.GREEN));
            case R_THORNS -> list(
                    ComponentUtils.AttributeDescription.thorns(
                            "+" + pct(0.10 + 0.02 * level)),
                    condition("受到伤害时按手持武器类型造成自适应伤害"));
            case R_PERCENT_HEALTH -> one(ComponentUtils.AttributeDescription.maxHealth(
                    "+" + pct(level * 0.01) + " 最大生命"));
            case R_PERCENT_DEFENSES -> list(
                    ComponentUtils.AttributeDescription.defence(
                            "+" + pct(level * 0.01) + " 护甲"),
                    ComponentUtils.AttributeDescription.manaDefence(
                            "+" + pct(level * 0.01) + " 魔抗"));
            case R_PERCENT_REGEN -> one(ComponentUtils.AttributeDescription.healthRecover(
                    "+" + pct(level * 0.001) + " 最大生命/秒"));
            case R_HIGH_LOW -> list(
                    ComponentUtils.AttributeDescription.commonDamage(
                            "生命 >50%：+" + pct(level * 0.02)),
                    ComponentUtils.AttributeDescription.healthRecover(
                            "生命 <50%：+" + pct(level * 0.004)));
            case R_CC_DEFENSES -> list(
                    ComponentUtils.AttributeDescription.defence(
                            "受控时 +" + pct(level * 0.03)),
                    condition("减速/控制期间获得双抗加成"));
            case R_SHIELD_POWER -> one(ComponentUtils.AttributeDescription.shieldPower(
                    "+" + pct(level * 0.02) + "（每级 +2%）"));
            case R_DODGE -> one(ComponentUtils.AttributeDescription.dodgeRate(
                    "+" + pct(level * 0.01) + "（每级 +1%）"));
            case R_HEAL_POWER -> one(line("治疗强度 +"
                    + pct(level * 0.01) + "（每级 +1%）", ChatFormatting.GREEN));
            case R_PERIODIC_SHIELD -> list(
                    ComponentUtils.AttributeDescription.shieldPower(
                            "每 10 秒获得 " + pct(0.10 + 0.01 * level)
                                    + " 最大生命护盾，持续 10 秒"),
                    condition("护盾值受护盾强度影响"));
            case R_MISSING_HEALTH -> list(
                    ComponentUtils.AttributeDescription.commonDamage(
                            "每损失 2% 生命 +" + pct(0.01 + 0.001 * level)),
                    ComponentUtils.AttributeDescription.healthRecover(
                            "每秒回复 " + pct(0.005 + 0.0005 * level) + " 已损失生命"));
            case R_UNDYING -> list(
                    line("致命伤害时回复 50% 最大生命与 50% 最大法力",
                            ChatFormatting.GREEN),
                    condition("冷却 " + (200 - 10 * level) + " 秒，每级 -10 秒"));

            // 启迪
            case I_XP -> one(ComponentUtils.AttributeDescription.experienceBonus(
                    "+" + pct(level * 0.02) + "（每级 +2%）"));
            case I_OUT_OF_COMBAT_MOVE -> one(ComponentUtils.AttributeDescription.movementSpeed(
                    "脱战 +" + pct(level * 0.02) + "（每级 +2%）"));
            case I_EXTRA_PRODUCE -> one(ComponentUtils.AttributeDescription.extraProduce(
                    "+" + pct(level * 0.01) + "（每级 +1%）"));
            case I_CONSUMABLE_REFUND -> one(line("使用药水时 "
                    + pct(level * 0.02) + " 概率返还（每级 +2%）", ChatFormatting.GREEN));
            case I_WATER_AFFINITY -> list(
                    ComponentUtils.AttributeDescription.commonDamage(
                            "水下 +" + pct(level * 0.02)),
                    line("水下游泳速度 +" + pct(level * 0.10), ChatFormatting.AQUA));
            case I_MINING_SPEED -> one(ComponentUtils.AttributeDescription.miningSpeed(
                    "+" + pct(level * 0.02) + "（每级 +2%）"));
            case I_ELEMENT_STRENGTH -> one(line("元素强度 +"
                    + pct(level * 0.01) + "（每级 +1%）", ChatFormatting.AQUA));
            case I_OUT_OF_COMBAT_TIME -> one(line("脱战时间 8 秒 - "
                    + String.format("%.1f", level * 0.4) + " 秒", ChatFormatting.GRAY));
            case I_EXTRA_PRODUCE_2 -> one(ComponentUtils.AttributeDescription.extraProduce(
                    "+" + pct(level * 0.01) + "（每级 +1%）"));
            case I_KILL_GOLD -> one(line("击杀有 " + pct(0.025 + 0.005 * level)
                    + " 概率额外获得铜币", ChatFormatting.GOLD));
            case I_EQUIP_PASSIVE -> one(ComponentUtils.AttributeDescription.equipmentPower(
                    "+" + pct(level * 0.01) + "（每级 +1%）"));
            case I_EQUIP_HASTE -> one(line("装备被动/主动冷却 -"
                    + pct(level * 0.02) + "（每级 +2%）", ChatFormatting.AQUA));
            case I_EQUIP_STATS -> one(ComponentUtils.AttributeDescription.equipmentPower(
                    "基础数值 +" + pct(level * 0.01) + "（每级 +1%）"));
            case I_TRAVELER -> one(ComponentUtils.AttributeDescription.movementSpeed(
                    "未释放终极时 +" + pct(level * 0.03) + "（每级 +3%）"));
            case I_FIRST_STRIKE -> list(
                    ComponentUtils.AttributeDescription.firstStrike(
                            "+" + pct(0.05 + 0.005 * level)),
                    condition("对每只怪物的第一次攻击生效"));
            case I_SLOW_IMMUNE -> one(ComponentUtils.AttributeDescription.slowResist(
                    "+" + pct(level * 0.03) + "（每级 +3%）"));
        };
    }

    private static List<Component> one(Component component) {
        return List.of(component);
    }

    private static List<Component> list(Component... components) {
        return List.of(components);
    }

    private static Component line(String text, ChatFormatting color) {
        return Component.literal(text).withStyle(color);
    }

    private static Component condition(String text) {
        return Component.literal(text).withStyle(ChatFormatting.GRAY);
    }

    private static String pct(double value) {
        return String.format("%.2f%%", value * 100);
    }

    public static MasteryEffectId at(MasteryTree tree, int row, int node) {
        for (MasteryEffectId id : values()) {
            if (id.tree == tree && id.row == row && id.node == node) {
                return id;
            }
        }
        return null;
    }

    public static MasteryEffectId at(int tree, int row, int node) {
        return at(MasteryTree.byIndex(tree), row, node);
    }
}
