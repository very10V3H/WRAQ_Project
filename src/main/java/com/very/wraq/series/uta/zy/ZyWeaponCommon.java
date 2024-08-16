package com.very.wraq.series.uta.zy;

import com.very.wraq.common.Compute;
import com.very.wraq.common.registry.ModItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface ZyWeaponCommon {
    static void commonPassiveTick(Player player) {
        if (Objects.requireNonNull(player.getServer()).getTickCount() % 5 == 0) {
            List<? extends Entity> nearMobList = Compute.getNearEntity(player, Mob.class, 6);
            nearMobList.stream().filter(entity -> entity instanceof Mob).forEach(mob -> {
                Compute.Damage.causeAutoAdaptionRateDamageToMob(player, (Mob) mob, 1);
            });
        }
    }

    static void commonActiveEffect(Player player) {
        List<? extends Entity> nearMobList = Compute.getNearEntity(player, Mob.class, 6);
        nearMobList.stream().filter(entity -> entity instanceof Mob).forEach(mob -> {
            Compute.Damage.causeAutoAdaptionRateDamageToMob(player, (Mob) mob, 1 * (1 - mob.distanceTo(player) / 6.0));
        });
    }

    Map<String, Integer> tier1Passive = new HashMap<>();
    Map<String, Integer> tier2Passive = new HashMap<>();

    static void commonPassiveOfKillEffect(Player player) {
        String name = player.getName().getString();
        int tick = Objects.requireNonNull(player.getServer()).getTickCount();
        int tier1PassiveLastTick = 20;
        int tier2PassiveLastTick = 20;
        if (tier1Passive.getOrDefault(name, 0) + tier1PassiveLastTick < tick
                && tier2Passive.getOrDefault(name, 0) + tier2PassiveLastTick < tick) {
            tier1Passive.put(name, tick + tier1PassiveLastTick);
            Compute.sendEffectLastTime(player, ModItems.silverCoin.get(), tier1PassiveLastTick);
        } else {
            if (tier1Passive.get(name) + tier1PassiveLastTick >= tick) {
                tier1Passive.put(name, 0);
                tier2Passive.put(name, tick + tier2PassiveLastTick);
                Compute.sendEffectLastTime(player, ModItems.goldCoin.get(), tier2PassiveLastTick);
            }
        }
    }
}
