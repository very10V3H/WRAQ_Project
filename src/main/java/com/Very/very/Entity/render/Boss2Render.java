package com.Very.very.Entity.render;

import com.Very.very.Entity.Entities.Boss2.Boss2;
import com.Very.very.Entity.model.Boss2Model;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.mojang.blaze3d.vertex.PoseStack;
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
        return new ResourceLocation(Utils.MOD_ID,"textures/entity/boss2.png");
    }

    @Override
    public void render(Boss2 entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {


        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
