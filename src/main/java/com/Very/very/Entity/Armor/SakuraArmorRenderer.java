package com.Very.very.Entity.Armor;

import com.Very.very.Series.SakuraSeries.SakurMob.SakuraArmorHelmet;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class SakuraArmorRenderer extends GeoArmorRenderer<SakuraArmorHelmet> {

    public SakuraArmorRenderer() {
        super(new SakuraArmorModel());
    }
}
