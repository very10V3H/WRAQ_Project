package com.very.wraq.entities.model;

import com.very.wraq.projectiles.mana.Meteorite;
import com.very.wraq.common.util.Utils;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class MeteoriteModel extends GeoModel<Meteorite> {

    @Override
    public ResourceLocation getModelResource(Meteorite animatable) {
        return new ResourceLocation(Utils.MOD_ID, "geo/meteorite.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Meteorite animatable) {
        return new ResourceLocation(Utils.MOD_ID, "texture/entity/meteorite.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Meteorite animatable) {
        return new ResourceLocation(Utils.MOD_ID, "animations/meteorite.animation.json");
    }

    @Override
    public void setCustomAnimations(Meteorite newArrowPlain, long instanceId, AnimationState<Meteorite> animationState) {

    }


}
