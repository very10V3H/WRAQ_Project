package com.very.wraq.series.uta.hml;

import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ModItems;
import com.very.wraq.process.func.particle.ParticleProvider;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public interface HmlWeaponCommon {

    record ActiveEffect(Mob mob, Player player, double rate) {
    }

    Map<Mob, ActiveEffect> mobEffects = new HashMap<>();

    static void active(Player player) {
        double rate = 1;
        Compute.getNearEntity(player, Mob.class, 6).stream().filter(mob -> mob instanceof Mob).map(mob -> (Mob) mob).forEach(mob -> {
            mobEffects.put(mob, new ActiveEffect(mob, player, rate));
        });
    }

    static void serverTick(TickEvent.ServerTickEvent e) {
        mobEffects.entrySet().removeIf(entry -> !entry.getKey().isAlive() || entry.getValue().player == null);
        Random random = new Random();
        int frequency = 5;
        mobEffects.forEach((mob, effect) -> {
            if (random.nextDouble() < frequency / 20.0) {
                Compute.Damage.causeAutoAdaptionRateDamageToMob(effect.player(), mob, effect.rate());

                // 粒子
                ParticleProvider.createSingleParticle(mob, 16, ParticleTypes.FLASH, mob.getEyePosition());
            }
        });
    }

    Map<String, Integer> specialActiveEffects = new HashMap<>();
    static void specialActive(Player player) {
        specialActiveEffects.put(player.getName().getString(), player.getServer().getTickCount() + 100);
        Compute.sendEffectLastTime(player, ModItems.goldCoin.get(), 100);
    }

    static void specialActiveGravitySet(Player player) {
        if (specialActiveEffects.getOrDefault(player.getName().getString(), 0) > player.getServer().getTickCount()) {
            player.getAttribute(ForgeMod.ENTITY_GRAVITY.get()).setBaseValue(0.01);
        } else {
            player.getAttribute(ForgeMod.ENTITY_GRAVITY.get()).setBaseValue(0.08);
        }
    }

    static double pressAttackDamageEnhance(Player player, Mob mob) {
        double rate = 1;
        if (player.getMainHandItem().getItem() instanceof HmlWeaponCommon) {
            if (player.getEyePosition().y > mob.getEyePosition().y) {
                return rate;
            }
        }
        return 0;
    }
}
