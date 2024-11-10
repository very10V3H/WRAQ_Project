package fun.wraq.series.specialevents.midautumn;

import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.series.moon.Equip.MoonBow;
import net.minecraft.network.chat.Style;

public class MidAutumnBow extends MoonBow {

    public MidAutumnBow(Properties properties, int exTargetCount) {
        super(properties, exTargetCount);
        Utils.attackDamage.put(this, 0d);
        Utils.xpLevelAttackDamage.put(this, 8d);
        Utils.defencePenetration0.put(this, 0d);
        Utils.xpLevelDefencePenetration0.put(this, 0.2);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfMoon;
    }
}

