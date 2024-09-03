package com.very.wraq.process.func;

import com.very.wraq.events.mob.MobSpawn;
import com.very.wraq.networking.ModNetworking;
import com.very.wraq.networking.misc.ParticlePackets.SlowDownParticleS2CPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.TickEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EffectOnMob {
    public static Map<Mob, Integer> mobSlowDownEffectTick = new HashMap<>();
    public static Map<Mob, Double> mobSlowDownEffectRate = new HashMap<>();

    public static void addSlowDownEffect(Mob mob, int lastTick, double rate) {
        int tick = mob.getServer().getTickCount();
        mobSlowDownEffectTick.put(mob, tick + lastTick);
        mobSlowDownEffectRate.put(mob, rate);

        List<ServerPlayer> playerList = mob.level().getServer().getPlayerList().getPlayers();
        playerList.forEach(serverPlayer -> {
            ModNetworking.sendToClient(new SlowDownParticleS2CPacket(mob.getId(), lastTick), serverPlayer);
        });
    }

    public static void levelTick(TickEvent.LevelTickEvent event) {
        mobSlowDownEffectTick.entrySet().removeIf(entry -> !entry.getKey().isAlive());
        mobSlowDownEffectRate.entrySet().removeIf(entry -> !entry.getKey().isAlive());

        mobSlowDownEffectTick.forEach((mob, tick) -> {
            int tickCount = mob.getServer().getTickCount();
            if (mobSlowDownEffectRate.containsKey(mob)) {
                if (tick > tickCount) {
                    mob.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(
                            MobSpawn.MobBaseAttributes.getMobBaseAttribute(mob, MobSpawn.MobBaseAttributes.movementSpeed)
                                    * (1 - mobSlowDownEffectRate.get(mob)));
                } else {
                    mob.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(
                            MobSpawn.MobBaseAttributes.getMobBaseAttribute(mob, MobSpawn.MobBaseAttributes.movementSpeed));
                }
            }
        });
    }
}
