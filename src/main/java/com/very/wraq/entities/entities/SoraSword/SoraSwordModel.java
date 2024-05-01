package com.very.wraq.entities.entities.SoraSword;

import com.very.wraq.customized.players.sceptre.Sora_vanilla.SoraVanillaSword;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SoraSwordModel extends GeoModel<SoraVanillaSword>{

    @Override
    public ResourceLocation getModelResource(SoraVanillaSword animatable) {
        return new ResourceLocation(Utils.MOD_ID,"geo/sora_sword.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SoraVanillaSword animatable) {
        return new ResourceLocation(Utils.MOD_ID,"textures/entity/murasama.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SoraVanillaSword animatable) {
        return new ResourceLocation(Utils.MOD_ID,"animations/sakurahat.animation.json");
    }

}
