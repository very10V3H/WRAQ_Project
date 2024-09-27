package com.very.wraq.series.overworld.chapter2.manaArmor;

import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ItemMaterial;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.projectiles.WraqArmor;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class LifeMana1 extends WraqArmor {

    public LifeMana1(ItemMaterial Material, Type Slots, Properties itemProperties) {
        super(Material, Slots, itemProperties);
        Utils.defence.put(this, 1d);
        Utils.manaDamage.put(this, 100d);
        Utils.maxHealth.put(this, 100d);
        Utils.maxMana.put(this, 20d);
        Utils.manaPenetration0.put(this, 1d);
        Utils.manaRecover.put(this, 5d);
        Utils.healthRecover.put(this, 5d);
        if (type.equals(Type.BOOTS)) Utils.movementSpeedCommon.put(this, 0.4);
        Utils.coolDownDecrease.put(this, 0.1);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfHealth;
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Component.literal("自然能量迸发").withStyle(CustomStyle.styleOfHealth));
        components.add(Component.literal(" 每穿着一件生机自然魔力装备，都会使你的").withStyle(ChatFormatting.WHITE));
        components.add(Component.literal(" ").withStyle(ChatFormatting.WHITE).
                append(ModItems.PlainPower.get().getDefaultInstance().getDisplayName()).
                append(Component.literal(" ")).
                append(ModItems.ForestPower.get().getDefaultInstance().getDisplayName()));
        components.add(Component.literal(" ").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.CoolDown("")).
                append(Component.literal("减少").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("1s").withStyle(ChatFormatting.AQUA)));
        components.add(Component.literal(" ").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaCost("")).
                append(Component.literal("减少")).
                append(Component.literal("10").withStyle(CustomStyle.styleOfMana)));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixChapterII();
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
