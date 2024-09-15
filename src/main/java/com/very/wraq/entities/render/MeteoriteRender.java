package com.very.wraq.entities.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.very.wraq.common.util.Utils;
import com.very.wraq.entities.model.MeteoriteModel;
import com.very.wraq.projectiles.mana.Meteorite;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MeteoriteRender extends GeoEntityRenderer<Meteorite> {

    public MeteoriteRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MeteoriteModel());
    }

    @Override
    public ResourceLocation getTextureLocation(Meteorite animatable) {
        return new ResourceLocation(Utils.MOD_ID, "textures/entity/meteorite.png");
    }

    @Override
    public void render(Meteorite entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
/*        this.model.getBone("bone").ifPresent(geoBone -> {
            Vec3 vec3 = entity.getDeltaMovement();
            Vec3 vec3NoY = new Vec3(vec3.x,0,vec3.z);
            Vec3 zNormal = new Vec3(0,0,-1);
            double angle = Math.acos(vec3NoY.dot(zNormal)/(vec3NoY.length() * zNormal.length()));
            if (vec3.x > 0) angle = - angle;
            geoBone.setRotY((float) angle);

            Vec3 vec3NoX = new Vec3(0,vec3.y,vec3.z);
            angle = Math.atan(vec3NoX.y / Math.abs(vec3NoX.z));
            geoBone.setRotX((float) - angle);
        });*/
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
