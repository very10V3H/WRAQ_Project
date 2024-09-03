package com.very.wraq.entities.model;

import com.very.wraq.projectiles.mana.SwordAir;
import com.very.wraq.common.util.Utils;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class SwordAirModel extends GeoModel<SwordAir> {

    @Override
    public ResourceLocation getModelResource(SwordAir animatable) {
        return new ResourceLocation(Utils.MOD_ID, "geo/sword_air.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SwordAir animatable) {
        return new ResourceLocation(Utils.MOD_ID, "texture/entity/sword_air.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SwordAir animatable) {
        return new ResourceLocation(Utils.MOD_ID, "animations/meteorite.animation.json");
    }

    @Override
    public void setCustomAnimations(SwordAir newArrowPlain, long instanceId, AnimationState<SwordAir> animationState) {

    }


}
