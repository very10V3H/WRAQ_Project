/** AI-Generated, 2026-07-26 */
package fun.wraq.render.hud.main;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import fun.wraq.common.util.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * AI-Generated, 2026-07-26
 * 使用 RenderLevelStageEvent 在每帧渲染平滑的水平圆环。
 * 替代原有基于粒子引擎（20tick/s）的 createLastVerticalCircleParticles 渲染。
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientCircleRenderer {

    private static final List<ActiveCircle> activeCircles = new ArrayList<>();
    private static final int MAX_CIRCLES = 64;
    /** 圆环分段数 */
    private static final int SEGMENTS = 48;
    /** 圆环线条宽度（半径偏移量） */
    private static final double RING_WIDTH = 0.3;

    private static class ActiveCircle {
        final Vec3 pos;
        final long expireTick;
        final double radius;
        final int color;   // ARGB

        ActiveCircle(Vec3 pos, double radius, int color, long expireTick) {
            this.pos = pos;
            this.radius = radius;
            this.color = color;
            this.expireTick = expireTick;
        }
    }

    /**
     * 添加一个持续 durationTick 个 tick 的水平圆环。
     * @param pos          圆心世界坐标
     * @param radius       半径
     * @param color        ARGB 颜色
     * @param durationTick 持续 tick 数
     */
    public static void addCircle(Vec3 pos, double radius, int color, int durationTick) {
        if (activeCircles.size() >= MAX_CIRCLES) {
            activeCircles.remove(0);
        }
        long expireTick = ClientUtils.serverTick + durationTick;
        activeCircles.add(new ActiveCircle(pos, radius, color, expireTick));
    }

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES) return;
        if (activeCircles.isEmpty()) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        int currentTick = ClientUtils.serverTick;
        PoseStack poseStack = event.getPoseStack();
        Vec3 camPos = event.getCamera().getPosition();

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder buffer = tesselator.getBuilder();

        Iterator<ActiveCircle> it = activeCircles.iterator();
        while (it.hasNext()) {
            ActiveCircle circle = it.next();

            if (currentTick >= (int) circle.expireTick) {
                it.remove();
                continue;
            }

            Vec3 pos = circle.pos;
            double dx = pos.x - camPos.x;
            double dy = pos.y - camPos.y;
            double dz = pos.z - camPos.z;

            // 视锥体裁剪
            if (!event.getFrustum().isVisible(new AABB(pos.x, pos.y, pos.z, pos.x, pos.y, pos.z))) continue;

            // 根据剩余时间计算 alpha 淡出效果
            int remainingTicks = (int) circle.expireTick - currentTick;
            float alpha = Math.min(1.0f, remainingTicks / 20.0f); // 最后 20 tick 逐渐淡出
            int a = Math.max(2, (int) (alpha * 200));

            int r = (circle.color >> 16) & 0xFF;
            int g = (circle.color >> 8) & 0xFF;
            int b = circle.color & 0xFF;

            poseStack.pushPose();
            poseStack.translate(dx, dy, dz);

            Matrix4f mat = poseStack.last().pose();
            // 使用三角形带绘制宽圆环（内径/外径）
            buffer.begin(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);

            double radius = circle.radius;
            double innerR = Math.max(0.05, radius - RING_WIDTH);
            double outerR = radius + RING_WIDTH;
            for (int i = 0; i <= SEGMENTS; i++) {
                double theta = 2.0 * Math.PI * i / SEGMENTS;
                double cos = Math.cos(theta);
                double sin = Math.sin(theta);
                // 外圈顶点
                buffer.vertex(mat, (float) (outerR * cos), 0.0f, (float) (outerR * sin)).color(r, g, b, a).endVertex();
                // 内圈顶点
                buffer.vertex(mat, (float) (innerR * cos), 0.0f, (float) (innerR * sin)).color(r, g, b, a).endVertex();
            }

            tesselator.end();
            poseStack.popPose();
        }

        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
    }
}
