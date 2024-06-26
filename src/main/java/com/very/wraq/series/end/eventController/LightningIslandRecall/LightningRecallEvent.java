package com.very.wraq.series.end.eventController.LightningIslandRecall;

import com.very.wraq.render.toolTip.CustomStyle;
import com.very.wraq.valueAndTools.Compute;
import com.very.wraq.valueAndTools.Utils.Utils;
import com.very.wraq.valueAndTools.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;
import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber
public class LightningRecallEvent {
    @SubscribeEvent
    public static void Lightning(TickEvent.LevelTickEvent event) throws IOException {
        if (event.side.isServer()) {
            Random random = new Random();
            Level level = event.level;
            ServerLevel level1 = event.level.getServer().getLevel(Level.END);
            ServerLevel overWorld = event.level.getServer().getLevel(Level.OVERWORLD);
            if (level.equals(level1) && event.phase.equals(TickEvent.Phase.START)) {
                String ZoneName = "唤雷岛";
                Style style = CustomStyle.styleOfLightingIsland;
                double ZoneX = 1302;
                double ZoneY = 64;
                double ZoneZ = 574;
                double PlatformX = 0;
                double PlatformY = 158;
                double PlatformZ = 0;
                if (Utils.LightningRecall.RecallCount != -1) {
                    if (Utils.LightningRecall.RecallPlayer == null) {
                        Compute.FormatBroad(level,Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                Component.literal("").withStyle(ChatFormatting.WHITE).
                                        append(Utils.LightningRecall.playerName).
                                        append(Component.literal("尝试回想其在").withStyle(ChatFormatting.WHITE)).
                                        append(Component.literal(ZoneName).withStyle(style)).
                                        append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.WHITE)));
                        if (Utils.LightingRecallZombie != null) Utils.LightingRecallZombie.remove(Entity.RemovalReason.KILLED);

                        Utils.LightningRecall.Reset();
                        return;
                    }
                    if (Utils.LightningRecall.KillCount == -1 && Utils.LightningRecall.RecallCount != 400 && Utils.LightningRecall.RecallCount != 380) Utils.LightningRecall.RecallCount--;
                    if (Utils.LightningRecall.RecallCount == 600) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("开始"+ZoneName+"的回忆").withStyle(style));
                        Utils.LightningRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Compute.FormatBroad(level,Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                Component.literal(Utils.LightningRecall.RecallPlayer.getName().getString()+"开始回忆其在").withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(ZoneName).withStyle(style)).
                                        append(Component.literal("的经历。").withStyle(ChatFormatting.WHITE)));
                    }
                    if (Utils.LightningRecall.RecallCount == 540) {
                        Utils.LightningRecall.KillCount = 20;
                        Utils.LightningRecall.RecallPlayer.teleportTo(overWorld,ZoneX,ZoneY,ZoneZ,0,0);
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("击杀20只唤雷守卫").withStyle(style));
                        Utils.LightningRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Utils.LightningRecall.RecallCount--;
                    }
                    if (Utils.LightningRecall.KillCount == 0) {
                        Utils.LightningRecall.KillCount = -1;
                    }
                    if (Utils.LightningRecall.RecallCount == 500) {
                        Utils.LightningRecall.RecallPlayer.teleportTo(level1,PlatformX,PlatformY,PlatformZ,0,0);
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回想起什么了吗？").withStyle(style));
                        Utils.LightningRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                    }
                    if (Utils.LightningRecall.RecallCount == 400) {
                        if (Utils.LightingRecallZombie == null || !Utils.LightingRecallZombie.isAlive()) {
                            if(Utils.LightingRecallZombie != null) Utils.LightingRecallZombie.remove(Entity.RemovalReason.KILLED);
                            Utils.LightingRecallZombie = new Zombie(EntityType.ZOMBIE,level1);
                            Compute.SetMobCustomName(Utils.LightingRecallZombie, ModItems.ArmorLightningRecall.get(),Component.literal("模糊记忆中的唤雷守卫").withStyle(style));
                            Utils.LightingRecallZombie.setItemSlot(EquipmentSlot.HEAD , ModItems.ArmorLightningRecall.get().getDefaultInstance());
                            Utils.LightingRecallZombie.setItemSlot(EquipmentSlot.CHEST , ModItems.ArmorLZChest.get().getDefaultInstance());
                            Utils.LightingRecallZombie.setItemSlot(EquipmentSlot.LEGS , ModItems.ArmorLZLeggings.get().getDefaultInstance());
                            Utils.LightingRecallZombie.setItemSlot(EquipmentSlot.FEET , ModItems.ArmorLZBoots.get().getDefaultInstance());
                            Utils.LightingRecallZombie.setItemSlot(EquipmentSlot.MAINHAND , Items.IRON_SWORD.getDefaultInstance());
                            Utils.LightingRecallZombie.getAttribute(Attributes.MAX_HEALTH).setBaseValue(115200.0D);
                            Utils.LightingRecallZombie.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(200.0D);
                            Utils.LightingRecallZombie.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5D);
                            Utils.LightingRecallZombie.setHealth(Utils.LightingRecallZombie.getMaxHealth());
                            Utils.LightingRecallZombie.moveTo(PlatformX,PlatformY,PlatformZ);
                            level1.addFreshEntity(Utils.LightingRecallZombie);
                            Utils.LightningRecall.RecallCount--;
                            Utils.LightningRecall.mob = Utils.LightingRecallZombie;
                        }
                    }
                    if (Utils.LightningRecall.RecallCount == 380) {
                        if (Utils.LightningRecall.RecallSuccess) {
                            Utils.LightningRecall.RecallCount --;
                        }
                    }
                    if (Utils.LightningRecall.RecallCount == 340) {
                        if (Utils.LightningRecall.RecallSuccess) {
                            CompoundTag data = Utils.LightningRecall.RecallPlayer.getPersistentData();
                            String RecallTimes = "LightningRecallTimes";
                            String RecallSuccessTimes = "LightningRecall.RecallSuccessTimes";
                            int RecallTime = data.getInt(RecallTimes);
                            int RecallSuccessTime = data.getInt(RecallSuccessTimes);
                            if (random.nextInt(100) < 5 + RecallTime * 5 + Math.min(RecallSuccessTime,20)) {
                                data.putInt(RecallTimes,0);
                                data.putInt(RecallSuccessTimes,data.getInt(RecallSuccessTimes) + 1);
                                Utils.LightningRecall.RecallPlayer.teleportTo(overWorld,ZoneX,ZoneY,ZoneZ,0,0);
                                Compute.FormatBroad(level,Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                        Component.literal(Utils.LightningRecall.RecallPlayer.getName().getString()+"尝试回想在").withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(ZoneName).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal("成功").withStyle(ChatFormatting.GREEN)).
                                                append(Component.literal("回想起了在").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(style)).
                                                append(Component.literal("的所经所厉。").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal("这是Ta第").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(""+data.getInt(RecallSuccessTimes)).withStyle(CustomStyle.styleOfEnd)).
                                                append(Component.literal("回想起在").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.WHITE)));
                                Compute.FormatMSGSend(Utils.LightningRecall.RecallPlayer,Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                        Component.literal("这是你第").withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(""+data.getInt(RecallSuccessTimes)).withStyle(CustomStyle.styleOfEnd)).
                                                append(Component.literal("回想起在").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.WHITE)));
                                ItemStack itemStack = ModItems.LightningRecallSoul.get().getDefaultInstance();
                                itemStack.setCount(1);
                                Compute.ItemStackGive(Utils.LightningRecall.RecallPlayer,itemStack);
                            }
                            else {
                                data.putInt(RecallTimes,data.getInt(RecallTimes) + 1);
                                Utils.LightningRecall.RecallPlayer.teleportTo(level1,100.5,50,0.5,0,0);
                                Compute.FormatBroad(level,Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                        Component.literal(Utils.LightningRecall.RecallPlayer.getName().getString()+"尝试回想在").withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(ZoneName).withStyle(style)).
                                                append(Component.literal("的记忆。似乎就快想起来了。").withStyle(ChatFormatting.WHITE)));
                                Compute.FormatMSGSend(Utils.LightningRecall.RecallPlayer,Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                        Component.literal("这是你第").withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(""+data.getInt(RecallTimes)).withStyle(CustomStyle.styleOfEnd)).
                                                append(Component.literal("尝试回想起在").withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.WHITE)));
                                ItemStack itemStack = ModItems.LightningRune.get().getDefaultInstance();
                                itemStack.setCount(1);
                                Compute.ItemStackGive(Utils.LightningRecall.RecallPlayer,itemStack);
                            }
                        }
                    }
                    if (Utils.LightningRecall.RecallCount == 260) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                        Utils.LightningRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Utils.LightningRecall.Reset();
                    }
                }
                if (Utils.LightingRecallZombie != null && Utils.LightingRecallZombie.isAlive()) {
                    if (level.getServer().getTickCount() % 20 == 0) {
                        List<Player> playerList = level.getEntitiesOfClass(Player.class, AABB.ofSize(Utils.LightingRecallZombie.position(),20,20,20));
                        for (Player player : playerList) {
                            if (player.position().distanceTo(Utils.LightingRecallZombie.position()) <= 2.5f) {
                                LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT,level1);
                                lightningBolt.setDamage(player.getMaxHealth() * 0.2f);
                                lightningBolt.moveTo(player.position());
                                level1.addFreshEntity(lightningBolt);
                            }
                        }
                    }
                    if (level.getServer().getTickCount() % 20 < random.nextInt(5)) {
                        LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT,level1);
                        lightningBolt.setDamage(400);
                        lightningBolt.moveTo(PlatformX - 9 + random.nextInt(18),PlatformY,PlatformZ - 9 + random.nextInt(18));
                        level1.addFreshEntity(lightningBolt);
                    }
                }
                if (Utils.LightningRecall.RecallTimeLimit > 0) Utils.LightningRecall.RecallTimeLimit--;
                else {
                    if (Utils.LightningRecall.RecallTimeLimit == 0) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                        Utils.LightningRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Compute.FormatBroad(level,Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                                Component.literal(Utils.LightningRecall.RecallPlayer.getName().getString()+"尝试回想其在").withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(ZoneName).withStyle(style)).
                                        append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.WHITE)));
                        Utils.LightningRecall.RecallPlayer.teleportTo(level1,100.5,50,0.5,0,0);
                        if (Utils.LightingRecallZombie != null) Utils.LightingRecallZombie.remove(Entity.RemovalReason.KILLED);
                        Utils.LightningRecall.Reset();
                    }
                }
                if (Utils.LightningRecall.RecallTimeLimit > 0 && Utils.LightningRecall.RecallPlayer.isDeadOrDying()) {
                    ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                            new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                    Utils.LightningRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                    Compute.FormatBroad(level,Component.literal("回忆").withStyle(CustomStyle.styleOfEnd),
                            Component.literal(Utils.LightningRecall.RecallPlayer.getName().getString()+"尝试回想其在").withStyle(ChatFormatting.WHITE).
                                    append(Component.literal(ZoneName).withStyle(style)).
                                    append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.WHITE)));
                    Utils.LightningRecall.RecallPlayer.teleportTo(level1,100.5,50,0.5,0,0);
                    if (Utils.LightingRecallZombie != null) Utils.LightingRecallZombie.remove(Entity.RemovalReason.KILLED);
                    Utils.LightningRecall.Reset();
                }
            }
        }
    }
}
