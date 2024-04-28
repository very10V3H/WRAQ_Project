package com.very.wraq.entities.armor;

import com.very.wraq.series.overWorld.SakuraSeries.SakuraMob.SakuraArmorHelmet;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class SakuraArmorRenderer extends GeoArmorRenderer<SakuraArmorHelmet> {

    public SakuraArmorRenderer() {
        super(new SakuraArmorModel());
    }
}
