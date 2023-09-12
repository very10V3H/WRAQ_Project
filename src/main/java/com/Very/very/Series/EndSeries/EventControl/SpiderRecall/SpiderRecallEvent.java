package com.Very.very.Series.EndSeries.EventControl.SpiderRecall;

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
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber
public class SpiderRecallEvent {
    @SubscribeEvent
    public static void Kaze(TickEvent.LevelTickEvent event) {
        if (event.side.isServer()) {
            Random random = new Random();
            Level level = event.level;
            ServerLevel level1 = event.level.getServer().getLevel(Level.END);
            ServerLevel overWorld = event.level.getServer().getLevel(Level.OVERWORLD);
            if (level.equals(level1) && event.phase.equals(TickEvent.Phase.START)) {
                String ZoneName = "微光森林";
                Style style = Utils.styleOfSpider;
                double ZoneX = -167;
                double ZoneY = 124;
                double ZoneZ = 1241;
                double PlatformX = 0;
                double PlatformY = 158;
                double PlatformZ = -40;
                if (Utils.SpiderRecall.RecallCount != -1) {
                    if (Utils.SpiderRecall.KillCount == -1 && Utils.SpiderRecall.RecallCount != 400 && Utils.SpiderRecall.RecallCount != 380) Utils.SpiderRecall.RecallCount--;
                    if (Utils.SpiderRecall.RecallCount == 600) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("开始"+ZoneName+"的回忆").withStyle(style));
                        Utils.SpiderRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Compute.FormatBroad(level,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                Component.literal(Utils.SpiderRecall.RecallPlayer.getName().getString()+"开始回忆其在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                        append(Component.literal("的经历。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                    }
                    if (Utils.SpiderRecall.RecallCount == 540) {
                        Utils.SpiderRecall.KillCount = 20;
                        Utils.SpiderRecall.RecallPlayer.teleportTo(overWorld,ZoneX,ZoneY,ZoneZ,0,0);
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("击杀20只微光蜘蛛").withStyle(style));
                        Utils.SpiderRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Utils.SpiderRecall.RecallCount--;
                    }
                    if (Utils.SpiderRecall.KillCount == 0) {
                        Utils.SpiderRecall.KillCount = -1;
                    }
                    if (Utils.SpiderRecall.RecallCount == 500) {
                        Utils.SpiderRecall.RecallPlayer.teleportTo(level1,PlatformX,PlatformY,PlatformZ,0,0);
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回想起什么了吗？").withStyle(style));
                        Utils.SpiderRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                    }
                    if (Utils.SpiderRecall.RecallCount == 400) {
                        if (Utils.SpiderRecallSpider == null || !Utils.SpiderRecallSpider.isAlive()) {
                            if(Utils.SpiderRecallSpider != null) Utils.SpiderRecallSpider.remove(Entity.RemovalReason.KILLED);
                            Utils.SpiderRecallSpider = new Spider(EntityType.SPIDER,level1);
                            Compute.SetMobCustomName(Utils.SpiderRecallSpider,Moditems.ArmorSpiderRecall.get(),Component.literal("模糊记忆中的微光蜘蛛").withStyle(style));
                            Utils.SpiderRecallSpider.setItemSlot(EquipmentSlot.HEAD , Moditems.ArmorSpiderRecall.get().getDefaultInstance());
                            Utils.SpiderRecallSpider.setItemSlot(EquipmentSlot.CHEST , Moditems.ArmorKazeChest.get().getDefaultInstance());
                            Utils.SpiderRecallSpider.setItemSlot(EquipmentSlot.LEGS , Moditems.ArmorKazeLeggings.get().getDefaultInstance());
                            Utils.SpiderRecallSpider.setItemSlot(EquipmentSlot.FEET , Moditems.ArmorKazeBoots.get().getDefaultInstance());
                            Utils.SpiderRecallSpider.setItemSlot(EquipmentSlot.MAINHAND , Moditems.KazeSword3.get().getDefaultInstance());
                            Utils.SpiderRecallSpider.getAttribute(Attributes.MAX_HEALTH).setBaseValue(115200.0D);
                            Utils.SpiderRecallSpider.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(200.0D);
                            Utils.SpiderRecallSpider.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5D);
                            Utils.SpiderRecallSpider.setHealth(Utils.SpiderRecallSpider.getMaxHealth());
                            Utils.SpiderRecallSpider.moveTo(PlatformX,PlatformY,PlatformZ);
                            level1.addFreshEntity(Utils.SpiderRecallSpider);
                            Utils.SpiderRecall.RecallCount--;
                        }
                    }
                    if (Utils.SpiderRecall.RecallCount == 380) {
                        if (Utils.SpiderRecall.RecallSuccess) {
                            Utils.SpiderRecall.RecallCount --;
                        }
                    }
                    if (Utils.SpiderRecall.RecallCount == 340) {
                        if (Utils.SpiderRecall.RecallSuccess) {
                            CompoundTag data = Utils.SpiderRecall.RecallPlayer.getPersistentData();
                            String RecallTimes = "SpiderRecallTimes";
                            String RecallSuccessTimes = "SpiderRecall.RecallSuccessTimes";
                            int RecallTime = data.getInt(RecallTimes);
                            int RecallSuccessTime = data.getInt(RecallSuccessTimes);
                            if (random.nextInt(100) < 5 + RecallTime * 5 + Math.min(RecallSuccessTime,20)) {
                                data.putInt(RecallTimes,0);
                                data.putInt(RecallSuccessTimes,data.getInt(RecallSuccessTimes) + 1);
                                Utils.SpiderRecall.RecallPlayer.teleportTo(overWorld,ZoneX,ZoneY,ZoneZ,0,0);
                                Compute.FormatBroad(level,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                        Component.literal(Utils.SpiderRecall.RecallPlayer.getName().getString()+"尝试回想在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
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
                                Compute.FormatMSGSend(Utils.SpiderRecall.RecallPlayer,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                        Component.literal("这是你第").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(""+data.getInt(RecallSuccessTimes)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd)).
                                                append(Component.literal("回想起在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                                ItemStack itemStack = Moditems.SpiderRecallSoul.get().getDefaultInstance();
                                itemStack.getOrCreateTagElement(Utils.MOD_ID);
                                itemStack.setCount(1);
                                Compute.ItemStackGive(Utils.SpiderRecall.RecallPlayer,itemStack);
                            }
                            else {
                                data.putInt(RecallTimes,data.getInt(RecallTimes) + 1);
                                Utils.SpiderRecall.RecallPlayer.teleportTo(level1,100.5,50,0.5,0,0);
                                Compute.FormatBroad(level,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                        Component.literal(Utils.SpiderRecall.RecallPlayer.getName().getString()+"尝试回想在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                                append(Component.literal("的记忆。似乎就快想起来了。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                                Compute.FormatMSGSend(Utils.SpiderRecall.RecallPlayer,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                        Component.literal("这是你第").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(""+data.getInt(RecallTimes)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd)).
                                                append(Component.literal("尝试回想起在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                                ItemStack itemStack = Moditems.SpiderRune.get().getDefaultInstance();
                                itemStack.getOrCreateTagElement(Utils.MOD_ID);
                                itemStack.setCount(1);
                                Compute.ItemStackGive(Utils.SpiderRecall.RecallPlayer,itemStack);
                            }
                        }
                    }
                    if (Utils.SpiderRecall.RecallCount == 260) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                        Utils.SpiderRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Utils.SpiderRecall.Reset();
                        for (int i = 0; i < 9; i++){
                            if (Utils.SpiderNetBlockPos[i] != null) {
                                if (level1.getBlockState(Utils.SpiderNetBlockPos[i]).getBlock().equals(Blocks.COBWEB)) level1.destroyBlock(Utils.SpiderNetBlockPos[i],false);
                            }
                        }
                    }
                }

                if (Utils.SpiderRecallSpider != null && Utils.SpiderRecallSpider.isAlive()) { // AI
                    List<Player> list = level1.getEntitiesOfClass(Player.class, AABB.ofSize(Utils.SpiderRecallSpider.getPosition(1.0f),20,20,20));
                    for (Player player : list) {
                        if (player.getPosition(1.0f).distanceTo(Utils.SpiderRecallSpider.getPosition(1.0f)) <= 2.5) {
                            player.addEffect(new MobEffectInstance(MobEffects.POISON,100,32));
                            if(level1.getServer().getTickCount() % 20 == 0) player.setHealth(player.getHealth()-player.getHealth() * 0.1f);
                        }
                    }
                    if (level1.getServer().getTickCount() % 200 == 0) {
                        for (int i = 0; i < 9; i++){
                            if (Utils.SpiderNetBlockPos[i] != null) {
                                if (level1.getBlockState(Utils.SpiderNetBlockPos[i]).getBlock().equals(Blocks.COBWEB)) level1.destroyBlock(Utils.SpiderNetBlockPos[i],false);
                            }
                        }
                        int X = Utils.SpiderRecall.RecallPlayer.getBlockX();
                        int Y = Utils.SpiderRecall.RecallPlayer.getBlockY();
                        int Z = Utils.SpiderRecall.RecallPlayer.getBlockZ();
                        Utils.SpiderNetBlockPos[0] = new BlockPos(X,Y,Z);
                        Utils.SpiderNetBlockPos[1] = new BlockPos(X+1,Y,Z+1);
                        Utils.SpiderNetBlockPos[2] = new BlockPos(X+1,Y,Z-1);
                        Utils.SpiderNetBlockPos[3] = new BlockPos(X-1,Y,Z+1);
                        Utils.SpiderNetBlockPos[4] = new BlockPos(X-1,Y,Z-1);
                        Utils.SpiderNetBlockPos[5] = new BlockPos(X+1,Y,Z);
                        Utils.SpiderNetBlockPos[6] = new BlockPos(X,Y,Z+1);
                        Utils.SpiderNetBlockPos[7] = new BlockPos(X-1,Y,Z);
                        Utils.SpiderNetBlockPos[8] = new BlockPos(X,Y,Z-1);
                        for (int i = 0; i < 9; i++) {
                            if (level1.getBlockState(Utils.SpiderNetBlockPos[i]).getBlock().equals(Blocks.AIR)) level1.setBlockAndUpdate(Utils.SpiderNetBlockPos[i], Blocks.COBWEB.defaultBlockState());
                        }
                    }
                }

                if (Utils.SpiderRecall.RecallTimeLimit > 0) Utils.SpiderRecall.RecallTimeLimit--;
                else {
                    if (Utils.SpiderRecall.RecallTimeLimit == 0) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                        Utils.SpiderRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Compute.FormatBroad(level,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                Component.literal(Utils.SpiderRecall.RecallPlayer.getName().getString()+"尝试回想其在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                        append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                        Utils.SpiderRecall.RecallPlayer.teleportTo(level1,100.5,50,0.5,0,0);
                        if (Utils.SpiderRecallSpider != null) Utils.SpiderRecallSpider.remove(Entity.RemovalReason.KILLED);
                        Utils.SpiderRecall.Reset();
                        for (int i = 0; i < 9; i++){
                            if (Utils.SpiderNetBlockPos[i] != null) {
                                if (level1.getBlockState(Utils.SpiderNetBlockPos[i]).getBlock().equals(Blocks.COBWEB)) level1.destroyBlock(Utils.SpiderNetBlockPos[i],false);
                            }
                        }
                    }
                }
                if (Utils.SpiderRecall.RecallTimeLimit > 0 && Utils.SpiderRecall.RecallPlayer.isDeadOrDying()) {
                    ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                            new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                    Utils.SpiderRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                    Compute.FormatBroad(level,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                            Component.literal(Utils.SpiderRecall.RecallPlayer.getName().getString()+"尝试回想其在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                    append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                    Utils.SpiderRecall.RecallPlayer.teleportTo(level1,100.5,50,0.5,0,0);
                    if (Utils.SpiderRecallSpider != null) Utils.SpiderRecallSpider.remove(Entity.RemovalReason.KILLED);
                    Utils.SpiderRecall.Reset();
                    for (int i = 0; i < 9; i++){
                        if (Utils.SpiderNetBlockPos[i] != null) {
                            if (level1.getBlockState(Utils.SpiderNetBlockPos[i]).getBlock().equals(Blocks.COBWEB)) level1.destroyBlock(Utils.SpiderNetBlockPos[i],false);
                        }
                    }
                }
            }
        }
    }
}
