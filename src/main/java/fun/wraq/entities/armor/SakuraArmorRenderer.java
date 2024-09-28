package fun.wraq.entities.armor;

import fun.wraq.entities.armor.SakuraArmorModel;
import fun.wraq.series.overworld.sakuraSeries.SakuraMob.SakuraArmorHelmet;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class SakuraArmorRenderer extends GeoArmorRenderer<SakuraArmorHelmet> {

    public SakuraArmorRenderer() {
        super(new SakuraArmorModel());
    }
}
