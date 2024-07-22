package com.very.wraq.entities.armor;

import com.very.wraq.series.overworld.sakuraSeries.Scarecrow.WheatArmorChest;
import com.very.wraq.common.Utils.Utils;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class WheatArmorModel extends GeoModel<WheatArmorChest> {

    @Override
    public ResourceLocation getModelResource(WheatArmorChest animatable) {
        return new ResourceLocation(Utils.MOD_ID, "geo/wheatchest.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(WheatArmorChest animatable) {
        return new ResourceLocation(Utils.MOD_ID, "textures/models/armor/wheatchest.png");
    }

    @Override
    public ResourceLocation getAnimationResource(WheatArmorChest animatable) {
        return new ResourceLocation(Utils.MOD_ID, "animations/wheatchest.animation.json");
    }
}
