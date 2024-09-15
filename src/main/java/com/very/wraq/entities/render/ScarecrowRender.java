package com.very.wraq.entities.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.very.wraq.common.util.Utils;
import com.very.wraq.entities.entities.Scarecrow.Scarecrow;
import com.very.wraq.entities.model.ScarecrowModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ScarecrowRender extends GeoEntityRenderer<Scarecrow> {

    public ScarecrowRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ScarecrowModel());
    }

    @Override
    public ResourceLocation getTextureLocation(Scarecrow animatable) {
        return new ResourceLocation(Utils.MOD_ID, "textures/entity/scarecrow.png");
    }

    @Override
    public void render(Scarecrow entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {


        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
