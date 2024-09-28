package fun.wraq.entities.model;

import fun.wraq.common.util.Utils;
import fun.wraq.projectiles.mana.ShangMengLiSwordAir;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class ShangMengLiSwordAirModel extends GeoModel<ShangMengLiSwordAir> {

    @Override
    public ResourceLocation getModelResource(ShangMengLiSwordAir animatable) {
        return new ResourceLocation(Utils.MOD_ID, "geo/sword_air.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ShangMengLiSwordAir animatable) {
        return new ResourceLocation(Utils.MOD_ID, "texture/entity/sword_air.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ShangMengLiSwordAir animatable) {
        return new ResourceLocation(Utils.MOD_ID, "animations/meteorite.animation.json");
    }

    @Override
    public void setCustomAnimations(ShangMengLiSwordAir newArrowPlain, long instanceId, AnimationState<ShangMengLiSwordAir> animationState) {

    }


}
