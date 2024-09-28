package fun.wraq.entities.armor;

import fun.wraq.entities.armor.MineHatModel;
import fun.wraq.series.overworld.chapter1.Mine.MineHat;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class MineHatRenderer extends GeoArmorRenderer<MineHat> {

    public MineHatRenderer() {
        super(new MineHatModel());
    }
}
