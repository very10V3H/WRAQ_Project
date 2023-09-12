package com.Very.very.Series.EndSeries.EventControl.SnowRecall;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.ValueAndTools.registry.Moditems;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber
public class SnowRecallEvent {
    @SubscribeEvent
    public static void Lightning(TickEvent.LevelTickEvent event) {
        if (event.side.isServer()) {
            Random random = new Random();
            Level level = event.level;
            ServerLevel level1 = event.level.getServer().getLevel(Level.END);
            ServerLevel overWorld = event.level.getServer().getLevel(Level.OVERWORLD);
            if (level.equals(level1) && event.phase.equals(TickEvent.Phase.START)) {
                String ZoneName = "冰川";
                Style style = Utils.styleOfSnow;
                double ZoneX = -76;
                double ZoneY = 198;
                double ZoneZ = 1368;
                double PlatformX = -20;
                double PlatformY = 158;
                double PlatformZ = -20;
                if (Utils.SnowRecall.RecallCount != -1) {
                    if (Utils.SnowRecall.KillCount == -1 && Utils.SnowRecall.RecallCount != 400 && Utils.SnowRecall.RecallCount != 380) Utils.SnowRecall.RecallCount--;
                    if (Utils.SnowRecall.RecallCount == 600) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("开始"+ZoneName+"的回忆").withStyle(style));
                        Utils.SnowRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Compute.FormatBroad(level,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                Component.literal(Utils.SnowRecall.RecallPlayer.getName().getString()+"开始回忆其在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                        append(Component.literal("的经历。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                    }
                    if (Utils.SnowRecall.RecallCount == 540) {
                        Utils.SnowRecall.KillCount = 20;
                        Utils.SnowRecall.RecallPlayer.teleportTo(overWorld,ZoneX,ZoneY,ZoneZ,0,0);
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("击杀20只冰川流浪者").withStyle(style));
                        Utils.SnowRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Utils.SnowRecall.RecallCount--;
                    }
                    if (Utils.SnowRecall.KillCount == 0) {
                        Utils.SnowRecall.KillCount = -1;
                    }
                    if (Utils.SnowRecall.RecallCount == 500) {
                        Utils.SnowRecall.RecallPlayer.teleportTo(level1,PlatformX,PlatformY,PlatformZ,0,0);
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回想起什么了吗？").withStyle(style));
                        Utils.SnowRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                    }
                    if (Utils.SnowRecall.RecallCount == 400) {
                        if (Utils.SnowRecallStray == null || !Utils.SnowRecallStray.isAlive()) {
                            if(Utils.SnowRecallStray != null) Utils.SnowRecallStray.remove(Entity.RemovalReason.KILLED);
                            Utils.SnowRecallStray = new Stray(EntityType.STRAY,level1);
                            Compute.SetMobCustomName(Utils.SnowRecallStray,Moditems.ArmorSnowRecall.get(),Component.literal("模糊记忆中的冰川流浪者").withStyle(style));
                            Utils.SnowRecallStray.setItemSlot(EquipmentSlot.HEAD , Moditems.ArmorSnowRecall.get().getDefaultInstance());
                            Utils.SnowRecallStray.setItemSlot(EquipmentSlot.CHEST , Moditems.ArmorLZChest.get().getDefaultInstance());
                            Utils.SnowRecallStray.setItemSlot(EquipmentSlot.LEGS , Moditems.ArmorLZLeggings.get().getDefaultInstance());
                            Utils.SnowRecallStray.setItemSlot(EquipmentSlot.FEET , Moditems.ArmorLZBoots.get().getDefaultInstance());
                            Utils.SnowRecallStray.setItemSlot(EquipmentSlot.MAINHAND , Items.BOW.getDefaultInstance());
                            Utils.SnowRecallStray.getAttribute(Attributes.MAX_HEALTH).setBaseValue(115200.0D);
                            Utils.SnowRecallStray.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(800.0D);
                            Utils.SnowRecallStray.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5D);
                            Utils.SnowRecallStray.setHealth(Utils.SnowRecallStray.getMaxHealth());
                            Utils.SnowRecallStray.moveTo(PlatformX,PlatformY,PlatformZ);
                            Utils.SnowRecallStray.getPersistentData().putBoolean("Create",true);
                            level1.addFreshEntity(Utils.SnowRecallStray);
                            Utils.SnowRecall.RecallCount--;
                        }
                    }
                    if (Utils.SnowRecall.RecallCount == 380) {
                        if (Utils.SnowRecall.RecallSuccess) {
                            Utils.SnowRecall.RecallCount --;
                        }
                    }
                    if (Utils.SnowRecall.RecallCount == 340) {
                        if (Utils.SnowRecall.RecallSuccess) {
                            CompoundTag data = Utils.SnowRecall.RecallPlayer.getPersistentData();
                            String RecallTimes = "SnowRecallTimes";
                            String RecallSuccessTimes = "SnowRecall.RecallSuccessTimes";
                            int RecallTime = data.getInt(RecallTimes);
                            int RecallSuccessTime = data.getInt(RecallSuccessTimes);
                            if (random.nextInt(100) < 5 + RecallTime * 5 + Math.min(RecallSuccessTime,20)) {
                                data.putInt(RecallTimes,0);
                                data.putInt(RecallSuccessTimes,data.getInt(RecallSuccessTimes) + 1);
                                Utils.SnowRecall.RecallPlayer.teleportTo(overWorld,ZoneX,ZoneY,ZoneZ,0,0);
                                Compute.FormatBroad(level,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                        Component.literal(Utils.SnowRecall.RecallPlayer.getName().getString()+"尝试回想在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
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
                                Compute.FormatMSGSend(Utils.SnowRecall.RecallPlayer,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                        Component.literal("这是你第").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(""+data.getInt(RecallSuccessTimes)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd)).
                                                append(Component.literal("回想起在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                                ItemStack itemStack = Moditems.SnowRecallSoul.get().getDefaultInstance();
                                itemStack.getOrCreateTagElement(Utils.MOD_ID);
                                itemStack.setCount(1);
                                Compute.ItemStackGive(Utils.SnowRecall.RecallPlayer,itemStack);
                            }
                            else {
                                data.putInt(RecallTimes,data.getInt(RecallTimes) + 1);
                                Utils.SnowRecall.RecallPlayer.teleportTo(level1,100.5,50,0.5,0,0);
                                Compute.FormatBroad(level,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                        Component.literal(Utils.SnowRecall.RecallPlayer.getName().getString()+"尝试回想在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                                append(Component.literal("的记忆。似乎就快想起来了。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                                Compute.FormatMSGSend(Utils.SnowRecall.RecallPlayer,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                        Component.literal("这是你第").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(""+data.getInt(RecallTimes)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd)).
                                                append(Component.literal("尝试回想起在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                                ItemStack itemStack = Moditems.SnowRune.get().getDefaultInstance();
                                itemStack.getOrCreateTagElement(Utils.MOD_ID);
                                itemStack.setCount(1);
                                Compute.ItemStackGive(Utils.SnowRecall.RecallPlayer,itemStack);
                            }
                        }
                    }
                    if (Utils.SnowRecall.RecallCount == 260) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                        Utils.SnowRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Utils.SnowRecall.Reset();
                        int X = -20;
                        int Y = 158;
                        int Z = -11;
                        for (int i = 0; i < 19; i++) {
                            for (int j = 0; j <= Math.min(i,18 - i); j++) {
                                BlockPos blockPos = new BlockPos(X - j, Y, Z - i);
                                BlockPos blockPos1 = new BlockPos(X + j, Y, Z - i);
                                level.setBlockAndUpdate(blockPos,Blocks.AIR.defaultBlockState());
                                level.setBlockAndUpdate(blockPos1,Blocks.AIR.defaultBlockState());
                            }
                        }
                    }
                }
                if (Utils.SnowRecallStray != null && Utils.SnowRecallStray.isAlive()) {
                    if (Utils.SnowRecallStray.getPersistentData().getBoolean("Create") && Utils.SnowRecallStray.getHealth() <= Utils.SnowRecallStray.getMaxHealth() * 0.5f) {
                        int X = -20;
                        int Y = 158;
                        int Z = -11;
                        for (int i = 0; i < 19; i++) {
                            for (int j = 0; j <= Math.min(i,18 - i); j++) {
                                BlockPos blockPos = new BlockPos(X - j, Y, Z - i);
                                BlockPos blockPos1 = new BlockPos(X + j, Y, Z - i);
                                level.setBlockAndUpdate(blockPos,Blocks.POWDER_SNOW.defaultBlockState());
                                level.setBlockAndUpdate(blockPos1,Blocks.POWDER_SNOW.defaultBlockState());
                            }
                        }
                        Utils.SnowRecallStray.getPersistentData().putBoolean("Create",false);
                    }
                    if (level1.getServer().getTickCount() % 200 == 0) {
                        Utils.SnowRecall.RecallPlayer.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,60,4));
                        int X = Utils.SnowRecall.RecallPlayer.getBlockX();
                        int Y = Utils.SnowRecall.RecallPlayer.getBlockY() + 1;
                        int Z = Utils.SnowRecall.RecallPlayer.getBlockZ();
                        BlockPos blockPos = new BlockPos(X,Y,Z);
                        if (level1.getBlockState(blockPos).getBlock().equals(Blocks.AIR)) {
                            level1.setBlockAndUpdate(blockPos,Blocks.ICE.defaultBlockState());
                            level1.destroyBlock(blockPos,false);
                        }
                    }
                }
                if (Utils.SnowRecall.RecallTimeLimit > 0) Utils.SnowRecall.RecallTimeLimit--;
                else {
                    if (Utils.SnowRecall.RecallTimeLimit == 0) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                        Utils.SnowRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Compute.FormatBroad(level,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                Component.literal(Utils.SnowRecall.RecallPlayer.getName().getString()+"尝试回想其在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                        append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                        Utils.SnowRecall.RecallPlayer.teleportTo(level1,100.5,50,0.5,0,0);
                        if (Utils.SnowRecallStray != null) Utils.SnowRecallStray.remove(Entity.RemovalReason.KILLED);
                        Utils.SnowRecall.Reset();
                        int X = -20;
                        int Y = 158;
                        int Z = -11;
                        for (int i = 0; i < 19; i++) {
                            for (int j = 0; j <= Math.min(i,18 - i); j++) {
                                BlockPos blockPos = new BlockPos(X - j, Y, Z - i);
                                BlockPos blockPos1 = new BlockPos(X + j, Y, Z - i);
                                level.setBlockAndUpdate(blockPos,Blocks.AIR.defaultBlockState());
                                level.setBlockAndUpdate(blockPos1,Blocks.AIR.defaultBlockState());
                            }
                        }
                    }
                }
                if (Utils.SnowRecall.RecallTimeLimit > 0 && Utils.SnowRecall.RecallPlayer.isDeadOrDying()) {
                    ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                            new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                    Utils.SnowRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                    Compute.FormatBroad(level,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                            Component.literal(Utils.SnowRecall.RecallPlayer.getName().getString()+"尝试回想其在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                    append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                    Utils.SnowRecall.RecallPlayer.teleportTo(level1,100.5,50,0.5,0,0);
                    if (Utils.SnowRecallStray != null) Utils.SnowRecallStray.remove(Entity.RemovalReason.KILLED);
                    Utils.SnowRecall.Reset();
                    int X = -20;
                    int Y = 158;
                    int Z = -11;
                    for (int i = 0; i < 19; i++) {
                        for (int j = 0; j <= Math.min(i,18 - i); j++) {
                            BlockPos blockPos = new BlockPos(X - j, Y, Z - i);
                            BlockPos blockPos1 = new BlockPos(X + j, Y, Z - i);
                            level.setBlockAndUpdate(blockPos,Blocks.AIR.defaultBlockState());
                            level.setBlockAndUpdate(blockPos1,Blocks.AIR.defaultBlockState());
                        }
                    }
                }
            }
        }
    }
}
