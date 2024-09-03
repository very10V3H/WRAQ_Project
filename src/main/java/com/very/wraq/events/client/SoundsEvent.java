package com.very.wraq.events.client;

import com.very.wraq.common.util.ClientUtils;
import com.very.wraq.common.registry.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class SoundsEvent {
    @SubscribeEvent
    public static void Sounds(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        Level level = player.level();
        if (level.isClientSide && ClientUtils.Sounds != -1 && event.player.equals(Minecraft.getInstance().player)) {
            switch (ClientUtils.Sounds) {
                case 0 -> player.playSound(SoundEvents.PLAYER_ATTACK_CRIT);
                case 1 -> player.playSound(SoundEvents.PLAYER_ATTACK_STRONG);
                case 2 -> player.playSound(SoundEvents.PLAYER_HURT);
                case 3 -> player.playSound(SoundEvents.PLAYER_LEVELUP);
                case 4 -> player.playSound(SoundEvents.PLAYER_ATTACK_SWEEP);
                case 5 -> player.playSound(SoundEvents.ANVIL_USE);
                case 6 -> player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP);
                case 7 -> player.playSound(SoundEvents.TNT_PRIMED);
                case 8 -> player.playSound(SoundEvents.GENERIC_EXPLODE);
                case 9 -> player.playSound(ModSounds.DingZhen.get());
                case 10 -> player.playSound(ModSounds.YueMa.get());
                case 11 -> player.playSound(SoundEvents.ANVIL_BREAK);
                case 12 -> player.playSound(ModSounds.MSG.get());
                case 13 -> player.playSound(SoundEvents.FIRE_EXTINGUISH);
                case 14 -> player.playSound(ModSounds.HeihuangAttack.get());
                case 15 -> player.playSound(ModSounds.Dice.get());
                case 16 -> player.playSound(ModSounds.BreakDice.get());
            }
            ClientUtils.Sounds = -1;
        }
        if (event.side.isClient() && event.phase.equals(TickEvent.Phase.START) && event.player.equals(Minecraft.getInstance().player)) {
            if (ClientUtils.DingCounts > 0) {
                player.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP);
                ClientUtils.DingCounts--;
            }
        }
    }
}
