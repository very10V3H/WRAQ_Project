package com.Very.very.Items.Animation;

import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AnimatedItemModel extends GeoModel<AnimatedItem> {
    @Override
    public ResourceLocation getModelResource(AnimatedItem animatable) {
        return new ResourceLocation(Utils.MOD_ID, "geo/snowsword4a.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AnimatedItem animatable) {
        return new ResourceLocation(Utils.MOD_ID, "textures/item/snowsword4a.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AnimatedItem animatable) {
        return new ResourceLocation(Utils.MOD_ID, "animations/snowsword4a.animation.json");
    }
}
