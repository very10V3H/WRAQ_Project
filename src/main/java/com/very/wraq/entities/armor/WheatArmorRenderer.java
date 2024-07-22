package com.very.wraq.entities.armor;

import com.very.wraq.series.overworld.sakuraSeries.Scarecrow.WheatArmorChest;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class WheatArmorRenderer extends GeoArmorRenderer<WheatArmorChest> {

    public WheatArmorRenderer() {
        super(new WheatArmorModel());
    }
}
