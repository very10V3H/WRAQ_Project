package com.very.wraq.series.nether.Equip.Armor;

import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ItemMaterial;
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

public class NetherManaArmor extends WraqArmor {

    public NetherManaArmor(ItemMaterial Material, Type Slots, Properties itemProperties) {
        super(Material, Slots, itemProperties);
        Utils.maxHealth.put(this, 500d);
        Utils.defence.put(this, 2d);
        Utils.manaDamage.put(this, 200d);
        Utils.manaPenetration.put(this, 0.08);
        Utils.manaPenetration0.put(this, 1d);
        if (type.equals(Type.BOOTS)) {
            Utils.movementSpeedCommon.put(this, 0.4);
            Utils.maxHealth.put(this, 250d);
        }
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfNether;
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        Compute.DescriptionPassive(components, Component.literal("下界混沌解构术法").withStyle(CustomStyle.styleOfMana));
        components.add(Component.literal(" -你的普通法球攻击与法术攻击将基于目标的").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.ManaDefence("")).
                append(Component.literal("提供").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("12%伤害提升").withStyle(CustomStyle.styleOfPower)));
        components.add(Component.literal(" -每件下界混沌装备提供的伤害提升可以线性增长").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        components.add(Component.literal(" -当目标的魔法抗性达到500时给予满额伤害提升").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getSuffixChapterIII();
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }
}
