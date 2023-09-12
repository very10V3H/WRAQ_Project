package com.Very.very.Projectile.Mana;

import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NewArrowRender extends ArrowRenderer<NewArrow> {

    public NewArrowRender(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(NewArrow newArrow) {
        return new ResourceLocation(Utils.MOD_ID,"textures/entity/air.png");
    }
}
