package com.Very.very.Entity.Armor;

import com.Very.very.Series.OverWorldSeries.SakuraSeries.MineWorker.MinePants;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class MinePantsModel extends GeoModel<MinePants> {

    @Override
    public ResourceLocation getModelResource(MinePants animatable) {
        return new ResourceLocation(Utils.MOD_ID, "geo/minepants.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MinePants animatable) {
        return new ResourceLocation(Utils.MOD_ID, "textures/models/armor/minepants.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MinePants animatable) {
        return new ResourceLocation(Utils.MOD_ID, "animations/minepants.animation.json");
    }
}
