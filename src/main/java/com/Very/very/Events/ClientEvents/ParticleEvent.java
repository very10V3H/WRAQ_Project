package com.Very.very.Events.ClientEvents;

import ca.weblite.objc.Client;
import com.Very.very.ValueAndTools.Utils.ClientUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Iterator;
import java.util.Queue;
import java.util.UUID;

@Mod.EventBusSubscriber
public class ParticleEvent {
    @SubscribeEvent
    public static void Particle(TickEvent.PlayerTickEvent event)
    {
        Player player = event.player;
        Level level = player.level();
        if(ClientUtils.ParticleFlag && level.isClientSide && event.side.isClient())
        {
            ClientUtils.ParticleFlag = false;
            switch (ClientUtils.ParticleRandom) {
                case 0 -> {
                    for (double x = -171.5; x <= -164.5; x++) {
                        for (double z = 1409.5; z <= 1416.5; z++) {
                            level.addParticle(ParticleTypes.FIREWORK,x,116,z,0,0,0);
                        }
                    }
                }
                case 1 -> {
                    for (double x = -162.5; x <= -155.5; x++) {
                        for (double z = 1409.5; z <= 1416.5; z++) {
                            level.addParticle(ParticleTypes.FIREWORK,x,116,z,0,0,0);
                        }
                    }
                }
                case 2 -> {
                    for (double x = -162.5; x <= -155.5; x++) {
                        for (double z = 1418.5; z <= 1425.5; z++) {
                            level.addParticle(ParticleTypes.FIREWORK,x,116,z,0,0,0);
                        }
                    }
                }
                case 3 -> {
                    for (double x = -171.5; x <= -164.5; x++) {
                        for (double z = 1418.5; z <= 1425.5; z++) {
                            level.addParticle(ParticleTypes.FIREWORK,x,116,z,0,0,0);
                        }
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public static void FeiLeiShenParticle(TickEvent.PlayerTickEvent event)
    {
        if(event.player.tickCount % 20 == 0 && Utils.FeiLeiShenMap.get(event.player) != null && event.side.isClient())
        {
            Player player = event.player;
            Level level = player.level();
            Queue<Vec3> Fei = Utils.FeiLeiShenMap.get(player);
            Iterator<Vec3> iterator = Fei.iterator();
            while(iterator.hasNext())
            {
                Vec3 FeiPos = iterator.next();
                level.addParticle(ParticleTypes.WITCH,FeiPos.x,FeiPos.y,FeiPos.z,0,0,0);
            }
        }
    }
/*    @SubscribeEvent
    public static void WaltzParticle(TickEvent.PlayerTickEvent event)
    {
        Level level = event.player.level;
        if(level.isClientSide && ClientUtils.QuartzSabreParticlePos != -1)
        {
            if(ClientUtils.QuartzSabreParticlePos == 0)
            {
                level.addParticle(ParticleTypes.FIREWORK,ClientUtils.QuartzSabreParticleIndexX,ClientUtils.QuartzSabreParticleIndexY,ClientUtils.QuartzSabreParticleIndexZ-1,0,0,0);
            }
            if(ClientUtils.QuartzSabreParticlePos == 1)
            {
                level.addParticle(ParticleTypes.FIREWORK,ClientUtils.QuartzSabreParticleIndexX+1,ClientUtils.QuartzSabreParticleIndexY,ClientUtils.QuartzSabreParticleIndexZ,0,0,0);
            }
            if(ClientUtils.QuartzSabreParticlePos == 2)
            {
                level.addParticle(ParticleTypes.FIREWORK,ClientUtils.QuartzSabreParticleIndexX,ClientUtils.QuartzSabreParticleIndexY,ClientUtils.QuartzSabreParticleIndexZ+1,0,0,0);
            }
            if(ClientUtils.QuartzSabreParticlePos == 3)
            {
                level.addParticle(ParticleTypes.FIREWORK,ClientUtils.QuartzSabreParticleIndexX-1,ClientUtils.QuartzSabreParticleIndexY,ClientUtils.QuartzSabreParticleIndexZ,0,0,0);
            }
            ClientUtils.QuartzSabreParticlePos = -1;
        }
    }*/
}
