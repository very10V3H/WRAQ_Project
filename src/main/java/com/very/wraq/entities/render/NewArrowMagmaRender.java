package com.very.wraq.entities.render;


import com.mojang.blaze3d.vertex.PoseStack;
import com.very.wraq.common.util.Utils;
import com.very.wraq.entities.model.ManaArrowModel;
import com.very.wraq.projectiles.mana.ManaArrow;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class NewArrowMagmaRender extends GeoEntityRenderer<ManaArrow> {

    public NewArrowMagmaRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ManaArrowModel());
    }

    @Override
    public ResourceLocation getTextureLocation(ManaArrow animatable) {
        return new ResourceLocation(Utils.MOD_ID, "textures/entity/magma_mana_arrow.png");
    }

    @Override
    public void render(ManaArrow entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {


        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
