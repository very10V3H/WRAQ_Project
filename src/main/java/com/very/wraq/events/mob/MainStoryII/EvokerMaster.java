package com.very.wraq.events.mob.MainStoryII;

import com.very.wraq.process.element.Element;
import com.very.wraq.process.particle.ParticleProvider;
import com.very.wraq.render.particles.ModParticles;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.attributeValues.PlayerAttributes;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
@Mod.EventBusSubscriber
public class EvokerMaster {
    @SubscribeEvent
    public static void EvokerMaster(TickEvent.LevelTickEvent event) {
        if(event.side.isServer() && event.phase == TickEvent.Phase.START) {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if (!level.equals(level1)) return;
            for (Evoker evoker : Utils.EvokerMaster) {
                if (evoker != null && evoker.isAlive()) Element.ElementProvider(evoker, Element.Lightning, 2);
            }
            if(level.getServer().getTickCount() % 400 == 240 && level.equals(level1)
                    && Utils.SummonTick != level.getServer().getTickCount()) {
                Utils.SummonTick = level.getServer().getTickCount();
                int [] indexX = {101,46,-24,-59,-110};
                int [] indexY = {136,142,136,144,141};
                int [] indexZ = {906,879,885,916,951};
                int BoundaryX1 = 130;
                int BoundaryX2 = -130;
                int BoundaryY1 = 151;
                int BoundaryY2 = 126;
                int BoundaryZ1 = 971;
                int BoundaryZ2 = 886;
                List<Monster> list = level.getEntitiesOfClass(Monster.class, AABB.ofSize(new Vec3( (BoundaryX1 + BoundaryX2) /2, (BoundaryY1 + BoundaryY2) /2, (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+10,Math.abs(BoundaryY1-BoundaryY2)+10,Math.abs(BoundaryZ1-BoundaryZ2)+10));
                List<Player> list0 = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3( (BoundaryX1 + BoundaryX2) /2, (BoundaryY1 + BoundaryY2) /2, (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+50,Math.abs(BoundaryY1-BoundaryY2)+50,Math.abs(BoundaryZ1-BoundaryZ2)+50));

                if (list.size() > 15) {
                    for (Monster monster : list) {
                        monster.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                    }
                    Compute.FormatBroad(level,Component.literal("安全").withStyle(ChatFormatting.GREEN),
                            Component.literal("清理了森林唤魔大师。"));
                }
                for(int i=0;i<5;i++) {
                    if(list0.size() != 0 && (Utils.EvokerMaster[i] == null || !Utils.EvokerMaster[i].isAlive())) {
                        if(Utils.EvokerMaster[i] != null) Utils.EvokerMaster[i].remove(Entity.RemovalReason.KILLED);
                        Utils.EvokerMaster[i] = new Evoker(EntityType.EVOKER,level);
                        Compute.SetMobCustomName(Utils.EvokerMaster[i], ModItems.ArmorEvokerMaster.get(),Component.literal("森林唤魔大师").withStyle(ChatFormatting.LIGHT_PURPLE));
                        Utils.EvokerMaster[i].setItemSlot(EquipmentSlot.HEAD , ModItems.ArmorEvokerMaster.get().getDefaultInstance());
                        Utils.EvokerMaster[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(16000);
                        Utils.EvokerMaster[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5D);
                        Utils.EvokerMaster[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(500);
                        Utils.EvokerMaster[i].setHealth(16000);
                        Utils.EvokerMaster[i].moveTo(indexX[i],indexY[i],indexZ[i]);
                        level.addFreshEntity(Utils.EvokerMaster[i]);
                    }
                    if(Utils.EvokerMaster[i] != null){
                        double x = Utils.EvokerMaster[i].getX();
                        double y = Utils.EvokerMaster[i].getY();
                        double z = Utils.EvokerMaster[i].getZ();
                        if(x > 130 || x < -130 || y > 151 || y < 126 || z > 971 || z < 886)
                        {
                            Utils.EvokerMaster[i].moveTo(indexX[i],indexY[i],indexZ[i]);
                        }
                    }
                }
            }
            if(level.getServer().getTickCount() % 100 == 0 && level.equals(level1)) {
                for (Evoker evoker : Utils.EvokerMaster) {
                    if (evoker != null && evoker.isAlive()) {
                        ParticleProvider.DisperseParticle(evoker.position(),(ServerLevel) evoker.level(),1,1,120, ModParticles.LONG_ENTROPY.get(),1);
                        ParticleProvider.DisperseParticle(evoker.position(),(ServerLevel) evoker.level(),1.5,1,120,ModParticles.LONG_ENTROPY.get(),1);
                        List<Player> playerList = level.getEntitiesOfClass(Player.class,AABB.ofSize(evoker.getPosition(1),16,16,16));
                        for (Player player : playerList) {
                            Compute.PlayerManaAddOrCost(player, (int) ((100 + PlayerAttributes.PlayerMaxMana(player)) * -0.5));
                            Compute.FormatMSGSend(player,Component.literal("唤魔大师").withStyle(ChatFormatting.LIGHT_PURPLE),
                                    Component.literal("唤魔大师偷取了你的").withStyle(ChatFormatting.WHITE).
                                            append(Compute.AttributeDescription.MaxMana("50%")));
                            Compute.Damage.ManaDamageToPlayer(evoker,player,((100 + PlayerAttributes.PlayerMaxMana(player)) * 0.5));
/*                            ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket =
                                    new ClientboundSetEntityMotionPacket(player.getId(),evoker.getPosition(1).subtract(player.position()).scale(0.2));
                            List<ServerPlayer> serverPlayerList = level.getServer().getPlayerList().getPlayers();
                            for (ServerPlayer serverPlayer : serverPlayerList) {
                                serverPlayer.connection.send(clientboundSetEntityMotionPacket);
                            }*/
                        }
                        if (playerList.size() == 0) evoker.heal(666);
                    }
                }
            }
        }
    }
}
