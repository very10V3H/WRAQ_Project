package fun.wraq.process.system.spur.Items.crop;

import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class CropCharm extends WraqCurios {

    public CropCharm(Properties properties, int serial) {
        super(properties);
        int[] levelRequires = {40, 60, 80, 100, 120, 140, 160};
        double[] maxHealth = {800, 3200, 2800, 4400, 6400, 8800, 11500};
        double[] healthRecover = {5, 10, 20, 35, 55, 80, 110};
        Utils.levelRequire.put(this, levelRequires[serial]);
        Utils.maxHealth.put(this, maxHealth[serial]);
        Utils.percentMaxHealthEnhance.put(this, new double[]{1, 2, 3, 4, 6, 9, 13}[serial] * 0.01);
        Utils.healthRecover.put(this, healthRecover[serial]);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getDefenceTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        return List.of();
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfField;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSpurSuffix();
    }
}
