package com.very.wraq.projectiles.bow;

import com.very.wraq.core.MyArrow;
import com.very.wraq.common.util.Utils;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class RenderForArrow extends ArrowRenderer<MyArrow> {
    public static final ResourceLocation TEXTRUE = new ResourceLocation(Utils.MOD_ID, "textures/entity/air.png");

    public RenderForArrow(EntityRendererProvider.Context p_173917_) {
        super(p_173917_);
    }

    @Override
    public ResourceLocation getTextureLocation(MyArrow myArrow) {
        return TEXTRUE;
    }

    @Override
    public boolean shouldRender(MyArrow p_114491_, Frustum p_114492_, double p_114493_, double p_114494_, double p_114495_) {
        return false;
    }
}
