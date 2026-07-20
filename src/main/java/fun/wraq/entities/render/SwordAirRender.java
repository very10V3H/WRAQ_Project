package fun.wraq.entities.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import fun.wraq.projectiles.mana.swordair.SwordAir;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * AI-Generated, 2026-07-19
 * 程序化弧形剑气渲染器。
 * 新月形弧面以多层水平面叠加（中心向前凸起），
 * 正反面均绘制以保证各角度可见。
 */
public class SwordAirRender extends EntityRenderer<SwordAir> {

    private static final int SEGMENTS = 24;
    private static final float ARC_TOTAL = (float) Math.toRadians(120);
    private static final float HALF_ARC = ARC_TOTAL / 2;

    private static final float OUTER_RADIUS = 0.8f;
    private static final float INNER_RADIUS = 0.55f;
    private static final float BASE_SCALE = 0.6f;

    /** Y 层数 3 层，范围 ±0.12，中心最亮 */
    private static final int LAYERS = 3;
    private static final float Y_SPREAD = 0.12f;

    /** 剑气颜色（淡白） */
    private static final float R = 0.9f;
    private static final float G = 0.9f;
    private static final float B = 1.0f;

    public SwordAirRender(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(SwordAir entity, float entityYaw, float partialTick,
                       PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        Vec3 motion = entity.getDeltaMovement();
        if (motion.lengthSqr() < 0.001) return;

        poseStack.pushPose();
        poseStack.translate(0, entity.getBbHeight() / 2, 0);

        // 正交基：fwd=运动方向, up≈世界Y, right=右侧
        Vec3 norm = motion.normalize();
        Vector3f fwd = new Vector3f((float) norm.x, (float) norm.y, (float) norm.z);
        Vector3f upRef = new Vector3f(0, 1, 0);
        if (Math.abs(fwd.y) > 0.9999f) upRef.set(1, 0, 0);

        Vector3f right = new Vector3f();
        fwd.cross(upRef, right);
        right.normalize();

        Vector3f up = new Vector3f();
        right.cross(fwd, up);

        Matrix4f rotMat = new Matrix4f();
        rotMat.set(new float[] {
                right.x, right.y, right.z, 0,
                up.x,    up.y,    up.z,    0,
                fwd.x,   fwd.y,   fwd.z,   0,
                0,       0,       0,       1
        });
        poseStack.last().pose().mul(rotMat);

        poseStack.scale(BASE_SCALE, BASE_SCALE, BASE_SCALE);

        VertexConsumer consumer = bufferSource.getBuffer(RenderType.lightning());
        Matrix4f mat = poseStack.last().pose();

        for (int layer = 0; layer < LAYERS; layer++) {
            float t = (float) layer / (LAYERS - 1);
            float y = (t - 0.5f) * Y_SPREAD * 2f;
            float intensity = 0.10f + 0.55f * (1f - 4f * (t - 0.5f) * (t - 0.5f));
            renderArcFace(consumer, mat, y, intensity, R, G, B);
        }

        poseStack.popPose();
    }

    /**
     * 绘制单层新月形弧面，每段以 QUAD（4 顶点）提交。
     * lightning() 使用 QUADS 模式，4 顶点一组构成四边形。
     * lightning() 不开背面剔除，无需画反面。
     */
    private void renderArcFace(VertexConsumer consumer, Matrix4f mat,
                               float y, float alpha, float r, float g, float b) {
        for (int i = 0; i < SEGMENTS; i++) {
            float t1 = (float) i / SEGMENTS;
            float t2 = (float) (i + 1) / SEGMENTS;

            float angle1 = -HALF_ARC + t1 * ARC_TOTAL;
            float angle2 = -HALF_ARC + t2 * ARC_TOTAL;

            float sin1 = (float) Math.sin(angle1);
            float cos1 = (float) Math.cos(angle1);
            float sin2 = (float) Math.sin(angle2);
            float cos2 = (float) Math.cos(angle2);

            float x1o = OUTER_RADIUS * sin1;
            float z1o = OUTER_RADIUS * cos1 - OUTER_RADIUS;
            float x2o = OUTER_RADIUS * sin2;
            float z2o = OUTER_RADIUS * cos2 - OUTER_RADIUS;

            float x1i = INNER_RADIUS * sin1;
            float z1i = INNER_RADIUS * cos1 - OUTER_RADIUS;
            float x2i = INNER_RADIUS * sin2;
            float z2i = INNER_RADIUS * cos2 - OUTER_RADIUS;

            // 正向绕序（从 +Y 看可见）
            consumer.vertex(mat, x1o, y, z1o).color(r, g, b, alpha).endVertex();
            consumer.vertex(mat, x2o, y, z2o).color(r, g, b, alpha).endVertex();
            consumer.vertex(mat, x2i, y, z2i).color(r, g, b, alpha).endVertex();
            consumer.vertex(mat, x1i, y, z1i).color(r, g, b, alpha).endVertex();
            // 反向绕序（从 -Y 看可见）
            consumer.vertex(mat, x1o, y, z1o).color(r, g, b, alpha).endVertex();
            consumer.vertex(mat, x1i, y, z1i).color(r, g, b, alpha).endVertex();
            consumer.vertex(mat, x2i, y, z2i).color(r, g, b, alpha).endVertex();
            consumer.vertex(mat, x2o, y, z2o).color(r, g, b, alpha).endVertex();
        }
    }

    @Override
    public ResourceLocation getTextureLocation(SwordAir entity) {
        return null;
    }
}
