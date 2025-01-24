package fun.wraq.entities.armor;

import fun.wraq.series.overworld.sakura.SakuraMob.SakuraArmorHelmet;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class SakuraArmorRenderer extends GeoArmorRenderer<SakuraArmorHelmet> {

    public SakuraArmorRenderer() {
        super(new SakuraArmorModel());
    }
}
