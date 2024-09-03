package com.very.wraq.series.overworld.chapter1.forest;

import com.very.wraq.common.util.Utils;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ForestPowerControl {
    @SubscribeEvent
    public static void ForestPowerControlEvent(TickEvent.LevelTickEvent event) {
        if (!event.level.isClientSide && event.phase.equals(TickEvent.Phase.START)
                && event.level.equals(event.level.getServer().getLevel(Level.OVERWORLD))) {

            Utils.ForestPowerEffectMobList.forEach(forestPowerEffectMob -> {
                if (forestPowerEffectMob.getEffectTick() > 0)
                    forestPowerEffectMob.getMob().setDeltaMovement(forestPowerEffectMob.getDestination().subtract(forestPowerEffectMob.getMob().position()).scale(0.2));
                forestPowerEffectMob.TickSub();
            });

            Utils.ForestPowerEffectMobList.removeIf(forestPowerEffectMob -> {
                return forestPowerEffectMob.getEffectTick() <= 0;
            });

        }
    }
}
