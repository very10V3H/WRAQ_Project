package com.very.wraq.entities.render;

import com.very.wraq.common.util.Utils;
import com.very.wraq.entities.entities.MainBoss.MainBoss;
import com.very.wraq.entities.model.TestModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RenderTest extends MobRenderer<MainBoss, TestModel<MainBoss>> {
    public static final ResourceLocation TEXTRUE = new ResourceLocation(Utils.MOD_ID, "textures/entity/testtexture.png");

    public RenderTest(EntityRendererProvider.Context p_174304_) {
        super(p_174304_, new TestModel<>(p_174304_.bakeLayer(TestModel.LAYER_LOCATION)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(MainBoss p_114482_) {
        return TEXTRUE;
    }


}
