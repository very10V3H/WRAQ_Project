package com.very.wraq.series.overworld.sakuraSeries.MineWorker;

import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.projectiles.WraqPickaxe;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class PurplePickaxe extends WraqPickaxe {
    public PurplePickaxe(Properties properties, int tier) {
        super(properties);
        WraqPickaxe.mineSpeed.put(this, new double[]{0.25, 0.5, 0.75, 1}[tier]);
        Utils.levelRequire.put(this, new int[]{50, 100, 150, 200}[tier]);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfPurpleIron;
    }

    @Override
    public List<Component> getAdditionalComponents(ItemStack stack) {
        return List.of();
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixOfPurpleIron();
    }
}
