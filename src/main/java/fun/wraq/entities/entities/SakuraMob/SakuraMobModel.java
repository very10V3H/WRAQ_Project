package fun.wraq.entities.entities.SakuraMob;

import fun.wraq.common.util.Utils;
import fun.wraq.entities.entities.SakuraMob.SakuraMob;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class SakuraMobModel extends GeoModel<fun.wraq.entities.entities.SakuraMob.SakuraMob> {

    @Override
    public ResourceLocation getModelResource(fun.wraq.entities.entities.SakuraMob.SakuraMob animatable) {
        return new ResourceLocation(Utils.MOD_ID, "geo/sakuramob.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(fun.wraq.entities.entities.SakuraMob.SakuraMob animatable) {
        return new ResourceLocation(Utils.MOD_ID, "texture/entity/sakuramob.png");
    }

    @Override
    public ResourceLocation getAnimationResource(fun.wraq.entities.entities.SakuraMob.SakuraMob animatable) {
        return new ResourceLocation(Utils.MOD_ID, "animations/sakuramob.animation.json");
    }

    @Override
    public void setCustomAnimations(fun.wraq.entities.entities.SakuraMob.SakuraMob animatable, long instanceId, AnimationState<SakuraMob> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}
