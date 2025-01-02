package fun.wraq.series.overworld.curios;

import fun.wraq.common.equip.WraqCurios;
import fun.wraq.common.util.ComponentUtils;
import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class FancySapphireNecklace extends WraqCurios {

    public FancySapphireNecklace(Properties p_41383_, int num) {
        super(p_41383_);
        Utils.maxHealth.put(this, new double[]{12000, 16000, 20000, 24000}[num]);
        Utils.coolDownDecrease.put(this, new double[]{0.12, 0.14, 0.16, 0.2}[num]);
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
        return CustomStyle.styleOfSakura;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSuffixOfSakura();
    }
}
