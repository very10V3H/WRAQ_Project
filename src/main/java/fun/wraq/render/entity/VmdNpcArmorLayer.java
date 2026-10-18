/** AI-Generated, 2026-10-17 */
package fun.wraq.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import fun.wraq.entities.entities.npc.VmdNpc;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

/**
 * NPC 的盔甲渲染层：按 slim / wide 委派到对应模型的 {@link HumanoidArmorLayer}。
 * <p>
 * 为什么需要这层包装：{@code HumanoidArmorLayer} 的内外两层盔甲模型在构造时就固定了，
 * 而 {@code LivingEntityRenderer#addLayer} 是 {@code final}，无法按实体动态增删。
 * 因此这里同时持有宽版/细版两套盔甲层，渲染时按 {@link VmdNpc#isSlim()} 选择一个——
 * 否则细版 NPC 穿盔甲会出现肩膀错位。
 */
public class VmdNpcArmorLayer extends RenderLayer<VmdNpc, PlayerModel<VmdNpc>> {

    private final HumanoidArmorLayer<VmdNpc, PlayerModel<VmdNpc>, HumanoidArmorModel<VmdNpc>> wideArmor;
    private final HumanoidArmorLayer<VmdNpc, PlayerModel<VmdNpc>, HumanoidArmorModel<VmdNpc>> slimArmor;

    public VmdNpcArmorLayer(RenderLayerParent<VmdNpc, PlayerModel<VmdNpc>> parent,
                            EntityRendererProvider.Context context) {
        super(parent);
        this.wideArmor = new HumanoidArmorLayer<>(parent,
                new HumanoidArmorModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidArmorModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)),
                context.getModelManager());
        this.slimArmor = new HumanoidArmorLayer<>(parent,
                new HumanoidArmorModel<>(context.bakeLayer(ModelLayers.PLAYER_SLIM_INNER_ARMOR)),
                new HumanoidArmorModel<>(context.bakeLayer(ModelLayers.PLAYER_SLIM_OUTER_ARMOR)),
                context.getModelManager());
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, VmdNpc npc,
                       float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks,
                       float netHeadYaw, float headPitch) {
        HumanoidArmorLayer<VmdNpc, PlayerModel<VmdNpc>, HumanoidArmorModel<VmdNpc>> armor =
                npc.isSlim() ? this.slimArmor : this.wideArmor;
        armor.render(poseStack, bufferSource, packedLight, npc, limbSwing, limbSwingAmount,
                partialTick, ageInTicks, netHeadYaw, headPitch);
    }
}
