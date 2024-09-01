package com.very.wraq.customized.uniform.element;

import com.very.wraq.common.Utils.Utils;
import com.very.wraq.process.system.element.RainbowCrystal;
import com.very.wraq.customized.WraqUniformCurios;
import net.minecraft.network.chat.Component;

public abstract class WraqElementUniformCurios extends WraqUniformCurios {

    public WraqElementUniformCurios(Properties properties) {
        super(properties);
        Utils.xpLevelDefence.put(this, 2d);
        Utils.xpLevelManaDefence.put(this, 2d);
        Utils.xpLevelDefencePenetration0.put(this, 2d);
        Utils.xpLevelManaPenetration0.put(this, 2d);
        Utils.coolDownDecrease.put(this, 0.25);
    }

    @Override
    public Component getFirstPassiveName() {
        return RainbowCrystal.rainBowNameFourChar("世界根基");
    }
}
