package com.very.wraq.entities.armor;

import com.very.wraq.series.overWorld.MainStory_I.Mine.MineHat;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class MineHatRenderer extends GeoArmorRenderer<MineHat> {

    public MineHatRenderer() {
        super(new MineHatModel());
    }
}
