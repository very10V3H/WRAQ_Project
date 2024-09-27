package com.very.wraq.process.system.spur.Items;

import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.projectiles.WraqCurios;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class LogCharm extends WraqCurios {

    public LogCharm(Properties properties, int serial) {
        super(properties);
        int[] levelRequires = {40, 60, 80, 100, 120, 140, 160};
        double[] defence = {1, 2, 2, 3, 5, 8, 12};
        double[] manaDefence = {1, 2, 2, 3, 5, 8, 12};
        Utils.levelRequire.put(this, levelRequires[serial]);
        Utils.defence.put(this, defence[serial]);
        Utils.manaDefence.put(this, manaDefence[serial]);
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
        return CustomStyle.styleOfHusk;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSpurSuffix();
    }
}
