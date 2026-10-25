/** AI-Generated, 2026-10-18 */
package fun.wraq.process.system.mastery;

/**
 * 五条精通路径。列顺序即界面从左到右的顺序。
 * 颜色用于界面描边与节点高亮，不使用任何贴图，图标以圆圈 + 首字代替。
 */
public enum MasteryTree {
    PRECISION("精密", "强化攻击和持久能力", 0xFFD8BE82, 0xFF8A7350),
    DOMINATION("主宰", "爆发伤害并前往目标", 0xFFD99691, 0xFF8A5A56),
    SORCERY("巫术", "强化技能和资源控制", 0xFFBCA6E3, 0xFF6E5E8C),
    RESOLVE("坚决", "耐久和控制", 0xFF9BCB96, 0xFF517A4E),
    INSPIRATION("启迪", "创造性的工具并弯曲规则", 0xFF99C9D6, 0xFF4E7A86);

    private final String displayName;
    private final String description;
    private final int color;
    private final int deepColor;

    MasteryTree(String displayName, String description, int color, int deepColor) {
        this.displayName = displayName;
        this.description = description;
        this.color = color;
        this.deepColor = deepColor;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    public int getColor() {
        return color;
    }

    public int getDeepColor() {
        return deepColor;
    }

    /**
     * 精通名称/标题使用的颜色。
     *
     * <p>圆形填充刻意使用了低饱和版本，但名称仍保留各路径的高辨识度颜色；
     * 主宰使用 Harbinger 红。</p>
     */
    public int getNameColor() {
        return switch (this) {
            case PRECISION -> 0xFFD9A94E;
            case DOMINATION -> 0xFFFF4E00; // Harbinger red
            case SORCERY -> 0xFFA97BFF;
            case RESOLVE -> 0xFF5FC95C;
            case INSPIRATION -> 0xFF5BC9E8;
        };
    }

    /** 界面圆圈内使用的单字标记 */
    public String getSigil() {
        return displayName.substring(0, 1);
    }

    public static MasteryTree byIndex(int index) {
        MasteryTree[] values = values();
        if (index < 0 || index >= values.length) {
            return PRECISION;
        }
        return values[index];
    }
}
