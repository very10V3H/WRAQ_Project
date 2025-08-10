package fun.wraq.customized.uniform.element;

import fun.wraq.common.util.Utils;
import fun.wraq.customized.WraqUniformCurios;
import fun.wraq.process.system.element.RainbowCrystal;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public abstract class WraqElementUniformCurios extends WraqUniformCurios {

    public static List<Item> ELEMENT_CURIOS = new ArrayList<>();

    public WraqElementUniformCurios(Properties properties) {
        super(properties);
        Utils.xpLevelDefence.put(this, 0.2d);
        Utils.xpLevelManaDefence.put(this, 0.2d);
        Utils.xpLevelDefencePenetration0.put(this, 0.1d);
        Utils.xpLevelManaPenetration0.put(this, 0.1d);
        Utils.coolDownDecrease.put(this, 0.1);
        ELEMENT_CURIOS.add(this);
    }

    @Override
    public Component getFirstPassiveName() {
        return RainbowCrystal.rainBowNameFourChar("世界根基");
    }

    @Override
    public double getFinalDamageEnhanceRate() {
        return 0.5;
    }
}
