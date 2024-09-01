package com.very.wraq.customized.uniform.attack;

import com.very.wraq.common.Utils.ComponentUtils;
import com.very.wraq.projectiles.OnCuriosSlotHitDamageInfluence;
import com.very.wraq.projectiles.OnKillEffectCurios;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AttackCuriosYxwg extends WraqAttackUniformCurios implements OnKillEffectCurios, OnCuriosSlotHitDamageInfluence {

    public AttackCuriosYxwg(Properties properties) {
        super(properties);
    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        List<Component> components = new ArrayList<>();
        ComponentUtils.descriptionPassive(components, Component.literal(""));

        return components;
    }

    @Override
    public Component getFirstPassiveName() {
        return null;
    }

    @Override
    public double onHitDamageInfluence(Player player, Mob mob) {
        return 0;
    }

    @Override
    public void onKill(Player player, Mob mob) {

    }
}
