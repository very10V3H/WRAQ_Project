package com.very.wraq.process.system.potion;

import com.very.wraq.render.mobEffects.ModEffects;
import net.minecraft.world.entity.player.Player;

public class NewPotionEffects {
    public static double damageEnhance(Player player) {
        if (player.getEffect(ModEffects.DAMAGE_ENHANCE.get()) != null && player.getEffect(ModEffects.DAMAGE_ENHANCE.get()).getAmplifier() == 0)
            return 0.35;
        if (player.getEffect(ModEffects.DAMAGE_ENHANCE.get()) != null && player.getEffect(ModEffects.DAMAGE_ENHANCE.get()).getAmplifier() == 1)
            return 0.5;
        return 0;
    }

    public static double attackDamageEnhance(Player player) {
        if (player.getEffect(ModEffects.ATTACK_DAMAGE_ENHANCE.get()) != null && player.getEffect(ModEffects.ATTACK_DAMAGE_ENHANCE.get()).getAmplifier() == 0)
            return 0.35;
        if (player.getEffect(ModEffects.ATTACK_DAMAGE_ENHANCE.get()) != null && player.getEffect(ModEffects.ATTACK_DAMAGE_ENHANCE.get()).getAmplifier() == 1)
            return 0.5;
        return 0;
    }

    public static double manaDamageEnhance(Player player) {
        if (player.getEffect(ModEffects.MANA_DAMAGE_ENHANCE.get()) != null && player.getEffect(ModEffects.MANA_DAMAGE_ENHANCE.get()).getAmplifier() == 0)
            return 0.35;
        if (player.getEffect(ModEffects.MANA_DAMAGE_ENHANCE.get()) != null && player.getEffect(ModEffects.MANA_DAMAGE_ENHANCE.get()).getAmplifier() == 1)
            return 0.5;
        return 0;
    }

    public static double maxHealthEnhance(Player player) {
        if (player.getEffect(ModEffects.GIANT.get()) != null && player.getEffect(ModEffects.GIANT.get()).getAmplifier() == 0)
            return 0.15;
        if (player.getEffect(ModEffects.GIANT.get()) != null && player.getEffect(ModEffects.GIANT.get()).getAmplifier() == 1)
            return 0.25;
        return 0;
    }

    public static double resistanceEnhance(Player player) {
        if (player.getEffect(ModEffects.STONE.get()) != null && player.getEffect(ModEffects.STONE.get()).getAmplifier() == 0)
            return 0.15;
        if (player.getEffect(ModEffects.STONE.get()) != null && player.getEffect(ModEffects.STONE.get()).getAmplifier() == 1)
            return 0.25;
        return 0;
    }

    public static double exHarvestEnhance(Player player) {
        if (player.getEffect(ModEffects.EX_HARVEST.get()) != null && player.getEffect(ModEffects.EX_HARVEST.get()).getAmplifier() == 0)
            return 0.15;
        if (player.getEffect(ModEffects.EX_HARVEST.get()) != null && player.getEffect(ModEffects.EX_HARVEST.get()).getAmplifier() == 1)
            return 0.25;
        return 0;
    }
}
