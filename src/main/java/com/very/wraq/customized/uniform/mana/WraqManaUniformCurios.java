package com.very.wraq.customized.uniform.mana;

import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.projectiles.WraqUniformCurios;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

public abstract class WraqManaUniformCurios extends WraqUniformCurios {

    public WraqManaUniformCurios(Properties properties) {
        super(properties);
        Utils.xpLevelManaDamage.put(this, 4d);
        Utils.xpLevelManaPenetration0.put(this, 2d);
        Utils.manaRecover.put(this, 20d);
        Utils.maxMana.put(this, 80d);
        Utils.coolDownDecrease.put(this, 0.3);
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfMana;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getUniformSuffix();
    }
}
