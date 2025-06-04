package fun.wraq.entities.armor;

import fun.wraq.common.util.Utils;
import fun.wraq.series.overworld.sakura.Scarecrow.WheatChest;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class WheatArmorModel extends GeoModel<WheatChest> {

    @Override
    public ResourceLocation getModelResource(WheatChest animatable) {
        return new ResourceLocation(Utils.MOD_ID, "geo/wheatchest.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(WheatChest animatable) {
        return new ResourceLocation(Utils.MOD_ID, "textures/models/armor/wheatchest.png");
    }

    @Override
    public ResourceLocation getAnimationResource(WheatChest animatable) {
        return new ResourceLocation(Utils.MOD_ID, "animations/wheatchest.animation.json");
    }
}
