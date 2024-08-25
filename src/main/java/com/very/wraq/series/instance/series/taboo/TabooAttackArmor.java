package com.very.wraq.series.instance.series.taboo;

import com.very.wraq.projectiles.WraqArmor;
import com.very.wraq.common.Compute;
import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.common.Utils.Utils;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.common.registry.ItemMaterial;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TabooAttackArmor extends WraqArmor {

    public TabooAttackArmor(ItemMaterial Material, Type Slots, Properties itemProperties) {
        super(Material, Slots, itemProperties);
        Utils.maxHealth.put(this, 4096d);
        Utils.attackDamage.put(this, 750d);
        Utils.defence.put(this, 800d);
        Utils.manaDefence.put(this, 500d);
    }

    @Override
    public Style getMainStyle() {
        return CustomStyle.styleOfBloodMana;
    }

    @Override
    public List<Component> getAdditionalComponents() {
        List<Component> components = new ArrayList<>();
        Style style = getMainStyle();
        Compute.DescriptionPassive(components, Component.literal("禁忌秘法-加护").withStyle(style));
        components.add(Component.literal(" 当受到来自怪物的伤害时，若拥有高于").withStyle(ChatFormatting.WHITE).
                append(Compute.AttributeDescription.MaxMana("10%")).
                append(Component.literal("，则消耗").withStyle(ChatFormatting.WHITE)).
                append(Compute.AttributeDescription.MaxMana("10%")).
                append(Component.literal("来使即将到来的伤害减少80%").withStyle(ChatFormatting.WHITE)));
        return components;
    }

    @Override
    public Component getSuffix() {
        return ComponentUtils.getDemonAndElementStorySuffix2Sakura();
    }

    @Override
    public boolean isFoil(ItemStack p_41453_) {
        return true;
    }

    public static boolean IsOn(Player player) {
        return player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.TabooAttackLeggings.get());
    }

    public static double Passive(Player player) {
        if (!IsOn(player)) return 1;
        if (Compute.PlayerCurrentManaNum(player) / Compute.PlayerMaxManaNum(player) > 0.1) {
            Compute.playerManaAddOrCost(player, (-Compute.PlayerMaxManaNum(player) * 0.1));
            return 0.2;
        }
        return 1;
    }


}
