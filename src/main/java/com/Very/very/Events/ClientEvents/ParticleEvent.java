package com.Very.very.Events.ClientEvents;

import ca.weblite.objc.Client;
import com.Very.very.Render.Particles.ModParticles;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.ClientUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ParticleEvent {
    @SubscribeEvent
    public static void Particle(TickEvent.PlayerTickEvent event)
    {
        if (event.side.isClient() && event.phase.equals(TickEvent.Phase.START) && event.player.equals(Minecraft.getInstance().player)) {
            Level level = event.player.level();
            ClientLevel clientLevel = (ClientLevel) level;
            Player player = event.player;
            Random random = new Random();

            if (ClientUtils.ClientLevelFlag == 0 && player.getX() > 1785 && player.getX() < 1888
                    && player.getY() > 104 && player.getY() < 152
                    && player.getZ() > 860 && player.getZ() < 920) {
                for (int i = 0 ; i < 75 ; i ++) {
                    level.addParticle(ParticleTypes.WITCH,player.getX() + random.nextInt(100) - 50,
                            player.getY() + random.nextInt(100) - 50, player.getZ() + random.nextInt(100) - 50,
                            0,0.5,0);
                }
            }

            if (ClientUtils.ClientLevelFlag == 0 && player.getX() > 1873 && player.getX() < 1942
                    && player.getY() > 108 && player.getY() < 146
                    && player.getZ() > 1202 && player.getZ() < 1283) {
                for (int i = 0 ; i < 75 ; i ++) {
                    level.addParticle(ParticleTypes.WITCH,player.getX() + random.nextInt(100) - 50,
                            player.getY() + random.nextInt(100) - 50, player.getZ() + random.nextInt(100) - 50,
                            0,0.5,0);
                }
                for (int i = 0 ; i < 75 ; i ++) {
                    level.addParticle(ModParticles.LONG_ENTROPY.get(), player.getX() + random.nextInt(100) - 50,
                            player.getY() + random.nextInt(100) - 50, player.getZ() + random.nextInt(100) - 50,
                            0,0.5,0);
                }
            }

            if (ClientUtils.ClientLevelFlag == 0 && player.getX() > 2104 && player.getX() < 2209
                    && player.getY() > 52 && player.getY() < 84
                    && player.getZ() > 1026 && player.getZ() < 1126) {
                for (int i = 0 ; i < 75 ; i ++) {
                    level.addParticle(ModParticles.LONG_ENTROPY.get(), player.getX() + random.nextInt(100) - 50,
                            player.getY() + random.nextInt(100) - 50, player.getZ() + random.nextInt(100) - 50,
                            0,0.5,0);
                }
            }

            if (player.tickCount % 20 == 0) {
                ClientUtils.EndRune2Pos.forEach(posAndLastTime -> {
                    posAndLastTime.TickCount -= 20;
                    for (int i = 0 ; i < 100 ; i ++) {
                        level.addParticle(ParticleTypes.DRAGON_BREATH,
                                posAndLastTime.vec3.x + random.nextDouble(10) - 5,
                                posAndLastTime.vec3.y + 1,
                                posAndLastTime.vec3.z + random.nextDouble(10) - 5,0,0,0);
                    }
                });
                ClientUtils.EndRune2Pos.removeIf(posAndLastTime -> posAndLastTime.TickCount < 0);
            } //下界符石 末影龙息

            List<Mob> mobList = level.getEntitiesOfClass(Mob.class, AABB.ofSize(player.position(),50,50,50));
            mobList.forEach(mob -> {
                if (mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.ArmorPlainBossHelmet.get())) {
                    double r = 6;
                    int num = 60;
                    double pickDistance = 1;
                    Vec3 bottomPos = new Vec3(mob.position().x,mob.position().y,mob.position().z);
                    for (int i = 0; i < num; i++) {
                        double angle = (2*Math.PI/num)*(i);
                        Vec3 Point = new Vec3(bottomPos.x+r*cos(angle),bottomPos.y+pickDistance,bottomPos.z+r*sin(angle));
                        level.addParticle(ModParticles.PLAIN.get(), Point.x,Point.y,Point.z,
                                0,0,0);
                        level.addParticle(ModParticles.PLAIN.get(), Point.x,Point.y + 0.5,Point.z,
                                0,0,0);
                    }
                }
                if (ClientUtils.SpringInstanceAttackCount > 0 && mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.MobArmorSpringHelmet.get())) {
                    double r = 6;
                    int num = 60;
                    double pickDistance = 1;
                    Vec3 bottomPos = new Vec3(mob.position().x,mob.position().y,mob.position().z);
                    for (int i = 0; i < num; i++) {
                        double angle = (2*Math.PI/num)*(i);
                        Vec3 Point = new Vec3(bottomPos.x+r*cos(angle),bottomPos.y+pickDistance,bottomPos.z+r*sin(angle));
                        level.addParticle(ModParticles.SPRING.get(), Point.x,Point.y,Point.z,
                                0,0,0);
                        level.addParticle(ModParticles.SPRING.get(), Point.x,Point.y + 0.5,Point.z,
                                0,0,0);
                    }
                    ClientUtils.SpringInstanceAttackCount --;
                }
            });

            Utils.WorldEntropyPos.forEach(worldEntropy -> {
                Compute.RandomToDesParticle(random.nextInt(20),worldEntropy.getVec3().add(0.5,0,0.5),level,5);
            });

            {
                int tick = event.player.tickCount % 20;
                Vec3 bottomPos = new Vec3(52.5, -53.5, 1039.5);
                Vec3 delta = new Vec3(0, 0.2, 0);
                double r = 1.5;
                double angle = (2 * Math.PI / 20) * (tick);
                Vec3 Point = new Vec3(bottomPos.x + r * cos(angle), bottomPos.y, bottomPos.z + r * sin(angle));

                level.addParticle(ModParticles.VOLCANO_TP.get(), Point.x, Point.y, Point.z,
                        delta.x, delta.y, delta.z);
            }
        }



        Player player = event.player;
        Level level = player.level();
        if(ClientUtils.ParticleFlag && level.isClientSide && event.side.isClient() && event.player.equals(Minecraft.getInstance().player))
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
        if(event.player.tickCount % 20 == 0 && Utils.FeiLeiShenMap.get(event.player) != null && event.side.isClient() && event.player.equals(Minecraft.getInstance().player))
        {
            Player player = event.player;
            Level level = player.level();
            List<Vec3> Fei = Utils.FeiLeiShenMap.get(player);
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
