package fun.wraq.entities.armor;

import fun.wraq.Items.MobItem.HolyArmor;
import fun.wraq.entities.armor.HolyArmorModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class HolyArmorRenderer extends GeoArmorRenderer<HolyArmor> {

    public HolyArmorRenderer() {
        super(new HolyArmorModel());
    }
}
