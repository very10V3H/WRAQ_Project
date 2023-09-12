package com.Very.very.Events.ClientEvents;

import com.Very.very.ValueAndTools.Utils.ClientUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class SoundsEvent {
    @SubscribeEvent
    public static void Sounds(TickEvent.PlayerTickEvent event)
    {
        Player player = event.player;
        Level level = player.level();
        if(level.isClientSide && ClientUtils.Sounds != -1)
        {
            switch (ClientUtils.Sounds) {
                case 0 -> {
                    player.playSound(SoundEvents.PLAYER_ATTACK_CRIT);
                }
                case 1 -> {
                    player.playSound(SoundEvents.PLAYER_ATTACK_STRONG);
                }
                case 2 -> {
                    player.playSound(SoundEvents.PLAYER_HURT);
                }
                case 3 -> {
                    player.playSound(SoundEvents.PLAYER_LEVELUP);
                }
            }
            ClientUtils.Sounds = -1;
        }
    }
}
