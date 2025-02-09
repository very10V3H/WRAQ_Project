package fun.wraq.entities.model;

import fun.wraq.common.util.Utils;
import fun.wraq.entities.entities.Scarecrow.Scarecrow;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class ScarecrowModel extends GeoModel<Scarecrow> {

    @Override
    public ResourceLocation getModelResource(Scarecrow animatable) {
        return new ResourceLocation(Utils.MOD_ID, "geo/scarecrow.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Scarecrow animatable) {
        return new ResourceLocation(Utils.MOD_ID, "texture/entity/scarecrow.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Scarecrow animatable) {
        return new ResourceLocation(Utils.MOD_ID, "animations/scarecrow.animation.json");
    }

    @Override
    public void setCustomAnimations(Scarecrow animatable, long instanceId, AnimationState<Scarecrow> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}
