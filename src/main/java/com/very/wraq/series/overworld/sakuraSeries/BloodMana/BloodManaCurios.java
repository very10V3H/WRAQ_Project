package com.very.wraq.series.overworld.sakuraSeries.BloodMana;

import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.common.util.Utils;
import com.very.wraq.process.func.StableAttributesModifier;
import com.very.wraq.projectiles.WraqCurios;
import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.series.overworld.sakuraSeries.EarthMana.EarthManaCurios;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BloodManaCurios extends WraqCurios {

    public BloodManaCurios(Properties p_41383_, int tier) {
        super(p_41383_);
        Utils.manaDefence.put(this, tier == 0 ? 500d : 800d);
        Utils.attackDamage.put(this, tier == 0 ? 100d : 175d);
        Utils.healthSteal.put(this, tier == 0 ? 0.05 : 0.08);
        Utils.manaRecover.put(this, tier == 0 ? 15d : 25d);
        Utils.curiosTag.put(this, 1d);
        Utils.curiosList.add(this);
    }

    @Override
    public Component getTypeDescription() {
        return null;
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        Style style = hoverMainStyle();
        ComponentUtils.descriptionPassive(components, Component.literal("旧世仁慈魔浴").withStyle(style));
        components.add(Component.literal(" 当你受到").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.ManaDamageValue("")).
                append(Component.literal("时，为你提供持续3s的").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.AttributeDescription.HealthRecover("8%")));
        return components;
    }

    @Override
    public Style hoverMainStyle() {
        return CustomStyle.styleOfBloodMana;
    }

    @Override
    public Component suffix() {
        return ComponentUtils.getDemonAndElementStorySuffix1Sakura();
    }

    @Override
    public int levelRequirement() {
        return 0;
    }


    public static void passive(Player player) {
        if (WraqCurios.isOn(BloodManaCurios.class, player) || WraqCurios.isOn(EarthManaCurios.class, player)) {
            StableAttributesModifier.addAttributeModifier(player, StableAttributesModifier.playerHealthRecoverModifier,
                    new StableAttributesModifier("manaCuriosPassiveHealthRecover", player.getMaxHealth() * 0.08, player.getServer().getTickCount() + 60));
            if (WraqCurios.isOn(BloodManaCurios.class, player))
                Compute.sendEffectLastTime(player, ModItems.BloodManaCurios.get(), 60);
            else Compute.sendEffectLastTime(player, ModItems.EarthManaCurios.get(), 60);
        }
    }
}
