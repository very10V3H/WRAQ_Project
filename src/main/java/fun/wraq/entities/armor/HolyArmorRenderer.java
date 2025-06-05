package fun.wraq.entities.armor;

import fun.wraq.items.mob.HolyArmor;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class HolyArmorRenderer extends GeoArmorRenderer<HolyArmor> {

    public HolyArmorRenderer() {
        super(new HolyArmorModel());
    }
}
