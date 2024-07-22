package com.very.wraq.entities.armor;

import com.very.wraq.Items.MobItem.HolyArmor;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class HolyArmorRenderer extends GeoArmorRenderer<HolyArmor> {

    public HolyArmorRenderer() {
        super(new HolyArmorModel());
    }
}
