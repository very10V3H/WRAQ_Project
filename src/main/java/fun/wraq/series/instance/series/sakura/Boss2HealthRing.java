package fun.wraq.series.instance.series.sakura;

import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class Boss2HealthRing extends WraqCurios {

    public Boss2HealthRing(Properties properties, int tier) {
        super(properties);
        Utils.maxHealth.put(this, new double[]{900, 1300, 1400, 2100}[tier]);
        Utils.percentMaxHealthEnhance.put(this, new double[]{0.01, 0.015, 0.02, 0.025}[tier]);
        Utils.levelRequire.put(this, 150);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getFuncTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        return List.of();
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfGold;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfSakura();
    }

}
