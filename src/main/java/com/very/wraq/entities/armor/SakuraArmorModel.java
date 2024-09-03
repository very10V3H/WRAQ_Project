package com.very.wraq.entities.armor;

import com.very.wraq.series.overworld.sakuraSeries.SakuraMob.SakuraArmorHelmet;
import com.very.wraq.common.util.Utils;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SakuraArmorModel extends GeoModel<SakuraArmorHelmet> {

    @Override
    public ResourceLocation getModelResource(SakuraArmorHelmet animatable) {
        return new ResourceLocation(Utils.MOD_ID, "geo/sakurahat.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SakuraArmorHelmet animatable) {
        return new ResourceLocation(Utils.MOD_ID, "textures/models/armor/sakura.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SakuraArmorHelmet animatable) {
        return new ResourceLocation(Utils.MOD_ID, "animations/sakurahat.animation.json");
    }
}
