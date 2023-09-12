package com.Very.very.Series.EndSeries.EventControl.ForestRecall;

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
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber
public class ForestRecallEvent {
    @SubscribeEvent
    public static void Forest(TickEvent.LevelTickEvent event) {
        if (event.side.isServer()) {
            Random random = new Random();
            Level level = event.level;
            ServerLevel level1 = event.level.getServer().getLevel(Level.END);
            ServerLevel overWorld = event.level.getServer().getLevel(Level.OVERWORLD);
            if (level.equals(level1) && event.phase.equals(TickEvent.Phase.START)) {
                String ZoneName = "森林";
                Style style = Utils.styleOfForest;
                double ZoneX = 138;
                double ZoneY = 69;
                double ZoneZ = 967;
                double PlatformX = 0;
                double PlatformY = 158;
                double PlatformZ = -20;
                if (Utils.ForestRecall.RecallCount != -1) {
                    if (Utils.ForestRecall.KillCount == -1 && Utils.ForestRecall.RecallCount != 400 && Utils.ForestRecall.RecallCount != 380) Utils.ForestRecall.RecallCount--;
                    if (Utils.ForestRecall.RecallCount == 600) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("开始"+ZoneName+"的回忆").withStyle(style));
                        Utils.ForestRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Compute.FormatBroad(level,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                Component.literal(Utils.ForestRecall.RecallPlayer.getName().getString()+"开始回忆其在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                        append(Component.literal("的经历。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                    }
                    if (Utils.ForestRecall.RecallCount == 540) {
                        Utils.ForestRecall.KillCount = 40;
                        Utils.ForestRecall.RecallPlayer.teleportTo(overWorld,ZoneX,ZoneY,ZoneZ,0,0);
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("击杀40只森林骷髅/森林僵尸").withStyle(style));
                        Utils.ForestRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Utils.ForestRecall.RecallCount--;
                    }
                    if (Utils.ForestRecall.KillCount == 0) {
                        Utils.ForestRecall.KillCount = -1;
                    }
                    if (Utils.ForestRecall.RecallCount == 500) {
                        Utils.ForestRecall.RecallPlayer.teleportTo(level1,PlatformX,PlatformY,PlatformZ,0,0);
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回想起什么了吗？").withStyle(style));
                        Utils.ForestRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                    }
                    if (Utils.ForestRecall.RecallCount == 400) {
                        if (Utils.ForestRecallZombie == null || !Utils.ForestRecallZombie.isAlive()) {
                            if(Utils.ForestRecallZombie != null) Utils.ForestRecallZombie.remove(Entity.RemovalReason.KILLED);
                            Utils.ForestRecallZombie = new Zombie(EntityType.ZOMBIE,level1);
                            Compute.SetMobCustomName(Utils.ForestRecallZombie,Moditems.ArmorForestRecall.get(),Component.literal("模糊记忆中的森林僵尸").withStyle(style));
                            Utils.ForestRecallZombie.setItemSlot(EquipmentSlot.HEAD , Moditems.ArmorForestRecall.get().getDefaultInstance());
                            Utils.ForestRecallZombie.setItemSlot(EquipmentSlot.MAINHAND , Moditems.forestsword3.get().getDefaultInstance());
                            Utils.ForestRecallZombie.getAttribute(Attributes.MAX_HEALTH).setBaseValue(115200.0D);
                            Utils.ForestRecallZombie.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(200.0D);
                            Utils.ForestRecallZombie.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5D);
                            Utils.ForestRecallZombie.setHealth(Utils.ForestRecallZombie.getMaxHealth());
                            Utils.ForestRecallZombie.moveTo(PlatformX,PlatformY,PlatformZ);
                            level1.addFreshEntity(Utils.ForestRecallZombie);
                            Utils.ForestRecall.RecallCount--;
                        }
                        if (Utils.ForestRecallSkeleton == null || !Utils.ForestRecallSkeleton.isAlive()) {
                            if(Utils.ForestRecallSkeleton != null) Utils.ForestRecallSkeleton.remove(Entity.RemovalReason.KILLED);
                            Utils.ForestRecallSkeleton = new Skeleton(EntityType.SKELETON,level1);
                            Compute.SetMobCustomName(Utils.ForestRecallSkeleton,Moditems.ArmorForestRecall.get(),Component.literal("模糊记忆中的森林骷髅").withStyle(style));
                            Utils.ForestRecallSkeleton.setItemSlot(EquipmentSlot.HEAD , Moditems.ArmorForestRecall.get().getDefaultInstance());
                            Utils.ForestRecallSkeleton.setItemSlot(EquipmentSlot.MAINHAND , Items.BOW.getDefaultInstance());
                            Utils.ForestRecallSkeleton.getAttribute(Attributes.MAX_HEALTH).setBaseValue(115200.0D);
                            Utils.ForestRecallSkeleton.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(200.0D);
                            Utils.ForestRecallSkeleton.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5D);
                            Utils.ForestRecallSkeleton.setHealth(Utils.ForestRecallSkeleton.getMaxHealth());
                            Utils.ForestRecallSkeleton.moveTo(PlatformX,PlatformY,PlatformZ);
                            level1.addFreshEntity(Utils.ForestRecallSkeleton);
                            Utils.ForestRecall.RecallCount--;
                        }
                    }
                    if (Utils.ForestRecall.RecallCount == 380) {
                        if (Utils.ForestRecall.RecallSuccess) {
                            Utils.ForestRecall.RecallCount --;
                        }
                    }
                    if (Utils.ForestRecall.RecallCount == 340) {
                        if (Utils.ForestRecall.RecallSuccess) {
                            CompoundTag data = Utils.ForestRecall.RecallPlayer.getPersistentData();
                            String RecallTimes = "ForestRecallTimes";
                            String RecallSuccessTimes = "ForestRecall.RecallSuccessTimes";
                            int RecallTime = data.getInt(RecallTimes);
                            int RecallSuccessTime = data.getInt(RecallSuccessTimes);
                            if (random.nextInt(100) < 5 + RecallTime * 5 + Math.min(RecallSuccessTime,20)) {
                                data.putInt(RecallTimes,0);
                                data.putInt(RecallSuccessTimes,data.getInt(RecallSuccessTimes) + 1);
                                Utils.ForestRecall.RecallPlayer.teleportTo(overWorld,ZoneX,ZoneY,ZoneZ,0,0);
                                Compute.FormatBroad(level,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                        Component.literal(Utils.ForestRecall.RecallPlayer.getName().getString()+"尝试回想在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
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
                                Compute.FormatMSGSend(Utils.ForestRecall.RecallPlayer,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                        Component.literal("这是你第").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(""+data.getInt(RecallSuccessTimes)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd)).
                                                append(Component.literal("回想起在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                                ItemStack itemStack = Moditems.IntensifiedForestRecallSoul.get().getDefaultInstance();
                                itemStack.getOrCreateTagElement(Utils.MOD_ID);
                                itemStack.setCount(1);
                                Compute.ItemStackGive(Utils.ForestRecall.RecallPlayer,itemStack);
                            }
                            else {
                                data.putInt(RecallTimes,data.getInt(RecallTimes) + 1);
                                Utils.ForestRecall.RecallPlayer.teleportTo(level1,100.5,50,0.5,0,0);
                                Compute.FormatBroad(level,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                        Component.literal(Utils.ForestRecall.RecallPlayer.getName().getString()+"尝试回想在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                                append(Component.literal("的记忆。似乎就快想起来了。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                                Compute.FormatMSGSend(Utils.ForestRecall.RecallPlayer,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                        Component.literal("这是你第").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(""+data.getInt(RecallTimes)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd)).
                                                append(Component.literal("尝试回想起在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                                ItemStack itemStack = Moditems.ForestRune.get().getDefaultInstance();
                                itemStack.getOrCreateTagElement(Utils.MOD_ID);
                                itemStack.setCount(1);
                                Compute.ItemStackGive(Utils.ForestRecall.RecallPlayer,itemStack);
                            }
                        }
                    }
                    if (Utils.ForestRecall.RecallCount == 260) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                        Utils.ForestRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Utils.ForestRecall.Reset();
                        Utils.ForestRecallBossKillCount = 0;
                    }
                }

                if (Utils.ForestRecallZombie != null && Utils.ForestRecallZombie.isAlive()) { // AI

                }

                if (Utils.ForestRecall.RecallTimeLimit > 0) Utils.ForestRecall.RecallTimeLimit--;
                else {
                    if (Utils.ForestRecall.RecallTimeLimit == 0) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                        Utils.ForestRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Compute.FormatBroad(level,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                Component.literal(Utils.ForestRecall.RecallPlayer.getName().getString()+"尝试回想其在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                        append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                        Utils.ForestRecall.RecallPlayer.teleportTo(level1,100.5,50,0.5,0,0);
                        if (Utils.ForestRecallZombie != null) Utils.ForestRecallZombie.remove(Entity.RemovalReason.KILLED);
                        Utils.ForestRecall.Reset();
                        Utils.ForestRecallBossKillCount = 0;
                    }
                }
                if (Utils.ForestRecall.RecallTimeLimit > 0 && Utils.ForestRecall.RecallPlayer.isDeadOrDying()) {
                    ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                            new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                    Utils.ForestRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                    Compute.FormatBroad(level,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                            Component.literal(Utils.ForestRecall.RecallPlayer.getName().getString()+"尝试回想其在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                    append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                    Utils.ForestRecall.RecallPlayer.teleportTo(level1,100.5,50,0.5,0,0);
                    if (Utils.ForestRecallZombie != null) Utils.ForestRecallZombie.remove(Entity.RemovalReason.KILLED);
                    Utils.ForestRecall.Reset();
                    Utils.ForestRecallBossKillCount = 0;
                }
            }
        }
    }
}
