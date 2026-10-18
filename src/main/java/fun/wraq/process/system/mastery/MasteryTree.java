/** AI-Generated, 2026-10-18 */
package fun.wraq.process.system.mastery;

/**
 * 五条精通路径。列顺序即界面从左到右的顺序。
 * 颜色用于界面描边与节点高亮，不使用任何贴图，图标以圆圈 + 首字代替。
 */
public enum MasteryTree {
    PRECISION("精密", "强化攻击和持久能力", 0xFFD9A94E, 0xFF8A6A22),
    DOMINATION("主宰", "爆发伤害并前往目标", 0xFFE25A55, 0xFF8A2A28),
    SORCERY("巫术", "强化技能和资源控制", 0xFFA97BFF, 0xFF5A3A9A),
    RESOLVE("坚决", "耐久和控制", 0xFF5FC95C, 0xFF2E7A2C),
    INSPIRATION("启迪", "创造性的工具并弯曲规则", 0xFF5BC9E8, 0xFF2A7A92);

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
