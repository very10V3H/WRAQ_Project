package com.Very.very.Entity.model;

import com.Very.very.Entity.SakuraMob;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class SakuraMobModel extends GeoModel<SakuraMob>{

    @Override
    public ResourceLocation getModelResource(SakuraMob animatable) {
        return new ResourceLocation(Utils.MOD_ID,"geo/sakuramob.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SakuraMob animatable) {
        return new ResourceLocation(Utils.MOD_ID,"texture/entity/sakuramob.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SakuraMob animatable) {
        return new ResourceLocation(Utils.MOD_ID,"animations/sakuramob.animation.json");
    }

    @Override
    public void setCustomAnimations(SakuraMob animatable, long instanceId, AnimationState<SakuraMob> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}
