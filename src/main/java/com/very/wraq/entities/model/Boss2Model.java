package com.very.wraq.entities.model;

import com.very.wraq.entities.entities.Boss2.Boss2;
import com.very.wraq.common.Utils.Utils;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class Boss2Model extends GeoModel<Boss2> {

    @Override
    public ResourceLocation getModelResource(Boss2 boss2) {
        return new ResourceLocation(Utils.MOD_ID, "geo/boss2.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Boss2 boss2) {
        return new ResourceLocation(Utils.MOD_ID, "texture/entity/boss2.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Boss2 boss2) {
        return new ResourceLocation(Utils.MOD_ID, "animations/boss2.animation.json");
    }

    @Override
    public void setCustomAnimations(Boss2 boss2, long instanceId, AnimationState<Boss2> animationState) {
        super.setCustomAnimations(boss2, instanceId, animationState);
    }
}
