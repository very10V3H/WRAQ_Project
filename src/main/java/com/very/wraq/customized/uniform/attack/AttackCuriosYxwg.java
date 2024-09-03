package com.very.wraq.customized.uniform.attack;

import com.very.wraq.common.util.ComponentUtils;
import com.very.wraq.process.system.element.RainbowCrystal;
import com.very.wraq.projectiles.OnCuriosSlotHitDamageInfluence;
import com.very.wraq.render.toolTip.CustomStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AttackCuriosYxwg extends WraqAttackUniformCurios implements OnCuriosSlotHitDamageInfluence {

    public AttackCuriosYxwg(Properties properties) {
        super(properties);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Component.literal("索命").withStyle(hoverMainStyle()));
        components.add(Component.literal(" 对").withStyle(ChatFormatting.WHITE).
                append(ComponentUtils.AttributeDescription.Health("")).
                append(Component.literal("低于").withStyle(ChatFormatting.WHITE)).
                append(Component.literal("50%").withStyle(CustomStyle.styleOfHealth)).
                append(Component.literal("的目标造成的伤害将得到").withStyle(ChatFormatting.WHITE)).
                append(ComponentUtils.getCommonDamageEnhance("45%")));
        return components;
    }

    @Override
    public Component getFirstPassiveName() {
        return RainbowCrystal.rainBowNameFourChar("英雄挽歌");
    }

    @Override
    public String getName() {
        return "yxwg";
    }

    @Override
    public double onHitDamageInfluence(Player player, Mob mob) {
        return (mob.getHealth() / mob.getMaxHealth()) < 0.5 ? 0.45 : 0;
    }
}
