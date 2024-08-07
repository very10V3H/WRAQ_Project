package com.very.wraq.process.system.spur.Items;

import com.very.wraq.projectiles.WraqCurios;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class CropCharm extends WraqCurios {

    private final int levelRequire;

    public CropCharm(Properties properties, int serial) {
        super(properties);
        int[] levelRequires = {40, 60, 80, 100, 120, 140, 160};
        double[] maxHealth = {200, 400, 700, 1100, 1600, 2200, 2900};
        double[] healthRecover = {5, 10, 20, 35, 55, 80, 110};
        this.levelRequire = levelRequires[serial];
        Utils.maxHealth.put(this, maxHealth[serial]);
        Utils.healthRecover.put(this, healthRecover[serial]);
    }

    @Override
    public Component getTypeDescription() {
        return ComponentUtils.getDefenceTypeDescriptionOfCurios();
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        return List.of();
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfField;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getSpurSuffix();
    }

    @Override
    public int levelRequirement() {
        return levelRequire;
    }

    @Override
    public boolean isArbitrarily() {
        return false;
    }
}
