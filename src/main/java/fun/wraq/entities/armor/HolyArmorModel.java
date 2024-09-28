package fun.wraq.entities.armor;

import fun.wraq.Items.MobItem.HolyArmor;
import fun.wraq.common.util.Utils;
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
