package fun.wraq.entities.animatedItem;

import fun.wraq.common.util.Utils;
import fun.wraq.entities.animatedItem.AnimatedItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AnimatedItemModel extends GeoModel<fun.wraq.entities.animatedItem.AnimatedItem> {
    @Override
    public ResourceLocation getModelResource(fun.wraq.entities.animatedItem.AnimatedItem animatable) {
        return new ResourceLocation(Utils.MOD_ID, "geo/animated_item.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(fun.wraq.entities.animatedItem.AnimatedItem animatable) {
        return new ResourceLocation(Utils.MOD_ID, "textures/item/animated_item.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AnimatedItem animatable) {
        return new ResourceLocation(Utils.MOD_ID, "animations/animated_item.animation.json");
    }
}
