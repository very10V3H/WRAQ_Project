/** AI-Generated, 2026-10-17 */
package fun.wraq.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import fun.wraq.entities.entities.npc.VmdNpc;
import fun.wraq.process.system.missions.mainline.MainQuestVisibility;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

/**
 * 玩家模型 NPC 渲染器。
 * <p>
 * 复用原版 {@link PlayerModel}（64x64 皮肤 UV 布局、slim/wide 两套），不新建模型层、不需要 AT。
 * 宽版与细版模型各烘一份，在 {@link #render} 里按 NPC 的 slim 标记切换
 * （{@code LivingEntityRenderer.model} 是 protected 非 final 字段，可以安全替换）。
 * <p>
 * 装备层使用 {@link VmdNpcArmorLayer}（按 slim 委派到对应盔甲模型），
 * 手持物品层使用原版 {@link ItemInHandLayer}（{@code PlayerItemInHandLayer} 是
 * {@code AbstractClientPlayer} 专用，不能用于自研实体）。
 */
public class VmdNpcRenderer extends LivingEntityRenderer<VmdNpc, PlayerModel<VmdNpc>> {

    private final VmdNpcPlayerModel wideModel;
    private final VmdNpcPlayerModel slimModel;

    public VmdNpcRenderer(EntityRendererProvider.Context context) {
        super(context, new VmdNpcPlayerModel(context.bakeLayer(ModelLayers.PLAYER), false), 0.5F);
        this.wideModel = (VmdNpcPlayerModel) this.model;
        this.slimModel = new VmdNpcPlayerModel(context.bakeLayer(ModelLayers.PLAYER_SLIM), true);
        this.addLayer(new VmdNpcArmorLayer(this, context));
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
    }

    @Override
    public void render(VmdNpc npc, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {
        // 主线阶段未到的 NPC：模型 / 装备层 / 名字板整体不渲染
        if (!MainQuestVisibility.isVisibleToLocalPlayer(npc.getDialogId())) {
            return;
        }
        VmdNpcPlayerModel activeModel = npc.isSlim() ? this.slimModel : this.wideModel;
        this.model = activeModel;
        activeModel.sitting = npc.isSitting();
        activeModel.setAllVisible(true);
        activeModel.crouching = false;
        activeModel.rightArmPose = armPose(npc.getMainHandItem().isEmpty());
        activeModel.leftArmPose = armPose(npc.getOffhandItem().isEmpty());
        super.render(npc, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    private static HumanoidModel.ArmPose armPose(boolean empty) {
        return empty ? HumanoidModel.ArmPose.EMPTY : HumanoidModel.ArmPose.ITEM;
    }

    @Override
    protected void scale(VmdNpc npc, PoseStack poseStack, float partialTick) {
        float scale = npc.getScale();
        if (scale != 1.0F) {
            poseStack.scale(scale, scale, scale);
        }
        // 坐姿时整体下移，贴合椅子/地面（正值向下）；偏移在缩放之后，单位即世界格
        if (npc.isSitting()) {
            float offset = npc.getSitOffset();
            if (offset != 0.0F) {
                poseStack.translate(0.0D, -offset, 0.0D);
            }
        }
    }

    @Override
    public ResourceLocation getTextureLocation(VmdNpc npc) {
        return NpcSkinCache.resolve(npc);
    }
}
