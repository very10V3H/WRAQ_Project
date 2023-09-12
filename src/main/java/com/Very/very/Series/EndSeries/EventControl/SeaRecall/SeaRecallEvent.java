package com.Very.very.Series.EndSeries.EventControl.SeaRecall;

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
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber
public class SeaRecallEvent {
    @SubscribeEvent
    public static void Sea(TickEvent.LevelTickEvent event) {
        if (event.side.isServer()) {
            Random random = new Random();
            Level level = event.level;
            ServerLevel level1 = event.level.getServer().getLevel(Level.END);
            ServerLevel overWorld = event.level.getServer().getLevel(Level.OVERWORLD);
            if (level.equals(level1) && event.phase.equals(TickEvent.Phase.START)) {
                String ZoneName = "海洋神殿";
                Style style = Utils.styleOfSea;
                double ZoneX = 655.5;
                double ZoneY = 44;
                double ZoneZ = 223.5;
                double PlatformX = 20;
                double PlatformY = 158;
                double PlatformZ = 20;
                if (Utils.SeaRecall.RecallCount != -1) {
                    if (Utils.SeaRecall.KillCount == -1 && Utils.SeaRecall.RecallCount != 400 && Utils.SeaRecall.RecallCount != 380) Utils.SeaRecall.RecallCount--;
                    if (Utils.SeaRecall.RecallCount == 600) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("开始"+ZoneName+"的回忆").withStyle(style));
                        Utils.SeaRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Compute.FormatBroad(level,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                Component.literal(Utils.SeaRecall.RecallPlayer.getName().getString()+"开始回忆其在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                        append(Component.literal("的经历。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                    }
                    if (Utils.SeaRecall.RecallCount == 540) {
                        Utils.SeaRecall.KillCount = 20;
                        Utils.SeaRecall.RecallPlayer.teleportTo(overWorld,ZoneX,ZoneY,ZoneZ,0,0);
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("击杀20只神殿守卫").withStyle(style));
                        Utils.SeaRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Utils.SeaRecall.RecallCount--;
                    }
                    if (Utils.SeaRecall.KillCount == 0) {
                        Utils.SeaRecall.KillCount = -1;
                    }
                    if (Utils.SeaRecall.RecallCount == 500) {
                        Utils.SeaRecall.RecallPlayer.teleportTo(level1,PlatformX,PlatformY,PlatformZ,0,0);
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回想起什么了吗？").withStyle(style));
                        Utils.SeaRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                    }
                    if (Utils.SeaRecall.RecallCount == 400) {
                        if (Utils.SeaRecallElderGuardian == null || !Utils.SeaRecallElderGuardian.isAlive()) {
                            if(Utils.SeaRecallElderGuardian != null) Utils.SeaRecallElderGuardian.remove(Entity.RemovalReason.KILLED);
                            Utils.SeaRecallElderGuardian = new ElderGuardian(EntityType.ELDER_GUARDIAN,level1);
                            Compute.SetMobCustomName(Utils.SeaRecallElderGuardian,Moditems.ArmorSeaRecall.get(),Component.literal("模糊记忆中的神殿守卫").withStyle(style));
                            Utils.SeaRecallElderGuardian.setItemSlot(EquipmentSlot.HEAD , Moditems.ArmorSeaRecall.get().getDefaultInstance());
                            Utils.SeaRecallElderGuardian.setItemSlot(EquipmentSlot.MAINHAND , Items.IRON_SWORD.getDefaultInstance());
                            Utils.SeaRecallElderGuardian.getAttribute(Attributes.MAX_HEALTH).setBaseValue(115200.0D);
                            Utils.SeaRecallElderGuardian.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(200.0D);
                            Utils.SeaRecallElderGuardian.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5D);
                            Utils.SeaRecallElderGuardian.setHealth(Utils.SeaRecallElderGuardian.getMaxHealth());
                            Utils.SeaRecallElderGuardian.moveTo(PlatformX,PlatformY,PlatformZ);
                            level1.addFreshEntity(Utils.SeaRecallElderGuardian);
                            Utils.SeaRecall.RecallCount--;
                        }
                    }
                    if (Utils.SeaRecall.RecallCount == 380) {
                        if (Utils.SeaRecall.RecallSuccess) {
                            Utils.SeaRecall.RecallCount --;
                        }
                    }
                    if (Utils.SeaRecall.RecallCount == 340) {
                        if (Utils.SeaRecall.RecallSuccess) {
                            CompoundTag data = Utils.SeaRecall.RecallPlayer.getPersistentData();
                            String RecallTimes = "SeaRecallTimes";
                            String RecallSuccessTimes = "SeaRecall.RecallSuccessTimes";
                            int RecallTime = data.getInt(RecallTimes);
                            int RecallSuccessTime = data.getInt(RecallSuccessTimes);
                            if (random.nextInt(100) < 5 + RecallTime * 5 + Math.min(RecallSuccessTime,20)) {
                                data.putInt(RecallTimes,0);
                                data.putInt(RecallSuccessTimes,data.getInt(RecallSuccessTimes) + 1);
                                Utils.SeaRecall.RecallPlayer.teleportTo(overWorld,ZoneX,ZoneY,ZoneZ,0,0);
                                Compute.FormatBroad(level,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                        Component.literal(Utils.SeaRecall.RecallPlayer.getName().getString()+"尝试回想在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
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
                                Compute.FormatMSGSend(Utils.SeaRecall.RecallPlayer,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                        Component.literal("这是你第").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(""+data.getInt(RecallSuccessTimes)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd)).
                                                append(Component.literal("回想起在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                                ItemStack itemStack = Moditems.SeaRecallSoul.get().getDefaultInstance();
                                itemStack.getOrCreateTagElement(Utils.MOD_ID);
                                itemStack.setCount(1);
                                Compute.ItemStackGive(Utils.SeaRecall.RecallPlayer,itemStack);
                            }
                            else {
                                data.putInt(RecallTimes,data.getInt(RecallTimes) + 1);
                                Utils.SeaRecall.RecallPlayer.teleportTo(level1,100.5,50,0.5,0,0);
                                Compute.FormatBroad(level,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                        Component.literal(Utils.SeaRecall.RecallPlayer.getName().getString()+"尝试回想在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                                append(Component.literal("的记忆。似乎就快想起来了。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                                Compute.FormatMSGSend(Utils.SeaRecall.RecallPlayer,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                        Component.literal("这是你第").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(""+data.getInt(RecallTimes)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd)).
                                                append(Component.literal("尝试回想起在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                                ItemStack itemStack = Moditems.SeaRune.get().getDefaultInstance();
                                itemStack.getOrCreateTagElement(Utils.MOD_ID);
                                itemStack.setCount(1);
                                Compute.ItemStackGive(Utils.SeaRecall.RecallPlayer,itemStack);
                            }
                        }
                    }
                    if (Utils.SeaRecall.RecallCount == 260) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                        Utils.SeaRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Utils.SeaRecall.Reset();
                    }
                }

                if (Utils.SeaRecall.RecallTimeLimit > 0) Utils.SeaRecall.RecallTimeLimit--;
                else {
                    if (Utils.SeaRecall.RecallTimeLimit == 0) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                        Utils.SeaRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Compute.FormatBroad(level,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                Component.literal(Utils.SeaRecall.RecallPlayer.getName().getString()+"尝试回想其在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                        append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                        Utils.SeaRecall.RecallPlayer.teleportTo(level1,100.5,50,0.5,0,0);
                        if (Utils.SeaRecallElderGuardian != null) Utils.SeaRecallElderGuardian.remove(Entity.RemovalReason.KILLED);
                        Utils.SeaRecall.Reset();
                    }
                }
                if (Utils.SeaRecall.RecallTimeLimit > 0 && Utils.SeaRecall.RecallPlayer.isDeadOrDying()) {
                    ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                            new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                    Utils.SeaRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                    Compute.FormatBroad(level,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                            Component.literal(Utils.SeaRecall.RecallPlayer.getName().getString()+"尝试回想其在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                    append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                    Utils.SeaRecall.RecallPlayer.teleportTo(level1,100.5,50,0.5,0,0);
                    if (Utils.SeaRecallElderGuardian != null) Utils.SeaRecallElderGuardian.remove(Entity.RemovalReason.KILLED);
                    Utils.SeaRecall.Reset();
                }
            }
        }
    }
}
