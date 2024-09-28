package fun.wraq.entities.armor;

import fun.wraq.entities.armor.MinePantsModel;
import fun.wraq.series.overworld.sakuraSeries.MineWorker.MinePants;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class MinePantsRenderer extends GeoArmorRenderer<MinePants> {

    public MinePantsRenderer() {
        super(new MinePantsModel());
    }
}
