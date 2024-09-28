package fun.wraq.entities.entities.Civil;

import fun.wraq.common.util.Utils;
import fun.wraq.entities.entities.Civil.Civil;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class CivilModel extends GeoModel<fun.wraq.entities.entities.Civil.Civil> {

    @Override
    public ResourceLocation getModelResource(fun.wraq.entities.entities.Civil.Civil animatable) {
        return new ResourceLocation(Utils.MOD_ID, "geo/civil.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(fun.wraq.entities.entities.Civil.Civil animatable) {
        return new ResourceLocation(Utils.MOD_ID, "texture/entity/civil.png");
    }

    @Override
    public ResourceLocation getAnimationResource(fun.wraq.entities.entities.Civil.Civil animatable) {
        return new ResourceLocation(Utils.MOD_ID, "animations/civil.animation.json");
    }

    @Override
    public void setCustomAnimations(fun.wraq.entities.entities.Civil.Civil animatable, long instanceId, AnimationState<Civil> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}
