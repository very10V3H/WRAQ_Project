package fun.wraq.entities.armor;

import fun.wraq.series.overworld.sakura.Scarecrow.WheatChest;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class WheatArmorRenderer extends GeoArmorRenderer<WheatChest> {

    public WheatArmorRenderer() {
        super(new WheatArmorModel());
    }
}
