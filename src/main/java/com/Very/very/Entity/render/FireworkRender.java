package com.Very.very.Entity.render;


import com.Very.very.Entity.model.FireworkModel;
import com.Very.very.Entity.model.ManaArrowModel;
import com.Very.very.Projectile.Firework.Firework;
import com.Very.very.Projectile.Mana.ManaArrow;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.mojang.blaze3d.vertex.PoseStack;
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
        return new ResourceLocation(Utils.MOD_ID,"textures/entity/fire_work.png");
    }

    @Override
    public void render(Firework entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {


        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
