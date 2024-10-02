package fun.wraq.customized.uniform.element;

import fun.wraq.common.util.Utils;
import fun.wraq.customized.WraqUniformCurios;
import fun.wraq.process.system.element.RainbowCrystal;
import net.minecraft.network.chat.Component;

public abstract class WraqElementUniformCurios extends WraqUniformCurios {

    public WraqElementUniformCurios(Properties properties) {
        super(properties);
        Utils.xpLevelDefence.put(this, 0.4d);
        Utils.xpLevelManaDefence.put(this, 0.4d);
        Utils.xpLevelDefencePenetration0.put(this, 0.2d);
        Utils.xpLevelManaPenetration0.put(this, 0.2d);
        Utils.coolDownDecrease.put(this, 0.1);
    }

    @Override
    public Component getFirstPassiveName() {
        return RainbowCrystal.rainBowNameFourChar("世界根基");
    }
}
