package com.very.wraq.entities.armor;

import com.very.wraq.series.overworld.chapter1.Mine.MineHat;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class MineHatRenderer extends GeoArmorRenderer<MineHat> {

    public MineHatRenderer() {
        super(new MineHatModel());
    }
}
