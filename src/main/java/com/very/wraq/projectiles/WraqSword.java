package com.very.wraq.projectiles;

import com.very.wraq.common.util.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;

public abstract class WraqSword extends WraqMainHandEquip {

    public WraqSword(Properties properties) {
        super(properties);
        Utils.swordTag.put(this, 1d);
    }

    @Override
    public Component getType() {
        return Component.literal("近战").withStyle(CustomStyle.styleOfPower);
    }
}
