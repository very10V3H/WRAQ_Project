package fun.wraq.entities.armor;

import fun.wraq.entities.armor.WheatArmorModel;
import fun.wraq.series.overworld.sakuraSeries.Scarecrow.WheatArmorChest;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class WheatArmorRenderer extends GeoArmorRenderer<WheatArmorChest> {

    public WheatArmorRenderer() {
        super(new WheatArmorModel());
    }
}
