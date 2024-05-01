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
public class EvokerEvent {
    @SubscribeEvent
    public static void Evoker(TickEvent.LevelTickEvent event) {
        if(event.side.isServer() && event.phase == TickEvent.Phase.START) {
            Level level = event.level;
            Level level1 = event.level.getServer().getLevel(Level.OVERWORLD);
            if (!level.equals(level1)) return;
            for (Evoker evoker : Utils.Evokers) {
                if (evoker != null && evoker.isAlive()) Element.ElementProvider(evoker, Element.Lightning, 1);
            }
            if(level.getServer().getTickCount() % 400 == 200 && level.equals(level1)) {
                int [] indexX = {137,173,213,210,197};
                int [] indexY = {132,134,131,129,134};
                int [] indexZ = {922,944,967,1011,1058};
                int BoundaryX1 = 239;
                int BoundaryX2 = 123;
                int BoundaryY1 = 151;
                int BoundaryY2 = 122;
                int BoundaryZ1 = 1099;
                int BoundaryZ2 = 896;
                List<Monster> list = level.getEntitiesOfClass(Monster.class, AABB.ofSize(new Vec3( (BoundaryX1 + BoundaryX2) /2, (BoundaryY1 + BoundaryY2) /2, (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+10,Math.abs(BoundaryY1-BoundaryY2)+10,Math.abs(BoundaryZ1-BoundaryZ2)+10));
                List<Player> list0 = level.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3( (BoundaryX1 + BoundaryX2) /2, (BoundaryY1 + BoundaryY2) /2, (BoundaryZ1 + BoundaryZ2) /2),
                        Math.abs(BoundaryX1-BoundaryX2)+50,Math.abs(BoundaryY1-BoundaryY2)+50,Math.abs(BoundaryZ1-BoundaryZ2)+50));

                if (list.size() > 15) {
                    for (Monster monster : list) {
                        monster.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
                    }
                    Compute.FormatBroad(level,Component.literal("安全").withStyle(ChatFormatting.GREEN),
                            Component.literal("清理了森林唤魔者。"));
                }
                for(int i=0;i<5;i++) {
                    if(list0.size() != 0 && (Utils.Evokers[i] == null || !Utils.Evokers[i].isAlive()))
                    {
                        if(Utils.Evokers[i] != null) Utils.Evokers[i].remove(Entity.RemovalReason.KILLED);
                        Utils.Evokers[i] = new Evoker(EntityType.EVOKER,level);
                        Compute.SetMobCustomName(Utils.Evokers[i], ModItems.ArmorEvoker.get(),Component.literal("森林唤魔者").withStyle(ChatFormatting.LIGHT_PURPLE));
                        Utils.Evokers[i].setItemSlot(EquipmentSlot.HEAD , ModItems.ArmorEvoker.get().getDefaultInstance());
                        Utils.Evokers[i].getAttribute(Attributes.MAX_HEALTH).setBaseValue(1600.0D);
                        Utils.Evokers[i].getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.4D);
                        Utils.Evokers[i].getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(250);

                        Utils.Evokers[i].setHealth(Utils.Evokers[i].getMaxHealth());
                        Utils.Evokers[i].moveTo(indexX[i],indexY[i],indexZ[i]);
                        level.addFreshEntity(Utils.Evokers[i]);
                    }
                    if(Utils.Evokers[i] != null){
                        double x = Utils.Evokers[i].getX();
                        double y = Utils.Evokers[i].getY();
                        double z = Utils.Evokers[i].getZ();
                        if(x > 239 || x < 123 || y > 151 || y < 122 || z > 1099 || z < 896)
                        {
                            Utils.Evokers[i].moveTo(indexX[i],indexY[i],indexZ[i]);
                        }
                    }
                }
            }
            if(level.getServer().getTickCount() % 100 == 0 && level.equals(level1)) {
                for (Evoker evoker : Utils.Evokers) {
                    if (evoker != null && evoker.isAlive()) {
                        ParticleProvider.DisperseParticle(evoker.position(),(ServerLevel) evoker.level(),1,1,120, ModParticles.LONG_ENTROPY.get(),1);
                        ParticleProvider.DisperseParticle(evoker.position(),(ServerLevel) evoker.level(),1.5,1,120,ModParticles.LONG_ENTROPY.get(),1);
                        List<Player> playerList = level.getEntitiesOfClass(Player.class,AABB.ofSize(evoker.getPosition(1),8,8,8));
                        for (Player player : playerList) {
                            Compute.PlayerManaAddOrCost(player, (int) ((100 + PlayerAttributes.PlayerMaxMana(player)) * - 0.25));

                            Compute.FormatMSGSend(player,Component.literal("唤魔者").withStyle(ChatFormatting.LIGHT_PURPLE),
                                    Component.literal("唤魔者偷取了你的").withStyle(ChatFormatting.WHITE).
                                            append(Compute.AttributeDescription.MaxMana("25%")));
                            Compute.Damage.ManaDamageToPlayer(evoker,player,((100 + PlayerAttributes.PlayerMaxMana(player)) * 0.25));
/*                            ClientboundSetEntityMotionPacket clientboundSetEntityMotionPacket =
                                    new ClientboundSetEntityMotionPacket(player.getId(),evoker.getPosition(1).subtract(player.position()).scale(0.1));
                            List<ServerPlayer> serverPlayerList = level.getServer().getPlayerList().getPlayers();
                            for (ServerPlayer serverPlayer : serverPlayerList) {
                                serverPlayer.connection.send(clientboundSetEntityMotionPacket);
                            }*/

                        }
                        if (playerList.size() == 0) evoker.heal(66);
                    }
                }
            }
        }
    }
}
