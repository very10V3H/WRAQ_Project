package com.Very.very.Events.ClientEvents;

import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.NetWorking.Packets.AnimationPackets.RangeAttackRequestC2SPacket;
import com.Very.very.NetWorking.Packets.CodeSceptrePackets.CodeC2SPacket;
import com.Very.very.NetWorking.Packets.SkillPackets.Charging.ChargedFullC2SPacket;
import com.Very.very.NetWorking.Packets.SkillPackets.SkillRequestC2SPacket;
import com.Very.very.NetWorking.Packets.SmartPhonePackets.RequestC2SPacket;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.ClientUtils;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.Render.Particles.ModParticles;
import com.Very.very.ValueAndTools.registry.Moditems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientTickEvent {
    @SubscribeEvent
    public static void RangeAttackCount(TickEvent.PlayerTickEvent event) {
        if (event.side.isClient() && event.phase.equals(TickEvent.Phase.START)) {
            if (ClientUtils.RangeAttackCount == 0) {
                ModNetworking.sendToServer(new RangeAttackRequestC2SPacket(10));
                ClientUtils.RangeAttackCount--;
            }
            if (ClientUtils.RangeAttackCount > 0) ClientUtils.RangeAttackCount --;

            if (ClientUtils.PickAxeAttackCount == 0) {
                ModNetworking.sendToServer(new RangeAttackRequestC2SPacket(10));
                ClientUtils.PickAxeAttackCount--;
            }
            if (ClientUtils.PickAxeAttackCount > 0) ClientUtils.PickAxeAttackCount --;
            if (ClientUtils.PickAxeAttackAnimationCount >= 0) ClientUtils.PickAxeAttackAnimationCount --;
            if (ClientUtils.RangeAttackAnimationCount >= 0) ClientUtils.RangeAttackAnimationCount --;

        }
    }
    @SubscribeEvent
    public static void TestForContainer(ScreenEvent event)
    {
/*        if(event.getScreen() instanceof FirstBlockScreen && ClientUtils.TickCount % 20 == 0) {
            ModNetworking.sendToServer(new LimitC2SPacket());
        }
        if(event.getScreen() instanceof BrewingScreen && ClientUtils.TickCount % 20 == 0){
            ModNetworking.sendToServer(new LimitC2SPacket());
        }*/
    }
    @SubscribeEvent
    public static void PacketsLimited(TickEvent.PlayerTickEvent event)
    {
        if (event.side.isClient() && event.phase.equals(TickEvent.Phase.START)) {
            for (int i = 1; i <= 15; i ++) {
                if (ClientUtils.Sword_Image[i] != null && ClientUtils.Sword_Image[i].getTickTime() > 0) ClientUtils.Sword_Image[i].setTickTime(ClientUtils.Sword_Image[i].getTickTime() - 1);
                if (ClientUtils.Bow_Image[i] != null && ClientUtils.Bow_Image[i].getTickTime() > 0) ClientUtils.Bow_Image[i].setTickTime(ClientUtils.Bow_Image[i].getTickTime() - 1);
                if (ClientUtils.Mana_Image[i] != null && ClientUtils.Mana_Image[i].getTickTime() > 0) ClientUtils.Mana_Image[i].setTickTime(ClientUtils.Mana_Image[i].getTickTime() - 1);
            }
        }
        if(event.side.isClient())
        {
            ClientUtils.TickCount ++;
            Player player = event.player;
            if (ClientUtils.ChargedCountsSwordSkill12 < 100 && player.getDeltaMovement().length() > 0.1 && ClientUtils.SwordSkillPoint.Point[13] > 0)
                ClientUtils.ChargedCountsSwordSkill12 += 0.1;
            if (ClientUtils.ChargedCountsBowSkill12 < 100 && player.getDeltaMovement().length() > 0.1 && ClientUtils.BowSkillPoint.Point[13] > 0)
                ClientUtils.ChargedCountsBowSkill12 += 0.1;
            if (ClientUtils.ChargedCountsManaSkill12 < 100 && player.getDeltaMovement().length() > 0.1 && ClientUtils.ManaSkillPoint.Point[13] > 0)
                ClientUtils.ChargedCountsManaSkill12 += 0.1;
            if (ClientUtils.ChargedCountsManaSkill13 < 100 && player.getDeltaMovement().length() > 0.1 && ClientUtils.ManaSkillPoint.Point[14] > 0)
                ClientUtils.ChargedCountsManaSkill13 += 0.1;
            if (ClientUtils.ChargedCountsSwordSkill12 >= 100 && ClientUtils.ChargedFlagSwordSkill12) {
                ModNetworking.sendToServer(new ChargedFullC2SPacket(0));
                ClientUtils.ChargedFlagSwordSkill12 = false;
            }
            if (ClientUtils.ChargedCountsBowSkill12 >= 100 && ClientUtils.ChargedFlagBowSkill12) {
                ModNetworking.sendToServer(new ChargedFullC2SPacket(1));
                ClientUtils.ChargedFlagBowSkill12 = false;
            }
            if (ClientUtils.ChargedCountsManaSkill12 >= 100 && ClientUtils.ChargedFlagManaSkill12) {
                ModNetworking.sendToServer(new ChargedFullC2SPacket(2));
                ClientUtils.ChargedFlagManaSkill12 = false;
            }
            if (ClientUtils.ChargedCountsManaSkill13 >= 100 && ClientUtils.ChargedFlagManaSkill13) {
                ModNetworking.sendToServer(new ChargedFullC2SPacket(3));
                ClientUtils.ChargedFlagManaSkill13 = false;
            }


            if (player.tickCount % 20 == 10) {
                ModNetworking.sendToServer(new SkillRequestC2SPacket());
            }
            if (player.tickCount % 20 == 0)
            {
                ClientUtils.PacketsLimit = 10;
            }
            Vec2 vec2 = new Vec2((float) player.getX(),(float) player.getZ());
            if (Compute.DotIn(vec2, Utils.KazeDot1, Utils.KazeDot2, Utils.KazeDot3, Utils.KazeDot4)) {
                player.setDeltaMovement(player.getDeltaMovement().add(0.01,0,-0.01));
                if (player.tickCount % 200 == 0 && event.phase.equals(TickEvent.Phase.START)) {
                    Compute.FormatMSGSend(player,Component.literal("风之谷").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfKaze),
                            Component.literal("来自西南的风不断地吹打着你。"));
                }
            }
            if (player.getItemInHand(InteractionHand.MAIN_HAND).is(Moditems.CodeSceptre.get())) {
                Inventory inventory = player.getInventory();
                if (ClientUtils.NumToItem.isEmpty()) ClientUtils.NumToItemInit();
                if (ClientUtils.ItemToNum.isEmpty()) ClientUtils.ItemToNumInit();
                ClientUtils.PowerInSlot.put(0,0);
                ClientUtils.PowerInSlot.put(1, ClientUtils.ItemToNum.getOrDefault(inventory.getItem(5).getItem(), 0));

                ClientUtils.PowerInSlot.put(2,ClientUtils.ItemToNum.getOrDefault(inventory.getItem(6).getItem(), 0));
                ClientUtils.PowerInSlot.put(3,ClientUtils.ItemToNum.getOrDefault(inventory.getItem(7).getItem(), 0));
                ClientUtils.PowerInSlot.put(4,ClientUtils.ItemToNum.getOrDefault(inventory.getItem(8).getItem(), 0));
                ClientUtils.IsHandlingPower = true;
                int power1 = 0,power2 = 0,power3 = 0,power4 = 0;
                Iterator<Integer> iterator = ClientUtils.PowerQueue.iterator();
                if (iterator.hasNext()) power1 = iterator.next();
                if (iterator.hasNext()) power2 = iterator.next();
                if (iterator.hasNext()) power3 = iterator.next();
                if (iterator.hasNext()) power4 = iterator.next();
                ModNetworking.sendToServer(new CodeC2SPacket(ClientUtils.PowerInSlot.get(power1),ClientUtils.PowerInSlot.get(power2),
                        ClientUtils.PowerInSlot.get(power3),ClientUtils.PowerInSlot.get(power4)));
            }
            else ClientUtils.IsHandlingPower = false;
        }
    }
    @SubscribeEvent
    public static void PacketsSendTo(TickEvent.PlayerTickEvent event)
    {
        if(event.side.isClient()){
            if(event.player.tickCount % 20 == 0 && event.phase.equals(TickEvent.Phase.START)){
                ModNetworking.sendToServer(new RequestC2SPacket());
            }
        }
    }
    @SubscribeEvent
    public static void ClientParticleEvent(TickEvent.PlayerTickEvent event) {
        if (event.side.isClient() && event.phase.equals(TickEvent.Phase.START)) {
            Player player = event.player;
            Level level = player.level();
            int TickCount = player.tickCount;
            List<Mob> list = level.getEntitiesOfClass(Mob.class, AABB.ofSize(player.getPosition(1.0f),100,100,100));
            for (Mob mob : list) {
                if (mob.getItemBySlot(EquipmentSlot.HEAD).is(Moditems.ArmorEnderMan.get())) {
                    Vec3 Pos = mob.getPosition(1.0f);
                    int num = 20;
                    int i = mob.tickCount % 20;
                    double r = 1;
                    double angle = (2 * Math.PI / num) * (i);
                    double X = Pos.x + Math.cos(angle) * r;
                    double Z = Pos.z + Math.sin(angle) * r;
                    double Y = Pos.y + 1;
                    Vec3 Point = new Vec3(X, Y, Z);
                    Vec3 Position = Point.subtract(Pos);
                    Compute.EntityFaceCircleCreate(mob, Position, new Vec3(Pos.x, Pos.y + 2, Pos.z), 0, 2, 40, ModParticles.END_PARTICLE.get(),false);
                    i = 20 - i;
                    angle = (2 * Math.PI / num) * (i);
                    X = Pos.x + Math.cos(angle) * r;
                    Z = Pos.z + Math.sin(angle) * r;
                    Y = Pos.y + 1;
                    Point = new Vec3(X, Y, Z);
                    Position = Point.subtract(Pos);
                    Compute.EntityFaceCircleCreate(mob, Position, new Vec3(Pos.x, Pos.y + 2, Pos.z), 0, 2, 40, ModParticles.END_PARTICLE.get(),false);
                }
                if (mob.getItemBySlot(EquipmentSlot.HEAD).is(Moditems.ArmorSilverFish.get())) {
                    Compute.EntityEffectVerticleCircleParticle(mob,0,2,40, ModParticles.END_PARTICLE.get(), 10,false);
                }
                if (mob.getItemBySlot(EquipmentSlot.HEAD).is(Moditems.ArmorKazeRecall.get())) {
                    Vec3 Pos = mob.getPosition(1.0f);
                    int num = 20;
                    int i = mob.tickCount % 20;
                    double r = 1;
                    double angle = (2 * Math.PI / num) * (i);
                    double X = Pos.x + Math.cos(angle) * r;
                    double Z = Pos.z + Math.sin(angle) * r;
                    double Y = Pos.y + 1;
                    Vec3 Point = new Vec3(X, Y, Z);
                    Vec3 Position = Point.subtract(Pos);
                    Compute.EntityFaceCircleCreate(mob, Position, new Vec3(Pos.x, Pos.y + 1.5, Pos.z), 0, 2, 40, ModParticles.KAZE.get(),false);
                    i += 5;
                    angle = (2 * Math.PI / num) * (i);
                    X = Pos.x + Math.cos(angle) * r;
                    Z = Pos.z + Math.sin(angle) * r;
                    Y = Pos.y + 1;
                    Point = new Vec3(X, Y, Z);
                    Position = Point.subtract(Pos);
                    Compute.EntityFaceCircleCreate(mob, Position, new Vec3(Pos.x, Pos.y + 1.5, Pos.z), 0, 2, 40, ModParticles.KAZE.get(),false);
                    i += 5;
                    angle = (2 * Math.PI / num) * (i);
                    X = Pos.x + Math.cos(angle) * r;
                    Z = Pos.z + Math.sin(angle) * r;
                    Y = Pos.y + 1;
                    Point = new Vec3(X, Y, Z);
                    Position = Point.subtract(Pos);
                    Compute.EntityFaceCircleCreate(mob, Position, new Vec3(Pos.x, Pos.y + 1.5, Pos.z), 0, 2, 40, ModParticles.KAZE.get(),false);
                    i += 5;
                    angle = (2 * Math.PI / num) * (i);
                    X = Pos.x + Math.cos(angle) * r;
                    Z = Pos.z + Math.sin(angle) * r;
                    Y = Pos.y + 1;
                    Point = new Vec3(X, Y, Z);
                    Position = Point.subtract(Pos);
                    Compute.EntityFaceCircleCreate(mob, Position, new Vec3(Pos.x, Pos.y + 1.5, Pos.z), 0, 2, 40, ModParticles.KAZE.get(),false);
                    List<Player> playerList = level.getEntitiesOfClass(Player.class,AABB.ofSize(mob.getPosition(1.0f),20,20,20));
                    for (Player player1 : playerList) {
                        if (player1.getPosition(1.0f).distanceTo(mob.getPosition(1.0f)) <= 2.5) {
                            player1.setDeltaMovement(player1.getPosition(1.0f).subtract(mob.getPosition(1.0f)));
                        }
                    }
                }
                if (mob.getItemBySlot(EquipmentSlot.HEAD).is(Moditems.ArmorSpiderRecall.get())) {
                    Vec3 Pos = mob.getPosition(1.0f);
                    int num = 20;
                    int i = mob.tickCount % 20;
                    double r = 1;
                    double angle = (2 * Math.PI / num) * (i);
                    double X = Pos.x + Math.cos(angle) * r;
                    double Z = Pos.z + Math.sin(angle) * r;
                    double Y = Pos.y + 1;
                    Vec3 Point = new Vec3(X, Y, Z);
                    Vec3 Position = Point.subtract(Pos);
                    Compute.EntityFaceCircleCreate(mob, Position, new Vec3(Pos.x, Pos.y + 1.5, Pos.z), 0, 2, 40, ModParticles.SPIDER.get(),false);
                    i += 5;
                    angle = (2 * Math.PI / num) * (i);
                    X = Pos.x + Math.cos(angle) * r;
                    Z = Pos.z + Math.sin(angle) * r;
                    Y = Pos.y + 1;
                    Point = new Vec3(X, Y, Z);
                    Position = Point.subtract(Pos);
                    Compute.EntityFaceCircleCreate(mob, Position, new Vec3(Pos.x, Pos.y + 1.5, Pos.z), 0, 2, 40, ModParticles.SPIDER.get(),false);
                    i += 5;
                    angle = (2 * Math.PI / num) * (i);
                    X = Pos.x + Math.cos(angle) * r;
                    Z = Pos.z + Math.sin(angle) * r;
                    Y = Pos.y + 1;
                    Point = new Vec3(X, Y, Z);
                    Position = Point.subtract(Pos);
                    Compute.EntityFaceCircleCreate(mob, Position, new Vec3(Pos.x, Pos.y + 1.5, Pos.z), 0, 2, 40, ModParticles.SPIDER.get(),false);
                    i += 5;
                    angle = (2 * Math.PI / num) * (i);
                    X = Pos.x + Math.cos(angle) * r;
                    Z = Pos.z + Math.sin(angle) * r;
                    Y = Pos.y + 1;
                    Point = new Vec3(X, Y, Z);
                    Position = Point.subtract(Pos);
                    Compute.EntityFaceCircleCreate(mob, Position, new Vec3(Pos.x, Pos.y + 1.5, Pos.z), 0, 2, 40, ModParticles.SPIDER.get(),false);
                }
                if (mob.getItemBySlot(EquipmentSlot.HEAD).is(Moditems.ArmorHuskRecall.get())) {
                    if (ClientUtils.BlackForestParticle) {
                        ClientUtils.BlackForestParticle = false;
                        int num = 160;
                        for (int i = 0; i < num; i++) {
                            double angle = (2*Math.PI/num)*(i);
                            double X = Math.cos(angle) * 0.5;
                            double Y = 0;
                            double Z = Math.sin(angle) * 0.5;
                            level.addParticle(ModParticles.BLACKFOREST_RECALL.get(),mob.getX(),mob.getY() + 1.5,mob.getZ(),
                                    X,Y,Z);
                        }
                        for (int i = 0; i < num; i++) {
                            double angle = (2*Math.PI/num)*(i);
                            double X = Math.cos(angle) * 0.25;
                            double Y = 0;
                            double Z = Math.sin(angle) * 0.25;
                            level.addParticle(ModParticles.BLACKFOREST_RECALL.get(),mob.getX(),mob.getY() + 2,mob.getZ(),
                                    X,Y,Z);
                        }
                        for (int i = 0; i < num; i++) {
                            double angle = (2*Math.PI/num)*(i);
                            double X = Math.cos(angle) * 0.25;
                            double Y = 0;
                            double Z = Math.sin(angle) * 0.25;
                            level.addParticle(ModParticles.BLACKFOREST_RECALL.get(),mob.getX(),mob.getY() + 1,mob.getZ(),
                                    X,Y,Z);
                        }
                    }
                    Vec3 Pos = mob.getPosition(1.0f);
                    int num = 20;
                    int i = mob.tickCount % 20;
                    double r = 1;
                    double angle = (2 * Math.PI / num) * (i);
                    double X = Pos.x + Math.cos(angle) * r;
                    double Z = Pos.z + Math.sin(angle) * r;
                    double Y = Pos.y + 1;
                    Vec3 Point = new Vec3(X, Y, Z);
                    Vec3 Position = Point.subtract(Pos);
                    Compute.EntityFaceCircleCreate(mob, Position, new Vec3(Pos.x, Pos.y + 1.5, Pos.z), 0, 2, 40, ModParticles.BLACKFOREST.get(),false);
                    i += 5;
                    angle = (2 * Math.PI / num) * (i);
                    X = Pos.x + Math.cos(angle) * r;
                    Z = Pos.z + Math.sin(angle) * r;
                    Y = Pos.y + 1;
                    Point = new Vec3(X, Y, Z);
                    Position = Point.subtract(Pos);
                    Compute.EntityFaceCircleCreate(mob, Position, new Vec3(Pos.x, Pos.y + 1.5, Pos.z), 0, 2, 40, ModParticles.BLACKFOREST.get(),false);
                    i += 5;
                    angle = (2 * Math.PI / num) * (i);
                    X = Pos.x + Math.cos(angle) * r;
                    Z = Pos.z + Math.sin(angle) * r;
                    Y = Pos.y + 1;
                    Point = new Vec3(X, Y, Z);
                    Position = Point.subtract(Pos);
                    Compute.EntityFaceCircleCreate(mob, Position, new Vec3(Pos.x, Pos.y + 1.5, Pos.z), 0, 2, 40, ModParticles.BLACKFOREST.get(),false);
                    i += 5;
                    angle = (2 * Math.PI / num) * (i);
                    X = Pos.x + Math.cos(angle) * r;
                    Z = Pos.z + Math.sin(angle) * r;
                    Y = Pos.y + 1;
                    Point = new Vec3(X, Y, Z);
                    Position = Point.subtract(Pos);
                    Compute.EntityFaceCircleCreate(mob, Position, new Vec3(Pos.x, Pos.y + 1.5, Pos.z), 0, 2, 40, ModParticles.BLACKFOREST.get(),false);
                }
                if (mob.getItemBySlot(EquipmentSlot.HEAD).is(Moditems.ArmorLightningRecall.get())) {
                    Vec3 Pos = mob.getPosition(1.0f);
                    int num = 20;
                    int i = mob.tickCount % 20;
                    double r = 1;
                    double angle = (2 * Math.PI / num) * (i);
                    double X = Pos.x + Math.cos(angle) * r;
                    double Z = Pos.z + Math.sin(angle) * r;
                    double Y = Pos.y + 1;
                    Vec3 Point = new Vec3(X, Y, Z);
                    Vec3 Position = Point.subtract(Pos);
                    Compute.EntityFaceCircleCreate(mob, Position, new Vec3(Pos.x, Pos.y + 1.5, Pos.z), 0, 2, 40, ModParticles.LIGHTNINGISLAND.get(),false);
                    i += 5;
                    angle = (2 * Math.PI / num) * (i);
                    X = Pos.x + Math.cos(angle) * r;
                    Z = Pos.z + Math.sin(angle) * r;
                    Y = Pos.y + 1;
                    Point = new Vec3(X, Y, Z);
                    Position = Point.subtract(Pos);
                    Compute.EntityFaceCircleCreate(mob, Position, new Vec3(Pos.x, Pos.y + 1.5, Pos.z), 0, 2, 40, ModParticles.LIGHTNINGISLAND.get(),false);
                    i += 5;
                    angle = (2 * Math.PI / num) * (i);
                    X = Pos.x + Math.cos(angle) * r;
                    Z = Pos.z + Math.sin(angle) * r;
                    Y = Pos.y + 1;
                    Point = new Vec3(X, Y, Z);
                    Position = Point.subtract(Pos);
                    Compute.EntityFaceCircleCreate(mob, Position, new Vec3(Pos.x, Pos.y + 1.5, Pos.z), 0, 2, 40, ModParticles.LIGHTNINGISLAND.get(),false);
                    i += 5;
                    angle = (2 * Math.PI / num) * (i);
                    X = Pos.x + Math.cos(angle) * r;
                    Z = Pos.z + Math.sin(angle) * r;
                    Y = Pos.y + 1;
                    Point = new Vec3(X, Y, Z);
                    Position = Point.subtract(Pos);
                    Compute.EntityFaceCircleCreate(mob, Position, new Vec3(Pos.x, Pos.y + 1.5, Pos.z), 0, 2, 40, ModParticles.LIGHTNINGISLAND.get(),false);
                }
                if (mob.getItemBySlot(EquipmentSlot.HEAD).is(Moditems.ArmorNetherRecall.get())) {
                    if (ClientUtils.NetherParticle) {
                        ClientUtils.NetherParticle = false;
                        int num = 160;
                        for (int i = 0; i < num; i++) {
                            double angle = (2*Math.PI/num)*(i);
                            double X = Math.cos(angle) * 0.5;
                            double Y = 0;
                            double Z = Math.sin(angle) * 0.5;
                            level.addParticle(ModParticles.NETHER.get(),mob.getX(),mob.getY() + 1.5,mob.getZ(),
                                    X,Y,Z);
                        }
                        for (int i = 0; i < num; i++) {
                            double angle = (2*Math.PI/num)*(i);
                            double X = Math.cos(angle) * 0.25;
                            double Y = 0;
                            double Z = Math.sin(angle) * 0.25;
                            level.addParticle(ModParticles.NETHER.get(),mob.getX(),mob.getY() + 2,mob.getZ(),
                                    X,Y,Z);
                        }
                        for (int i = 0; i < num; i++) {
                            double angle = (2*Math.PI/num)*(i);
                            double X = Math.cos(angle) * 0.25;
                            double Y = 0;
                            double Z = Math.sin(angle) * 0.25;
                            level.addParticle(ModParticles.NETHER.get(),mob.getX(),mob.getY() + 1,mob.getZ(),
                                    X,Y,Z);
                        }
                    }
                    Vec3 Pos = mob.getPosition(1.0f);
                    int num = 20;
                    int i = mob.tickCount % 20;
                    double r = 1;
                    double angle = (2 * Math.PI / num) * (i);
                    double X = Pos.x + Math.cos(angle) * r;
                    double Z = Pos.z + Math.sin(angle) * r;
                    double Y = Pos.y + 1;
                    Vec3 Point = new Vec3(X, Y, Z);
                    Vec3 Position = Point.subtract(Pos);
                    Compute.EntityFaceCircleCreate(mob, Position, new Vec3(Pos.x, Pos.y + 1.5, Pos.z), 0, 2, 40, ModParticles.NETHER.get(),false);
                    i += 5;
                    angle = (2 * Math.PI / num) * (i);
                    X = Pos.x + Math.cos(angle) * r;
                    Z = Pos.z + Math.sin(angle) * r;
                    Y = Pos.y + 1;
                    Point = new Vec3(X, Y, Z);
                    Position = Point.subtract(Pos);
                    Compute.EntityFaceCircleCreate(mob, Position, new Vec3(Pos.x, Pos.y + 1.5, Pos.z), 0, 2, 40, ModParticles.NETHER.get(),false);
                    i += 5;
                    angle = (2 * Math.PI / num) * (i);
                    X = Pos.x + Math.cos(angle) * r;
                    Z = Pos.z + Math.sin(angle) * r;
                    Y = Pos.y + 1;
                    Point = new Vec3(X, Y, Z);
                    Position = Point.subtract(Pos);
                    Compute.EntityFaceCircleCreate(mob, Position, new Vec3(Pos.x, Pos.y + 1.5, Pos.z), 0, 2, 40, ModParticles.NETHER.get(),false);
                    i += 5;
                    angle = (2 * Math.PI / num) * (i);
                    X = Pos.x + Math.cos(angle) * r;
                    Z = Pos.z + Math.sin(angle) * r;
                    Y = Pos.y + 1;
                    Point = new Vec3(X, Y, Z);
                    Position = Point.subtract(Pos);
                    Compute.EntityFaceCircleCreate(mob, Position, new Vec3(Pos.x, Pos.y + 1.5, Pos.z), 0, 2, 40, ModParticles.NETHER.get(),false);
                }
                if (mob.getItemBySlot(EquipmentSlot.HEAD).is(Moditems.ArmorSnowRecall.get())) {
                    Vec3 Pos = mob.getPosition(1.0f);
                    int num = 20;
                    int i = mob.tickCount % 20;
                    double r = 1;
                    double angle = (2 * Math.PI / num) * (i);
                    double X = Pos.x + Math.cos(angle) * r;
                    double Z = Pos.z + Math.sin(angle) * r;
                    double Y = Pos.y + 1;
                    Vec3 Point = new Vec3(X, Y, Z);
                    Vec3 Position = Point.subtract(Pos);
                    Compute.EntityFaceCircleCreate(mob, Position, new Vec3(Pos.x, Pos.y + 1.5, Pos.z), 0, 2, 40, ModParticles.SNOW.get(),false);
                    i += 5;
                    angle = (2 * Math.PI / num) * (i);
                    X = Pos.x + Math.cos(angle) * r;
                    Z = Pos.z + Math.sin(angle) * r;
                    Y = Pos.y + 1;
                    Point = new Vec3(X, Y, Z);
                    Position = Point.subtract(Pos);
                    Compute.EntityFaceCircleCreate(mob, Position, new Vec3(Pos.x, Pos.y + 1.5, Pos.z), 0, 2, 40, ModParticles.SNOW.get(),false);
                    i += 5;
                    angle = (2 * Math.PI / num) * (i);
                    X = Pos.x + Math.cos(angle) * r;
                    Z = Pos.z + Math.sin(angle) * r;
                    Y = Pos.y + 1;
                    Point = new Vec3(X, Y, Z);
                    Position = Point.subtract(Pos);
                    Compute.EntityFaceCircleCreate(mob, Position, new Vec3(Pos.x, Pos.y + 1.5, Pos.z), 0, 2, 40, ModParticles.SNOW.get(),false);
                    i += 5;
                    angle = (2 * Math.PI / num) * (i);
                    X = Pos.x + Math.cos(angle) * r;
                    Z = Pos.z + Math.sin(angle) * r;
                    Y = Pos.y + 1;
                    Point = new Vec3(X, Y, Z);
                    Position = Point.subtract(Pos);
                    Compute.EntityFaceCircleCreate(mob, Position, new Vec3(Pos.x, Pos.y + 1.5, Pos.z), 0, 2, 40, ModParticles.SNOW.get(),false);
                }
                if (mob.getItemBySlot(EquipmentSlot.HEAD).is(Moditems.ArmorForestBoss.get())) {
                    Vec3 Pos = mob.getPosition(1.0f);
                    int num = 20;
                    int i = mob.tickCount % 20;
                    double r = 1;
                    double angle = (2 * Math.PI / num) * (i);
                    double X = Pos.x + Math.cos(angle) * r;
                    double Z = Pos.z + Math.sin(angle) * r;
                    double Y = Pos.y + 1;
                    Vec3 Point = new Vec3(X, Y, Z);
                    Vec3 Position = Point.subtract(Pos);
                    Compute.EntityFaceCircleCreate(mob, Position, new Vec3(Pos.x, Pos.y + 1.5, Pos.z), 0, 2, 40, ModParticles.ENTROPY.get(),false);
                    i += 5;
                    angle = (2 * Math.PI / num) * (i);
                    X = Pos.x + Math.cos(angle) * r;
                    Z = Pos.z + Math.sin(angle) * r;
                    Y = Pos.y + 1;
                    Point = new Vec3(X, Y, Z);
                    Position = Point.subtract(Pos);
                    Compute.EntityFaceCircleCreate(mob, Position, new Vec3(Pos.x, Pos.y + 1.5, Pos.z), 0, 2, 40, ModParticles.FOREST.get(),false);
                    i += 5;
                    angle = (2 * Math.PI / num) * (i);
                    X = Pos.x + Math.cos(angle) * r;
                    Z = Pos.z + Math.sin(angle) * r;
                    Y = Pos.y + 1;
                    Point = new Vec3(X, Y, Z);
                    Position = Point.subtract(Pos);
                    Compute.EntityFaceCircleCreate(mob, Position, new Vec3(Pos.x, Pos.y + 1.5, Pos.z), 0, 2, 40, ModParticles.ENTROPY.get(),false);
                    i += 5;
                    angle = (2 * Math.PI / num) * (i);
                    X = Pos.x + Math.cos(angle) * r;
                    Z = Pos.z + Math.sin(angle) * r;
                    Y = Pos.y + 1;
                    Point = new Vec3(X, Y, Z);
                    Position = Point.subtract(Pos);
                    Compute.EntityFaceCircleCreate(mob, Position, new Vec3(Pos.x, Pos.y + 1.5, Pos.z), 0, 2, 40, ModParticles.FOREST.get(),false);
                }
                if (mob.getItemBySlot(EquipmentSlot.HEAD).is(Moditems.ArmorVolcanoBoss.get())) {
                    Vec3 Pos = mob.getPosition(1.0f);
                    int num = 20;
                    int i = mob.tickCount % 20;
                    double r = 1;
                    double angle = (2 * Math.PI / num) * (i);
                    double X = Pos.x + Math.cos(angle) * r;
                    double Z = Pos.z + Math.sin(angle) * r;
                    double Y = Pos.y + 1;
                    Vec3 Point = new Vec3(X, Y, Z);
                    Vec3 Position = Point.subtract(Pos);
                    Compute.EntityFaceCircleCreate(mob, Position, new Vec3(Pos.x, Pos.y + 1.5, Pos.z), 0, 2, 40, ModParticles.ENTROPY.get(),false);
                    i += 5;
                    angle = (2 * Math.PI / num) * (i);
                    X = Pos.x + Math.cos(angle) * r;
                    Z = Pos.z + Math.sin(angle) * r;
                    Y = Pos.y + 1;
                    Point = new Vec3(X, Y, Z);
                    Position = Point.subtract(Pos);
                    Compute.EntityFaceCircleCreate(mob, Position, new Vec3(Pos.x, Pos.y + 1.5, Pos.z), 0, 2, 40, ModParticles.VOLCANO.get(),false);
                    i += 5;
                    angle = (2 * Math.PI / num) * (i);
                    X = Pos.x + Math.cos(angle) * r;
                    Z = Pos.z + Math.sin(angle) * r;
                    Y = Pos.y + 1;
                    Point = new Vec3(X, Y, Z);
                    Position = Point.subtract(Pos);
                    Compute.EntityFaceCircleCreate(mob, Position, new Vec3(Pos.x, Pos.y + 1.5, Pos.z), 0, 2, 40, ModParticles.ENTROPY.get(),false);
                    i += 5;
                    angle = (2 * Math.PI / num) * (i);
                    X = Pos.x + Math.cos(angle) * r;
                    Z = Pos.z + Math.sin(angle) * r;
                    Y = Pos.y + 1;
                    Point = new Vec3(X, Y, Z);
                    Position = Point.subtract(Pos);
                    Compute.EntityFaceCircleCreate(mob, Position, new Vec3(Pos.x, Pos.y + 1.5, Pos.z), 0, 2, 40, ModParticles.VOLCANO.get(),false);
                }
            }
            List<Player> playerList = level.getEntitiesOfClass(Player.class,AABB.ofSize(player.getPosition(1),100,100,100));
            Random random = new Random();
            for (Player player1 : playerList) {
                if (TickCount % 20 == 0 && player1.getItemBySlot(EquipmentSlot.HEAD).is(Moditems.SakuraArmorHelmet.get())) {
                    level.addParticle(ParticleTypes.CHERRY_LEAVES,player1.getX() + random.nextDouble(-0.5,0.5),player1.getY() + 1.7,player1.getZ() + random.nextDouble(-0.5,0.5),0,0,0);
                }
            }
        }
    }
}
