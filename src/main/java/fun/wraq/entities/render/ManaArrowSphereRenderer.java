package fun.wraq.entities.render;

import com.mojang.blaze3d.vertex.*;
import fun.wraq.projectiles.mana.ManaArrow;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

/**
 * AI-Generated, 2026-07-18
 * 程序化球体法球渲染器。
 * 使用 RenderType.lightning()（加法混合），多层同心球面从外向内绘制：
 * 外层淡 → 内层浓，底部加法叠加形成球心深色高饱和的层次效果。
 */
public class ManaArrowSphereRenderer extends EntityRenderer<ManaArrow> {

    private static final int SLICES = 20;
    private static final int STACKS = 12;
    private static final float SCALE = 0.10f;
    /** 层数：从外向内递增强度 */
    private static final int LAYERS = 5;
    /** 每层强度，外层淡内层浓 */
    private static final float[] INTENSITIES = {0.15f, 0.20f, 0.25f, 0.30f, 0.35f};
    /** 每层缩放，外层大内层小 */
    private static final float[] SCALES =        {1.00f, 0.85f, 0.70f, 0.55f, 0.40f};

    public ManaArrowSphereRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(ManaArrow entity, float entityYaw, float partialTick,
                       PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        int argbColor = entity.getStyle();
        float r = ((argbColor >> 16) & 0xFF) / 255f;
        float g = ((argbColor >> 8) & 0xFF) / 255f;
        float b = (argbColor & 0xFF) / 255f;

        VertexConsumer consumer = bufferSource.getBuffer(RenderType.lightning());

        // 外层→内层绘制：加法混合叠加，中心最深
        for (int layer = 0; layer < LAYERS; layer++) {
            poseStack.pushPose();
            poseStack.translate(0, entity.getBbHeight() / 2, 0);
            poseStack.scale(SCALE * SCALES[layer], SCALE * SCALES[layer], SCALE * SCALES[layer]);

            Matrix4f mat = poseStack.last().pose();
            float intensity = INTENSITIES[layer];

            for (int stack = 0; stack < STACKS; stack++) {
                double phi1 = Math.PI * stack / STACKS;
                double phi2 = Math.PI * (stack + 1) / STACKS;

                for (int slice = 0; slice < SLICES; slice++) {
                    double theta1 = 2 * Math.PI * slice / SLICES;
                    double theta2 = 2 * Math.PI * (slice + 1) / SLICES;

                    double x1 = Math.sin(phi1) * Math.cos(theta1);
                    double y1 = Math.cos(phi1);
                    double z1 = Math.sin(phi1) * Math.sin(theta1);

                    double x2 = Math.sin(phi2) * Math.cos(theta1);
                    double y2 = Math.cos(phi2);
                    double z2 = Math.sin(phi2) * Math.sin(theta1);

                    double x3 = Math.sin(phi2) * Math.cos(theta2);
                    double y3 = Math.cos(phi2);
                    double z3 = Math.sin(phi2) * Math.sin(theta2);

                    double x4 = Math.sin(phi1) * Math.cos(theta2);
                    double y4 = Math.cos(phi1);
                    double z4 = Math.sin(phi1) * Math.sin(theta2);

                    consumer.vertex(mat, (float) x1, (float) y1, (float) z1).color(r, g, b, intensity).endVertex();
                    consumer.vertex(mat, (float) x2, (float) y2, (float) z2).color(r, g, b, intensity).endVertex();
                    consumer.vertex(mat, (float) x3, (float) y3, (float) z3).color(r, g, b, intensity).endVertex();
                    consumer.vertex(mat, (float) x4, (float) y4, (float) z4).color(r, g, b, intensity).endVertex();
                }
            }

            poseStack.popPose();
        }
    }

    @Override
    public ResourceLocation getTextureLocation(ManaArrow entity) {
        return null;
    }
}
