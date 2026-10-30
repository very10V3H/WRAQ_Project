package fun.wraq.series.overworld.chapter2.sky;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.renderer.GeoItemRenderer;

import java.util.List;

/**
 * AI-Generated, 2026-10-30
 * Sky-Line 手环 GeckoLib 物品渲染器。
 * 在 Geo 模型渲染完成后，把四个配置槽中的物品绘制在手环四面。
 */
public class SkyLineRenderer extends GeoItemRenderer<SkyLineOffhand> {

    private static final float[][] OFFSETS = {
            {0.32f, 0.72f, 0.32f},
            {-0.32f, 0.72f, 0.32f},
            {-0.32f, 0.72f, -0.32f},
            {0.32f, 0.72f, -0.32f}
    };

    public SkyLineRenderer() {
        super(new SkyLineModel());
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack,
                            MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        super.renderByItem(stack, displayContext, poseStack, bufferSource, packedLight, packedOverlay);
        List<ItemStack> items = SkyLineData.getItems(stack);
        for (int i = 0; i < items.size(); i++) {
            ItemStack item = items.get(i);
            if (item.isEmpty()) {
                continue;
            }
            float[] offset = OFFSETS[i];
            poseStack.pushPose();
            poseStack.translate(0.5f + offset[0], offset[1], 0.5f + offset[2]);
            poseStack.mulPose(Axis.YP.rotationDegrees(i * 90f));
            poseStack.scale(0.18f, 0.18f, 0.18f);
            Minecraft.getInstance().getItemRenderer().renderStatic(
                    item, ItemDisplayContext.FIXED, packedLight, packedOverlay,
                    poseStack, bufferSource, Minecraft.getInstance().level, 0);
            poseStack.popPose();
        }
        // 配置物品使用独立的 ItemRenderer 批次，必须立即 flush，否则 GUI/副手渲染时不会出现。
        if (bufferSource instanceof MultiBufferSource.BufferSource source) {
            source.endBatch();
        }
    }
}
