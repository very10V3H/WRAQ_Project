package com.Very.very.Series.EndSeries.EventControl.BlackForestRecall;

import com.Very.very.NetWorking.ModNetworking;
import com.Very.very.NetWorking.Packets.ParticlePackets.BlackForestRecallParticleS2CPacket;
import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.Moditems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber
public class HuskRecallEvent {
    @SubscribeEvent
    public static void Kaze(TickEvent.LevelTickEvent event) {
        if (event.side.isServer()) {
            Random random = new Random();
            Level level = event.level;
            ServerLevel level1 = event.level.getServer().getLevel(Level.END);
            ServerLevel overWorld = event.level.getServer().getLevel(Level.OVERWORLD);
            if (level.equals(level1) && event.phase.equals(TickEvent.Phase.START)) {
                String ZoneName = "暗黑森林";
                Style style = Utils.styleOfBlackForest;
                double ZoneX = 1112.5;
                double ZoneY = 64;
                double ZoneZ = 636.5;
                double PlatformX = 40;
                double PlatformY = 158;
                double PlatformZ = 0;
                if (Utils.HuskRecall.RecallCount != -1) {
                    if (Utils.HuskRecall.KillCount == -1 && Utils.HuskRecall.RecallCount != 400 && Utils.HuskRecall.RecallCount != 380) Utils.HuskRecall.RecallCount--;
                    if (Utils.HuskRecall.RecallCount == 600) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("开始"+ZoneName+"的回忆").withStyle(style));
                        Utils.HuskRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Compute.FormatBroad(level,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                Component.literal(Utils.HuskRecall.RecallPlayer.getName().getString()+"开始回忆其在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                        append(Component.literal("的经历。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                    }
                    if (Utils.HuskRecall.RecallCount == 540) {
                        Utils.HuskRecall.KillCount = 20;
                        Utils.HuskRecall.RecallPlayer.teleportTo(overWorld,ZoneX,ZoneY,ZoneZ,0,0);
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("击杀20只脆弱的灵魂").withStyle(style));
                        Utils.HuskRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Utils.HuskRecall.RecallCount--;
                    }
                    if (Utils.HuskRecall.KillCount == 0) {
                        Utils.HuskRecall.KillCount = -1;
                    }
                    if (Utils.HuskRecall.RecallCount == 500) {
                        Utils.HuskRecall.RecallPlayer.teleportTo(level1,PlatformX,PlatformY,PlatformZ,0,0);
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回想起什么了吗？").withStyle(style));
                        Utils.HuskRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                    }
                    if (Utils.HuskRecall.RecallCount == 400) {
                        if (Utils.HuskRecallHusk == null || !Utils.HuskRecallHusk.isAlive()) {
                            if(Utils.HuskRecallHusk != null) Utils.HuskRecallHusk.remove(Entity.RemovalReason.KILLED);
                            Utils.HuskRecallHusk = new Husk(EntityType.HUSK,level1);
                            Compute.SetMobCustomName(Utils.HuskRecallHusk,Moditems.ArmorHuskRecall.get(),Component.literal("模糊记忆中的脆弱灵魂").withStyle(style));
                            Utils.HuskRecallHusk.setItemSlot(EquipmentSlot.HEAD , Moditems.ArmorHuskRecall.get().getDefaultInstance());
                            Utils.HuskRecallHusk.setItemSlot(EquipmentSlot.MAINHAND , Moditems.BlackForestSword4.get().getDefaultInstance());
                            Utils.HuskRecallHusk.getAttribute(Attributes.MAX_HEALTH).setBaseValue(115200.0D);
                            Utils.HuskRecallHusk.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(200.0D);
                            Utils.HuskRecallHusk.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5D);
                            Utils.HuskRecallHusk.setHealth(Utils.HuskRecallHusk.getMaxHealth());
                            Utils.HuskRecallHusk.getPersistentData().putBoolean("Half",false);
                            Utils.HuskRecallHusk.moveTo(PlatformX,PlatformY,PlatformZ);
                            level1.addFreshEntity(Utils.HuskRecallHusk);
                            Utils.HuskRecall.RecallCount--;
                        }
                    }
                    if (Utils.HuskRecall.RecallCount == 380) {
                        if (Utils.HuskRecall.RecallSuccess) {
                            Utils.HuskRecall.RecallCount --;
                        }
                    }
                    if (Utils.HuskRecall.RecallCount == 340) {
                        if (Utils.HuskRecall.RecallSuccess) {
                            CompoundTag data = Utils.HuskRecall.RecallPlayer.getPersistentData();
                            String RecallTimes = "HuskRecallTimes";
                            String RecallSuccessTimes = "HuskRecall.RecallSuccessTimes";
                            int RecallTime = data.getInt(RecallTimes);
                            int RecallSuccessTime = data.getInt(RecallSuccessTimes);
                            if (random.nextInt(100) < 5 + RecallTime * 5 + Math.min(RecallSuccessTime,20)) {
                                data.putInt(RecallTimes,0);
                                data.putInt(RecallSuccessTimes,data.getInt(RecallSuccessTimes) + 1);
                                Utils.HuskRecall.RecallPlayer.teleportTo(overWorld,ZoneX,ZoneY,ZoneZ,0,0);
                                Compute.FormatBroad(level,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                        Component.literal(Utils.HuskRecall.RecallPlayer.getName().getString()+"尝试回想在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal("成功").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.GREEN)).
                                                append(Component.literal("回想起了在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                                append(Component.literal("的所经所厉。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal("这是Ta第").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(""+data.getInt(RecallSuccessTimes)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd)).
                                                append(Component.literal("回想起在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                                Compute.FormatMSGSend(Utils.HuskRecall.RecallPlayer,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                        Component.literal("这是你第").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(""+data.getInt(RecallSuccessTimes)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd)).
                                                append(Component.literal("回想起在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                                ItemStack itemStack = Moditems.BlackForestRecallSoul.get().getDefaultInstance();
                                itemStack.getOrCreateTagElement(Utils.MOD_ID);
                                itemStack.setCount(1);
                                Compute.ItemStackGive(Utils.HuskRecall.RecallPlayer,itemStack);
                            }
                            else {
                                data.putInt(RecallTimes,data.getInt(RecallTimes) + 1);
                                Utils.HuskRecall.RecallPlayer.teleportTo(level1,100.5,50,0.5,0,0);
                                Compute.FormatBroad(level,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                        Component.literal(Utils.HuskRecall.RecallPlayer.getName().getString()+"尝试回想在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                                append(Component.literal("的记忆。似乎就快想起来了。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                                Compute.FormatMSGSend(Utils.HuskRecall.RecallPlayer,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                        Component.literal("这是你第").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(""+data.getInt(RecallTimes)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd)).
                                                append(Component.literal("尝试回想起在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                                ItemStack itemStack = Moditems.BlackForestRune.get().getDefaultInstance();
                                itemStack.getOrCreateTagElement(Utils.MOD_ID);
                                itemStack.setCount(1);
                                Compute.ItemStackGive(Utils.HuskRecall.RecallPlayer,itemStack);
                            }
                        }
                    }
                    if (Utils.HuskRecall.RecallCount == 260) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                        Utils.HuskRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Utils.HuskRecall.Reset();
                    }
                }

                if (Utils.HuskRecallHusk != null && Utils.HuskRecallHusk.isAlive()) { // AI
                    if (!Utils.HuskRecallHusk.getPersistentData().getBoolean("Half") && Utils.HuskRecallHusk.getHealth() <= Utils.HuskRecallHusk.getMaxHealth() * 0.25f) {
                        Utils.HuskRecallHusk.getPersistentData().putBoolean("Half",true);
                        Utils.HuskRecallHusk.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(400.0f);
                        Utils.HuskRecallHusk.heal(Utils.HuskRecall.RecallPlayer.getMaxHealth() * 10);
                        ModNetworking.sendToClient(new BlackForestRecallParticleS2CPacket(true),Utils.HuskRecall.RecallPlayer);
                    }
                }

                if (Utils.HuskRecall.RecallTimeLimit > 0) Utils.HuskRecall.RecallTimeLimit--;
                else {
                    if (Utils.HuskRecall.RecallTimeLimit == 0) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                        Utils.HuskRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Compute.FormatBroad(level,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                Component.literal(Utils.HuskRecall.RecallPlayer.getName().getString()+"尝试回想其在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                        append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                        Utils.HuskRecall.RecallPlayer.teleportTo(level1,100.5,50,0.5,0,0);
                        if (Utils.HuskRecallHusk != null) Utils.HuskRecallHusk.remove(Entity.RemovalReason.KILLED);
                        Utils.HuskRecall.Reset();
                    }
                }
                if (Utils.HuskRecall.RecallTimeLimit > 0 && Utils.HuskRecall.RecallPlayer.isDeadOrDying()) {
                    ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                            new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                    Utils.HuskRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                    Compute.FormatBroad(level,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                            Component.literal(Utils.HuskRecall.RecallPlayer.getName().getString()+"尝试回想其在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                    append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                    Utils.HuskRecall.RecallPlayer.teleportTo(level1,100.5,50,0.5,0,0);
                    if (Utils.HuskRecallHusk != null) Utils.HuskRecallHusk.remove(Entity.RemovalReason.KILLED);
                    Utils.HuskRecall.Reset();
                }
            }
        }
    }
}
