package fun.wraq.common.impl.forge;

import java.util.List;

/**
 * AI-Generated, 2025-01-11
 * <p>
 * 可锻造随机属性装备接口。
 * 实现此接口的物品在通过 ForgeScreen 锻造时，将从 {@link #getForgeAttributePool()} 返回的属性池中
 * 随机选取 {@link #minRandomAttributes()} ~ {@link #maxRandomAttributes()} 个属性，
 * 将其随机值写入物品的 NBT 标签（使用 {@link StringUtils.RandomAttributes} 中定义的键名），
 * 从而每个物品个体可获得不同的属性组合与数值。
 * <p>
 * 实现类不需要（也不应该）将属性注册到 {@link fun.wraq.common.util.Utils#attackDamage} 等静态 Map，
 * 因为 {@link fun.wraq.common.attribute.PlayerAttributes#handleAllEquipRandomAttribute} 管线
 * 已能读取 NBT 中的 "RandomXxx" 键并正确应用锻造品质倍率。
 */
public interface ForgeRandomEquip {

    /**
     * @return 属性池列表，每个条目定义了一个可随机获得的属性的 NBT 键名、数值范围与权重
     */
    List<ForgeAttributeEntry> getForgeAttributePool();

    /**
     * @return 锻造时最少获得的随机属性数量
     */
    int minRandomAttributes();

    /**
     * @return 锻造时最多获得的随机属性数量
     */
    int maxRandomAttributes();

    /**
     * @return true 表示每个属性最多出现一次（不放回抽样），false 表示可重复
     */
    boolean distinctRandomAttributes();

    /**
     * 便捷方法：创建一个权重为 1.0 的属性条目。
     *
     * @param nbtKey   NBT 键名，建议使用 {@link StringUtils.RandomAttributes} 中的常量
     * @param minValue 最小值
     * @param maxValue 最大值
     * @return 权重为 1.0 的 ForgeAttributeEntry
     */
    static ForgeAttributeEntry entry(String nbtKey, double minValue, double maxValue) {
        return new ForgeAttributeEntry(nbtKey, minValue, maxValue, 1.0);
    }

    /**
     * 便捷方法：创建一个指定权重的属性条目。
     *
     * @param nbtKey   NBT 键名
     * @param minValue 最小值
     * @param maxValue 最大值
     * @param weight   权重（相对值，越大概率越高）
     * @return ForgeAttributeEntry
     */
    static ForgeAttributeEntry entry(String nbtKey, double minValue, double maxValue, double weight) {
        return new ForgeAttributeEntry(nbtKey, minValue, maxValue, weight);
    }

    /**
     * 创建一个必带属性条目（mandatory = true），该属性在锻造时必定出现。
     *
     * @param nbtKey   NBT 键名
     * @param minValue 最小值
     * @param maxValue 最大值
     * @return ForgeAttributeEntry
     */
    static ForgeAttributeEntry mandatory(String nbtKey, double minValue, double maxValue) {
        return new ForgeAttributeEntry(nbtKey, minValue, maxValue, 1.0, true, 1.0);
    }

    /**
     * 创建一个独立概率属性条目（probability < 1.0），
     * 锻造时每个属性按给定概率独立判定是否出现。
     *
     * @param nbtKey     NBT 键名
     * @param minValue   最小值
     * @param maxValue   最大值
     * @param probability 出现概率 (0~1.0)
     * @return ForgeAttributeEntry
     */
    static ForgeAttributeEntry prob(String nbtKey, double minValue, double maxValue, double probability) {
        return new ForgeAttributeEntry(nbtKey, minValue, maxValue, 1.0, false, probability);
    }
}
