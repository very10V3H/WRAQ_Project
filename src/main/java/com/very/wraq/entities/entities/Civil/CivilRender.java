package com.very.wraq.entities.entities.Civil;

import com.very.wraq.common.Utils.Utils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CivilRender extends GeoEntityRenderer<Civil> {

    public CivilRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CivilModel());
    }

    @Override
    public ResourceLocation getTextureLocation(Civil animatable) {
        return new ResourceLocation(Utils.MOD_ID, "textures/entity/civil.png");
    }

    @Override
    public void render(Civil entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {


        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
