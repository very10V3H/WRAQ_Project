/** AI-Generated, 2026-07-21 */
package fun.wraq.events.mob;

import net.minecraft.world.phys.Vec2;

import java.util.List;

/**
 * 多边形区域边界定义。
 * <p>
 * 用于 {@link AreaMobSpawnController} 的多边形范围检测。定义一个 XZ 平面上的 2D 多边形，
 * 结合 Y 轴范围（{@code minY} ~ {@code maxY}）构成一个垂直棱柱状区域。
 * </p>
 *
 * <h3>使用方式</h3>
 * <pre>{@code
 * new PolygonBoundary(List.of(
 *     new Vec2(4000, 3000), new Vec2(4100, 3100),
 *     new Vec2(4050, 3200), new Vec2(3950, 3100)
 * ), 50, 150);
 * }</pre>
 *
 * <h3>顶点约束</h3>
 * <ul>
 *   <li>至少 3 个顶点（三角形为最小多边形）</li>
 *   <li>顶点顺序顺时针或逆时针均可（射线法不依赖方向）</li>
 *   <li>多边形可以是凸多边形或凹多边形</li>
 *   <li>顶点 {@link Vec2#x x} = 方块 X 坐标，{@link Vec2#y y} = 方块 Z 坐标</li>
 * </ul>
 *
 * @param vertices XZ 平面多边形顶点列表（至少 3 个）
 * @param minY     区域最低 Y（包含）
 * @param maxY     区域最高 Y（包含）
 */
public record PolygonBoundary(List<Vec2> vertices, double minY, double maxY) {

    public PolygonBoundary {
        if (vertices.size() < 3) {
            throw new IllegalArgumentException(
                    "PolygonBoundary requires at least 3 vertices, got " + vertices.size());
        }
        if (minY > maxY) {
            throw new IllegalArgumentException(
                    "minY (" + minY + ") must not be greater than maxY (" + maxY + ")");
        }
    }
}
