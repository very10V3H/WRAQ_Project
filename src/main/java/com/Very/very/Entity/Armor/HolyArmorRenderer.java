package com.Very.very.Entity.Armor;

import com.Very.very.Items.MobItem.HolyArmor;
import com.Very.very.Series.OverWorldSeries.SakuraSeries.SakuraMob.SakuraArmorHelmet;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class HolyArmorRenderer extends GeoArmorRenderer<HolyArmor> {

    public HolyArmorRenderer() {
        super(new HolyArmorModel());
    }
}
