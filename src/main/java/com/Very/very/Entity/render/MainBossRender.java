package com.Very.very.Entity.render;

import com.Very.very.Entity.Entities.MainBoss.MainBoss;
import com.Very.very.Entity.model.MainBossModel;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MainBossRender extends GeoEntityRenderer<MainBoss> {

    public MainBossRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MainBossModel());
    }

    @Override
    public ResourceLocation getTextureLocation(MainBoss animatable) {
        return new ResourceLocation(Utils.MOD_ID,"textures/entity/mainboss.png");
    }

    @Override
    public void render(MainBoss entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {


        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
