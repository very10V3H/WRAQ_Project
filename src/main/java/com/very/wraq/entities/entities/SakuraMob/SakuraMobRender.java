package com.very.wraq.entities.entities.SakuraMob;

import com.very.wraq.common.Utils.Utils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SakuraMobRender extends GeoEntityRenderer<SakuraMob> {

    public SakuraMobRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SakuraMobModel());
    }

    @Override
    public ResourceLocation getTextureLocation(SakuraMob animatable) {
        return new ResourceLocation(Utils.MOD_ID, "textures/entity/sakuramob.png");
    }

    @Override
    public void render(SakuraMob entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {


        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
