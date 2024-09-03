package com.very.wraq.entities.model;

import com.very.wraq.entities.entities.MainBoss.MainBoss;
import com.very.wraq.common.util.Utils;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class MainBossModel extends GeoModel<MainBoss> {

    @Override
    public ResourceLocation getModelResource(MainBoss animatable) {
        return new ResourceLocation(Utils.MOD_ID, "geo/mainboss.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MainBoss animatable) {
        return new ResourceLocation(Utils.MOD_ID, "texture/entity/mainboss.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MainBoss animatable) {
        return new ResourceLocation(Utils.MOD_ID, "animations/mainboss.animation.json");
    }

    @Override
    public void setCustomAnimations(MainBoss animatable, long instanceId, AnimationState<MainBoss> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}
