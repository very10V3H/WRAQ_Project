package com.Very.very.Entity.model;

import com.Very.very.Projectile.Mana.ManaArrow;
import com.Very.very.Projectile.Mana.Meteorite;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class MeteoriteModel extends GeoModel<Meteorite>{

    @Override
    public ResourceLocation getModelResource(Meteorite animatable) {
        return new ResourceLocation(Utils.MOD_ID,"geo/meteorite.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Meteorite animatable) {
        return new ResourceLocation(Utils.MOD_ID,"texture/entity/meteorite.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Meteorite animatable) {
        return new ResourceLocation(Utils.MOD_ID,"animations/meteorite.animation.json");
    }

    @Override
    public void setCustomAnimations(Meteorite newArrowPlain, long instanceId, AnimationState<Meteorite> animationState) {

    }


}
