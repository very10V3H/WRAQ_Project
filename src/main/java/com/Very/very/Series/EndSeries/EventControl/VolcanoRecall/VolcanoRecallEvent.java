package com.Very.very.Series.EndSeries.EventControl.VolcanoRecall;

import com.Very.very.ValueAndTools.Compute;
import com.Very.very.ValueAndTools.Utils.Utils;
import com.Very.very.Render.Particles.ModParticles;
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
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber
public class VolcanoRecallEvent {
    @SubscribeEvent
    public static void Volcano(TickEvent.LevelTickEvent event) {
        if (event.side.isServer()) {
            Random random = new Random();
            Level level = event.level;
            ServerLevel level1 = event.level.getServer().getLevel(Level.END);
            ServerLevel overWorld = event.level.getServer().getLevel(Level.OVERWORLD);
            if (level.equals(level1) && event.phase.equals(TickEvent.Phase.START)) {
                String ZoneName = "火山";
                Style style = Utils.styleOfVolcano;
                double ZoneX = 56;
                double ZoneY = -20;
                double ZoneZ = 1092;
                double PlatformX = 0;
                double PlatformY = 158;
                double PlatformZ = 20;
                if (Utils.VolcanoRecall.RecallCount != -1) {
                    if (Utils.VolcanoRecall.KillCount == -1 && Utils.VolcanoRecall.RecallCount != 400 && Utils.VolcanoRecall.RecallCount != 380) Utils.VolcanoRecall.RecallCount--;
                    if (Utils.VolcanoRecall.RecallCount == 600) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("开始"+ZoneName+"的回忆").withStyle(style));
                        Utils.VolcanoRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Compute.FormatBroad(level,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                Component.literal(Utils.VolcanoRecall.RecallPlayer.getName().getString()+"开始回忆其在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                        append(Component.literal("的经历。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                    }
                    if (Utils.VolcanoRecall.RecallCount == 540) {
                        Utils.VolcanoRecall.KillCount = 20;
                        Utils.VolcanoRecall.RecallPlayer.teleportTo(overWorld,ZoneX,ZoneY,ZoneZ,0,0);
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("击杀20只火山烈焰").withStyle(style));
                        Utils.VolcanoRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Utils.VolcanoRecall.RecallCount--;
                    }
                    if (Utils.VolcanoRecall.KillCount == 0) {
                        Utils.VolcanoRecall.KillCount = -1;
                    }
                    if (Utils.VolcanoRecall.RecallCount == 500) {
                        Utils.VolcanoRecall.RecallPlayer.teleportTo(level1,PlatformX,PlatformY,PlatformZ,0,0);
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回想起什么了吗？").withStyle(style));
                        Utils.VolcanoRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                    }
                    if (Utils.VolcanoRecall.RecallCount == 400) {
                        if (Utils.VolcanoRecallBlaze == null || !Utils.VolcanoRecallBlaze.isAlive()) {
                            if(Utils.VolcanoRecallBlaze != null) Utils.VolcanoRecallBlaze.remove(Entity.RemovalReason.KILLED);
                            Utils.VolcanoRecallBlaze = new Blaze(EntityType.BLAZE,level1);
                            Compute.SetMobCustomName(Utils.VolcanoRecallBlaze,Moditems.ArmorVolcanoRecall.get(),Component.literal("模糊记忆中的火山熔岩").withStyle(style));
                            Utils.VolcanoRecallBlaze.setItemSlot(EquipmentSlot.HEAD , Moditems.ArmorVolcanoRecall.get().getDefaultInstance());
                            Utils.VolcanoRecallBlaze.setItemSlot(EquipmentSlot.MAINHAND , Moditems.forestsword3.get().getDefaultInstance());
                            Utils.VolcanoRecallBlaze.getAttribute(Attributes.MAX_HEALTH).setBaseValue(115200.0D);
                            Utils.VolcanoRecallBlaze.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(200.0D);
                            Utils.VolcanoRecallBlaze.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.5D);
                            Utils.VolcanoRecallBlaze.setHealth(Utils.VolcanoRecallBlaze.getMaxHealth());
                            Utils.VolcanoRecallBlaze.moveTo(PlatformX,PlatformY,PlatformZ);
                            level1.addFreshEntity(Utils.VolcanoRecallBlaze);
                            Utils.VolcanoRecall.RecallCount--;
                        }
                    }
                    if (Utils.VolcanoRecall.RecallCount == 380) {
                        if (Utils.VolcanoRecall.RecallSuccess) {
                            Utils.VolcanoRecall.RecallCount --;
                        }
                    }
                    if (Utils.VolcanoRecall.RecallCount == 340) {
                        if (Utils.VolcanoRecall.RecallSuccess) {
                            CompoundTag data = Utils.VolcanoRecall.RecallPlayer.getPersistentData();
                            String RecallTimes = "VolcanoRecallTimes";
                            String RecallSuccessTimes = "VolcanoRecall.RecallSuccessTimes";
                            int RecallTime = data.getInt(RecallTimes);
                            int RecallSuccessTime = data.getInt(RecallSuccessTimes);
                            if (random.nextInt(100) < 5 + RecallTime * 5 + Math.min(RecallSuccessTime,20)) {
                                data.putInt(RecallTimes,0);
                                data.putInt(RecallSuccessTimes,data.getInt(RecallSuccessTimes) + 1);
                                Utils.VolcanoRecall.RecallPlayer.teleportTo(overWorld,ZoneX,ZoneY,ZoneZ,0,0);
                                Compute.FormatBroad(level,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                        Component.literal(Utils.VolcanoRecall.RecallPlayer.getName().getString()+"尝试回想在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
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
                                Compute.FormatMSGSend(Utils.VolcanoRecall.RecallPlayer,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                        Component.literal("这是你第").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(""+data.getInt(RecallSuccessTimes)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd)).
                                                append(Component.literal("回想起在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                                ItemStack itemStack = Moditems.IntensifiedVolcanoRecallSoul.get().getDefaultInstance();
                                itemStack.getOrCreateTagElement(Utils.MOD_ID);
                                itemStack.setCount(1);
                                Compute.ItemStackGive(Utils.VolcanoRecall.RecallPlayer,itemStack);
                            }
                            else {
                                data.putInt(RecallTimes,data.getInt(RecallTimes) + 1);
                                Utils.VolcanoRecall.RecallPlayer.teleportTo(level1,100.5,50,0.5,0,0);
                                Compute.FormatBroad(level,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                        Component.literal(Utils.VolcanoRecall.RecallPlayer.getName().getString()+"尝试回想在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                                append(Component.literal("的记忆。似乎就快想起来了。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                                Compute.FormatMSGSend(Utils.VolcanoRecall.RecallPlayer,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                        Component.literal("这是你第").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                                append(Component.literal(""+data.getInt(RecallTimes)).withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd)).
                                                append(Component.literal("尝试回想起在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)).
                                                append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                                append(Component.literal("的记忆。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                                ItemStack itemStack = Moditems.VolcanoRune.get().getDefaultInstance();
                                itemStack.getOrCreateTagElement(Utils.MOD_ID);
                                itemStack.setCount(1);
                                Compute.ItemStackGive(Utils.VolcanoRecall.RecallPlayer,itemStack);
                            }
                        }
                    }
                    if (Utils.VolcanoRecall.RecallCount == 260) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                        Utils.VolcanoRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Utils.VolcanoRecall.Reset();
                    }
                }

                if (Utils.VolcanoRecallBlaze != null && Utils.VolcanoRecallBlaze.isAlive()) { // AI
                    if (level1.getServer().getTickCount() % 20 == 0) {
                        List<Player> playerList = level1.getEntitiesOfClass(Player.class, AABB.ofSize(Utils.VolcanoRecallBlaze.getPosition(1.0f),20,20,20));
                        for (Player player : playerList) {
                            if (player.getPosition(1.0f).distanceTo(Utils.VolcanoRecallBlaze.getPosition(1.0f)) <= 10) {
                                player.setHealth(player.getHealth() - 100);
                                Compute.LineParticle(level1,20,Utils.VolcanoRecallBlaze.getPosition(1.0f),player.getPosition(1.0f), ModParticles.LONG_VOLCANO.get());
                            }
                        }
                    }
                }

                if (Utils.VolcanoRecall.RecallTimeLimit > 0) Utils.VolcanoRecall.RecallTimeLimit--;
                else {
                    if (Utils.VolcanoRecall.RecallTimeLimit == 0) {
                        ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                                new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                        Utils.VolcanoRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                        Compute.FormatBroad(level,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                                Component.literal(Utils.VolcanoRecall.RecallPlayer.getName().getString()+"尝试回想其在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                        append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                        append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                        Utils.VolcanoRecall.RecallPlayer.teleportTo(level1,100.5,50,0.5,0,0);
                        if (Utils.VolcanoRecallBlaze != null) Utils.VolcanoRecallBlaze.remove(Entity.RemovalReason.KILLED);
                        Utils.VolcanoRecall.Reset();
                    }
                }
                if (Utils.VolcanoRecall.RecallTimeLimit > 0 && Utils.VolcanoRecall.RecallPlayer.isDeadOrDying()) {
                    ClientboundSetTitleTextPacket clientboundSetTitleTextPacket =
                            new ClientboundSetTitleTextPacket(Component.literal("回忆到这里结束了。").withStyle(style));
                    Utils.VolcanoRecall.RecallPlayer.connection.send(clientboundSetTitleTextPacket);
                    Compute.FormatBroad(level,Component.literal("回忆").withStyle(ChatFormatting.RESET).withStyle(Utils.styleOfEnd),
                            Component.literal(Utils.VolcanoRecall.RecallPlayer.getName().getString()+"尝试回想其在").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE).
                                    append(Component.literal(ZoneName).withStyle(ChatFormatting.RESET).withStyle(style)).
                                    append(Component.literal("的经历，但是似乎什么都没想起来。").withStyle(ChatFormatting.RESET).withStyle(ChatFormatting.WHITE)));
                    Utils.VolcanoRecall.RecallPlayer.teleportTo(level1,100.5,50,0.5,0,0);
                    if (Utils.VolcanoRecallBlaze != null) Utils.VolcanoRecallBlaze.remove(Entity.RemovalReason.KILLED);
                    Utils.VolcanoRecall.Reset();
                }
            }
        }
    }
}
