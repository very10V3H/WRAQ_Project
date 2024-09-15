package com.very.wraq.entities.model;

import com.very.wraq.common.util.Utils;
import com.very.wraq.projectiles.firework.Firework;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class FireworkModel extends GeoModel<Firework> {

    @Override
    public ResourceLocation getModelResource(Firework animatable) {
        return new ResourceLocation(Utils.MOD_ID, "geo/fire_work.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Firework animatable) {
        return new ResourceLocation(Utils.MOD_ID, "texture/entity/fire_work.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Firework animatable) {
        return new ResourceLocation(Utils.MOD_ID, "animations/fire_work.animation.json");
    }

    @Override
    public void setCustomAnimations(Firework animatable, long instanceId, AnimationState<Firework> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
        super.setCustomAnimations(animatable, instanceId, animationState);
    }

}
