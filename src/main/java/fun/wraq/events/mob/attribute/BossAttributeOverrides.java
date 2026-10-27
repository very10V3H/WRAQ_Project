package fun.wraq.events.mob.attribute;

import fun.wraq.common.attribute.MobAttributes;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * AI-Generated, 2026-10-27
 * 领主级怪物（NoTeamInstance）的运行时属性覆盖。仅保存在内存中，
 * 只在下一次召唤时生效；不修改已经生成的 Boss，也不写磁盘。
 */
public final class BossAttributeOverrides {

    private static final Map<String, MobAttributeOverride> OVERRIDES = new LinkedHashMap<>();

    private BossAttributeOverrides() {
    }

    public static MobAttributes resolve(String profile, MobAttributes base) {
        if (base == null || profile == null) {
            return base;
        }
        MobAttributeOverride override = OVERRIDES.get(profile);
        return override == null ? base : override.applyTo(base);
    }

    public static void set(String profile, String field, double value) {
        String normalized = MobAttributeOverride.normalizeField(field);
        if (normalized == null) {
            throw new IllegalArgumentException("未知属性: " + field);
        }
        double validated = MobAttributeOverride.validateValue(normalized, value);
        MobAttributeOverride current = OVERRIDES.getOrDefault(profile, MobAttributeOverride.EMPTY);
        OVERRIDES.put(profile, current.with(normalized, validated));
    }

    public static boolean reset(String profile, String field) {
        String normalized = MobAttributeOverride.normalizeField(field);
        if (normalized == null) {
            throw new IllegalArgumentException("未知属性: " + field);
        }
        MobAttributeOverride current = OVERRIDES.get(profile);
        if (current == null || current.get(normalized) == null) {
            return false;
        }
        MobAttributeOverride updated = current.without(normalized);
        if (updated.isEmpty()) {
            OVERRIDES.remove(profile);
        } else {
            OVERRIDES.put(profile, updated);
        }
        return true;
    }

    public static boolean reset(String profile) {
        return OVERRIDES.remove(profile) != null;
    }

    public static MobAttributeOverride get(String profile) {
        return OVERRIDES.get(profile);
    }

    public static Map<String, MobAttributeOverride> getOverrides() {
        return Collections.unmodifiableMap(new LinkedHashMap<>(OVERRIDES));
    }

    public static void clear() {
        OVERRIDES.clear();
    }
}
