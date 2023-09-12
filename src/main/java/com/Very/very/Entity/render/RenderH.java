package com.Very.very.Entity.render;

import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.Entity.HEntity;
import com.Very.very.Entity.model.HEntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RenderH extends MobRenderer<HEntity, HEntityModel<HEntity>> {
    public static final ResourceLocation TEXTRUE = new ResourceLocation(Utils.MOD_ID,"textures/entity/hentity.png");

    public RenderH(EntityRendererProvider.Context p_174304_) {
        super(p_174304_, new HEntityModel<>(p_174304_.bakeLayer(HEntityModel.LAYER_LOCATION)),0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(HEntity p_114482_) {
        return TEXTRUE;
    }


}
