package fun.wraq.series.overworld.knife;

import fun.wraq.common.equip.WraqOffHandItem;
import fun.wraq.common.fast.Te;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import java.util.List;

public class OriginKnife extends WraqOffHandItem {

    private final Style style;
    private final Component suffix;
    public OriginKnife(Properties properties, Style style, Component suffix, int tier) {
        super(properties, Te.s("短匕", CustomStyle.styleOfFlexible));
        this.style = style;
        this.suffix = suffix;
        Utils.attackDamage.put(this, new double[]{5, 10, 15, 20, 30, 45}[tier]);
        Utils.expUp.put(this, new double[]{0.1, 0.15, 0.2, 0.25, 0.3, 0.4}[tier]);
        Utils.movementSpeedCommon.put(this, new double[]{0.01, 0.02, 0.03, 0.04, 0.05, 0.06}[tier]);
    }

    @Override
    public Style getMainStyle() {
        return style;
    }

    @Override
    public List<Component> getAdditionalComponents() {
        return List.of();
    }

    @Override
    public Component getSuffix() {
        return suffix;
    }
}
