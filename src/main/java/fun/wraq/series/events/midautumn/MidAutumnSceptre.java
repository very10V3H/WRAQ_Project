package fun.wraq.series.events.midautumn;

import fun.wraq.common.util.Utils;
import fun.wraq.render.toolTip.CustomStyle;
import fun.wraq.series.instance.series.moon.Equip.MoonSceptre;
import net.minecraft.network.chat.Style;

public class MidAutumnSceptre extends MoonSceptre {

    public MidAutumnSceptre(Properties properties, double activeRate) {
        super(properties, activeRate, 1);
        Utils.manaDamage.put(this, 0d);
        Utils.xpLevelManaDamage.put(this, 16d);
        Utils.manaPenetration0.put(this, 0d);
        Utils.xpLevelManaPenetration0.put(this, 0.2);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfMoon;
    }
}
