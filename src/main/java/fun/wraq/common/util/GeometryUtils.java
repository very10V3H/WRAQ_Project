/** AI-Generated, 2026-07-21 */
package fun.wraq.common.util;

import fun.wraq.events.mob.PolygonBoundary;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Random;

/**
 * 几何计算工具类。
 * <p>
 * 提供二维多边形与三维多边形棱柱的几何判定与随机点生成方法。
 * 用于 {@link fun.wraq.events.mob.AreaMobSpawnController 区域刷怪控制器} 的
 * 多边形范围检测。
 * </p>
 */
public class GeometryUtils {

    /**
     * 射线法判断点是否在 2D 多边形内。
     * <p>
     * 从目标点向右水平发射射线，统计与多边形各边的交点数量。
     * 奇数次交点 → 点在多边形内部；偶数次 → 外部。
     * </p>
     *
     * @param px      目标点的 X 坐标
     * @param pz      目标点的 Z 坐标（Vec2.y 存储的是 Z）
     * @param polygon 多边形顶点列表（XZ 平面，Vec2.x=X, Vec2.y=Z）
     * @return true 如果点在多边形内部或边上
     */
    public static boolean isPointInPolygon(double px, double pz, List<Vec2> polygon) {
        boolean inside = false;
        int n = polygon.size();
        for (int i = 0, j = n - 1; i < n; j = i++) {
            double xi = polygon.get(i).x;
            double zi = polygon.get(i).y;
            double xj = polygon.get(j).x;
            double zj = polygon.get(j).y;

            // 检查射线 (px, pz) → (+∞, pz) 是否与边 (xi,zi)-(xj,zj) 相交
            if ((zi > pz) != (zj > pz)) {
                double intersectX = (xj - xi) * (pz - zi) / (zj - zi) + xi;
                if (px < intersectX) {
                    inside = !inside;
                }
            }
        }
        return inside;
    }

    /**
     * 判断 3D 点是否在多边形棱柱区域内。
     * <p>
     * 条件：XZ 投影在多边形内，且 Y 在 [minY, maxY] 范围内。
     * </p>
     *
     * @param point    3D 空间点
     * @param boundary 多边形边界定义
     * @return true 如果点在多边形棱柱内
     */
    public static boolean isPointInPolygonBoundary(Vec3 point, PolygonBoundary boundary) {
        if (point.y < boundary.minY() || point.y > boundary.maxY()) {
            return false;
        }
        return isPointInPolygon(point.x, point.z, boundary.vertices());
    }

    /**
     * 在多边形棱柱内生成随机点。
     * <p>
     * XZ 坐标使用拒绝采样法生成：在多边形的轴对齐包围盒内随机取点，
     * 判定是否在多边形内，最多尝试 {@code MAX_ATTEMPTS} 次。
     * Y 坐标在 [minY, maxY] 间均匀随机。
     * </p>
     *
     * @param boundary 多边形边界定义
     * @param random   随机数生成器
     * @return 多边形棱柱内的随机 3D 点，若拒绝采样全部失败则返回质心点
     */
    public static Vec3 randomPointInPolygon(PolygonBoundary boundary, Random random) {
        List<Vec2> vertices = boundary.vertices();

        // 计算多边形 XZ 轴对齐包围盒
        double minX = Double.MAX_VALUE, maxX = -Double.MAX_VALUE;
        double minZ = Double.MAX_VALUE, maxZ = -Double.MAX_VALUE;
        for (Vec2 v : vertices) {
            if (v.x < minX) minX = v.x;
            if (v.x > maxX) maxX = v.x;
            if (v.y < minZ) minZ = v.y;
            if (v.y > maxZ) maxZ = v.y;
        }

        // 拒绝采样
        final int MAX_ATTEMPTS = 100;
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            double x = minX + random.nextDouble() * (maxX - minX);
            double z = minZ + random.nextDouble() * (maxZ - minZ);
            if (isPointInPolygon(x, z, vertices)) {
                double y = boundary.minY() + random.nextDouble() * (boundary.maxY() - boundary.minY());
                return new Vec3(x, y, z);
            }
        }

        // 兜底：返回多边形质心
        double cx = 0, cz = 0;
        for (Vec2 v : vertices) {
            cx += v.x;
            cz += v.y;
        }
        cx /= vertices.size();
        cz /= vertices.size();
        double cy = (boundary.minY() + boundary.maxY()) / 2.0;
        return new Vec3(cx, cy, cz);
    }
}
