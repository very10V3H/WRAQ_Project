package fun.wraq.entities.entities.SoraSword;

import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.entities.entities.SoraSword.SoraSwordAir;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class SoraSwordAirModel extends GeoModel<fun.wraq.entities.entities.SoraSword.SoraSwordAir> {

    @Override
    public ResourceLocation getModelResource(fun.wraq.entities.entities.SoraSword.SoraSwordAir animatable) {
        return new ResourceLocation(Utils.MOD_ID, "geo/sword_air.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(fun.wraq.entities.entities.SoraSword.SoraSwordAir animatable) {
        return new ResourceLocation(Utils.MOD_ID, "texture/entity/sword_air.png");
    }

    @Override
    public ResourceLocation getAnimationResource(fun.wraq.entities.entities.SoraSword.SoraSwordAir animatable) {
        return new ResourceLocation(Utils.MOD_ID, "animations/meteorite.animation.json");
    }

    @Override
    public void setCustomAnimations(fun.wraq.entities.entities.SoraSword.SoraSwordAir newArrowPlain, long instanceId, AnimationState<SoraSwordAir> animationState) {

    }


}
