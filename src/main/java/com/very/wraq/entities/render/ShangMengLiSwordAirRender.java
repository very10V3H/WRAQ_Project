package com.very.wraq.entities.render;

import com.very.wraq.entities.model.ShangMengLiSwordAirModel;
import com.very.wraq.projectiles.mana.ShangMengLiSwordAir;
import com.very.wraq.common.Utils.Utils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ShangMengLiSwordAirRender extends GeoEntityRenderer<ShangMengLiSwordAir> {

    public ShangMengLiSwordAirRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ShangMengLiSwordAirModel());
    }

    @Override
    public ResourceLocation getTextureLocation(ShangMengLiSwordAir animatable) {
        return new ResourceLocation(Utils.MOD_ID, "textures/entity/shangmengli_sword_air.png");
    }

    @Override
    public void render(ShangMengLiSwordAir entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        this.model.getBone("bone").ifPresent(geoBone -> {
            Vec3 vec3 = entity.getDeltaMovement();
            Vec3 vec3NoY = new Vec3(vec3.x, 0, vec3.z);
            Vec3 zNormal = new Vec3(0, 0, -1);
            double angle = Math.acos(vec3NoY.dot(zNormal) / (vec3NoY.length() * zNormal.length()));
            if (vec3.x > 0) angle = -angle;
            geoBone.setRotY((float) angle);

            angle = Math.atan(vec3.y / Math.abs(Math.sqrt(vec3.x * vec3.x + vec3.z * vec3.z)));
            geoBone.setRotX((float) -angle);

        });
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
