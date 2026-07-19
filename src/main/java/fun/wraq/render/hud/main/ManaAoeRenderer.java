package fun.wraq.render.hud.main;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import fun.wraq.common.util.struct.ManaAoeEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import com.mojang.blaze3d.vertex.VertexFormat;
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
 * AI-Generated, 2026-07-18
 * 使用 RenderLevelStageEvent 在每帧渲染平滑的 Mana AOE 扩散圆环。
 * 替代原有基于粒子引擎（20tick/s）的 BallParticle 渲染。
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ManaAoeRenderer {

    private static final List<ManaAoeEffect> activeEffects = new ArrayList<>();
    private static final int MAX_EFFECTS = 64;
    private static final long DEFAULT_DURATION_MS = 500;

    /** 每个 AOE 使用 2 个同心圆环，间隔 75ms 出发 */
    private static final int RING_COUNT = 2;
    private static final long RING_DELAY_MS = 75;
    /** 环从半径 0.5 扩展到 1.5 */
    private static final double MIN_RADIUS = 0.5;
    private static final double MAX_RADIUS = 1.5;
    /** 环分段数 */
    private static final int SEGMENTS = 48;

    /** 将法球的 particleType 映射为 ARGB 颜色 */
    public static int getColorForParticleType(String particleType) {
        return switch (particleType) {
            case "LifeElementParticle" -> 0x60FF5555;      // 生命 - 红
            case "WaterElementParticle" -> 0x605555FF;     // 水 - 蓝
            case "FireElementParticle" -> 0x60FF8800;      // 火 - 橙
            case "StoneElementParticle" -> 0x60AA8866;     // 石 - 棕
            case "IceElementParticle" -> 0x6099EEFF;       // 冰 - 淡蓝
            case "LightningElementParticle" -> 0x60FFFF44;  // 雷 - 黄
            case "WindElementParticle" -> 0x6044FFAA;      // 风 - 青
            case "ParticleRangeMana" -> 0x604488FF;
            case "ParticleDamageMana" -> 0x60FF4444;
            case "ParticlePenetrationMana" -> 0x60FF44FF;
            case "ParticleSnowMana" -> 0x60FFFFFF;
            case "ParticleKazeMana" -> 0x6044FF88;
            case "ParticleLightningMana" -> 0x60FFFF00;
            case "ParticleGatherMana" -> 0x608833CC;
            default -> 0x60AA88FF;                          // 默认紫色
        };
    }

    public static void addEffect(Vec3 pos, String particleType) {
        addEffect(pos, getColorForParticleType(particleType));
    }

    public static void addEffect(Vec3 pos, int color) {
        if (activeEffects.size() >= MAX_EFFECTS) {
            activeEffects.remove(0);
        }
        activeEffects.add(new ManaAoeEffect(pos, System.currentTimeMillis(), DEFAULT_DURATION_MS, color));
    }

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES) return;
        if (activeEffects.isEmpty()) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        PoseStack poseStack = event.getPoseStack();
        Vec3 camPos = event.getCamera().getPosition();
        long now = System.currentTimeMillis();

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder buffer = tesselator.getBuilder();

        Iterator<ManaAoeEffect> it = activeEffects.iterator();
        while (it.hasNext()) {
            ManaAoeEffect effect = it.next();
            long elapsed = now - effect.getStartTimeMs();

            if (elapsed > effect.getDurationMs()) {
                it.remove();
                continue;
            }

            Vec3 pos = effect.getPos();
            double dx = pos.x - camPos.x;
            double dy = pos.y - camPos.y;
            double dz = pos.z - camPos.z;

            if (!event.getFrustum().isVisible(new AABB(pos.x, pos.y, pos.z, pos.x, pos.y, pos.z))) continue;

            float progress = (float) elapsed / effect.getDurationMs(); // 0→1

            for (int ring = 0; ring < RING_COUNT; ring++) {
                long ringElapsed = elapsed - ring * RING_DELAY_MS;
                if (ringElapsed < 0) continue;
                float ringProgress = (float) ringElapsed / effect.getDurationMs();
                if (ringProgress > 1.0f) continue;

                double radius = MIN_RADIUS + (MAX_RADIUS - MIN_RADIUS) * ringProgress;
                float alpha = (1.0f - ringProgress) * 0.7f;
                int a = Math.max(2, (int) (alpha * 255));

                // 从 color 提取 RGB，补上当前计算的 alpha
                int r = (effect.getColor() >> 16) & 0xFF;
                int g = (effect.getColor() >> 8) & 0xFF;
                int b = effect.getColor() & 0xFF;

                poseStack.pushPose();
                poseStack.translate(dx, dy, dz);

                Matrix4f mat = poseStack.last().pose();
                buffer.begin(VertexFormat.Mode.DEBUG_LINE_STRIP, DefaultVertexFormat.POSITION_COLOR);

                for (int i = 0; i <= SEGMENTS; i++) {
                    double theta = 2.0 * Math.PI * i / SEGMENTS;
                    double rx = radius * Math.cos(theta);
                    double rz = radius * Math.sin(theta);
                    buffer.vertex(mat, (float) rx, 0.0f, (float) rz).color(r, g, b, a).endVertex();
                }

                tesselator.end();
                poseStack.popPose();
            }
        }

        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
    }
}
