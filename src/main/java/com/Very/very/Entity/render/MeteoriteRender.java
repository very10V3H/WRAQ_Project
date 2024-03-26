package com.Very.very.Entity.render;

import com.Very.very.Entity.model.MeteoriteModel;
import com.Very.very.Projectile.Mana.Meteorite;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.model.geometry.UnbakedGeometryHelper;
import org.joml.Vector3d;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Random;

public class MeteoriteRender extends GeoEntityRenderer<Meteorite> {

    public MeteoriteRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MeteoriteModel());
    }

    @Override
    public ResourceLocation getTextureLocation(Meteorite animatable) {
        return new ResourceLocation(Utils.MOD_ID,"textures/entity/meteorite.png");
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
