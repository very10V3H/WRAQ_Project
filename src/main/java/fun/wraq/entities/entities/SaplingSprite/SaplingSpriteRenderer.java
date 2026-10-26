/**
 * AI-Generated, 2026-10-27
 * 树苗精灵渲染器：直接把原版树苗方块模型（cross 模型）渲染成生物外观，
 * 并叠加轻微的上下浮动与左右摇摆，让它看起来像活物。
 */
package fun.wraq.entities.entities.SaplingSprite;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class SaplingSpriteRenderer extends EntityRenderer<SaplingSprite> {

    private static final BlockState SAPLING = Blocks.OAK_SAPLING.defaultBlockState();
    private final BlockRenderDispatcher blockRenderer;

    public SaplingSpriteRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.blockRenderer = context.getBlockRenderDispatcher();
        this.shadowRadius = 0.25F;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void render(SaplingSprite entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight) {
        float time = entity.tickCount + partialTick;

        poseStack.pushPose();
        // 轻微浮动 + 摇摆
        poseStack.translate(0.0D, 0.03D + Mth.sin(time * 0.18F) * 0.03D, 0.0D);
        poseStack.mulPose(Axis.YP.rotationDegrees(-entityYaw));
        poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.sin(time * 0.12F) * 3.0F));
        // 方块模型坐标是 0..1，把它居中到实体脚下
        poseStack.translate(-0.5D, 0.0D, -0.5D);
        this.blockRenderer.renderSingleBlock(SAPLING, poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();

        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(SaplingSprite entity) {
        // 方块模型通过 BlockRenderDispatcher 使用方块图集，这里返回方块图集即可
        return InventoryMenu.BLOCK_ATLAS;
    }
}
