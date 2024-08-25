package com.very.wraq.series.instance.devil;

import com.very.wraq.projectiles.WraqArmor;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.registry.ItemMaterial;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DevilManaArmor extends WraqArmor {

    public DevilManaArmor(ItemMaterial Material, Type Slots, Properties itemProperties) {
        super(Material, Slots, itemProperties);
        Utils.maxHealth.put(this, 1728d);
        Utils.manaDamage.put(this, 900d);
        Utils.defence.put(this, 360d);
        Utils.manaDefence.put(this, 240d);
        Utils.manaRecover.put(this, 20d);
        Utils.coolDownDecrease.put(this, 0.2);
        if (type.equals(Type.BOOTS)) Utils.movementSpeedCommon.put(this, 0.45);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfMana;
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("唤魔者之威").withStyle(style));
        components.add(Component.literal(" 提升").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDamage("25%")));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getDemonAndElementStorySuffix1Sakura();
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

}
