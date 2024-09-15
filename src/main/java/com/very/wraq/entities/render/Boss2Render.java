package com.very.wraq.entities.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.very.wraq.common.util.Utils;
import com.very.wraq.entities.entities.Boss2.Boss2;
import com.very.wraq.entities.model.Boss2Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Boss2Render extends GeoEntityRenderer<Boss2> {

    public Boss2Render(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Boss2Model());
    }

    @Override
    public ResourceLocation getTextureLocation(Boss2 animatable) {
        return new ResourceLocation(Utils.MOD_ID, "textures/entity/boss2.png");
    }

    @Override
    public void render(Boss2 entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {


        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
