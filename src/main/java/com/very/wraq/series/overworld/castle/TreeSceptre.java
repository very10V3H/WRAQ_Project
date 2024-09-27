package com.very.wraq.series.overworld.castle;

import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.projectiles.WraqPassiveEquip;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import java.util.List;

public class TreeSceptre extends WraqPassiveEquip {

    private static final double[] ManaDamage = {
            400, 600, 800, 1000
    };

    private static final double[] MaxMana = {
            25, 50, 75, 100
    };

    private static final double[] ManaPenetration0 = {
            2, 3, 3, 4
    };

    public TreeSceptre(Properties properties, int tier) {
        super(properties);
        Utils.manaDamage.put(this, ManaDamage[tier]);
        Utils.maxMana.put(this, MaxMana[tier]);
        Utils.manaPenetration0.put(this, ManaPenetration0[tier]);
        Utils.levelRequire.put(this, 180);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfHealth;
    }

    @Override
    public List<Component> getAdditionDescriptions() {
        return List.of();
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfCastle();
    }

    @Override
    public Component getType() {
        return Component.literal("法杖").withStyle(ChatFormatting.LIGHT_PURPLE);
    }
}
