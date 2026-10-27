package fun.wraq.events.mob.attribute;

import fun.wraq.common.attribute.MobAttributes;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * AI-Generated, 2026-10-27
 * 普通 MobSpawnController 怪物的运行时属性覆盖。覆盖值在召唤时应用，
 * 不修改已经生成的怪物，也不写磁盘。
 */
public final class MobSpawnControllerAttributeOverrides {

    private static final Map<String, MobAttributeOverride> OVERRIDES = new LinkedHashMap<>();

    private MobSpawnControllerAttributeOverrides() {
    }

    /**
     * 先按刷新点倍率缩放基础属性，再应用覆盖字段。
     * 覆盖字段是最终绝对值，不会被刷新点倍率再次放大。
     */
    public static MobAttributes resolve(String controllerId, MobAttributes base, double rate) {
        if (base == null) {
            return null;
        }
        MobAttributes scaled = scale(base, rate);
        MobAttributeOverride override = OVERRIDES.get(controllerId);
        return override == null ? scaled : override.applyTo(scaled);
    }

    public static void set(String controllerId, String field, double value) {
        String normalized = MobAttributeOverride.normalizeField(field);
        if (normalized == null) {
            throw new IllegalArgumentException("未知属性: " + field);
        }
        double validated = MobAttributeOverride.validateValue(normalized, value);
        MobAttributeOverride current = OVERRIDES.getOrDefault(controllerId, MobAttributeOverride.EMPTY);
        OVERRIDES.put(controllerId, current.with(normalized, validated));
    }

    public static boolean reset(String controllerId, String field) {
        String normalized = MobAttributeOverride.normalizeField(field);
        if (normalized == null) {
            throw new IllegalArgumentException("未知属性: " + field);
        }
        MobAttributeOverride current = OVERRIDES.get(controllerId);
        if (current == null || current.get(normalized) == null) {
            return false;
        }
        MobAttributeOverride updated = current.without(normalized);
        if (updated.isEmpty()) {
            OVERRIDES.remove(controllerId);
        } else {
            OVERRIDES.put(controllerId, updated);
        }
        return true;
    }

    public static boolean reset(String controllerId) {
        return OVERRIDES.remove(controllerId) != null;
    }

    public static MobAttributeOverride get(String controllerId) {
        return OVERRIDES.get(controllerId);
    }

    public static Map<String, MobAttributeOverride> getOverrides() {
        return Collections.unmodifiableMap(new LinkedHashMap<>(OVERRIDES));
    }

    public static void clear() {
        OVERRIDES.clear();
    }

    private static MobAttributes scale(MobAttributes base, double rate) {
        return new MobAttributes(
                base.attackDamage * rate,
                base.defence * rate,
                base.manaDefence * rate,
                base.critRate * rate,
                base.critDamage * rate,
                base.defencePenetration * rate,
                base.defencePenetration0 * rate,
                base.healthSteal * rate,
                base.maxHealth * rate,
                base.movementSpeed * rate,
                base.healthRecover * rate,
                base.percentHealthRecover
        );
    }
}
