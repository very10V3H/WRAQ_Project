package com.very.wraq.entities.armor;

import com.very.wraq.series.overworld.chapter1.Mine.MineHat;
import com.very.wraq.common.Utils.Utils;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class MineHatModel extends GeoModel<MineHat> {

    @Override
    public ResourceLocation getModelResource(MineHat animatable) {
        return new ResourceLocation(Utils.MOD_ID, "geo/mine_hat.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MineHat animatable) {
        return new ResourceLocation(Utils.MOD_ID, "textures/models/armor/mine_hat.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MineHat animatable) {
        return new ResourceLocation(Utils.MOD_ID, "animations/sakurahat.animation.json");
    }
}
