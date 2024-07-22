package com.very.wraq.entities.model;

import com.very.wraq.projectiles.mana.BlazeSword;
import com.very.wraq.common.Utils.Utils;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class BlazeSwordModel extends GeoModel<BlazeSword> {

    @Override
    public ResourceLocation getModelResource(BlazeSword animatable) {
        return new ResourceLocation(Utils.MOD_ID, "geo/blaze_sword.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BlazeSword animatable) {
        return new ResourceLocation(Utils.MOD_ID, "texture/entity/blaze_sword.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BlazeSword animatable) {
        return new ResourceLocation(Utils.MOD_ID, "animations/meteorite.animation.json");
    }

    @Override
    public void setCustomAnimations(BlazeSword newArrowPlain, long instanceId, AnimationState<BlazeSword> animationState) {

    }


}
