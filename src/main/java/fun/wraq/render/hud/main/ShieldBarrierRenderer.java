/**
 * AI-Generated, 2026-10-25
 */
package fun.wraq.render.hud.main;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import fun.wraq.render.hud.ClientShieldData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

/**
 * 护盾屏障渲染器（类似英雄联盟的屏障 / 护盾气泡）。
 *
 * <p>给带护盾的生物套一层透明白色球体：</p>
 * <ul>
 *     <li>球体由经纬网格构成，只绘制朝向相机的一侧；</li>
 *     <li>透明度按「法线与视线夹角」变化：正对镜头处几乎透明、接近轮廓处最亮，
 *         形成气泡边缘的亮带；</li>
 *     <li>叠加轻微的呼吸脉冲，并开启深度测试，被地形自然遮挡；</li>
 *     <li>本地玩家不绘制（第一人称会糊住镜头），只给其他玩家与怪物绘制。</li>
 * </ul>
 *
 * <p>护盾值取自 {@link ClientShieldData#getEntityShield(int)}（服务端同步的 VMD 护盾）
 * 加上原版吸收伤害，与血条上的白色护盾段保持一致。</p>
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ShieldBarrierRenderer {

    /** 最大显示距离（方块） */
    private static final double MAX_DISTANCE = 32.0D;
    /** 球体经线 / 纬线分段数 */
    private static final int RINGS = 14;
    private static final int SEGMENTS = 24;
    /** 正对镜头方向的透明度（几乎看不见） */
    private static final float CENTER_ALPHA = 0.03F;
    /** 轮廓处的透明度（最亮） */
    private static final float RIM_ALPHA = 0.45F;
    /** 法线与视线夹角超过该值（背面）就跳过；略小于 0 用于加宽边缘亮带 */
    private static final double RIM_CUTOFF = -0.25D;

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) return;

        Vec3 camPos = event.getCamera().getPosition();
        float partialTick = event.getPartialTick();
        float time = mc.level.getGameTime() + partialTick;

        List<LivingEntity> targets = new ArrayList<>();
        for (Entity entity : mc.level.entitiesForRendering()) {
            if (!(entity instanceof LivingEntity living)) continue;
            // 本地玩家第一人称不画，免得糊住镜头
            if (living == mc.player) continue;
            if (!living.isAlive() || living.isInvisible()) continue;
            if (living.getPosition(partialTick).distanceTo(camPos) > MAX_DISTANCE) continue;
            if (!event.getFrustum().isVisible(living.getBoundingBox().inflate(1.0D))) continue;
            if (shieldOf(living) <= 0.0F) continue;
            targets.add(living);
        }
        if (targets.isEmpty()) return;

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        RenderSystem.depthMask(false);
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder builder = tesselator.getBuilder();
        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        for (LivingEntity living : targets) {
            appendBarrierSphere(builder, event.getPoseStack(), camPos, living, partialTick, time);
        }
        tesselator.end();

        RenderSystem.depthMask(true);
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
    }

    /** 护盾值：原版吸收伤害 + 客户端同步的 VMD 护盾（与血条白色段一致）。 */
    private static float shieldOf(LivingEntity living) {
        float shield = Math.max(0.0F, living.getAbsorptionAmount());
        shield += (float) ClientShieldData.getEntityShield(living.getId());
        return shield;
    }

    private static void appendBarrierSphere(BufferBuilder builder, PoseStack poseStack, Vec3 camPos,
                                            LivingEntity living, float partialTick, float time) {
        Vec3 pos = living.getPosition(partialTick);
        double centerX = pos.x;
        double centerY = pos.y + living.getBbHeight() * 0.5;
        double centerZ = pos.z;
        double radius = Math.max(living.getBbWidth(), living.getBbHeight()) * 0.55 + 0.1;

        // 相机 → 球心方向（用于计算每个面片的朝向）
        double cameraDx = camPos.x - centerX;
        double cameraDy = camPos.y - centerY;
        double cameraDz = camPos.z - centerZ;
        double cameraDistance = Math.sqrt(cameraDx * cameraDx + cameraDy * cameraDy + cameraDz * cameraDz);
        if (cameraDistance < 1.0E-4) return;
        double viewX = cameraDx / cameraDistance;
        double viewY = cameraDy / cameraDistance;
        double viewZ = cameraDz / cameraDistance;

        // 呼吸脉冲：不同生物相位错开
        float pulse = 0.8F + 0.2F * (float) Math.sin(time * 0.12F + living.getId() * 1.7F);

        poseStack.pushPose();
        poseStack.translate(centerX - camPos.x, centerY - camPos.y, centerZ - camPos.z);
        Matrix4f mat = poseStack.last().pose();

        for (int ring = 0; ring < RINGS; ring++) {
            double theta0 = Math.PI * ring / RINGS;
            double theta1 = Math.PI * (ring + 1) / RINGS;
            double sin0 = Math.sin(theta0), cos0 = Math.cos(theta0);
            double sin1 = Math.sin(theta1), cos1 = Math.cos(theta1);
            for (int segment = 0; segment < SEGMENTS; segment++) {
                double phi0 = 2.0 * Math.PI * segment / SEGMENTS;
                double phi1 = 2.0 * Math.PI * (segment + 1) / SEGMENTS;
                double cosPhi0 = Math.cos(phi0), sinPhi0 = Math.sin(phi0);
                double cosPhi1 = Math.cos(phi1), sinPhi1 = Math.sin(phi1);

                double x00 = sin0 * cosPhi0, y00 = cos0, z00 = sin0 * sinPhi0;
                double x01 = sin0 * cosPhi1, y01 = cos0, z01 = sin0 * sinPhi1;
                double x10 = sin1 * cosPhi0, y10 = cos1, z10 = sin1 * sinPhi0;
                double x11 = sin1 * cosPhi1, y11 = cos1, z11 = sin1 * sinPhi1;

                // 面片中心法线
                double nx = (x00 + x01 + x10 + x11) * 0.25;
                double ny = (y00 + y01 + y10 + y11) * 0.25;
                double nz = (z00 + z01 + z10 + z11) * 0.25;
                double normalLength = Math.sqrt(nx * nx + ny * ny + nz * nz);
                if (normalLength < 1.0E-6) continue;
                nx /= normalLength;
                ny /= normalLength;
                nz /= normalLength;

                double facing = nx * viewX + ny * viewY + nz * viewZ;
                if (facing < RIM_CUTOFF) continue;

                double rim = 1.0 - Math.max(0.0, facing);
                double alpha = (CENTER_ALPHA + (RIM_ALPHA - CENTER_ALPHA) * rim * rim) * pulse;
                int alphaInt = Mth.clamp((int) Math.round(alpha * 255.0), 0, 255);
                int color = (alphaInt << 24) | 0xFFFFFF;

                vertex(builder, mat, x00 * radius, y00 * radius, z00 * radius, color);
                vertex(builder, mat, x01 * radius, y01 * radius, z01 * radius, color);
                vertex(builder, mat, x11 * radius, y11 * radius, z11 * radius, color);
                vertex(builder, mat, x10 * radius, y10 * radius, z10 * radius, color);
            }
        }

        poseStack.popPose();
    }

    private static void vertex(BufferBuilder builder, Matrix4f mat,
                               double x, double y, double z, int color) {
        builder.vertex(mat, (float) x, (float) y, (float) z).color(color).endVertex();
    }
}
