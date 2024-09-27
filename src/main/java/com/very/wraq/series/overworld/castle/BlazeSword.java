
package com.very.wraq.series.overworld.castle;

import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.projectiles.WraqPassiveEquip;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class BlazeSword extends WraqPassiveEquip {

    private static final double[] ExAttackDamage = {
            200, 300, 400, 500
    };

    private static final double[] Defence = {
            2, 3, 5, 6
    };

    private static final double[] MaxHealth = {
            400, 600, 800, 1000
    };

    public BlazeSword(Properties properties, int tier) {
        super(properties);
        Utils.attackDamage.put(this, ExAttackDamage[tier]);
        Utils.defence.put(this, Defence[tier]);
        Utils.maxHealth.put(this, MaxHealth[tier]);
        Utils.levelRequire.put(this, 180);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfPower;
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
        return Component.literal("长剑").withStyle(CustomStyle.styleOfPower);
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
