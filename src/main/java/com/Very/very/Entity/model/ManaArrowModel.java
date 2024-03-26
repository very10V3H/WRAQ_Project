package com.Very.very.Entity.model;

import com.Very.very.Projectile.Mana.ManaArrow;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

import java.util.Random;

public class ManaArrowModel extends GeoModel<ManaArrow>{

    @Override
    public ResourceLocation getModelResource(ManaArrow animatable) {
        return new ResourceLocation(Utils.MOD_ID,"geo/mana_arrow.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ManaArrow animatable) {
        return new ResourceLocation(Utils.MOD_ID,"texture/entity/mana_arrow.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ManaArrow animatable) {
        return new ResourceLocation(Utils.MOD_ID,"animations/mana_arrow.animation.json");
    }

    @Override
    public void setCustomAnimations(ManaArrow newArrowPlain, long instanceId, AnimationState<ManaArrow> animationState) {

    }


}
