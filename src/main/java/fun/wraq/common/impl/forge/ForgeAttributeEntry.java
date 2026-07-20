package fun.wraq.common.impl.forge;

/**
 * AI-Generated, 2025-01-11
 * <p>
 * 锻造随机属性的条目定义。
 * 每个条目表示一个可随机获得的属性，包括其 NBT 键名、数值范围、权重、是否必带、独立概率。
 * <p>
 * 模式说明：
 * <ul>
 *   <li><b>必带模式</b> — {@code mandatory = true}，该属性必定出现</li>
 *   <li><b>独立概率模式</b> — {@code mandatory = false, probability < 1.0}，
 *       每个属性按 {@code probability} 概率独立判定是否出现</li>
 *   <li><b>权重池模式</b> — {@code mandatory = false, probability >= 1.0}，
 *       从属性池中按 {@code weight} 加权选取 {@code minCount~maxCount} 个</li>
 * </ul>
 */
public record ForgeAttributeEntry(
        String nbtKey,
        double minValue,
        double maxValue,
        double weight,
        boolean mandatory,
        double probability
) {

    /**
     * 简单条目：等权重 1.0，非必带，概率 1.0（参与权重池选取）。
     */
    public ForgeAttributeEntry(String nbtKey, double minValue, double maxValue) {
        this(nbtKey, minValue, maxValue, 1.0, false, 1.0);
    }

    /**
     * 指定权重的条目：非必带，概率 1.0（参与权重池选取）。
     */
    public ForgeAttributeEntry(String nbtKey, double minValue, double maxValue, double weight) {
        this(nbtKey, minValue, maxValue, weight, false, 1.0);
    }
}
