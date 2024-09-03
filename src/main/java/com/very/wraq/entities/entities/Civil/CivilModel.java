package com.very.wraq.entities.entities.Civil;

import com.very.wraq.common.util.Utils;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class CivilModel extends GeoModel<Civil> {

    @Override
    public ResourceLocation getModelResource(Civil animatable) {
        return new ResourceLocation(Utils.MOD_ID, "geo/civil.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Civil animatable) {
        return new ResourceLocation(Utils.MOD_ID, "texture/entity/civil.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Civil animatable) {
        return new ResourceLocation(Utils.MOD_ID, "animations/civil.animation.json");
    }

    @Override
    public void setCustomAnimations(Civil animatable, long instanceId, AnimationState<Civil> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}
