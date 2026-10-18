/**
 * AI-Generated, 2026-10-17
 */
package fun.wraq.common.util;

import java.util.Locale;

/**
 * 数值显示工具类。
 *
 * <p>用于把 VMD 中动辄上亿的数值（血量、伤害等）压缩成玩家易读的短格式，
 * 避免在血条、HUD 上显示一长串数字：</p>
 * <ul>
 *     <li>{@code 10000} → {@code 1w}（万）</li>
 *     <li>{@code 1000000000} → {@code 10e}（亿）</li>
 *     <li>{@code 1500000000000} → {@code 1.5we}（万亿，极少出现）</li>
 * </ul>
 * <p>小于一万的数值保留一位小数并去掉多余的 {@code .0}，与 AsteorBar 原生的显示习惯一致。</p>
 */
public class NumberUtils {

    /**
     * 把数值缩写为 w / e / we 格式。
     *
     * @param value 原始数值，负值按 0 处理
     * @return 缩写后的字符串，非有限值（NaN、Infinity）返回 {@code ∞}
     */
    public static String abbreviate(double value) {
        if (!Double.isFinite(value)) {
            return "∞";
        }
        double v = Math.max(0.0, value);
        if (v >= 1.0E15) {
            // 极端情况（理论上不会出现）退回科学计数法，避免产生数百位长的数字
            return String.format(Locale.ROOT, "%.1e", v);
        }
        if (v >= 1.0E12) {
            return oneDecimal(v / 1.0E12) + "we";
        }
        if (v >= 1.0E8) {
            return oneDecimal(v / 1.0E8) + "e";
        }
        if (v >= 1.0E4) {
            return oneDecimal(v / 1.0E4) + "w";
        }
        return oneDecimal(v);
    }

    private static String oneDecimal(double value) {
        String str = String.format(Locale.ROOT, "%.1f", value);
        if (str.endsWith(".0")) {
            str = str.substring(0, str.length() - 2);
        }
        return str;
    }
}
