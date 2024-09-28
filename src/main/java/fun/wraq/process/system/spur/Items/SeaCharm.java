package fun.wraq.process.system.spur.Items;

import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.common.equip.WraqCurios;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class SeaCharm extends WraqCurios {

    public SeaCharm(Properties properties, int serial) {
        super(properties);
        int[] levelRequires = {40, 60, 80, 100, 120, 140, 160};
        double[] healthRecover = {5, 10, 20, 35, 55, 80, 110};
        double[] manaRecover = {5, 8, 12, 17, 23, 30, 38};
        double[] cooldown = {0.05, 0.08, 0.12, 0.17, 0.23, 0.30, 0.38};
        double[] swiftnessUp = {0.2, 0.4, 0.7, 1.1, 1.6, 2.2, 2.9};
        Utils.levelRequire.put(this, levelRequires[serial]);
        Utils.healthRecover.put(this, healthRecover[serial]);
        Utils.manaRecover.put(this, manaRecover[serial]);
        Utils.coolDownDecrease.put(this, cooldown[serial]);
        Utils.swiftnessUp.put(this, swiftnessUp[serial]);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getComprehensiveTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        return List.of();
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfSea;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSpurSuffix();
    }
}
