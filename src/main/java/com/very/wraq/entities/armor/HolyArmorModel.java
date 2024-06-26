package com.very.wraq.entities.armor;

import com.very.wraq.Items.MobItem.HolyArmor;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HolyArmorModel extends GeoModel<HolyArmor> {

    @Override
    public ResourceLocation getModelResource(HolyArmor animatable) {
        return new ResourceLocation(Utils.MOD_ID, "geo/amethyst_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(HolyArmor animatable) {
        return new ResourceLocation(Utils.MOD_ID, "textures/armor/amethyst_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(HolyArmor animatable) {
        return new ResourceLocation(Utils.MOD_ID, "animations/amethyst_armor.animation.json");
    }
}
