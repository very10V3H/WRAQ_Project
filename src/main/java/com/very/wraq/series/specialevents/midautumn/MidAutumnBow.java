package com.very.wraq.series.specialevents.midautumn;

import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.instance.series.moon.Equip.MoonBow;
import net.minecraft.network.chat.Style;

public class MidAutumnBow extends MoonBow {

    public MidAutumnBow(Properties properties, int exTargetCount) {
        super(properties, exTargetCount);
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

