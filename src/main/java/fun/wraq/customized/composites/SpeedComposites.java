package fun.wraq.customized.composites;

import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class SpeedComposites extends WraqCurios {

    public SpeedComposites(Properties properties) {
        super(properties);
        Utils.attackSpeedEnhance.put(this, 0.2);
        Utils.movementSpeedCommon.put(this, 0.2);
        Utils.coolDownDecrease.put(this, 0.2);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getAttackTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        return List.of();
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfWind;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getUniformSuffix();
    }
}
