package com.very.wraq.entities.render;


import com.mojang.blaze3d.vertex.PoseStack;
import com.very.wraq.common.util.Utils;
import com.very.wraq.entities.model.FireworkModel;
import com.very.wraq.projectiles.firework.Firework;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FireworkRender extends GeoEntityRenderer<Firework> {

    public FireworkRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new FireworkModel());
    }

    @Override
    public ResourceLocation getTextureLocation(Firework animatable) {
        return new ResourceLocation(Utils.MOD_ID, "textures/entity/fire_work.png");
    }

    @Override
    public void render(Firework entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {


        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
