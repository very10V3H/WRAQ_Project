package com.very.wraq.entities.armor;

import com.very.wraq.series.overworld.sakuraSeries.MineWorker.MinePants;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class MinePantsRenderer extends GeoArmorRenderer<MinePants> {

    public MinePantsRenderer() {
        super(new MinePantsModel());
    }
}
