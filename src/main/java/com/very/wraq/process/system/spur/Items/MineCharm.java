package com.very.wraq.process.system.spur.Items;

import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.projectiles.WraqCurios;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class MineCharm extends WraqCurios {

    public MineCharm(Properties properties, int serial) {
        super(properties);
        int[] levelRequires = {40, 60, 80, 100, 120, 140, 160};
        double[] defencePenetration0 = {50, 100, 200, 300, 500, 800, 1200};
        double[] manaPenetration0 = {50, 100, 200, 300, 500, 800, 1200};
        Utils.levelRequire.put(this, levelRequires[serial]);
        Utils.defencePenetration0.put(this, defencePenetration0[serial]);
        Utils.manaPenetration0.put(this, manaPenetration0[serial]);
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
        return CustomStyle.styleOfMine;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSpurSuffix();
    }
}
