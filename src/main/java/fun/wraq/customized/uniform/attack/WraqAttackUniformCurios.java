package fun.wraq.customized.uniform.attack;

import fun.wraq.common.util.Utils;
import fun.wraq.customized.WraqUniformCurios;
import fun.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Style;

public abstract class WraqAttackUniformCurios extends WraqUniformCurios {

    public WraqAttackUniformCurios(Properties properties) {
        super(properties);
        Utils.xpLevelAttackDamage.put(this, 2d);
        Utils.xpLevelDefencePenetration0.put(this, 0.1d);
        Utils.critDamage.put(this, 0.35);
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfPower;
    }

}
