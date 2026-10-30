package fun.wraq.series.overworld.chapter2.sky;

import fun.wraq.common.util.Utils;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

/**
 * AI-Generated, 2026-10-30
 * Sky-Line 手环 GeckoLib 模型，使用 plainbracelet 几何与 skybracelet 材质。
 */
public class SkyLineModel extends GeoModel<SkyLineOffhand> {

    @Override
    public ResourceLocation getModelResource(SkyLineOffhand animatable) {
        return new ResourceLocation(Utils.MOD_ID, "geo/plainbracelet.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SkyLineOffhand animatable) {
        return new ResourceLocation(Utils.MOD_ID, "textures/item/skybracelet.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SkyLineOffhand animatable) {
        return new ResourceLocation(Utils.MOD_ID, "animations/plainbracelet.animation.json");
    }
}
