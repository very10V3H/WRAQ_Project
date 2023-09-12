package com.Very.very.Entity.render;

import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.Entity.HEntity;
import com.Very.very.Entity.model.TestModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RenderTest extends MobRenderer<HEntity,TestModel<HEntity>> {
    public static final ResourceLocation TEXTRUE = new ResourceLocation(Utils.MOD_ID,"textures/entity/testtexture.png");

    public RenderTest(EntityRendererProvider.Context p_174304_) {
        super(p_174304_, new TestModel<>(p_174304_.bakeLayer(TestModel.LAYER_LOCATION)),0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(HEntity p_114482_) {
        return TEXTRUE;
    }


}
