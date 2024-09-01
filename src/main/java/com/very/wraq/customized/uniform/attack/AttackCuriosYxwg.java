package com.very.wraq.customized.uniform.attack;

import com.very.wraq.projectiles.OnHitEffectCurios;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class AttackCuriosYxwg extends WraqAttackUniformCurios implements OnHitEffectCurios {

    public AttackCuriosYxwg(Properties properties) {
        super(properties);
    }

    @Override
    public void onHit(Player player, Mob mob) {

    }

    @Override
    public List<Component> additionHoverText(ItemStack stack) {
        return List.of();
    }
}
