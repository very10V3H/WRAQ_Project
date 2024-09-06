package com.very.wraq.series.specialevents.midautumn;

import com.very.wraq.common.util.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.instance.series.moon.Equip.MoonSword;
import net.minecraft.network.chat.Style;

public class MidAutumnSword extends MoonSword {

    public MidAutumnSword(Properties properties, double activeRate) {
        super(properties, activeRate);
        Utils.attackDamage.put(this, 0d);
        Utils.xpLevelAttackDamage.put(this, 8d);
        Utils.defencePenetration0.put(this, 0d);
        Utils.xpLevelDefencePenetration0.put(this, 16d);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfMoon;
    }
}
