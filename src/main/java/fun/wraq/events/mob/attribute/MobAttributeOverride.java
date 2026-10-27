package fun.wraq.events.mob.attribute;

import fun.wraq.common.attribute.MobAttributes;

import java.util.List;
import java.util.Locale;

/**
 * AI-Generated, 2026-10-27
 * 怪物属性覆盖的不可变数据模型。仅保存命令显式修改过的字段，
 * 未修改字段为 null，解析时沿用基础属性。
 */
public record MobAttributeOverride(
        Double attackDamage,
        Double defence,
        Double manaDefence,
        Double critRate,
        Double critDamage,
        Double defencePenetration,
        Double defencePenetration0,
        Double healthSteal,
        Double maxHealth,
        Double movementSpeed,
        Double healthRecover,
        Double percentHealthRecover
) {

    public static final List<String> FIELDS = List.of(
            "attackDamage", "defence", "manaDefence", "critRate", "critDamage",
            "defencePenetration", "defencePenetration0", "healthSteal",
            "maxHealth", "movementSpeed", "healthRecover", "percentHealthRecover"
    );

    public static final MobAttributeOverride EMPTY = new MobAttributeOverride(
            null, null, null, null, null, null, null, null, null, null, null, null);

    /** 将命令输入规范化为字段名；未知字段返回 null。 */
    public static String normalizeField(String input) {
        if (input == null) {
            return null;
        }
        return switch (input.trim().toLowerCase(Locale.ROOT)) {
            case "attackdamage", "attack", "atk", "damage" -> "attackDamage";
            case "defence", "defense", "def" -> "defence";
            case "manadefence", "manadefense", "mdef" -> "manaDefence";
            case "critrate", "crit" -> "critRate";
            case "critdamage", "critdmg" -> "critDamage";
            case "defencepenetration", "defpen" -> "defencePenetration";
            case "defencepenetration0", "defpen0" -> "defencePenetration0";
            case "healthsteal", "lifesteal" -> "healthSteal";
            case "maxhealth", "health", "hp", "maxhp" -> "maxHealth";
            case "movementspeed", "speed" -> "movementSpeed";
            case "healthrecover", "healthregen" -> "healthRecover";
            case "percenthealthrecover", "percenthealthregen", "percentregen" -> "percentHealthRecover";
            default -> null;
        };
    }

    /** 校验并规范化数值：最大生命至少为 1，其余属性不为负。 */
    public static double validateValue(String field, double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("属性值必须是有限数值");
        }
        return "maxHealth".equals(field) ? Math.max(1.0D, value) : Math.max(0.0D, value);
    }

    public MobAttributeOverride with(String field, double value) {
        return switch (field) {
            case "attackDamage" -> new MobAttributeOverride(value, defence, manaDefence, critRate, critDamage,
                    defencePenetration, defencePenetration0, healthSteal, maxHealth, movementSpeed,
                    healthRecover, percentHealthRecover);
            case "defence" -> new MobAttributeOverride(attackDamage, value, manaDefence, critRate, critDamage,
                    defencePenetration, defencePenetration0, healthSteal, maxHealth, movementSpeed,
                    healthRecover, percentHealthRecover);
            case "manaDefence" -> new MobAttributeOverride(attackDamage, defence, value, critRate, critDamage,
                    defencePenetration, defencePenetration0, healthSteal, maxHealth, movementSpeed,
                    healthRecover, percentHealthRecover);
            case "critRate" -> new MobAttributeOverride(attackDamage, defence, manaDefence, value, critDamage,
                    defencePenetration, defencePenetration0, healthSteal, maxHealth, movementSpeed,
                    healthRecover, percentHealthRecover);
            case "critDamage" -> new MobAttributeOverride(attackDamage, defence, manaDefence, critRate, value,
                    defencePenetration, defencePenetration0, healthSteal, maxHealth, movementSpeed,
                    healthRecover, percentHealthRecover);
            case "defencePenetration" -> new MobAttributeOverride(attackDamage, defence, manaDefence, critRate, critDamage,
                    value, defencePenetration0, healthSteal, maxHealth, movementSpeed,
                    healthRecover, percentHealthRecover);
            case "defencePenetration0" -> new MobAttributeOverride(attackDamage, defence, manaDefence, critRate, critDamage,
                    defencePenetration, value, healthSteal, maxHealth, movementSpeed,
                    healthRecover, percentHealthRecover);
            case "healthSteal" -> new MobAttributeOverride(attackDamage, defence, manaDefence, critRate, critDamage,
                    defencePenetration, defencePenetration0, value, maxHealth, movementSpeed,
                    healthRecover, percentHealthRecover);
            case "maxHealth" -> new MobAttributeOverride(attackDamage, defence, manaDefence, critRate, critDamage,
                    defencePenetration, defencePenetration0, healthSteal, value, movementSpeed,
                    healthRecover, percentHealthRecover);
            case "movementSpeed" -> new MobAttributeOverride(attackDamage, defence, manaDefence, critRate, critDamage,
                    defencePenetration, defencePenetration0, healthSteal, maxHealth, value,
                    healthRecover, percentHealthRecover);
            case "healthRecover" -> new MobAttributeOverride(attackDamage, defence, manaDefence, critRate, critDamage,
                    defencePenetration, defencePenetration0, healthSteal, maxHealth, movementSpeed,
                    value, percentHealthRecover);
            case "percentHealthRecover" -> new MobAttributeOverride(attackDamage, defence, manaDefence, critRate, critDamage,
                    defencePenetration, defencePenetration0, healthSteal, maxHealth, movementSpeed,
                    healthRecover, value);
            default -> throw new IllegalArgumentException("Unknown attribute field: " + field);
        };
    }

    public MobAttributeOverride without(String field) {
        return switch (field) {
            case "attackDamage" -> new MobAttributeOverride(null, defence, manaDefence, critRate, critDamage,
                    defencePenetration, defencePenetration0, healthSteal, maxHealth, movementSpeed,
                    healthRecover, percentHealthRecover);
            case "defence" -> new MobAttributeOverride(attackDamage, null, manaDefence, critRate, critDamage,
                    defencePenetration, defencePenetration0, healthSteal, maxHealth, movementSpeed,
                    healthRecover, percentHealthRecover);
            case "manaDefence" -> new MobAttributeOverride(attackDamage, defence, null, critRate, critDamage,
                    defencePenetration, defencePenetration0, healthSteal, maxHealth, movementSpeed,
                    healthRecover, percentHealthRecover);
            case "critRate" -> new MobAttributeOverride(attackDamage, defence, manaDefence, null, critDamage,
                    defencePenetration, defencePenetration0, healthSteal, maxHealth, movementSpeed,
                    healthRecover, percentHealthRecover);
            case "critDamage" -> new MobAttributeOverride(attackDamage, defence, manaDefence, critRate, null,
                    defencePenetration, defencePenetration0, healthSteal, maxHealth, movementSpeed,
                    healthRecover, percentHealthRecover);
            case "defencePenetration" -> new MobAttributeOverride(attackDamage, defence, manaDefence, critRate, critDamage,
                    null, defencePenetration0, healthSteal, maxHealth, movementSpeed,
                    healthRecover, percentHealthRecover);
            case "defencePenetration0" -> new MobAttributeOverride(attackDamage, defence, manaDefence, critRate, critDamage,
                    defencePenetration, null, healthSteal, maxHealth, movementSpeed,
                    healthRecover, percentHealthRecover);
            case "healthSteal" -> new MobAttributeOverride(attackDamage, defence, manaDefence, critRate, critDamage,
                    defencePenetration, defencePenetration0, null, maxHealth, movementSpeed,
                    healthRecover, percentHealthRecover);
            case "maxHealth" -> new MobAttributeOverride(attackDamage, defence, manaDefence, critRate, critDamage,
                    defencePenetration, defencePenetration0, healthSteal, null, movementSpeed,
                    healthRecover, percentHealthRecover);
            case "movementSpeed" -> new MobAttributeOverride(attackDamage, defence, manaDefence, critRate, critDamage,
                    defencePenetration, defencePenetration0, healthSteal, maxHealth, null,
                    healthRecover, percentHealthRecover);
            case "healthRecover" -> new MobAttributeOverride(attackDamage, defence, manaDefence, critRate, critDamage,
                    defencePenetration, defencePenetration0, healthSteal, maxHealth, movementSpeed,
                    null, percentHealthRecover);
            case "percentHealthRecover" -> new MobAttributeOverride(attackDamage, defence, manaDefence, critRate, critDamage,
                    defencePenetration, defencePenetration0, healthSteal, maxHealth, movementSpeed,
                    healthRecover, null);
            default -> throw new IllegalArgumentException("Unknown attribute field: " + field);
        };
    }

    public Double get(String field) {
        return switch (field) {
            case "attackDamage" -> attackDamage;
            case "defence" -> defence;
            case "manaDefence" -> manaDefence;
            case "critRate" -> critRate;
            case "critDamage" -> critDamage;
            case "defencePenetration" -> defencePenetration;
            case "defencePenetration0" -> defencePenetration0;
            case "healthSteal" -> healthSteal;
            case "maxHealth" -> maxHealth;
            case "movementSpeed" -> movementSpeed;
            case "healthRecover" -> healthRecover;
            case "percentHealthRecover" -> percentHealthRecover;
            default -> null;
        };
    }

    public boolean isEmpty() {
        return attackDamage == null && defence == null && manaDefence == null
                && critRate == null && critDamage == null
                && defencePenetration == null && defencePenetration0 == null
                && healthSteal == null && maxHealth == null && movementSpeed == null
                && healthRecover == null && percentHealthRecover == null;
    }

    /** 将覆盖字段应用到基础属性，未覆盖字段沿用基础值。 */
    public MobAttributes applyTo(MobAttributes base) {
        return new MobAttributes(
                valueOr(attackDamage, base.attackDamage),
                valueOr(defence, base.defence),
                valueOr(manaDefence, base.manaDefence),
                valueOr(critRate, base.critRate),
                valueOr(critDamage, base.critDamage),
                valueOr(defencePenetration, base.defencePenetration),
                valueOr(defencePenetration0, base.defencePenetration0),
                valueOr(healthSteal, base.healthSteal),
                valueOr(maxHealth, base.maxHealth),
                valueOr(movementSpeed, base.movementSpeed),
                valueOr(healthRecover, base.healthRecover),
                valueOr(percentHealthRecover, base.percentHealthRecover)
        );
    }

    private static double valueOr(Double value, double fallback) {
        return value == null ? fallback : value;
    }
}
