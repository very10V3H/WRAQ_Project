package com.Very.very.Events.WaltzAndModule;

import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.NetWorking.Packets.QuartzSword.QuartzSwordParticleS2CPacket;
import com.Very.very.ValueAndTools.Utils.Struct.ServerWaltzMonster;
import com.Very.very.ValueAndTools.Utils.Struct.ServerWaltzPlayer;
import com.Very.very.ValueAndTools.Utils.Utils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Iterator;
import java.util.Random;

@Mod.EventBusSubscriber
public class WaltzEvent {
    @SubscribeEvent
    public static void WaltzE(TickEvent.LevelTickEvent event)
    {
        if(event.side.isServer() && event.phase.equals(TickEvent.Phase.START) && event.level.getServer().getTickCount() != Utils.waltztick)
        {
            Utils.waltztick = event.level.getServer().getTickCount();
            if(!Utils.QuartzSabreCCPlayer.isEmpty())
            {
                Random r = new Random();
                Iterator<ServerWaltzPlayer> iterator = Utils.QuartzSabreCCPlayer.iterator();
                ServerWaltzPlayer serverWaltzPlayer0 = null;
                boolean removeFlag = false;
                while(iterator.hasNext())
                {
                    ServerWaltzPlayer serverWaltzPlayer = iterator.next();
                    Player target = serverWaltzPlayer.getTarget();
                    Player attacker = serverWaltzPlayer.getAttacker();
                    CompoundTag data = target.getPersistentData();
                    if(data.getInt("QuartzSabre") <= 0 && target.isDeadOrDying()) {
                        ModNetworking.sendToClient(new QuartzSwordParticleS2CPacket(target.getX(),target.getY(),target.getZ(),-1),(ServerPlayer) attacker);
                        ModNetworking.sendToClient(new QuartzSwordParticleS2CPacket(target.getX(),target.getY(),target.getZ(),-1),(ServerPlayer) target);
                        removeFlag = true;
                        serverWaltzPlayer0 = serverWaltzPlayer;
                    }
                    else
                    {
                        if(data.getInt("QuartzSabre") % 20 == 0)
                        {
                            if(data.getInt("QuartzSabrePos") == -1)
                            {
                                int pos = r.nextInt(4);
                                data.putInt("QuartzSabrePos",pos);
                            }
                            ModNetworking.sendToClient(new QuartzSwordParticleS2CPacket(target.getX(),target.getY(),target.getZ(),data.getInt("QuartzSabrePos")),(ServerPlayer) attacker);
                            ModNetworking.sendToClient(new QuartzSwordParticleS2CPacket(target.getX(),target.getY(),target.getZ(),data.getInt("QuartzSabrePos")),(ServerPlayer) target);
                        }
                    }
                }
                if(removeFlag) {
                    Utils.QuartzSabreCCPlayer.remove(serverWaltzPlayer0);
                }
            }
            if(!Utils.QuartzSabreCCMonster.isEmpty())
            {
                Random r = new Random();
                Iterator<ServerWaltzMonster> iterator = Utils.QuartzSabreCCMonster.iterator();
                ServerWaltzMonster serverWaltzMonster0 = null;
                boolean removeFlag = false;
                while(iterator.hasNext())
                {
                    ServerWaltzMonster serverWaltzMonster = iterator.next();
                    Mob target = serverWaltzMonster.getTarget();
                    Player attacker = serverWaltzMonster.getAttacker();
                    CompoundTag data = target.getPersistentData();
                    if(data.getInt("QuartzSabre") > 0) data.putInt("QuartzSabre",data.getInt("QuartzSabre")-1);
                    if(data.getInt("QuartzSabre") <= 0 && target.isDeadOrDying()) {
                        ModNetworking.sendToClient(new QuartzSwordParticleS2CPacket(target.getX(),target.getY(),target.getZ(),-1),(ServerPlayer) attacker);
                        removeFlag = true;
                        serverWaltzMonster0 = serverWaltzMonster;
                    }
                    else {
                        if(data.getInt("QuartzSabre") % 20 == 0)
                        {
                            if(data.getInt("QuartzSabrePos") == -1)
                            {
                                int pos = r.nextInt(4);
                                data.putInt("QuartzSabrePos",pos);
                            }
                            ModNetworking.sendToClient(new QuartzSwordParticleS2CPacket(target.getX(),target.getY(),target.getZ(),data.getInt("QuartzSabrePos")),(ServerPlayer) attacker);
                        }
                    }
                }
                if(removeFlag) Utils.QuartzSabreCCMonster.remove(serverWaltzMonster0);
            }
        }
    }
}
